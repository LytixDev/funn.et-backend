package edu.ntnu.idatt2105.placeholder.mapper.listing;

import edu.ntnu.idatt2105.placeholder.dto.listing.ListingDTO;
import edu.ntnu.idatt2105.placeholder.model.listing.Listing;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Class used to map between Listing and ListingDTO.
 * @author Nicolai H. B
 * @version 1.0 - 18.3.2023
 */
@Mapper(componentModel = "spring")
public interface ListingMapper {
  ListingMapper INSTANCE = Mappers.getMapper(ListingMapper.class);

  /**
   * Maps a Listing to a ListingDTO.
   * @param listing The Listing to map.
   * @return The mapped ListingDTO.
   */
  ListingDTO listingToListingDTO(Listing listing);

  /**
   * Maps a ListingDTO to a Listing.
   * @param listingDTO The ListingDTO to map.
   * @return The mapped Listing.
   */
  Listing listingDTOToListing(ListingDTO listingDTO);
}
