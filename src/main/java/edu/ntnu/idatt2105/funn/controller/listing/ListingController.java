package edu.ntnu.idatt2105.funn.controller.listing;

import edu.ntnu.idatt2105.funn.controller.file.ImageController;
import edu.ntnu.idatt2105.funn.dto.file.ImageResponseDTO;
import edu.ntnu.idatt2105.funn.dto.listing.ListingCreateDTO;
import edu.ntnu.idatt2105.funn.dto.listing.ListingDTO;
import edu.ntnu.idatt2105.funn.dto.listing.ListingUpdateDTO;
import edu.ntnu.idatt2105.funn.exceptions.DatabaseException;
import edu.ntnu.idatt2105.funn.exceptions.PermissionDeniedException;
import edu.ntnu.idatt2105.funn.exceptions.file.FileNotFoundException;
import edu.ntnu.idatt2105.funn.exceptions.listing.ListingAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.listing.ListingNotFoundException;
import edu.ntnu.idatt2105.funn.exceptions.location.LocationDoesntExistException;
import edu.ntnu.idatt2105.funn.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.funn.exceptions.validation.BadInputException;
import edu.ntnu.idatt2105.funn.exceptions.validation.BadSearchException;
import edu.ntnu.idatt2105.funn.filtering.SearchRequest;
import edu.ntnu.idatt2105.funn.mapper.listing.ListingMapper;
import edu.ntnu.idatt2105.funn.model.file.Image;
import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.model.user.Role;
import edu.ntnu.idatt2105.funn.model.user.User;
import edu.ntnu.idatt2105.funn.security.Auth;
import edu.ntnu.idatt2105.funn.service.file.ImageService;
import edu.ntnu.idatt2105.funn.service.file.ImageStorageService;
import edu.ntnu.idatt2105.funn.service.listing.ListingService;
import edu.ntnu.idatt2105.funn.service.user.UserService;
import edu.ntnu.idatt2105.funn.validation.AuthValidation;
import edu.ntnu.idatt2105.funn.validation.ListingValidation;
import edu.ntnu.idatt2105.funn.validation.SearchRequestValidation;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
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
import org.zalando.fauxpas.FauxPas;

