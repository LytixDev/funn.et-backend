package edu.ntnu.idatt2105.funn.repository.user;

import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.model.user.User;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for user operations on the database.
 * @author Callum G.
 * @version 1.0 - 13.3.2023
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUsername(@NonNull String username);
  Optional<User> findByEmail(@NonNull String email);
}
