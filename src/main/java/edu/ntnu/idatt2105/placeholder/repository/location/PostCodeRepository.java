package edu.ntnu.idatt2105.placeholder.repository.location;

import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
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
public interface PostCodeRepository extends JpaRepository<PostCode, Integer> {
  @Query("SELECT p.city FROM PostCode p WHERE p.postCode = ?1")
  Optional<List<String>> findCitiesByPostCode(Integer postCode);

  @Query("SELECT p.postCode FROM PostCode p WHERE p.city = ?1")
  Optional<List<Integer>> findPostCodesByCity(String city);
}
