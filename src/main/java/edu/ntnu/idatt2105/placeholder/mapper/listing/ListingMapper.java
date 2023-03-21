package edu.ntnu.idatt2105.placeholder.mapper.listing;

import edu.ntnu.idatt2105.placeholder.dto.listing.ListingDTO;
import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.LocationDoesntExistException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.placeholder.model.listing.Listing;
import edu.ntnu.idatt2105.placeholder.model.location.Location;
import edu.ntnu.idatt2105.placeholder.model.user.User;
import edu.ntnu.idatt2105.placeholder.service.location.LocationService;
import edu.ntnu.idatt2105.placeholder.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class used to map between Listing and ListingDTO.
 * @author Nicolai H. B, Callum G.
 * @version 1.1 - 18.3.2023
 */
@RequiredArgsConstructor
@Mapper(componentModel = "spring")
public abstract class ListingMapper {

  public static ListingMapper INSTANCE = Mappers.getMapper(ListingMapper.class);

  @Autowired
  private LocationService locationService;

  @Autowired
  private UserService userService;

  /**
   * Gets a location from the database.
   * @param id The id of the location to get.
   * @return The location with the given id.
   * @throws LocationDoesntExistException If the location does not exist.
   * @throws DatabaseException If there is a problem with the database.
   */
  @Named("getLocation")
  public Location getLocation(Long id)
    throws LocationDoesntExistException, DatabaseException {
    return locationService.getLocationById(id);
  }

  /**
   * Gets the id of a location.
   * @param location The location to get the id from.
   * @return The id of the location.
   */
  @Named("getLocationId")
  public Long getLocationId(Location location) {
    return location.getId();
  }

  /**
   * Gets a user from the database.
   * @param username The username of the user to get.
   * @return The user with the given username.
   * @throws UserDoesNotExistsException If the user does not exist.
   */
  @Named("getUser")
  public User getUser(String username) throws UserDoesNotExistsException {
    return userService.getUserByUsername(username);
  }

  /**
   * Gets the username of a user.
   * @param user The user to get the username from.
   * @return The username of the user.
   */
  @Named("getUsername")
  public String getUsername(User user) {
    return user.getUsername();
  }

  /**
   * Maps a Listing to a ListingDTO.
   * @param listing The Listing to map.
   * @return The mapped ListingDTO.
   */
  @Mappings(
    {
      @Mapping(
        source = "location",
        target = "location",
        qualifiedByName = "getLocationId"
      ),
      @Mapping(
        source = "user",
        target = "username",
        qualifiedByName = "getUsername"
      ),
      @Mapping(
        target = "imageResponse", 
        ignore = true
      ),
      @Mapping(
        target = "imageUpload", 
        ignore = true
      ),
    }
  )
  public abstract ListingDTO listingToListingDTO(Listing listing);

  /**
   * Maps a ListingDTO to a Listing.
   * @param listingDTO The ListingDTO to map.
   * @return The mapped Listing.
   */
  @Mappings(
    {
      @Mapping(
        source = "location",
        target = "location",
        qualifiedByName = "getLocation"
      ),
      @Mapping(
        source = "username",
        target = "user",
        qualifiedByName = "getUser"
      ),
      @Mapping(
        target = "images", 
        ignore = true
      ),
    }
  )
  public abstract Listing listingDTOToListing(ListingDTO listingDTO)
    throws LocationDoesntExistException, DatabaseException, UserDoesNotExistsException;
}
