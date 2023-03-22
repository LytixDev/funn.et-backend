package edu.ntnu.idatt2105.funn.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import edu.ntnu.idatt2105.funn.dto.listing.ListingDTO;
import edu.ntnu.idatt2105.funn.exceptions.DatabaseException;
import edu.ntnu.idatt2105.funn.exceptions.location.LocationDoesntExistException;
import edu.ntnu.idatt2105.funn.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.funn.mapper.listing.ListingMapper;
import edu.ntnu.idatt2105.funn.mapper.listing.ListingMapperImpl;
import edu.ntnu.idatt2105.funn.model.listing.Category;
import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.model.location.Location;
import edu.ntnu.idatt2105.funn.model.location.PostCode;
import edu.ntnu.idatt2105.funn.model.user.Role;
import edu.ntnu.idatt2105.funn.model.user.User;
import edu.ntnu.idatt2105.funn.service.location.LocationService;
import edu.ntnu.idatt2105.funn.service.user.UserService;
import java.time.LocalDate;
import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ListingMapperTest {

  @TestConfiguration
  static class ListingMapperTestContextConfiguration {

    @Bean
    public ListingMapper listingMapper() {
      return new ListingMapperImpl();
    }
  }

  @Autowired
  private ListingMapper listingMapper;

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
    postCode = new PostCode(0000, "Oslo", new HashSet<>());

    location =
      Location
        .builder()
        .id(1L)
        .address("address")
        .postCode(postCode)
        .latitude(0.0D)
        .longitude(0.0D)
        .listings(new HashSet<>())
        .build();

    user =
      User
        .builder()
        .username("username")
        .password("password")
        .firstName("firstName")
        .lastName("lastName")
        .email("email")
        .listings(new HashSet<>())
        .role(Role.USER)
        .build();

    when(locationService.getLocationById(location.getId())).thenReturn(location);
    when(userService.getUserByUsername(user.getUsername())).thenReturn(user);
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
    ListingDTO dto = listingMapper.listingToListingDTO(listing);

    assertEquals(listing.getId(), dto.getId());
    assertEquals(listing.getTitle(), dto.getTitle());
    assertEquals(listing.getFullDescription(), dto.getFullDescription());
    assertEquals(listing.getPrice(), dto.getPrice());
    assertEquals(listing.getLocation().getId(), dto.getLocation());
    assertEquals(listing.getUser().getUsername(), dto.getUsername());
    assertEquals(listing.getCategory(), dto.getCategory());
    assertEquals(listing.getPublicationDate(), dto.getPublicationDate());
    assertEquals(listing.getExpirationDate(), dto.getExpirationDate());
    assertEquals(listing.getBriefDescription(), dto.getBriefDescription());
  }

  @Test
  public void testMapDTOToListing() {
    ListingDTO dto = ListingDTO
      .builder()
      .id(1L)
      .title("title")
      .price(1000)
      .location(1L)
      .username("username")
      .briefDescription("description")
      .fullDescription("description")
      .category(Category.OTHER)
      .publicationDate(LocalDate.of(2012, 12, 12))
      .expirationDate(LocalDate.of(2013, 6, 12))
      .build();

    Listing listing;

    try {
      listing = listingMapper.listingDTOToListing(dto);
    } catch (Exception e) {
      fail(e.getMessage());
      return;
    }

    assertEquals(dto.getTitle(), listing.getTitle());
    assertEquals(dto.getFullDescription(), listing.getFullDescription());
    assertEquals(dto.getPrice(), listing.getPrice());
    assertEquals(dto.getLocation(), listing.getLocation().getId());
    assertEquals(dto.getUsername(), listing.getUser().getUsername());
    assertEquals(dto.getCategory(), listing.getCategory());
    assertEquals(dto.getPublicationDate(), listing.getPublicationDate());
    assertEquals(dto.getExpirationDate(), listing.getExpirationDate());
    assertEquals(dto.getBriefDescription(), listing.getBriefDescription());
  }
}
