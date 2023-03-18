package edu.ntnu.idatt2105.placeholder.mapper.listing;

import edu.ntnu.idatt2105.placeholder.dto.listing.ListingDTO;
import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.LocationDoesntExistException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.placeholder.model.listing.Listing;
import edu.ntnu.idatt2105.placeholder.model.location.Location;
import edu.ntnu.idatt2105.placeholder.model.user.User;
import edu.ntnu.idatt2105.placeholder.service.location.LocationService;
import edu.ntnu.idatt2105.placeholder.service.location.LocationServiceImpl;
import edu.ntnu.idatt2105.placeholder.service.user.UserService;
import edu.ntnu.idatt2105.placeholder.service.user.UserServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Class used to map between Listing and ListingDTO.
 * @author Nicolai H. B
 * @version 1.0 - 18.3.2023
 */
@Mapper(componentModel = "spring")
public interface ListingMapper {
  ListingMapper INSTANCE = Mappers.getMapper(ListingMapper.class);

  @Named("getLocation")
  public static Location getLocation(Long id)
    throws LocationDoesntExistException, DatabaseException {
    LocationService locationService = new LocationServiceImpl();
    return locationService.getLocationById(id);
  }

  @Named("getLocationId")
  public static Long getLocationId(Location location) {
    return location.getId();
  }

  @Named("getUser")
  public static User getUser(String username)
    throws UserDoesNotExistsException {
    UserService userService = new UserServiceImpl();
    return userService.getUserByUsername(username);
  }

  @Named("getUsername")
  public static String getUsername(User user) {
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
        target = "locationId",
        qualifiedByName = "getLocationId"
      ),
      @Mapping(
        source = "user",
        target = "username",
        qualifiedByName = "getUsername"
      ),
    }
  )
  ListingDTO listingToListingDTO(Listing listing);

  /**
   * Maps a ListingDTO to a Listing.
   * @param listingDTO The ListingDTO to map.
   * @return The mapped Listing.
   */
  @Mappings(
    {
      @Mapping(
        source = "locationId",
        target = "location",
        qualifiedByName = "getLocation"
      ),
      @Mapping(
        source = "username",
        target = "user",
        qualifiedByName = "getUser"
      ),
    }
  )
  Listing listingDTOToListing(ListingDTO listingDTO)
    throws LocationDoesntExistException, DatabaseException, UserDoesNotExistsException;
}
