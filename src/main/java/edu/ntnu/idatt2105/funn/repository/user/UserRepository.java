package edu.ntnu.idatt2105.funn.repository.user;

import edu.ntnu.idatt2105.funn.model.user.User;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for user operations on the database.
 * @author Callum G., Carl G.
 * @version 1.1 - 25.3.2023
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUsername(@NonNull String username);
  Optional<User> findByEmail(@NonNull String email);

  @Query("SELECT u FROM User u JOIN u.favoriteListings l WHERE l.id = ?1 AND u.username = ?2")
  Optional<User> findUserWhoFavoritedListing(
    @Param("listing_id") Long listing_id,
    @Param("username") String username
  );
}
