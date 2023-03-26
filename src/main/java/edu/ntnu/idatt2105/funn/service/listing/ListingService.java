package edu.ntnu.idatt2105.funn.service.listing;

import edu.ntnu.idatt2105.funn.exceptions.DatabaseException;
import edu.ntnu.idatt2105.funn.exceptions.listing.ListingAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.listing.ListingNotFoundException;
import edu.ntnu.idatt2105.funn.filtering.SearchRequest;
import edu.ntnu.idatt2105.funn.model.listing.Listing;
import java.util.List;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Service interface for listing operations.
 * @author Nicolai H. B, Callum G.
 * @version 1.1 - 18.3.2023
 */
public interface ListingService {
  boolean listingExists(@NonNull Listing listing) throws NullPointerException;

  Listing saveListing(@NonNull Listing listing)
    throws ListingAlreadyExistsException, DatabaseException, NullPointerException;

  Listing updateListing(@NonNull Listing listing)
    throws ListingNotFoundException, DatabaseException, NullPointerException;

  @PreAuthorize("hasRole('ADMIN') or authentication.principal.username.equals(#listing.user.username)")
  Listing deleteListing(@NonNull Listing listing)
    throws ListingNotFoundException, DatabaseException, NullPointerException;

  Listing getListing(@NonNull Long id) throws ListingNotFoundException, NullPointerException;

  List<Listing> getAllListings();

  Page<Listing> searchListingsPaginated(SearchRequest searchRequest) throws NullPointerException;
}
