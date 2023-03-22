package edu.ntnu.idatt2105.funn.service.location;

import edu.ntnu.idatt2105.funn.exceptions.DatabaseException;
import edu.ntnu.idatt2105.funn.exceptions.location.LocationAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.location.LocationDoesntExistException;
import edu.ntnu.idatt2105.funn.model.location.Location;
import edu.ntnu.idatt2105.funn.model.location.PostCode;
import java.util.List;
import lombok.NonNull;
import org.springframework.stereotype.Service;

/**
 * Interface for the service class for the location repository.
 * @author Callum G.
 * @version 1.0 - 17.03.2023
 */
@Service
public interface LocationService {
  boolean locationExists(@NonNull Location location) throws NullPointerException;

  Location saveLocation(@NonNull Location location)
    throws LocationAlreadyExistsException, DatabaseException, NullPointerException;

  Location getLocationById(Long id) throws LocationDoesntExistException, DatabaseException;

  Location updateLocation(@NonNull Location location)
    throws LocationDoesntExistException, DatabaseException, NullPointerException;

  void deleteLocation(@NonNull Location location)
    throws LocationDoesntExistException, DatabaseException, NullPointerException;

  void deleteLocation(Long id) throws LocationDoesntExistException, DatabaseException;

  List<Location> getLocationsByPostCode(@NonNull PostCode postcode)
    throws LocationDoesntExistException, DatabaseException, NullPointerException;

  List<Location> getLocationsByPostCode(@NonNull Integer postcode)
    throws LocationDoesntExistException, DatabaseException, NullPointerException;

  List<Location> getLocationsByCity(@NonNull String city)
    throws LocationDoesntExistException, DatabaseException, NullPointerException;

  List<Location> getLocationsByLongitude(double longitude)
    throws LocationDoesntExistException, DatabaseException, NullPointerException;

  List<Location> getLocationsByLatitude(double latitude)
    throws LocationDoesntExistException, DatabaseException, NullPointerException;

  List<Location> getLocationsInDistance(double latitude, double longitude, double distance)
    throws LocationDoesntExistException, DatabaseException, IllegalArgumentException;

  List<Location> getAllLocations() throws DatabaseException;
}
