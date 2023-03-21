package edu.ntnu.idatt2105.placeholder.service.location;

import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.LocationAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.LocationDoesntExistException;
import edu.ntnu.idatt2105.placeholder.model.location.Location;
import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import edu.ntnu.idatt2105.placeholder.repository.location.LocationRepository;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for the location repository.
 * @author Callum G., Carl G.
 * @version 1.2 - 18.03.2023
 */
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

  @Autowired
  private LocationRepository locationRepository;

  /**
   * Checks if a location exists in the database.
   * @param location The location to check.
   * @return True if the location exists, false otherwise.
   * @throws NullPointerException If the location is null.
   */
  @Override
  public boolean locationExists(@NonNull Location location)
    throws NullPointerException {
    return locationRepository.findById(location.getId()).isPresent();
  }

  /**
   * Saves a location to the database.
   * @param location The location to save.
   * @return The saved location.
   * @throws LocationAlreadyExistsException If the location already exists.
   * @throws DatabaseException If there is an error saving the location.
   * @throws NullPointerException If the location is null.
   */
  @Override
  public Location saveLocation(@NonNull Location location)
    throws LocationAlreadyExistsException, DatabaseException, NullPointerException {
    if (locationExists(location)) throw new LocationAlreadyExistsException();

    try {
      return locationRepository.save(location);
    } catch (Exception e) {
      throw new DatabaseException("Error saving location");
    }
  }

  /**
   * Gets a location from the database.
   * @param id The id of the location.
   * @return The location.
   * @throws LocationDoesntExistException If the location does not exist.
   * @throws DatabaseException If there is an error getting the location.
   * @throws NullPointerException If the id is null.
   */
  @Override
  public Location getLocationById(Long id)
    throws LocationDoesntExistException, DatabaseException {
    try {
      return locationRepository
        .findById(id)
        .orElseThrow(() -> new LocationDoesntExistException());
    } catch (Exception e) {
      if (
        e instanceof LocationDoesntExistException
      ) throw e; else throw new DatabaseException("Error getting location");
    }
  }

  /**
   * Updates a location in the database.
   * @param location The location to update.
   * @return The updated location.
   * @throws LocationDoesntExistException If the location does not exist.
   * @throws DatabaseException If there is an error updating the location.
   * @throws NullPointerException If the location is null.
   */
  @Override
  public Location updateLocation(@NonNull Location location)
    throws LocationDoesntExistException, DatabaseException, NullPointerException {
    if (!locationExists(location)) throw new LocationDoesntExistException();

    try {
      return locationRepository.save(location);
    } catch (Exception e) {
      throw new DatabaseException("Error updating location");
    }
  }

  /**
   * Deletes a location from the database.
   * @param location The location to delete.
   * @throws LocationDoesntExistException If the location does not exist.
   * @throws DatabaseException If there is an error deleting the location.
   * @throws NullPointerException If the location is null.
   */
  @Override
  public void deleteLocation(@NonNull Location location)
    throws LocationDoesntExistException, DatabaseException, NullPointerException {
    if (!locationExists(location)) throw new LocationDoesntExistException();

    try {
      locationRepository.delete(location);
    } catch (Exception e) {
      throw new DatabaseException("Error deleting location");
    }
  }

  /**
   * Deletes a location from the database.
   * @param id The id of the location to delete.
   * @throws LocationDoesntExistException If the location does not exist.
   * @throws DatabaseException If there is an error deleting the location.
   */
  @Override
  public void deleteLocation(Long id)
    throws LocationDoesntExistException, DatabaseException {
    if (
      !locationRepository.existsById(id)
    ) throw new LocationDoesntExistException();

    try {
      locationRepository.deleteById(id);
    } catch (Exception e) {
      throw new DatabaseException("Error deleting location");
    }
  }

  /**
   * Gets all locations by post code.
   * @param postCode The post code to search for.
   * @return A list of locations.
   * @throws LocationDoesntExistException If no locations are found.
   * @throws DatabaseException If there is an error getting the locations.
   * @throws NullPointerException If the post code is null.
   */
  @Override
  public List<Location> getLocationsByPostCode(@NonNull Integer postCode)
    throws LocationDoesntExistException, DatabaseException, NullPointerException {
    try {
      return locationRepository
        .findLocationsByPostCode(postCode)
        .filter(l -> !l.isEmpty())
        .orElseThrow(() -> new LocationDoesntExistException());
    } catch (Exception e) {
      if (
        e instanceof LocationDoesntExistException
      ) throw e; else throw new DatabaseException("Error getting locations");
    }
  }

  /**
   * Gets all locations by post code.
   * @param postCode The post code to search for.
   * @return A list of locations.
   * @throws LocationDoesntExistException If no locations are found.
   * @throws DatabaseException If there is an error getting the locations.
   * @throws NullPointerException If the post code is null.
   */
  @Override
  public List<Location> getLocationsByPostCode(@NonNull PostCode postCode)
    throws LocationDoesntExistException, DatabaseException, NullPointerException {
    try {
      return locationRepository
        .findLocationsByPostCode(postCode)
        .filter(l -> !l.isEmpty())
        .orElseThrow(() -> new LocationDoesntExistException());
    } catch (Exception e) {
      if (
        e instanceof LocationDoesntExistException
      ) throw e; else throw new DatabaseException("Error getting locations");
    }
  }

  /**
   * Gets all locations by city.
   * @param city The city to search for.
   * @return A list of locations.
   * @throws LocationDoesntExistException If no locations are found.
   * @throws DatabaseException If there is an error getting the locations.
   * @throws NullPointerException If the city is null.
   */
  @Override
  public List<Location> getLocationsByCity(@NonNull String city)
    throws LocationDoesntExistException, DatabaseException, NullPointerException {
    try {
      return locationRepository
        .findLocationsByCity(city)
        .filter(l -> !l.isEmpty())
        .orElseThrow(() -> new LocationDoesntExistException());
    } catch (Exception e) {
      if (
        e instanceof LocationDoesntExistException
      ) throw e; else throw new DatabaseException("Error getting locations.");
    }
  }

  /**
   * Gets all locations by longitude.
   * @param longitude The longitude to search for.
   * @return A list of locations.
   * @throws LocationDoesntExistException If no locations are found.
   * @throws DatabaseException If there is an error getting the locations.
   */
  @Override
  public List<Location> getLocationsByLongitude(double longitude)
    throws LocationDoesntExistException, DatabaseException {
    try {
      return locationRepository
        .findByLongitude(longitude)
        .filter(l -> !l.isEmpty())
        .orElseThrow(() -> new LocationDoesntExistException());
    } catch (Exception e) {
      if (
        e instanceof LocationDoesntExistException
      ) throw e; else throw new DatabaseException("Error getting locations");
    }
  }

  /**
   * Gets all locations by latitude.
   * @param latitude The latitude to search for.
   * @return A list of locations.
   * @throws LocationDoesntExistException If no locations are found.
   * @throws DatabaseException If there is an error getting the locations.
   */
  @Override
  public List<Location> getLocationsByLatitude(double latitude)
    throws LocationDoesntExistException, DatabaseException {
    try {
      return locationRepository
        .findByLatitude(latitude)
        .filter(l -> !l.isEmpty())
        .orElseThrow(() -> new LocationDoesntExistException());
    } catch (Exception e) {
      if (
        e instanceof LocationDoesntExistException
      ) throw e; else throw new DatabaseException("Error getting locations");
    }
  }

  /**
   * Gets all locations by longitude and latitude and a distance.
   * @param longitude The longitude to search for.
   * @param latitude The latitude to search for.
   * @param distance The distance to search for.
   * @return A list of locations.
   * @throws LocationDoesntExistException If no locations are found.
   * @throws DatabaseException If there is an error getting the locations.
   * @throws IllegalArgumentException If the distance is zero or negative.
   */
  @Override
  public List<Location> getLocationsInDistance(
    double longitude,
    double latitude,
    double distance
  )
    throws LocationDoesntExistException, DatabaseException, IllegalArgumentException {
    if (distance <= 0) throw new IllegalArgumentException(
      "Distance cannot be zero or negative"
    );
    double minLongitude = longitude - distance;
    double maxLongitude = longitude + distance;
    double minLatitude = latitude - distance;
    double maxLatitude = latitude + distance;
    try {
      return locationRepository
        .findByLatitudeAndLongitudeByRadius(
          minLatitude,
          maxLatitude,
          minLongitude,
          maxLongitude
        )
        .filter(l -> !l.isEmpty())
        .orElseThrow(() -> new LocationDoesntExistException());
    } catch (Exception e) {
      if (
        e instanceof LocationDoesntExistException
      ) throw e; else throw new DatabaseException("Error getting locations");
    }
  }

  /**
   * Gets all locations.
   * @return A list of locations.
   * @throws DatabaseException If there is an error getting the locations.
   */
  @Override
  public List<Location> getAllLocations() throws DatabaseException {
    try {
      return locationRepository.findAll();
    } catch (Exception e) {
      throw new DatabaseException("canNotGetLocation");
    }
  }
}
