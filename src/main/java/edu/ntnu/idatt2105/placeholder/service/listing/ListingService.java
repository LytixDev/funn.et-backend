package edu.ntnu.idatt2105.placeholder.service.listing;

import edu.ntnu.idatt2105.placeholder.dto.listing.ListingDTO;
import edu.ntnu.idatt2105.placeholder.model.listing.Listing;
import lombok.NonNull;

/**
 * Service interface for listing operations.
 * @author Nicolai H. B, Callum G.
 * @version 1.1
 * @date 18.3.2023
 */
public interface ListingService {

  Listing saveListing(@NonNull Listing listing);

  Listing updateListing(@NonNull Listing listing);

  Listing deleteListing(@NonNull Listing listing);

  Listing findListingById(@NonNull Long id);

  List<Listing> getAllListings();
}
