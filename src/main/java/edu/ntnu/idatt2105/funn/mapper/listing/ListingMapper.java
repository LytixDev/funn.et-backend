package edu.ntnu.idatt2105.funn.mapper.listing;

import edu.ntnu.idatt2105.funn.controller.file.ImageController;
import edu.ntnu.idatt2105.funn.dto.file.ImageResponseDTO;
import edu.ntnu.idatt2105.funn.dto.listing.ListingCreateDTO;
import edu.ntnu.idatt2105.funn.dto.listing.ListingDTO;
import edu.ntnu.idatt2105.funn.exceptions.DatabaseException;
import edu.ntnu.idatt2105.funn.exceptions.file.FileNotFoundException;
import edu.ntnu.idatt2105.funn.exceptions.location.LocationDoesntExistException;
import edu.ntnu.idatt2105.funn.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.funn.model.file.Image;
import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.model.location.Location;
import edu.ntnu.idatt2105.funn.model.user.User;
import edu.ntnu.idatt2105.funn.service.file.ImageService;
import edu.ntnu.idatt2105.funn.service.location.LocationService;
import edu.ntnu.idatt2105.funn.service.user.UserService;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.zalando.fauxpas.FauxPas;

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

  @Autowired
  private ImageService imageService;

  /**
   * Gets a location from the database.
   * @param id The id of the location to get.
   * @return The location with the given id.
   * @throws LocationDoesntExistException If the location does not exist.
   * @throws DatabaseException If there is a problem with the database.
   */
  @Named("getLocation")
  public Location getLocation(Long id) throws LocationDoesntExistException, DatabaseException {
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
   * Gets the images of a listing.
   * @param listingDTO The listing data transfer object to get the images from.
   * @return The images of the listing.
   */
  @Named("getImages")
  public List<Image> getImages(ListingDTO listingDTO)
    throws FileNotFoundException, DatabaseException {
    Function<Long, Image> getImage = FauxPas.throwingFunction(imageService::getFile);
    return listingDTO
      .getImageResponse()
      .stream()
      .map(image -> getImage.apply(image.getId()))
      .collect(Collectors.toList());
  }

  /**
   * Gets the images of a listing.
   * @param listing The listing to get the images from.
   * @return The images of the listing.
   */
  public List<ImageResponseDTO> getImages(List<Image> images) {
    return images
      .stream()
      .map(image ->
        new ImageResponseDTO(
          image.getId(),
          MvcUriComponentsBuilder
            .fromMethodName(ImageController.class, "getImage", image.getId())
            .build()
            .toString(),
          image.getAlt()
        )
      )
      .collect(Collectors.toList());
  }

  /**
   * Maps a Listing to a ListingDTO.
   * @param listing The Listing to map.
   * @return The mapped ListingDTO.
   */
  @Mappings(
    {
      @Mapping(source = "location", target = "location", qualifiedByName = "getLocationId"),
      @Mapping(source = "user", target = "username", qualifiedByName = "getUsername"),
      @Mapping(source = "images", target = "imageResponse", qualifiedByName = "getImages"),
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
      @Mapping(source = "location", target = "location", qualifiedByName = "getLocation"),
      @Mapping(source = "username", target = "user", qualifiedByName = "getUser"),
      @Mapping(source = "imageResponse", target = "images", qualifiedByName = "getImages"),
    }
  )
  public abstract Listing listingDTOToListing(ListingDTO listingDTO)
    throws LocationDoesntExistException, DatabaseException, UserDoesNotExistsException;

  /**
   * Maps a ListingDTO to a Listing.
   * @param listingDTO The ListingDTO to map.
   * @return The mapped Listing.
   */
  @Mappings(
    {
      @Mapping(source = "location", target = "location", qualifiedByName = "getLocation"),
      @Mapping(source = "username", target = "user", qualifiedByName = "getUser"),
      @Mapping(target = "images", ignore = true),
      @Mapping(target = "id", ignore = true),
    }
  )
  public abstract Listing listingCreateDTOToListing(ListingCreateDTO listingDTO)
    throws LocationDoesntExistException, DatabaseException, UserDoesNotExistsException;
}
