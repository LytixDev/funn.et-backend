package edu.ntnu.idatt2105.funn.controller.listing;

import edu.ntnu.idatt2105.funn.controller.file.ImageController;
import edu.ntnu.idatt2105.funn.dto.file.ImageResponseDTO;
import edu.ntnu.idatt2105.funn.dto.listing.ListingCreateDTO;
import edu.ntnu.idatt2105.funn.dto.listing.ListingDTO;
import edu.ntnu.idatt2105.funn.exceptions.DatabaseException;
import edu.ntnu.idatt2105.funn.exceptions.listing.ListingAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.listing.ListingNotFoundException;
import edu.ntnu.idatt2105.funn.exceptions.location.LocationDoesntExistException;
import edu.ntnu.idatt2105.funn.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.funn.filtering.SearchRequest;
import edu.ntnu.idatt2105.funn.mapper.listing.ListingMapper;
import edu.ntnu.idatt2105.funn.model.file.Image;
import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.service.file.ImageService;
import edu.ntnu.idatt2105.funn.service.file.ImageStorageService;
import edu.ntnu.idatt2105.funn.service.listing.ListingService;
import edu.ntnu.idatt2105.funn.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

/**
 * Controller for listings
 * Mappings for getting all, getting one,
 * creating, updating and deleting listings.
 * @author Nicolai H. B., Carl G., Callum G.
 * @version 1.3 - 25.3.2023
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ListingController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ListingController.class);

  @Autowired
  private ListingMapper listingMapper;

  private final ListingService listingService;

  private final ImageService imageService;

  private final ImageStorageService imageStorageService;

  private final UserService userService;

  /**
   * Returns all listings in the database.
   * Possible to search for keywords in listing
   * @param search The search query for listing
   * @return A list of all listings in the database.
   */
  @PostMapping(value = "/public/listings", produces = { MediaType.APPLICATION_JSON_VALUE })
  @Operation(
    summary = "Get listings by search and filter",
    description = "Returns all listings in the database. Possible to search for keywords in listing"
  )
  public ResponseEntity<List<ListingDTO>> getListingsByFilter(@RequestBody SearchRequest search)
    throws NullPointerException {
    LOGGER.info("Received request to get all listings");
    Page<Listing> listings = listingService.searchListingsPaginated(search);
    LOGGER.info("Found {} listings", listings.getContent().size());

    List<ListingDTO> listingDTOs = listings
      .stream()
      .map(l -> listingMapper.listingToListingDTO(l))
      .toList();

    LOGGER.info("Mapped listings to DTOs and returning");
    return ResponseEntity.ok(listingDTOs);
  }

  /**
   * Returns a listing with the given id.
   * @param id The id of the listing to return.
   * @return The listing with the given id.
   */
  @GetMapping(value = "/public/listings/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<ListingDTO> getListing(
    @PathVariable long id,
    @AuthenticationPrincipal String username
  ) throws UserDoesNotExistsException {
    LOGGER.info("Received user: {}", username);
    LOGGER.info("Received request to get listing with id: {}", id);
    Listing foundListing = listingService.getListing(id);
    LOGGER.info("Found listing {}", foundListing);
    ListingDTO listingDTO = listingMapper.listingToListingDTO(foundListing);
    LOGGER.info("Mapped listing to DTO and checking if possible user has favorited it");
    listingDTO.setIsFavorite(Optional.empty());
    if (username != null && !username.equals("anonymousUser")) {
      LOGGER.info("User is not anonymous, checking if user has favorited listing");
      boolean listingIsFavorite = userService.isFavoriteByUser(username, foundListing);
      LOGGER.info("Listing is favorite: {}", listingIsFavorite);
      listingDTO.setIsFavorite(Optional.of(listingIsFavorite));
    }
    return ResponseEntity.ok(listingDTO);
  }

  /**
   * Uploads images to a listing.
   * @param listing The listing to upload images to.
   * @param images The images to upload.
   * @param imageAlts The alt text for the images.
   * @return The listing with the uploaded images.
   */
  private List<ImageResponseDTO> uploadImages(
    Long listingId,
    MultipartFile[] images,
    String[] imageAlts
  ) throws RuntimeException, IOException {
    List<ImageResponseDTO> dtos = new ArrayList<>();
    Map<MultipartFile, String> imageAltMap = IntStream
      .range(0, images.length)
      .boxed()
      .collect(Collectors.toMap(i -> images[i], i -> imageAlts[i]));

    imageAltMap.forEach((image, alt) -> {
      LOGGER.info("Image upload request received");

      ImageResponseDTO dto = new ImageResponseDTO();

      Image imageFile = new Image();

      imageFile.setAlt(alt);

      imageFile.setListingId(listingId);

      LOGGER.info("Saving image file");

      try {
        imageFile = imageService.saveFile(imageFile);
      } catch (DatabaseException e) {
        throw new RuntimeException(e);
      }

      LOGGER.info("Storing image file");

      try {
        imageStorageService.init();
        imageStorageService.store(image, imageFile.getId());
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }

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

    LOGGER.info("Image upload responses {}", dtos);

    return dtos;
  }

  /**
   * Creates a listing from a listing dto
   * @param listingDTO The listing dto to create a listing from
   * @return The listing created
   * @throws LocationDoesntExistException If the location does not exist
   * @throws DatabaseException If the database could not handle a sql request
   * @throws UserDoesNotExistsException If the user does not exist
   * @throws ListingAlreadyExistsException If the listing already exists
   * @throws IOException
   * @throws RuntimeException
   */
  @PostMapping(
    value = "/private/listings",
    consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
    produces = { MediaType.APPLICATION_JSON_VALUE }
  )
  @Operation(summary = "Create listing", description = "Creates a listing from a listing dto")
  public ResponseEntity<ListingDTO> createListing(@ModelAttribute ListingCreateDTO listingDTO)
    throws LocationDoesntExistException, DatabaseException, UserDoesNotExistsException, ListingAlreadyExistsException, RuntimeException, IOException {
    LOGGER.info("Recieved request to create listing: {}", listingDTO);
    Listing requestedListing = listingMapper.listingCreateDTOToListing(listingDTO);

    LOGGER.info("Mapped DTO to listing: {}", requestedListing);
    Listing createdListing = listingService.saveListing(requestedListing);

    LOGGER.info("Saved listing to database");
    ListingDTO createdListingDTO = listingMapper.listingToListingDTO(createdListing);

    if (listingDTO.getImages() != null) {
      if (listingDTO.getImageAlts() == null) {
        String[] imageAlts = new String[listingDTO.getImages().length];
        for (int i = 0; i < listingDTO.getImages().length; i++) {
          imageAlts[i] = "image";
        }
        listingDTO.setImageAlts(imageAlts);
      }

      createdListingDTO.setImageResponse(
        uploadImages(requestedListing.getId(), listingDTO.getImages(), listingDTO.getImageAlts())
      );
    }
    LOGGER.info("Mapped listing to DTO and returning");

    return ResponseEntity.ok(createdListingDTO);
  }

  /**
   * Updates a listing with the given id.
   * @param listingDTO the updated values for a listing
   * @return the listing that was updated
   * @throws LocationDoesntExistException if location is invalid and does not exist
   * @throws DatabaseException if an sql operation fails
   * @throws UserDoesNotExistsException if the requesting user does not exist
   */
  @PutMapping(value = "/private/listings/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<ListingDTO> updateListing(
    @ModelAttribute ListingCreateDTO listingDTO,
    @PathVariable long id
  )
    throws LocationDoesntExistException, DatabaseException, UserDoesNotExistsException, RuntimeException, IOException {
    LOGGER.info("Recieveed request to update listing: {}", listingDTO);

    Listing requestedListing = listingMapper.listingCreateDTOToListing(listingDTO);

    requestedListing.setId(id);
    requestedListing.setImages(imageService.getAllFilesByListingId(id));

    LOGGER.info("Mapped DTO to listing: {}", requestedListing);

    Listing updatedListing = listingService.updateListing(requestedListing);

    LOGGER.info("Saved updated listing to database");
    ListingDTO updatedListingDTO = listingMapper.listingToListingDTO(updatedListing);

    if (listingDTO.getImages() != null) {
      if (listingDTO.getImageAlts() == null) {
        String[] imageAlts = new String[listingDTO.getImages().length];
        for (int i = 0; i < listingDTO.getImages().length; i++) {
          imageAlts[i] = "image";
        }
        listingDTO.setImageAlts(imageAlts);
      }

      updatedListingDTO.setImageResponse(
        Stream
          .concat(
            updatedListingDTO.getImageResponse().stream(),
            uploadImages(updatedListing.getId(), listingDTO.getImages(), listingDTO.getImageAlts())
              .stream()
          )
          .collect(Collectors.toList())
      );
    }

    LOGGER.info("Saved images to database");

    LOGGER.info("Mapped listing to DTO and returning");
    return ResponseEntity.ok(updatedListingDTO);
  }

  /**
   * Favorites a listing with the given id.
   * If a listing is already favorited, it is unfavorited.
   * @param username the username of the user
   * @param id the id of the listing to favorite
   * @return nothing
   * @throws UserDoesNotExistsException if the user does not exist
   * @throws ListingNotFoundException if the listing does not exist
   */
  @PutMapping(value = "/private/listings/{id}/favorite")
  public ResponseEntity<ListingDTO> favoriteOrUnfavoriteListing(
    @AuthenticationPrincipal String username,
    @PathVariable long id
  ) throws UserDoesNotExistsException, ListingNotFoundException {
    LOGGER.info("Received request to favorite listing with id {} by user {}", username, id);
    Listing listing = listingService.getListing(id);
    LOGGER.info("Found listing to favorite: {}, by user {}", listing, username);
    userService.favoriteOrUnfavoriteListing(username, listing);
    ListingDTO listingDTO = listingMapper.listingToListingDTO(listing);

    boolean listingIsFavorite = userService.isFavoriteByUser(username, listing);
    LOGGER.info("Listing is favorite: {}", listingIsFavorite);
    listingDTO.setIsFavorite(Optional.of(listingIsFavorite));
    return ResponseEntity.ok(listingDTO);
  }

  /**
   * Gets all favorite listings for a user.
   * @param username the username of the user. Ano
   * @return a set of favorite listings by a user
   * @throws UserDoesNotExistsException
   */
  @GetMapping(value = "/private/listings/favorites")
  public ResponseEntity<Set<ListingDTO>> getFavoriteListings(
    @AuthenticationPrincipal String username
  ) throws UserDoesNotExistsException {
    LOGGER.info("Received request to get favorite listings by user {}", username);
    Set<Listing> favorites = userService.getFavoriteListings(username);
    Set<ListingDTO> listingDTOs = favorites
      .stream()
      .map(l -> listingMapper.listingToListingDTO(l))
      .collect(Collectors.toSet());
    return ResponseEntity.ok(listingDTOs);
  }

  /**
   * Deletes a listing with the given id.
   * @param id the id of the listing to delete
   * @return status 204 - no content
   * @throws ListingNotFoundException if a listing with the given id is not found
   * @throws NullPointerException if a listing is invalid
   * @throws DatabaseException if the database could not run an sql operation
   */
  @DeleteMapping(value = "/private/listings/{id}")
  public ResponseEntity<Void> deleteListing(@PathVariable long id)
    throws ListingNotFoundException, NullPointerException, DatabaseException {
    LOGGER.info("Received request to delete listing with id: {}", id);
    Listing listing = listingService.getListing(id);

    listing
      .getImages()
      .forEach(image -> {
        try {
          imageStorageService.init();
          imageStorageService.deleteFile(image.getId());
        } catch (IOException e) {
          throw new UncheckedIOException(e);
        }
      });

    LOGGER.info("Found listing to delete: ", listing);
    listingService.deleteListing(listing);

    LOGGER.info("Deleted listing from database and returning 204");
    return ResponseEntity.noContent().build();
  }
}
