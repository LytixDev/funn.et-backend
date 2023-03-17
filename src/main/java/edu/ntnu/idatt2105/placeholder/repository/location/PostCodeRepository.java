package edu.ntnu.idatt2105.placeholder.repository.location;

import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import edu.ntnu.idatt2105.placeholder.model.location.PostCodeId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Interface for the post code repository.
 * @author Callum G.
 * @version 1.0 - 17.03.2023
 */
@Repository
public interface PostCodeRepository
  extends JpaRepository<PostCode, PostCodeId> {
  @Query("SELECT p.city FROM PostCode p WHERE p.postCode = ?1")
  Optional<List<String>> findCitiesByPostCode(String postCode);

  @Query("SELECT p.postCode FROM PostCode p WHERE p.city = ?1")
  Optional<List<String>> findPostCodesByCity(String city);
}
