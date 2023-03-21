package edu.ntnu.idatt2105.placeholder.service.listing;

import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.listing.ListingAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.listing.ListingNotFoundException;
import edu.ntnu.idatt2105.placeholder.filtering.SearchRequest;
import edu.ntnu.idatt2105.placeholder.filtering.SearchSpecification;
import edu.ntnu.idatt2105.placeholder.model.listing.Listing;
import edu.ntnu.idatt2105.placeholder.repository.listing.ListingRepository;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service for listing operations.
 * @author Nicolai H. B., Callum G.
 * @version 1.2 - 21.3.2023
 */
@Service
@RequiredArgsConstructor
public class ListingServiceImpl implements ListingService {

  @Autowired
  private ListingRepository listingRepository;

  /**
   * Checks if a listing exists in the database.
   * @param listing The listing to check.
   * @return True if the listing exists, false otherwise.
   * @throws NullPointerException If the listing is null.
   */
  @Override
  public boolean listingExists(@NonNull Listing listing)
    throws NullPointerException {
    return listingRepository.existsById(listing.getId());
  }

  /**
   * Saves a listing to the database.
   * @param listing The listing to save.
   * @return The saved listing.
   * @throws ListingAlreadyExistsException If the listing already exists.
   * @throws DatabaseException If the database operation fails.
   * @throws NullPointerException If the listing is null.
   */
  @Override
  public Listing saveListing(@NonNull Listing listing)
    throws ListingAlreadyExistsException, DatabaseException, NullPointerException {
      if (listing.getId() != null && listingExists(listing)) throw new ListingAlreadyExistsException();
      try {
      return listingRepository.save(listing);
    } catch (Exception e) {
      throw new DatabaseException();
    }
  }

  /**
   * Updates a listing in the database.
   * @param listing The listing to update.
   * @return The updated listing.
   * @throws ListingNotFoundException If the listing does not exist.
   * @throws DatabaseException If the database operation fails.
   * @throws NullPointerException If the listing is null.
   */
  @Override
  public Listing updateListing(@NonNull Listing listing)
    throws DatabaseException {
    if (!listingExists(listing)) throw new ListingNotFoundException();

    try {
      return listingRepository.save(listing);
    } catch (Exception e) {
      e.printStackTrace();
      throw new DatabaseException();
    }
  }

  /**
   * Deletes a listing from the database.
   * @param listing The listing to delete.
   * @return The deleted listing.
   * @throws ListingNotFoundException If the listing does not exist.
   * @throws DatabaseException If the database operation fails.
   * @throws NullPointerException If the listing is null.
   */
  @Override
  public Listing deleteListing(@NonNull Listing listing)
    throws ListingNotFoundException, DatabaseException, NullPointerException {
    if (!listingExists(listing)) throw new ListingNotFoundException();

    try {
      listingRepository.delete(listing);
      return listing;
    } catch (Exception e) {
      throw new DatabaseException();
    }
  }

  /**
   * Finds a listing by id.
   * @param id The id of the listing.
   * @return The listing.
   * @throws ListingNotFoundException If the listing does not exist.
   * @throws NullPointerException If the id is null.
   */
  @Override
  public Listing getListing(@NonNull Long id)
    throws ListingNotFoundException, NullPointerException {
    return listingRepository
      .findById(id)
      .orElseThrow(ListingNotFoundException::new);
  }

  /**
   * Finds all listings.
   * @return A list of all listings.
   */
  @Override
  public List<Listing> getAllListings() {
    return listingRepository.findAll();
  }

  /**
   * Finds all listings paginated.
   * @param pageable The pageable object.
   * @return A page of listings.
   * @throws NullPointerException If the pageable object is null.
   */
  @Override
  public Page<Listing> searchListingsPaginated(
    @NonNull SearchRequest searchRequest
  ) throws NullPointerException {
    SearchSpecification<Listing> searchSpecification = new SearchSpecification<>(
      searchRequest
    );
    Pageable pageable = SearchSpecification.getPageable(searchRequest);
    return listingRepository.findAll(searchSpecification, pageable);
  }
}
