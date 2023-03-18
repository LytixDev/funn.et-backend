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
  public boolean listingExists(@NonNull Listing listing)
    throws NullPointerException {
    return listingRepository.existsById(listing.getId());
  }

  @Override
  public Listing saveListing(@NonNull Listing listing)
    throws ListingAlreadyExistsException, DatabaseException, NullPointerException {
    if (listingExists(listing)) throw new ListingAlreadyExistsException();
    try {
      return listingRepository.save(listing);
    } catch (Exception e) {
      throw new DatabaseException();
    }
  }

  @Override
  public Listing updateListing(@NonNull Listing listing)
    throws DatabaseException {
    if (!listingExists(listing)) throw new ListingNotFoundException();

    try {
      return listingRepository.save(listing);
    } catch (Exception e) {
      throw new DatabaseException();
    }
  }

  @Override
  public Listing deleteListing(@NonNull Listing listing)
    throws DatabaseException {
    if (!listingExists(listing)) throw new ListingNotFoundException();

    try {
      listingRepository.delete(listing);
      return listing;
    } catch (Exception e) {
      throw new DatabaseException();
    }
  }

  @Override
  public Listing findListingById(@NonNull Long id)
    throws ListingNotFoundException {
    return listingRepository
      .findById(id)
      .orElseThrow(ListingNotFoundException::new);
  }

  @Override
  public List<Listing> getAllListings() {
    return listingRepository.findAll();
  }

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
