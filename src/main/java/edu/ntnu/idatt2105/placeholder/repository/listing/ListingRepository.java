package edu.ntnu.idatt2105.placeholder.repository.listing;

import edu.ntnu.idatt2105.placeholder.model.listing.Listing;
import edu.ntnu.idatt2105.placeholder.model.user.User;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for listing operations on the database.
 * @author Nicolai H. B., Callum G.
 * @version 1.1 - 18.3.2023
 */
@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
  @Query(
    "SELECT l FROM Listing l WHERE l.title LIKE %?1% OR l.fullDescription LIKE %?1% OR l.briefDescription LIKE %?1%"
  )
  Optional<List<Listing>> findByKeyWord(String keyWord, Sort sort);

  @Query("SELECT l FROM Listing l WHERE l.category = ?1")
  Optional<List<Listing>> findByCategory(String category, Sort sort);

  @Query("SELECT l FROM Listing l WHERE l.user = ?1")
  Optional<List<Listing>> findByUser(User user, Sort sort);

  @Query(
    "SELECT l FROM Listing l WHERE l.category = ?1 AND (l.title LIKE %?2% OR l.fullDescription LIKE %?2% OR l.briefDescription LIKE %?2%)"
  )
  Optional<List<Listing>> findByCategoryAndKeyWord(
    String category,
    String keyWord,
    Sort sort
  );

  @Query(
    "SELECT l FROM Listing l WHERE l.user = ?1 AND (l.title LIKE %?2% OR l.fullDescription LIKE %?2% OR l.briefDescription LIKE %?2%)"
  )
  Optional<List<Listing>> findByUserAndKeyWord(User user, String keyWord, Sort sort);

  @Query("SELECT l FROM Listing l WHERE l.user = ?1 AND l.category = ?2")
  Optional<List<Listing>> findByUserAndCategory(User user, String category, Sort sort);

  @Query(
    "SELECT l FROM Listing l WHERE l.user = ?1 AND l.category = ?2 AND (l.title LIKE %?3% OR l.fullDescription LIKE %?3% OR l.briefDescription LIKE %?3%)"
  )
  Optional<List<Listing>> findByUserAndCategoryAndKeyWord(
    User user,
    String category,
    String keyWord,
    Sort sort
  );

  @Query(
    "SELECT l FROM listing l WHERE l.category = ?1 AND (l.title LIKE %?2% OR l.fullDescription LIKE %?2% OR l.briefDescription LIKE %?2%) AND l.location.latitude > ?3 AND l.location.latitude < ?4 AND l.location.longitude > ?5 AND l.location.longitude < ?6"
  )
  Optional<List<Listing>> findByCategoryAndKeywordAndLocation(
    String category,
    String keyword,
    Double minLatitude,
    Double maxLatitude,
    Double minLongitude,
    Double maxLongitude,
    Sort sort
  );

  @Query(
    "SELECT l FROM listing l WHERE (l.title LIKE %?1% OR l.fullDescription LIKE %?1% OR l.briefDescription LIKE %?1%) AND l.location.latitude > ?2 AND l.location.latitude < ?3 AND l.location.longitude > ?4 AND l.location.longitude < ?5"
  )
  Optional<List<Listing>> findByKeywordAndLocation(
    String keyword,
    Double minLatitude,
    Double maxLatitude,
    Double minLongitude,
    Double maxLongitude,
    Sort sort
  );

  @Query(
    "SELECT l FROM listing l WHERE l.category = ?1 AND l.location.latitude > ?2 AND l.location.latitude < ?3 AND l.location.longitude > ?4 AND l.location.longitude < ?5"
  )
  Optional<List<Listing>> findByCategoryAndLocation(
    String category,
    Double minLatitude,
    Double maxLatitude,
    Double minLongitude,
    Double maxLongitude,
    Sort sort
  );
}
