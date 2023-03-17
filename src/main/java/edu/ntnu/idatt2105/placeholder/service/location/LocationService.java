package edu.ntnu.idatt2105.placeholder.service.location;

import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.LocationAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.LocationDoesntExistException;
import edu.ntnu.idatt2105.placeholder.model.location.Location;
import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import java.util.List;
import lombok.NonNull;

/**
 * Interface for the location service.
 * @author Callum G.
 * @version 1.0 - 17.03.2023
 */
public interface LocationService {
  boolean locationExists(@NonNull Location location)
    throws NullPointerException;

  Location saveLocation(@NonNull Location location)
    throws LocationAlreadyExistsException, DatabaseException, NullPointerException;

  Location getLocationById(@NonNull Long id)
    throws LocationDoesntExistException, DatabaseException, NullPointerException;

  Location updateLocation(@NonNull Location location)
    throws LocationDoesntExistException, DatabaseException, NullPointerException;

  void deleteLocation(@NonNull Location location)
    throws LocationDoesntExistException, DatabaseException, NullPointerException;

  void deleteLocation(@NonNull Long id)
    throws LocationDoesntExistException, DatabaseException, NullPointerException;

  List<Location> getLocationsByPostCode(@NonNull PostCode postcode)
    throws LocationDoesntExistException, DatabaseException, NullPointerException;

  List<Location> getLocationsByPostCode(@NonNull String postcode)
    throws LocationDoesntExistException, DatabaseException, NullPointerException;

  List<Location> getLocationsByCity(@NonNull String city)
    throws LocationDoesntExistException, DatabaseException, NullPointerException;

  List<Location> getLocationsByLongitude(@NonNull Double longitude)
    throws LocationDoesntExistException, DatabaseException, NullPointerException;

  List<Location> getLocationsByLatitude(@NonNull Double latitude)
    throws LocationDoesntExistException, DatabaseException, NullPointerException;

  List<Location> getLocationsInDistance(
    @NonNull Double latitude,
    @NonNull Double longitude,
    @NonNull Double distance
  )
    throws LocationDoesntExistException, DatabaseException, NullPointerException;

  List<Location> getAllLocations() throws DatabaseException;
}
