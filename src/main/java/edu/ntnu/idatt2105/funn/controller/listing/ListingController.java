package edu.ntnu.idatt2105.funn.controller.listing;

import edu.ntnu.idatt2105.funn.dto.listing.ListingCreateDTO;
import edu.ntnu.idatt2105.funn.dto.listing.ListingDTO;
import edu.ntnu.idatt2105.funn.exceptions.DatabaseException;
import edu.ntnu.idatt2105.funn.exceptions.listing.ListingAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.listing.ListingNotFoundException;
import edu.ntnu.idatt2105.funn.exceptions.location.LocationDoesntExistException;
import edu.ntnu.idatt2105.funn.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.funn.filtering.SearchRequest;
import edu.ntnu.idatt2105.funn.mapper.listing.ListingMapper;
import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.service.listing.ListingService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for listings
 * Mappings for getting all, getting one,
 * creating, updating and deleting listings.
 * @author Nicolai H. B., Carl G., Callum G.
 * @version 1.1 - 20.3.2023
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
    LOGGER.info("Recieved request to get all listings");
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
  public ResponseEntity<ListingDTO> getListing(@PathVariable long id) {
    LOGGER.info("Recieved request to get listing with id: {}", id);
    Listing foundListing = listingService.getListing(id);
    LOGGER.info("Found listing {}", foundListing);
    ListingDTO listingDTO = listingMapper.listingToListingDTO(foundListing);
    LOGGER.info("Mapped listing to DTO and returning");
    return ResponseEntity.ok(listingDTO);
  }

  /**
   * Creates a listing from a listing dto
   * @param listingDTO The id of the listing to delete.
   * @return The listing created
   * @throws LocationDoesntExistException If the location does not exist
   * @throws DatabaseException If the database could not handle a sql request
   * @throws UserDoesNotExistsException If the user does not exist
   * @throws NullPointerException If the user is null
   * @throws ListingAlreadyExistsException If the listing already exists
   */
  @PostMapping(
    value = "/private/listings",
    consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE },
    produces = { MediaType.APPLICATION_JSON_VALUE }
  )
  public ResponseEntity<ListingDTO> createListing(@RequestBody ListingCreateDTO listingDTO)
    throws LocationDoesntExistException, DatabaseException, UserDoesNotExistsException, NullPointerException, ListingAlreadyExistsException {
    LOGGER.info("Recieved request to create listing: {}", listingDTO);
    Listing requestedListing = listingMapper.listingCreateDTOToListing(listingDTO);
    LOGGER.info("Mapped DTO to listing: {}", requestedListing);
    Listing createdListing = listingService.saveListing(requestedListing);
    LOGGER.info("Saved listing to database");
    ListingDTO createdListingDTO = listingMapper.listingToListingDTO(createdListing);
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
    consumes = { MediaType.APPLICATION_JSON_VALUE },
    produces = { MediaType.APPLICATION_JSON_VALUE }
  )
  public ResponseEntity<ListingDTO> updateListing(
    @RequestBody ListingDTO listingDTO,
    @PathVariable long id
  ) throws LocationDoesntExistException, DatabaseException, UserDoesNotExistsException {
    LOGGER.info("Recieveed request to update listing: {}", listingDTO);

    if (listingDTO.getId() != id) throw new IllegalArgumentException("400 Bad Request");

    Listing requestedListing = listingMapper.listingDTOToListing(listingDTO);

    LOGGER.info("Mapped DTO to listing: {}", requestedListing);

    Listing updatedListing = listingService.updateListing(requestedListing);

    LOGGER.info("Saved updated listing to database");
    ListingDTO updatedListingDTO = listingMapper.listingToListingDTO(updatedListing);
    LOGGER.info("Mapped listing to DTO and returning");
    return ResponseEntity.ok(updatedListingDTO);
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
    LOGGER.info("Recieved request to delete listing with id: {}", id);
    Listing listing = listingService.getListing(id);

    LOGGER.info("Found listing to delete: ", listing);
    listingService.deleteListing(listing);

    LOGGER.info("Deleted listing from database and returning 204");
    return ResponseEntity.noContent().build();
  }
}
