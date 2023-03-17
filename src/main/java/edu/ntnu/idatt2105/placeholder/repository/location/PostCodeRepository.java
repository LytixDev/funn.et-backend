package edu.ntnu.idatt2105.placeholder.repository.location;

import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import edu.ntnu.idatt2105.placeholder.model.location.PostCodeId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for the post code repository.
 * @author Callum G.
 * @version 1.0 - 17.03.2023
 */
@Repository
public interface PostCodeRepository
  extends JpaRepository<PostCode, PostCodeId> {
  Optional<List<String>> findCitiesByPostCode(String postCode);

  Optional<List<String>> findPostCodesByCity(String city);
}
