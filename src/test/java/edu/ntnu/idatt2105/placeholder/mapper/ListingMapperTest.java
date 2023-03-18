package edu.ntnu.idatt2105.placeholder.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import edu.ntnu.idatt2105.placeholder.dto.listing.ListingDTO;
import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.LocationDoesntExistException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.placeholder.mapper.listing.ListingMapper;
import edu.ntnu.idatt2105.placeholder.model.listing.Category;
import edu.ntnu.idatt2105.placeholder.model.listing.Listing;
import edu.ntnu.idatt2105.placeholder.model.location.Location;
import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import edu.ntnu.idatt2105.placeholder.model.user.Role;
import edu.ntnu.idatt2105.placeholder.model.user.User;
import edu.ntnu.idatt2105.placeholder.service.location.LocationService;
import edu.ntnu.idatt2105.placeholder.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ListingMapperTest {

  @MockBean
  private LocationService locationService;

  @MockBean
  private UserService userService;

  User user;

  Location location;

  PostCode postCode;

  @Before
  public void setUp()
    throws UserDoesNotExistsException, LocationDoesntExistException, DatabaseException {
    postCode = new PostCode("0000", "Oslo");

    location =
      Location
        .builder()
        .id(1L)
        .address("address")
        .postCode(postCode)
        .latitude(0.0D)
        .longitude(0.0D)
        .build();

    user =
      User
        .builder()
        .username("username")
        .password("password")
        .firstName("firstName")
        .lastName("lastName")
        .email("email")
        .role(Role.USER)
        .build();

    when(locationService.getLocationById(1L)).thenReturn(location);
    when(userService.getUserByUsername("username")).thenReturn(user);
  }

  @Test
  public void testMapListingToDTO() {
    Listing listing = Listing
      .builder()
      .id(1L)
      .title("title")
      .briefDescription("description")
      .fullDescription("description")
      .price(1000)
      .location(location)
      .user(user)
      .category(Category.OTHER)
      .publicationDate(LocalDate.of(2012, 12, 12))
      .expirationDate(LocalDate.of(2013, 6, 12))
      .build();
    ListingDTO dto = ListingMapper.INSTANCE.listingToListingDTO(listing);

    assertEquals(listing.getId(), dto.getId());
    assertEquals(listing.getTitle(), dto.getTitle());
    assertEquals(listing.getFullDescription(), dto.getFullDescription());
    assertEquals(listing.getPrice(), dto.getPrice());
    assertEquals(listing.getLocation().getId(), dto.getLocationId());
    assertEquals(listing.getUser().getUsername(), dto.getUsername());
  }

  @Test
  public void testMapDTOToListing() {
    ListingDTO dto = ListingDTO.builder().title("title").price(1000).locationId(1L).username("username").briefDescription("description").fullDescription("description").category(Category.OTHER).publicationDate(LocalDate.of(2012, 12, 12)).expirationDate(LocalDate.of(2013, 6, 12)).build();
  }
}
