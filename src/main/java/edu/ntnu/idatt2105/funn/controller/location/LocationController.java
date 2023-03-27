package edu.ntnu.idatt2105.funn.controller.location;

import edu.ntnu.idatt2105.funn.dto.location.LocationCreateDTO;
import edu.ntnu.idatt2105.funn.dto.location.LocationResponseDTO;
import edu.ntnu.idatt2105.funn.exceptions.DatabaseException;
import edu.ntnu.idatt2105.funn.exceptions.PermissionDeniedException;
import edu.ntnu.idatt2105.funn.exceptions.location.LocationAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.location.LocationDoesntExistException;
import edu.ntnu.idatt2105.funn.exceptions.location.PostCodeAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.validation.BadInputException;
import edu.ntnu.idatt2105.funn.mapper.location.LocationMapper;
import edu.ntnu.idatt2105.funn.model.location.Location;
import edu.ntnu.idatt2105.funn.model.location.PostCode;
import edu.ntnu.idatt2105.funn.model.user.Role;
import edu.ntnu.idatt2105.funn.security.Auth;
import edu.ntnu.idatt2105.funn.service.location.LocationService;
import edu.ntnu.idatt2105.funn.service.location.PostCodeService;
import edu.ntnu.idatt2105.funn.validation.AuthValidation;
import edu.ntnu.idatt2105.funn.validation.LocationValidation;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for locations.
 * Mappings for getting all, getting one,
 * creating, updating and deleting locations.
 * @author Callum G.
 * @version 1.0 - 21.3.2023
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LocationController {

  private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

  private final LocationService locationService;

  private final PostCodeService postCodeService;

  /**
   * Returns all locations in the database.
   * @return List of all locations
   * @throws DatabaseException If the database is not available
   */
  @GetMapping(value = "/public/locations", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Get all locations", description = "Returns all locations in the database.")
  public ResponseEntity<List<LocationResponseDTO>> getAllLocations() throws DatabaseException {
    LOGGER.info("Received request to get all locations");
    List<Location> locations = locationService.getAllLocations();

    LOGGER.info("Found {} locations", locations.size());

    List<LocationResponseDTO> locationResponseDTOs = locations
      .stream()
      .map(l -> LocationMapper.INSTANCE.locationToLocationResponseDTO(l))
      .toList();

    LOGGER.info("Returning {} locations", locationResponseDTOs.size());

    return ResponseEntity.ok(locationResponseDTOs);
  }

  /**
   * Returns a location by id.
   * @param id The id of the location to return.
   * @return The location with the given id.
   * @throws LocationDoesntExistException If the location does not exist.
   * @throws DatabaseException If the database is not available
   */
  @GetMapping(value = "/public/locations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
    summary = "Get location by id",
    description = "Returns a location by id if it exists in the database."
  )
  public ResponseEntity<LocationResponseDTO> getLocationById(@PathVariable Long id)
    throws LocationDoesntExistException, DatabaseException {
    LOGGER.info("Received request to get location with id {}", id);
    Location location = locationService.getLocationById(id);

    LOGGER.info("Found location with id {}", id);

    LocationResponseDTO locationResponseDTO = LocationMapper.INSTANCE.locationToLocationResponseDTO(
      location
    );

    LOGGER.info("Returning location with id {}", id);

    return ResponseEntity.ok(locationResponseDTO);
  }

  /**
   * Creates a location.
   * @param locationCreateDTO The location to create.
   * @return The created location with an id.
   * @throws LocationAlreadyExistsException If the location already exists.
   * @throws DatabaseException If the database is not available.
   */
  @PostMapping(value = "/private/locations", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Create location", description = "Creates a location.")
  public ResponseEntity<LocationResponseDTO> createLocation(
    @RequestBody LocationCreateDTO locationCreateDTO
  ) throws LocationAlreadyExistsException, DatabaseException, BadInputException {
    if (
      !LocationValidation.validateLocation(
        locationCreateDTO.getAddress(),
        locationCreateDTO.getLatitude(),
        locationCreateDTO.getLongitude(),
        locationCreateDTO.getPostCode(),
        locationCreateDTO.getCity()
      )
    ) {
      throw new BadInputException("The creation input is not valid.");
    }

    LOGGER.info("Received request to create location {}", locationCreateDTO);

    Location location = LocationMapper.INSTANCE.locationCreateDTOToLocation(locationCreateDTO);

    PostCode postCode = LocationMapper.INSTANCE.locationCreateDTOToPostCode(locationCreateDTO);

    LOGGER.info("Found post code with code {}", postCode.getPostCode());

    try {
      postCodeService.savePostCode(postCode);
      LOGGER.info("Saved post code with code {}", postCode.getPostCode());
    } catch (PostCodeAlreadyExistsException e) {
      LOGGER.info("Post code already exists");
    }

    location.setPostCode(postCode);

    LOGGER.info("Creating location {}", location);

    location = locationService.saveLocation(location);

    LOGGER.info("Created location {}", location);

    LocationResponseDTO locationResponseDTO = LocationMapper.INSTANCE.locationToLocationResponseDTO(
      location
    );

    LOGGER.info("Returning created location {}", locationResponseDTO);

    return ResponseEntity.ok(locationResponseDTO);
  }

  /**
   * Updates a location by id.
   * @param locationResponseDTO The location to update.
   * @param id The id of the location to update.
   * @param auth The authentication object trying to update the location.
   * @return The updated location with the given id.
   * @throws PermissionDeniedException If the user is not an admin.
   * @throws BadInputException If the input is not valid.
   * @throws LocationDoesntExistException If the location does not exist.
   * @throws DatabaseException If the database is not available.
   */
  @PutMapping(value = "/private/locations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
    summary = "Update location by id",
    description = "Updates a location by id if it exists in the database, and returns the updated location. Only admins can update locations."
  )
  public ResponseEntity<LocationResponseDTO> updateLocationById(
    @RequestBody LocationResponseDTO locationResponseDTO,
    @PathVariable Long id,
    @AuthenticationPrincipal Auth auth
  )
    throws PermissionDeniedException, LocationDoesntExistException, DatabaseException, BadInputException {
    if (!AuthValidation.hasRole(auth, Role.ADMIN)) throw new PermissionDeniedException(
      "You do not have permission to update locations."
    );

    if (
      !LocationValidation.validateLocation(
        locationResponseDTO.getAddress(),
        locationResponseDTO.getLatitude(),
        locationResponseDTO.getLongitude(),
        locationResponseDTO.getPostCode(),
        locationResponseDTO.getCity()
      )
    ) throw new BadInputException("The update input is not valid for this location.");

    if (locationResponseDTO.getId() != id) {
      throw new LocationDoesntExistException(
        "The id in the request body does not match the id in the path variable."
      );
    }

    LOGGER.info("Received request to update location with id {}", id);
    Location location = LocationMapper.INSTANCE.locationResponseDTOToLocation(locationResponseDTO);

    LOGGER.info("Found location with id {}", id);

    PostCode postCode = LocationMapper.INSTANCE.locationResponseDTOToPostCode(locationResponseDTO);

    LOGGER.info("Found post code with code {}", postCode.getPostCode());

    try {
      postCodeService.savePostCode(postCode);
      LOGGER.info("Saved post code with code {}", postCode.getPostCode());
    } catch (PostCodeAlreadyExistsException e) {
      LOGGER.info("Post code already exists");
    }

    location.setPostCode(postCode);

    LOGGER.info("Updating location with id {}", id);
    locationService.updateLocation(location);

    LOGGER.info("Updated location with id {}", id);

    LocationResponseDTO updatedLocationResponseDTO = LocationMapper.INSTANCE.locationToLocationResponseDTO(
      location
    );

    LOGGER.info("Returning updated location with id {}", id);

    return ResponseEntity.ok(updatedLocationResponseDTO);
  }

  /**
   * Deletes a location by id.
   * @param id The id of the location to delete.
   * @param auth The authentication object trying to delete the location.
   * @return 204 No Content with no body.
   * @throws PermissionDeniedException If the user is not an admin.
   * @throws LocationDoesntExistException If the location does not exist.
   * @throws NullPointerException If the location is null.
   * @throws DatabaseException If the database is not available.
   */
  @DeleteMapping(value = "/private/locations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Delete location by id", description = "Deletes a location by id.")
  public ResponseEntity<Void> deleteLocation(
    @PathVariable Long id,
    @AuthenticationPrincipal Auth auth
  )
    throws PermissionDeniedException, LocationDoesntExistException, NullPointerException, DatabaseException {
    if (!AuthValidation.hasRole(auth, Role.ADMIN)) throw new PermissionDeniedException(
      "You do not have permission to delete locations."
    );

    LOGGER.info("Received request to delete location with id {}", id);

    locationService.deleteLocation(id);

    LOGGER.info("Deleted location with id {}", id);

    LOGGER.info("Returning 204 No Content");
    return ResponseEntity.noContent().build();
  }
}
