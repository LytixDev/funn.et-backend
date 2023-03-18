package edu.ntnu.idatt2105.placeholder.service.listing;

import edu.ntnu.idatt2105.placeholder.dto.listing.ListingDTO;
import edu.ntnu.idatt2105.placeholder.model.listing.Listing;
import lombok.NonNull;

/**
 * Service interface for listing operations.
 * @author Nicolai H. B.
 * @version 1.0
 * @date 18.3.2023
 */
public interface ListingService {
  public Listing saveListing(@NonNull Listing listing);

  Listing createListing(ListingDTO listingDTO);
}
