package edu.ntnu.idatt2105.placeholder.service.listing;

import java.util.List;

import org.springframework.data.domain.Page;

import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.listing.ListingAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.listing.ListingNotFoundException;
import edu.ntnu.idatt2105.placeholder.filtering.SearchRequest;
import edu.ntnu.idatt2105.placeholder.model.listing.Listing;
import lombok.NonNull;

/**
 * Service interface for listing operations.
 * @author Nicolai H. B, Callum G.
 * @version 1.1 - 18.3.2023
 */
public interface ListingService {

  boolean listingExists(@NonNull Listing listing) throws NullPointerException;

  Listing saveListing(@NonNull Listing listing) throws ListingAlreadyExistsException, DatabaseException, NullPointerException;

  Listing updateListing(@NonNull Listing listing) throws ListingNotFoundException, DatabaseException;

  Listing deleteListing(@NonNull Listing listing) throws ListingNotFoundException, DatabaseException;

  Listing findListingById(@NonNull Long id) throws ListingNotFoundException;

  List<Listing> getAllListings();

  Page<Listing> searchListingsPaginated(SearchRequest searchRequest) throws NullPointerException;
}
