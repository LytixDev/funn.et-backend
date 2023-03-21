package edu.ntnu.idatt2105.placeholder.repository.location;

import edu.ntnu.idatt2105.placeholder.model.location.Location;
import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Interface for the location repository.
 * @author Callum G.
 * @version 1.1 - 18.03.2023
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
  Optional<List<Location>> findByAddress(@NonNull String address);

  Optional<List<Location>> findLocationsByPostCode(@NonNull PostCode postCode);

  @Query("SELECT l FROM Location l WHERE l.postCode.postCode = ?1")
  Optional<List<Location>> findLocationsByPostCode(@NonNull Integer postCode);

  @Query("SELECT l FROM Location l WHERE l.postCode.city = ?1")
  Optional<List<Location>> findLocationsByCity(@NonNull String city);

  Optional<List<Location>> findByLatitude(double latitude);

  Optional<List<Location>> findByLongitude(double longitude);

  @Query(
    "SELECT l FROM Location l WHERE l.latitude > ?1 AND l.latitude < ?2 AND l.longitude > ?3 AND l.longitude < ?4"
  )
  Optional<List<Location>> findByLatitudeAndLongitudeByRadius(
    double minLatitude,
    double maxLatitude,
    double minLongitude,
    double maxLongitude
  );
}
