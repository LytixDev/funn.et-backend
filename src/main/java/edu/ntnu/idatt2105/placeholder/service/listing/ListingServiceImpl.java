package edu.ntnu.idatt2105.placeholder.service.listing;

import edu.ntnu.idatt2105.placeholder.dto.listing.ListingDTO;
import edu.ntnu.idatt2105.placeholder.mapper.listing.ListingMapper;
import edu.ntnu.idatt2105.placeholder.model.listing.Listing;
import edu.ntnu.idatt2105.placeholder.repository.listing.ListingRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for listing operations.
 * @author Nicolai H. B.
 * @version 1.0
 * @date 18.3.2023
 */
@Service
@RequiredArgsConstructor
public class ListingServiceImpl implements ListingService {

  @Autowired
  private ListingRepository listingRepository;

  @Override
  public Listing saveListing(@NonNull Listing listing) {
    return listingRepository.save(listing);
  }

  @Override
  public Listing createListing(ListingDTO listingDTO) {
    ListingMapper listingMapper = Mappers.getMapper(ListingMapper.class);
    Listing listing = listingMapper.listingDTOToListing(listingDTO);
    return listing;
  }
}
