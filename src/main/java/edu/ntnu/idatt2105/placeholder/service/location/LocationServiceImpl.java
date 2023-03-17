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

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

  @Autowired
  private LocationRepository locationRepository;

  @Override
  public boolean locationExists(@NonNull Location location)
    throws NullPointerException {
    return locationRepository.existsById(location.getId());
  }

  @Override
  public Location saveLocation(@NonNull Location location)
    throws LocationAlreadyExistsException, DatabaseException, NullPointerException {
    if (locationExists(location)) throw new LocationAlreadyExistsException(
      "Location already exists"
    );

    try {
      return locationRepository.save(location);
    } catch (Exception e) {
      throw new DatabaseException("Error saving location");
    }
  }

  @Override
  public Location getLocationById(Long id)
    throws LocationDoesntExistException, DatabaseException {
    try {
      return locationRepository
        .findById(id)
        .orElseThrow(() ->
          new LocationDoesntExistException("Location does not exist")
        );
    } catch (Exception e) {
      throw new DatabaseException("Error getting location");
    }
  }

  @Override
  public Location updateLocation(@NonNull Location location)
    throws LocationDoesntExistException, DatabaseException, NullPointerException {
    if (!locationExists(location)) throw new LocationDoesntExistException(
      "Location does not exist"
    );

    try {
      return locationRepository.save(location);
    } catch (Exception e) {
      throw new DatabaseException("Error updating location");
    }
  }

  @Override
  public void deleteLocation(@NonNull Location location)
    throws LocationDoesntExistException, DatabaseException, NullPointerException {
    if (!locationExists(location)) throw new LocationDoesntExistException(
      "Location does not exist"
    );

    try {
      locationRepository.delete(location);
    } catch (Exception e) {
      throw new DatabaseException("Error deleting location");
    }
  }

  @Override
  public void deleteLocation(Long id)
    throws LocationDoesntExistException, DatabaseException {
    if (
      !locationRepository.existsById(id)
    ) throw new LocationDoesntExistException("Location does not exist");

    try {
      locationRepository.deleteById(id);
    } catch (Exception e) {
      throw new DatabaseException("Error deleting location");
    }
  }

  @Override
  public List<Location> getLocationsByPostCode(@NonNull String postCode)
    throws LocationDoesntExistException, DatabaseException, NullPointerException {
    try {
      return locationRepository
        .findLocationsByPostCode(postCode)
        .orElseThrow(() ->
          new LocationDoesntExistException("No locations found for " + postCode)
        );
    } catch (Exception e) {
      throw new DatabaseException("Error getting locations");
    }
  }

  @Override
  public List<Location> getLocationsByPostCode(@NonNull PostCode postCode)
    throws LocationDoesntExistException, DatabaseException, NullPointerException {
    try {
      return locationRepository
        .findLocationsByPostCode(postCode)
        .orElseThrow(() ->
          new LocationDoesntExistException("No locations found for " + postCode)
        );
    } catch (Exception e) {
      throw new DatabaseException("Error getting locations");
    }
  }

  @Override
  public List<Location> getLocationsByCity(@NonNull String city)
    throws LocationDoesntExistException, DatabaseException, NullPointerException {
    try {
      return locationRepository
        .findLocationsByCity(city)
        .orElseThrow(() ->
          new LocationDoesntExistException("No locations found for " + city)
        );
    } catch (Exception e) {
      throw new DatabaseException("Error getting locations");
    }
  }

  @Override
  public List<Location> getLocationsByLongitude(@NonNull Double longitude)
    throws LocationDoesntExistException, DatabaseException, NullPointerException {
    try {
      return locationRepository
        .findByLongitude(longitude)
        .orElseThrow(() ->
          new LocationDoesntExistException(
            "No locations found for longitude: " + longitude
          )
        );
    } catch (Exception e) {
      throw new DatabaseException("Error getting locations");
    }
  }

  @Override
  public List<Location> getLocationsByLatitude(@NonNull Double latitude)
    throws LocationDoesntExistException, DatabaseException, NullPointerException {
    try {
      return locationRepository
        .findByLatitude(latitude)
        .orElseThrow(() ->
          new LocationDoesntExistException(
            "No locations found for latitude: " + latitude
          )
        );
    } catch (Exception e) {
      throw new DatabaseException("Error getting locations");
    }
  }

  @Override
  public List<Location> getLocationsInDistance(
    @NonNull Double longitude,
    @NonNull Double latitude,
    @NonNull Double distance
  )
    throws LocationDoesntExistException, DatabaseException, NullPointerException {
    Double minLongitude = longitude - distance;
    Double maxLongitude = longitude + distance;
    Double minLatitude = latitude - distance;
    Double maxLatitude = latitude + distance;
    try {
      return locationRepository
        .findByLatitudeAndLongitudeByRadius(
          minLatitude,
          maxLatitude,
          minLongitude,
          maxLongitude
        )
        .orElseThrow(() ->
          new LocationDoesntExistException(
            "No locations found in radius: " +
            distance +
            " from longitude: " +
            longitude +
            " and latitude: " +
            latitude
          )
        );
    } catch (Exception e) {
      throw new DatabaseException("Error getting locations");
    }
  }

  @Override
  public List<Location> getAllLocations() throws DatabaseException {
    try {
      return locationRepository.findAll();
    } catch (Exception e) {
      throw new DatabaseException("Error getting locations");
    }
  }
}
