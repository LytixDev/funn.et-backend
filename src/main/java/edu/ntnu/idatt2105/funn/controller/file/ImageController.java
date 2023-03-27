package edu.ntnu.idatt2105.funn.controller.file;

import edu.ntnu.idatt2105.funn.dto.file.ImageResponseDTO;
import edu.ntnu.idatt2105.funn.exceptions.DatabaseException;
import edu.ntnu.idatt2105.funn.exceptions.PermissionDeniedException;
import edu.ntnu.idatt2105.funn.exceptions.file.FileNotFoundException;
import edu.ntnu.idatt2105.funn.model.file.Image;
import edu.ntnu.idatt2105.funn.model.user.Role;
import edu.ntnu.idatt2105.funn.security.Auth;
import edu.ntnu.idatt2105.funn.service.file.ImageService;
import edu.ntnu.idatt2105.funn.service.file.ImageStorageService;
import edu.ntnu.idatt2105.funn.service.listing.ListingService;
import edu.ntnu.idatt2105.funn.validation.AuthValidation;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.zalando.fauxpas.FauxPas;

/**
 * Controller for image endpoints.
 * @author Callum G.
 * @version 1.2 - 27.03.2023
 */
@RestController
@RequestMapping(value = "/api/v1")
@EnableAutoConfiguration
@RequiredArgsConstructor
public class ImageController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

  private final ImageService imageService;

  private final ImageStorageService imageStorageService;

  private final ListingService listingService;

  /**
   * Gets a specific image from the server.
   * @param id The id of the image to get.
   * @return The image as a resource.
   * @throws IOException If the image could not be read.
   * @throws MalformedURLException If the image could not be found.
   */
  @GetMapping("/public/images/{id}")
  @Operation(
    summary = "Returns an image from the server.",
    description = "Returns an image from the server as a resource to be downloaded."
  )
  public ResponseEntity<Resource> getImage(@PathVariable Long id)
    throws IOException, MalformedURLException {
    LOGGER.info("Image download request received for id {}", id);

    Resource resource = imageStorageService.loadFile(id);

    LOGGER.info("Image download successful");

    return ResponseEntity
      .ok()
      .header(
        HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + resource.getFilename() + "\""
      )
      .body(resource);
  }

  /**
   * Uploads images to the server.
   * @param images The images to upload.
   * @param alts The alt text for the images.
   * @param auth the authentication object of the user that is uploading the images.
   * @return A list of image response DTOs.
   * @throws IOException If the images could not be read.
   * @throws DatabaseException If the images could not be saved.
   */
  @PostMapping("/private/images")
  @Operation(
    summary = "Uploads images to the server",
    description = "Uploads images to the server and returns a list of image response DTOs."
  )
  public ResponseEntity<List<ImageResponseDTO>> uploadImages(
    @RequestParam("images") MultipartFile[] images,
    @RequestParam("alts") String[] alts,
    @AuthenticationPrincipal Auth auth
  ) throws IOException, DatabaseException, PermissionDeniedException {
    if (!AuthValidation.hasRole(auth, Role.ADMIN)) throw new PermissionDeniedException(
      "Access denied"
    );

    List<ImageResponseDTO> dtos = new ArrayList<>();
    Map<MultipartFile, String> imageAltMap = IntStream
      .range(0, images.length)
      .boxed()
      .collect(Collectors.toMap(i -> images[i], i -> alts[i]));

    // Allows for throwing checked exceptions in a lambda.
    Function<Image, Image> saveImage = FauxPas.throwingFunction(imageService::saveFile);

    // Allows for throwing checked exceptions in a lambda.
    BiConsumer<MultipartFile, Long> storeImage = FauxPas.throwingBiConsumer((image, id) -> {
      imageStorageService.init();
      imageStorageService.store(image, id);
    });

    imageAltMap.forEach((image, alt) -> {
      LOGGER.info("Image upload request received");

      ImageResponseDTO dto = new ImageResponseDTO();

      Image imageFile = new Image();

      imageFile.setAlt(alt);

      LOGGER.info("Saving image file");

      saveImage.apply(imageFile);

      LOGGER.info("Storing image file");

      storeImage.accept(image, imageFile.getId());

      LOGGER.info("Image upload successful");

      dto.setId(imageFile.getId());

      dto.setAlt(imageFile.getAlt());

      dto.setUrl(
        MvcUriComponentsBuilder
          .fromMethodName(ImageController.class, "getImage", dto.getId())
          .build()
          .toString()
      );

      dtos.add(dto);
    });

    LOGGER.info("Image upload resonses {}", dtos);

    return ResponseEntity.status(HttpStatus.CREATED).body(dtos);
  }

  /**
   * Deletes an image from the server.
   * @param id The id of the image to delete.
   * @param auth the authentication object of the user that is deleting the image.
   * @return 204 No Content with no body.
   * @throws FileNotFoundException If the image could not be found.
   * @throws IOException If the image could not be read.
   * @throws DatabaseException If the image could not be deleted.
   * @throws PermissionDeniedException If the user does not have permission to delete the image.
   */
  @DeleteMapping("/private/images/{id}")
  @Operation(
    summary = "Deletes an image from the server",
    description = "Deletes an image from the server and returns a response entity."
  )
  public ResponseEntity<Void> deleteImage(
    @PathVariable Long id,
    @AuthenticationPrincipal Auth auth
  ) throws FileNotFoundException, IOException, DatabaseException, PermissionDeniedException {
    LOGGER.info("Image delete request received for id {}", id);

    Image tmp = imageService.getFile(id);

    if (
      !AuthValidation.hasRoleOrIsUser(
        auth,
        Role.ADMIN,
        listingService.getListing(tmp.getListingId()).getUser().getUsername()
      )
    ) throw new PermissionDeniedException("You do not have access to this resource");

    imageService.deleteFile(id);

    LOGGER.info("Image deleted from database");

    imageStorageService.deleteFile(id);

    LOGGER.info("Image deleted from storage");

    return ResponseEntity.noContent().build();
  }
}
