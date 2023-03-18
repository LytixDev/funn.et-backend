package edu.ntnu.idatt2105.placeholder.repository.listing;

import edu.ntnu.idatt2105.placeholder.model.listing.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for listing operations on the database.
 * @author Nicolai H. B.
 * @version 1.0
 * @date 18.3.2023
 */
public interface ListingRepository extends JpaRepository<Listing, Long> {}
