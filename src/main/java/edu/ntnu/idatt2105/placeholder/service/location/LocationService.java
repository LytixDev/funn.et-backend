package edu.ntnu.idatt2105.placeholder.service.location;

import edu.ntnu.idatt2105.placeholder.model.location.Location;
import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import java.util.List;

/**
 * Interface for the location service.
 * @author Callum G.
 * @version 1.0 - 17.03.2023
 */
public interface LocationService {
  Location saveLocation(Location location);

  Location getLocationById(Long id);

  Location updateLocation(Location location);

  void deleteLocation(Location location);

  void deleteLocation(Long id);

  List<String> getAddressesByPostcode(PostCode postcode);

  List<Location> getLocationsInDistance(
    Double latitude,
    Double longitude,
    Double distance
  );

  List<Location> getAllLocations();
}
