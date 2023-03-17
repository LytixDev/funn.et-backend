package edu.ntnu.idatt2105.placeholder.service.location;

import edu.ntnu.idatt2105.placeholder.model.location.Location;
import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import lombok.NonNull;

import java.util.List;

/**
 * Interface for the location service.
 * @author Callum G.
 * @version 1.0 - 17.03.2023
 */
public interface LocationService {
  Location saveLocation(@NonNull Location location);

  Location getLocationById(@NonNull Long id);

  Location updateLocation(@NonNull Location location);

  void deleteLocation(@NonNull Location location);

  void deleteLocation(@NonNull Long id);

  List<String> getAddressesByPostcode(@NonNull PostCode postcode);

  List<String> getAddressesByPostcode(@NonNull String postcode);

  List<String> getAddressesByCity(@NonNull String city);

  List<String> getLocationsByLongitude(@NonNull Double longitude);

  List<String> getLocationsByLatitude(@NonNull Double latitude);

  List<Location> getLocationsInDistance(
    @NonNull Double latitude,
    @NonNull Double longitude,
    @NonNull Double distance
  );

  List<Location> getAllLocations();
}
