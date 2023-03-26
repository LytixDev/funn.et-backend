package edu.ntnu.idatt2105.funn.service.listing;

import edu.ntnu.idatt2105.funn.exceptions.DatabaseException;
import edu.ntnu.idatt2105.funn.exceptions.listing.ListingAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.listing.ListingNotFoundException;
import edu.ntnu.idatt2105.funn.filtering.SearchRequest;
import edu.ntnu.idatt2105.funn.model.listing.Listing;
import java.util.List;
import lombok.NonNull;
import org.springframework.data.domain.Page;

/**
 * Service interface for listing operations.
 * @author Nicolai H. B, Callum G.
 * @version 1.2 - 26.3.2023
 */
public interface ListingService {
  boolean listingExists(@NonNull Listing listing) throws NullPointerException;

  Listing saveListing(@NonNull Listing listing)
    throws ListingAlreadyExistsException, DatabaseException, NullPointerException;

  Listing updateListing(@NonNull Listing listing)
    throws ListingNotFoundException, DatabaseException, NullPointerException;

  Listing deleteListing(@NonNull Listing listing)
    throws ListingNotFoundException, DatabaseException, NullPointerException;

  Listing getListing(@NonNull Long id) throws ListingNotFoundException, NullPointerException;

  List<Listing> getAllListings();

  List<Listing> getListingsByUser(@NonNull String username) throws NullPointerException;

  Page<Listing> searchListingsPaginated(SearchRequest searchRequest) throws NullPointerException;
}
