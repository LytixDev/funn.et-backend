package edu.ntnu.idatt2105.placeholder.service.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.LocationAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.LocationDoesntExistException;
import edu.ntnu.idatt2105.placeholder.model.location.Location;
import edu.ntnu.idatt2105.placeholder.repository.location.LocationRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    
    @Autowired
    private LocationRepository locationRepository;

    @Override
    public boolean locationExists(@NonNull Location location) throws NullPointerException {
        return locationRepository.existsById(location.getId());
    }

    @Override
    public Location saveLocation(@NonNull Location location) throws LocationAlreadyExistsException, DatabaseException, NullPointerException {
        if (locationExists(location))
            throw new LocationAlreadyExistsException("Location already exists");

        try {
            return locationRepository.save(location);
        } catch (Exception e) {
            throw new DatabaseException("Error saving location");
        }
    }

    @Override
    public Location getLocationById(Long id) throws LocationDoesntExistException, DatabaseException, NullPointerException {
        try {
            return locationRepository.findById(id).orElseThrow(() -> new LocationDoesntExistException("Location does not exist"));
        } catch (Exception e) {
            throw new DatabaseException("Error getting location");
        }
    }
}
