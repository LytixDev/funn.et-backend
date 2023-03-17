package edu.ntnu.idatt2105.placeholder.service.location;

import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.LocationAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.LocationDoesntExistException;
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
  boolean locationExists(@NonNull Location location) throws NullPointerException;

  Location saveLocation(@NonNull Location location) throws LocationAlreadyExistsException, DatabaseException, NullPointerException;

  Location getLocationById(@NonNull Long id) throws LocationDoesntExistException, DatabaseException, NullPointerException;

  Location updateLocation(@NonNull Location location) throws LocationDoesntExistException, DatabaseException, NullPointerException;

  void deleteLocation(@NonNull Location location) throws LocationDoesntExistException, DatabaseException, NullPointerException;

  void deleteLocation(@NonNull Long id) throws LocationDoesntExistException, DatabaseException, NullPointerException;

  List<String> getAddressesByPostcode(@NonNull PostCode postcode) throws DatabaseException, NullPointerException;

  List<String> getAddressesByPostcode(@NonNull String postcode) throws DatabaseException, NullPointerException;

  List<String> getAddressesByCity(@NonNull String city) throws DatabaseException, NullPointerException;

  List<String> getLocationsByLongitude(@NonNull Double longitude) throws DatabaseException, NullPointerException;

  List<String> getLocationsByLatitude(@NonNull Double latitude) throws DatabaseException, NullPointerException;

  List<Location> getLocationsInDistance(
    @NonNull Double latitude,
    @NonNull Double longitude,
    @NonNull Double distance
  ) throws DatabaseException, NullPointerException;

  List<Location> getAllLocations() throws DatabaseException;
}