/**
 * Controller for listings
 * Mappings for getting all, getting one,
 * creating, updating and deleting listings.
 * @author Nicolai H. B., Carl G., Callum G.
 * @version 1.5 - 27.3.2023
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
   * @throws NullPointerException If search is null
   * @throws BadSearchException If search is not valid
   */
  @PostMapping(value = "/public/listings", produces = { MediaType.APPLICATION_JSON_VALUE })
  @Operation(
    summary = "Get listings by search and filter.",
    description = "Returns all listings in the database. Possible to search for keywords in listing. Keywords share a name with the variable in the Listing class."
  )
  public ResponseEntity<List<ListingDTO>> getListingsByFilter(@RequestBody SearchRequest search)
    throws NullPointerException, BadSearchException {
    LOGGER.info("Received search request for listings");

    if (!SearchRequestValidation.validateSearchRequest(search)) throw new BadSearchException(
      "Search request is not valid"
    );

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
   * Returns all listings by user in the database.
   * @param username The username of the user
   * @return A list of all listings in the database.
   * @throws NullPointerException If username is null.
   */
  @GetMapping(
    value = "/public/listings/users/{username}",
    produces = { MediaType.APPLICATION_JSON_VALUE }
  )
  @Operation(
    summary = "Get all listings published by a user.",
    description = "Returns all listings in the database that are published by a user."
  )
  public ResponseEntity<List<ListingDTO>> getListingsByUser(@PathVariable String username)
    throws NullPointerException {
    LOGGER.info("Received request to get all listings by user");

    List<Listing> listings = listingService.getListingsByUser(username);
    LOGGER.info("Found {} listings", listings.size());

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
   * @param auth the authentication object of the user that is getting the listing.
   * @return The listing with the given id.
   * @throws UserDoesNotExistsException If user does not exist
   */
  @GetMapping(value = "/public/listings/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
  @Operation(
    summary = "Get a listing by id.",
    description = "Returns a listing with the given id. If user is logged in, it will also return if the user has favorited the listing."
  )
  public ResponseEntity<ListingDTO> getListing(
    @PathVariable long id,
    @AuthenticationPrincipal Auth auth
  ) throws UserDoesNotExistsException {
    LOGGER.info("Received auth: {}", auth);

    boolean checkFavorite = false;
    Optional<Boolean> isFavorite = Optional.empty();

    if (AuthValidation.validateAuth(auth)) checkFavorite = true;

    LOGGER.info("Received request to get listing with id: {}", id);

    Listing foundListing = listingService.getListing(id);
    LOGGER.info("Found listing {}", foundListing);

    ListingDTO listingDTO = listingMapper.listingToListingDTO(foundListing);
    LOGGER.info("Mapped listing to DTO and checking if possible user has favorited it");

    if (checkFavorite) {
      LOGGER.info("User is logged in, checking if user has favorited listing");
      boolean listingIsFavorite = userService.isFavoriteByUser(auth.getUsername(), foundListing);

      LOGGER.info("Listing is favorite: {}", listingIsFavorite);
      isFavorite = Optional.of(listingIsFavorite);
    }

    listingDTO.setIsFavorite(isFavorite);

    return ResponseEntity.ok(listingDTO);
  }

  /**
   * Method used to upload images to a listing.
   * @param listing The listing to upload images to.
   * @param images The images to upload.
   * @param imageAlts The alt text for the images.
   * @return The listing with the uploaded images.
   * @throws IOException If the storing of an image fails.
   * @throws DatabaseException If an image upload fails.
   * @throws NullPointerException If an image file is null.
   */
  private List<ImageResponseDTO> uploadImages(
    Long listingId,
    MultipartFile[] images,
    String[] imageAlts
  ) throws IOException, DatabaseException, NullPointerException {
    List<ImageResponseDTO> dtos = new ArrayList<>();
    Map<MultipartFile, String> imageAltMap = IntStream
      .range(0, images.length)
      .boxed()
      .collect(Collectors.toMap(i -> images[i], i -> imageAlts[i]));

    // Allows for throwing checked exceptions in a lambda.
    Function<Image, Image> saveImage = FauxPas.throwingFunction(imageService::saveFile);

    // Allows for throwing checked exceptions in a lambda.
    BiConsumer<MultipartFile, Long> storeImage = FauxPas.throwingBiConsumer((image, id) ->
      imageStorageService.store(image, id)
    );

    imageStorageService.init();

    LOGGER.info("Uploading {} images", imageAltMap.size());

    imageAltMap.forEach((image, alt) -> {
      LOGGER.info("Image upload request received");

      ImageResponseDTO dto = new ImageResponseDTO();

      Image imageFile = new Image();

      imageFile.setAlt(alt);

      imageFile.setListingId(listingId);

      LOGGER.info("Saving image file");

      imageFile = saveImage.apply(imageFile);

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

    LOGGER.info("Image upload responses {}", dtos);

    return dtos;
  }

  /**
   * Creates a listing from a listing dto.
   * Uploads images to the listing if they are present
   * using the uploadImages method.
   * @param listingDTO The listing dto to create a listing from.
   * @param auth the authentication object of the user that is creating the listing.
   * @return The created listing as a listing dto with images.
   * @throws PermissionDeniedException If the auth is null.
   * @throws BadRequestException If the user input is invalid.
   * @throws ListingAlreadyExistsException If the listing already exists.
   * @throws LocationDoesntExistException If the location does not exist
   * @throws DatabaseException If the database could not handle a sql request
   * @throws UserDoesNotExistsException If the requested user does not exist.
   * @throws IOException If the storing of an image fails.
   * @throws NullPointerException If an image file is null.
   */
  @PostMapping(
    value = "/private/listings",
    consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
    produces = { MediaType.APPLICATION_JSON_VALUE }
  )
  @Operation(
    summary = "Creates a listing with the given data.",
    description = "Creates a listing with the given data. Checks that the user exists and that the location exists. Also checks that the listing does not already exist. If the listing is created successfully, it will return the listing with the id and the images uploaded."
  )
  public ResponseEntity<ListingDTO> createListing(
    @ModelAttribute ListingCreateDTO listingDTO,
    @AuthenticationPrincipal Auth auth
  )
    throws PermissionDeniedException, BadInputException, LocationDoesntExistException, DatabaseException, UserDoesNotExistsException, ListingAlreadyExistsException, NullPointerException, IOException {
    if (!AuthValidation.validateAuth(auth)) {
      throw new PermissionDeniedException("User is not logged in");
    }

    if (
      !ListingValidation.validateListing(
        listingDTO.getTitle(),
        listingDTO.getBriefDescription(),
        listingDTO.getFullDescription(),
        listingDTO.getPrice(),
        listingDTO.getPublicationDate(),
        listingDTO.getExpirationDate(),
        listingDTO.getImages(),
        listingDTO.getImageAlts()
      )
    ) {
      throw new BadInputException("Listing data is not valid");
    }

    if (!ListingValidation.validateCategory(listingDTO.getCategory().getName())) {
      throw new BadInputException("Category is not valid");
    }

    final String username = auth.getUsername();

    LOGGER.info("Recieved request to create listing: {}", listingDTO);
    Listing requestedListing = listingMapper.listingCreateUpdateDTOToListing(listingDTO);

    requestedListing.setUser(userService.getUserByUsername(username));

    LOGGER.info("Mapped DTO to listing: {}", requestedListing);
    Listing createdListing = listingService.saveListing(requestedListing);

    LOGGER.info("Saved listing to database");
    ListingDTO createdListingDTO = listingMapper.listingToListingDTO(createdListing);

    if (listingDTO.getImages() != null) {
      if (listingDTO.getImageAlts() == null) {
        String[] imageAlts = new String[listingDTO.getImages().length];
        for (int i = 0; i < imageAlts.length; i++) {
          imageAlts[i] = "No Description";
        }
        listingDTO.setImageAlts(imageAlts);
      } else if (listingDTO.getImages().length != listingDTO.getImageAlts().length) {
        for (String alt : listingDTO.getImageAlts()) {
          if (alt == null || alt.isEmpty()) {
            alt = "No Description";
          }
        }
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
  @PutMapping(
    value = "/private/listings/{id}",
    consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
    produces = { MediaType.APPLICATION_JSON_VALUE }
  )
  public ResponseEntity<ListingDTO> updateListing(
    @ModelAttribute ListingUpdateDTO listingDTO,
    @PathVariable long id,
    @AuthenticationPrincipal Auth auth
  )
    throws LocationDoesntExistException, DatabaseException, UserDoesNotExistsException, RuntimeException, IOException, PermissionDeniedException {
    LOGGER.info("Auth: {}", auth);
    if (
      auth == null ||
      (!auth.getUsername().equals(listingDTO.getUsername()) && auth.getRole() != Role.ADMIN)
    ) throw new PermissionDeniedException("You do not have permission to update this listing");

    LOGGER.info("Received request to update listing: {}", listingDTO);

    Listing requestedListing = listingMapper.listingCreateUpdateDTOToListing(listingDTO);

    requestedListing.setId(id);
    User user = userService.getUserByUsername(listingDTO.getUsername());
    requestedListing.setUser(user);

    // Filter images on images to keep if they images to keep is defined
    List<Image> images = imageService.getAllFilesByListingId(id);
    List<Image> imagesToKeep = new ArrayList<>();
    List<Long> keepImageIds = listingDTO.getImagesToKeep();
    LOGGER.info("KeepImageIds: {}", keepImageIds);
    if (keepImageIds != null) {
      images.forEach(i -> {
        LOGGER.info("Image: {}", i);
        if (!keepImageIds.contains(i.getId())) {
          try {
            imageStorageService.init();
            imageStorageService.deleteFile(i.getId());
          } catch (FileNotFoundException | IOException e) {
            LOGGER.error("Could not delete image: {}.\nError: {}", i.getId(), e.getMessage());
          }
        } else {
          imagesToKeep.add(i);
        }
      });
      images = imagesToKeep;
    }
    LOGGER.info("Images that are kept: {}", images);

    requestedListing.setImages(images);

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
   * @param auth the authentication object of the user that is favoriting the listing.
   * @param id the id of the listing to favorite
   * @return nothing
   * @throws UserDoesNotExistsException if the user does not exist
   * @throws ListingNotFoundException if the listing does not exist
   * @throws PermissionDeniedException if the user is not logged in
   */
  @PutMapping(value = "/private/listings/{id}/favorite")
  @Operation(
    summary = "Favorites a listing with the given id.",
    description = "Favorites a listing with the given id. If the listing is already favorited, it is unfavorited."
  )
  public ResponseEntity<ListingDTO> favoriteOrUnfavoriteListing(
    @AuthenticationPrincipal Auth auth,
    @PathVariable long id
  ) throws UserDoesNotExistsException, ListingNotFoundException, PermissionDeniedException {
    if (!AuthValidation.validateAuth(auth)) throw new PermissionDeniedException(
      "User is not logged in"
    );

    final String username = auth.getUsername();

    LOGGER.info(
      "Received request to change favorite value on listing with id {} by user {}",
      username,
      id
    );
    Listing listing = listingService.getListing(id);

    LOGGER.info("Found listing to change value for: {}, by user {}", listing, username);
    userService.favoriteOrUnfavoriteListing(username, listing);

    ListingDTO listingDTO = listingMapper.listingToListingDTO(listing);

    boolean listingIsFavorite = userService.isFavoriteByUser(username, listing);

    LOGGER.info("Listing is favorite: {}", listingIsFavorite);
    listingDTO.setIsFavorite(Optional.of(listingIsFavorite));

    return ResponseEntity.ok(listingDTO);
  }

  /**
   * Gets all favorite listings for a user.
   * @param auth authentication object of the user who is requesting their favorite listings.
   * @return a set of favorite listings by a user.
   * @throws PermissionDeniedException if the user is not logged in.
   * @throws UserDoesNotExistsException if the user does not exist.
   */
  @GetMapping(value = "/private/listings/favorites")
  @Operation(
    summary = "Gets all favorite listings for a user.",
    description = "Gets all favorite listings for a user. If the user is not logged in, an empty set is returned."
  )
  public ResponseEntity<Set<ListingDTO>> getFavoriteListings(@AuthenticationPrincipal Auth auth)
    throws UserDoesNotExistsException, PermissionDeniedException {
    if (!AuthValidation.validateAuth(auth)) throw new PermissionDeniedException(
      "User is not logged in"
    );

    final String username = auth.getUsername();
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
   * @param auth the authentication object of the user that is deleting the listing.
   * @return 204 No Content with no body.
   * @throws ListingNotFoundException if a listing with the given id is not found
   * @throws PermissionDeniedException if the user is not an admin or the owner of the listing.
   * @throws NullPointerException if a listing is invalid
   * @throws DatabaseException if the database could not run an sql operation
   * @throws PermissionDeniedException
   */
  @DeleteMapping(value = "/private/listings/{id}")
  @Operation(
    summary = "Deletes a listing with the given id.",
    description = "Deletes a listing with the given id. If the user is not and admin or the user is not the owner of the listing, the listing is not deleted."
  )
  public ResponseEntity<Void> deleteListing(
    @PathVariable long id,
    @AuthenticationPrincipal Auth auth
  )
    throws ListingNotFoundException, PermissionDeniedException, NullPointerException, FileNotFoundException, DatabaseException {
    LOGGER.info("Received request to delete listing with id: {}", id);
    Listing listing = listingService.getListing(id);

    if (
      !AuthValidation.hasRoleOrIsUser(auth, Role.ADMIN, listing.getUser().getUsername())
    ) throw new PermissionDeniedException("User is not an admin or the owner of the listing");

    BiConsumer<String, Listing> saveImage = FauxPas.throwingBiConsumer(
      userService::favoriteOrUnfavoriteListing
    );
    listing.getFavoritedBy().forEach(user -> saveImage.accept(user.getUsername(), listing));

    listing
      .getImages()
      .forEach(image -> {
        try {
          imageStorageService.init();
          imageStorageService.deleteFile(image.getId());
        } catch (IOException e) {
          throw new UncheckedIOException(e);
        } catch (FileNotFoundException e) {
          LOGGER.error("File not found: {}", image.getId());
        }
      });

    LOGGER.info("Found listing to delete: ", listing);
    listingService.deleteListing(listing);

    LOGGER.info("Deleted listing from database and returning 204");
    return ResponseEntity.noContent().build();
  }
}
