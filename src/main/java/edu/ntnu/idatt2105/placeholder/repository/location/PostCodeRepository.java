package edu.ntnu.idatt2105.placeholder.repository.location;

import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for the post code repository.
 * @author Callum G.
 * @version 1.0 - 17.03.2023
 */
public interface PostCodeRepository extends JpaRepository<String, PostCode> {
  Optional<List<String>> findCitiesByPostCode(String postCode);

  Optional<List<String>> findPostCodesByCity(String city);
}
