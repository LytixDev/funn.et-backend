package edu.ntnu.idatt2105.funn.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import edu.ntnu.idatt2105.funn.exceptions.listing.ListingAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.listing.ListingNotFoundException;
import edu.ntnu.idatt2105.funn.model.listing.Category;
import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.model.location.Location;
import edu.ntnu.idatt2105.funn.model.location.PostCode;
import edu.ntnu.idatt2105.funn.model.user.Role;
import edu.ntnu.idatt2105.funn.model.user.User;
import edu.ntnu.idatt2105.funn.repository.listing.ListingRepository;
import edu.ntnu.idatt2105.funn.service.listing.ListingService;
import edu.ntnu.idatt2105.funn.service.listing.ListingServiceImpl;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ListingServiceIntegrationTest {

  @TestConfiguration
  static class ListingServiceIntegrationTestContextConfiguration {

    @Bean
    public ListingService listingService() {
      return new ListingServiceImpl();
    }
  }

  @Autowired
  private ListingService listingService;

  @MockBean
  private ListingRepository listingRepository;

  private Listing existingListing;

  private Listing newListing;

  @Before
  public void setUp() {
    User user = new User(
      "username",
      "email",
      "firstName",
      "lastName",
      "password",
      new HashSet<>(),
      new HashSet<>(),
      Role.USER
    );

    PostCode postCode = new PostCode(1234, "Oslo", new HashSet<>());

    Location location = Location
      .builder()
      .id(1L)
      .address("Testveien 1")
      .postCode(postCode)
      .latitude(59.9127D)
      .longitude(10.7461D)
      .listings(new HashSet<>())
      .build();

    existingListing =
      Listing
        .builder()
        .id(1L)
        .title("Test")
        .briefDescription("Test")
        .fullDescription("Test")
        .price(1000)
        .category(Category.BOOKS)
        .expirationDate(LocalDate.of(2021, 12, 31))
        .publicationDate(LocalDate.of(2020, 12, 31))
        .user(user)
        .location(location)
        .build();

    newListing =
      Listing
        .builder()
        .id(2L)
        .title("Test2")
        .briefDescription("Test2")
        .fullDescription("Test2")
        .price(2000)
        .category(Category.BOOKS)
        .expirationDate(LocalDate.of(2021, 12, 31))
        .publicationDate(LocalDate.of(2020, 12, 31))
        .user(user)
        .location(location)
        .build();

    when(listingRepository.save(existingListing)).thenReturn(existingListing);
    when(listingRepository.save(newListing)).thenReturn(newListing);

    when(listingRepository.existsById(existingListing.getId())).thenReturn(true);
    when(listingRepository.existsById(newListing.getId())).thenReturn(false);

    when(listingRepository.findById(existingListing.getId()))
      .thenReturn(Optional.of(existingListing));
    when(listingRepository.findById(newListing.getId())).thenReturn(Optional.empty());

    when(listingRepository.findAll()).thenReturn(List.of(existingListing));

    doNothing().when(listingRepository).delete(existingListing);
    doNothing().when(listingRepository).delete(newListing);
  }

  @Test
  public void testListingExistsNewListing() {
    assertEquals(listingService.listingExists(newListing), false);
  }

  @Test
  public void testListingExistsExistingListing() {
    assertEquals(listingService.listingExists(existingListing), true);
  }

  @Test
  public void testSaveListingThatDoesNotExist() {
    Listing savedListing;
    try {
      savedListing = listingService.saveListing(newListing);
    } catch (Exception e) {
      fail();
      return;
    }

    assertEquals(savedListing.getTitle(), newListing.getTitle());
    assertEquals(savedListing.getBriefDescription(), newListing.getBriefDescription());
    assertEquals(savedListing.getFullDescription(), newListing.getFullDescription());
    assertEquals(savedListing.getPrice(), newListing.getPrice());
    assertEquals(savedListing.getCategory(), newListing.getCategory());
    assertEquals(savedListing.getExpirationDate(), newListing.getExpirationDate());
    assertEquals(savedListing.getPublicationDate(), newListing.getPublicationDate());
    assertEquals(savedListing.getUser().getUsername(), newListing.getUser().getUsername());
    assertEquals(savedListing.getLocation().getAddress(), newListing.getLocation().getAddress());
  }

  @Test
  public void testSaveListingThatAlreadyExists() {
    try {
      listingService.saveListing(existingListing);
      fail();
    } catch (ListingAlreadyExistsException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testUpdateListingThatDoesNotExist() {
    try {
      listingService.updateListing(newListing);
      fail();
    } catch (ListingNotFoundException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testUpdateListingThatAlreadyExists() {
    Listing updatedListing;
    try {
      updatedListing = listingService.updateListing(existingListing);
    } catch (Exception e) {
      fail();
      return;
    }

    assertEquals(updatedListing.getTitle(), existingListing.getTitle());
    assertEquals(updatedListing.getBriefDescription(), existingListing.getBriefDescription());
    assertEquals(updatedListing.getFullDescription(), existingListing.getFullDescription());
    assertEquals(updatedListing.getPrice(), existingListing.getPrice());
    assertEquals(updatedListing.getCategory(), existingListing.getCategory());
    assertEquals(updatedListing.getExpirationDate(), existingListing.getExpirationDate());
    assertEquals(updatedListing.getPublicationDate(), existingListing.getPublicationDate());
    assertEquals(updatedListing.getUser().getUsername(), existingListing.getUser().getUsername());
    assertEquals(
      updatedListing.getLocation().getAddress(),
      existingListing.getLocation().getAddress()
    );
  }

  @Test
  public void testDeleteListingThatDoesNotExist() {
    try {
      listingService.deleteListing(newListing);
      fail();
    } catch (ListingNotFoundException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testDeleteListingThatAlreadyExists() {
    try {
      listingService.deleteListing(existingListing);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetListingThatDoesNotExist() {
    try {
      listingService.getListing(newListing.getId());
      fail();
    } catch (ListingNotFoundException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetListingThatAlreadyExists() {
    Listing listing;
    try {
      listing = listingService.getListing(existingListing.getId());
    } catch (Exception e) {
      fail();
      return;
    }

    assertEquals(listing.getTitle(), existingListing.getTitle());
    assertEquals(listing.getBriefDescription(), existingListing.getBriefDescription());
    assertEquals(listing.getFullDescription(), existingListing.getFullDescription());
    assertEquals(listing.getPrice(), existingListing.getPrice());
    assertEquals(listing.getCategory(), existingListing.getCategory());
    assertEquals(listing.getExpirationDate(), existingListing.getExpirationDate());
    assertEquals(listing.getPublicationDate(), existingListing.getPublicationDate());
    assertEquals(listing.getUser().getUsername(), existingListing.getUser().getUsername());
    assertEquals(listing.getLocation().getAddress(), existingListing.getLocation().getAddress());
  }

  @Test
  public void testGetAllListings() {
    List<Listing> listings;
    try {
      listings = listingService.getAllListings();
    } catch (Exception e) {
      fail();
      return;
    }

    assertEquals(listings.size(), 1);
    assertEquals(listings.get(0).getTitle(), existingListing.getTitle());
    assertEquals(listings.get(0).getBriefDescription(), existingListing.getBriefDescription());
    assertEquals(listings.get(0).getFullDescription(), existingListing.getFullDescription());
    assertEquals(listings.get(0).getPrice(), existingListing.getPrice());
    assertEquals(listings.get(0).getCategory(), existingListing.getCategory());
    assertEquals(listings.get(0).getExpirationDate(), existingListing.getExpirationDate());
    assertEquals(listings.get(0).getPublicationDate(), existingListing.getPublicationDate());
    assertEquals(listings.get(0).getUser().getUsername(), existingListing.getUser().getUsername());
    assertEquals(
      listings.get(0).getLocation().getAddress(),
      existingListing.getLocation().getAddress()
    );
  }
}
