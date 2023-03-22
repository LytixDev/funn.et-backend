package edu.ntnu.idatt2105.funn.repository.listing;

import edu.ntnu.idatt2105.funn.model.listing.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository for listing operations on the database.
 * @author Nicolai H. B., Callum G.
 * @version 1.1 - 18.3.2023
 */
@Repository
public interface ListingRepository
  extends JpaRepository<Listing, Long>, JpaSpecificationExecutor<Listing> {}
