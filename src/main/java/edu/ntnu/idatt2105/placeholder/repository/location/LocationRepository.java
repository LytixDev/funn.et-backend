package edu.ntnu.idatt2105.placeholder.repository.location;

import edu.ntnu.idatt2105.placeholder.model.location.Location;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Interface for the location repository.
 * @author Callum G.
 * @version 1.0 - 17.03.2023
 */
public interface LocationRepository extends JpaRepository<Long, Location> {
  Optional<List<Location>> findByAddress(String address);

  Optional<List<Location>> findByPostCode(String postCode);

  Optional<List<Location>> findByCity(String city);

  Optional<List<Location>> findByLatitude(Double latitude);

  Optional<List<Location>> findByLongitude(Double longitude);

  @Query(
    "SELECT l FROM Location l WHERE l.latitude > ?1 AND l.latitude < ?2 AND l.longitude > ?3 AND l.longitude < ?4"
  )
  Optional<List<Location>> findByLatitudeAndLongitude(
    Double minLatitude,
    Double maxLatitude,
    Double minLongitude,
    Double maxLongitude
  );
}
