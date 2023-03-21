package edu.ntnu.idatt2105.placeholder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import edu.ntnu.idatt2105.placeholder.exceptions.location.LocationAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.LocationDoesntExistException;
import edu.ntnu.idatt2105.placeholder.model.location.Location;
import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import edu.ntnu.idatt2105.placeholder.repository.location.LocationRepository;
import edu.ntnu.idatt2105.placeholder.service.location.LocationService;
import edu.ntnu.idatt2105.placeholder.service.location.LocationServiceImpl;
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
public class LocationServiceIntegrationTest {

  @TestConfiguration
  static class LocationServiceTestConfiguration {

    @Bean
    public LocationService locationService() {
      return new LocationServiceImpl();
    }
  }

  @Autowired
  LocationService locationService;

  @MockBean
  LocationRepository locationRepository;

  PostCode existingPostCode;

  PostCode nonExistingPostCode;

  Location existingLocation;

  Location nonExistingLocation;

  List<Location> existingList;

  List<Location> found;

  Location foundLocation;

  @Before
  public void setUp() {
    // Positive tests setup
    existingPostCode = new PostCode(1234, "Oslo", new HashSet<>());

    existingLocation =
      Location
        .builder()
        .id(1L)
        .address("Testveien 1")
        .postCode(existingPostCode)
        .latitude(59.9127D)
        .longitude(10.7461D)
        .listings(new HashSet<>())
        .build();

    existingList = List.of(existingLocation);

    when(
      locationRepository.findLocationsByCity(
        existingLocation.getPostCode().getCity()
      )
    )
      .thenReturn(Optional.of(existingList));

    when(
      locationRepository.findLocationsByPostCode(existingLocation.getPostCode())
    )
      .thenReturn(Optional.of(existingList));

    when(locationRepository.save(existingLocation))
      .thenReturn(existingLocation);

    when(locationRepository.findById(existingLocation.getId()))
      .thenReturn(Optional.of(existingLocation));

    when(
      locationRepository.findLocationsByCity(
        existingLocation.getPostCode().getCity()
      )
    )
      .thenReturn(Optional.of(existingList));

    when(
      locationRepository.findLocationsByPostCode(
        existingLocation.getPostCode().getPostCode()
      )
    )
      .thenReturn(Optional.of(existingList));

    when(
      locationRepository.findLocationsByPostCode(existingLocation.getPostCode())
    )
      .thenReturn(Optional.of(existingList));

    when(locationRepository.findByLatitude(existingLocation.getLatitude()))
      .thenReturn(Optional.of(existingList));

    when(locationRepository.findByLongitude(existingLocation.getLongitude()))
      .thenReturn(Optional.of(existingList));

    doNothing().when(locationRepository).delete(existingLocation);

    when(
      locationRepository.findByLatitudeAndLongitudeByRadius(
        existingLocation.getLatitude() - 1.0D,
        existingLocation.getLatitude() + 1.0D,
        existingLocation.getLongitude() - 1.0D,
        existingLocation.getLongitude() + 1.0D
      )
    )
      .thenReturn(Optional.of(existingList));

    // Negative tests setup

    nonExistingPostCode = new PostCode(1235, "Drangedal", new HashSet<>());

    nonExistingLocation =
      Location
        .builder()
        .id(2L)
        .address("Testveien 2")
        .postCode(nonExistingPostCode)
        .latitude(0.0)
        .longitude(0.0)
        .build();

    when(
      locationRepository.findLocationsByCity(
        nonExistingLocation.getPostCode().getCity()
      )
    )
      .thenReturn(Optional.empty());

    when(
      locationRepository.findLocationsByPostCode(
        nonExistingLocation.getPostCode()
      )
    )
      .thenReturn(Optional.empty());

    when(locationRepository.save(nonExistingLocation))
      .thenReturn(nonExistingLocation);

    when(locationRepository.findById(nonExistingLocation.getId()))
      .thenReturn(Optional.empty());

    when(
      locationRepository.findLocationsByCity(
        nonExistingLocation.getPostCode().getCity()
      )
    )
      .thenReturn(Optional.of(List.of()));

    when(
      locationRepository.findLocationsByPostCode(
        nonExistingLocation.getPostCode().getPostCode()
      )
    )
      .thenReturn(Optional.of(List.of()));

    when(
      locationRepository.findLocationsByPostCode(
        nonExistingLocation.getPostCode()
      )
    )
      .thenReturn(Optional.of(List.of()));

    when(locationRepository.findByLatitude(nonExistingLocation.getLatitude()))
      .thenReturn(Optional.of(List.of()));

    when(locationRepository.findByLongitude(nonExistingLocation.getLongitude()))
      .thenReturn(Optional.of(List.of()));

    doNothing().when(locationRepository).delete(nonExistingLocation);

    when(
      locationRepository.findByLatitudeAndLongitudeByRadius(
        nonExistingLocation.getLatitude() - 1.0D,
        nonExistingLocation.getLatitude() + 1.0D,
        nonExistingLocation.getLongitude() - 1.0D,
        nonExistingLocation.getLongitude() + 1.0D
      )
    )
      .thenReturn(Optional.of(List.of()));
  }

  @Test
  public void testGetLocationsByCityExisting() {
    try {
      found =
        locationService.getLocationsByCity(
          existingLocation.getPostCode().getCity()
        );
    } catch (Exception e) {
      fail();
      return;
    }

    assertEquals(found.get(0).getAddress(), existingLocation.getAddress());
    assertEquals(found.get(0).getPostCode(), existingLocation.getPostCode());
    assertEquals(found.get(0).getLatitude(), existingLocation.getLatitude());
    assertEquals(found.get(0).getLongitude(), existingLocation.getLongitude());
  }

  @Test
  public void testGetLocationsByCityNonExisting() {
    try {
      locationService.getLocationsByCity(
        nonExistingLocation.getPostCode().getCity()
      );
      fail();
    } catch (LocationDoesntExistException e) {
      return;
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void testGetLocationsByPostCodeExisting() {
    try {
      found =
        locationService.getLocationsByPostCode(existingLocation.getPostCode());
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }

    assertEquals(found.get(0).getAddress(), existingLocation.getAddress());
    assertEquals(found.get(0).getPostCode(), existingLocation.getPostCode());
    assertEquals(found.get(0).getLatitude(), existingLocation.getLatitude());
    assertEquals(found.get(0).getLongitude(), existingLocation.getLongitude());
  }

  @Test
  public void testGetLocationsByPostCodeNonExisting() {
    try {
      locationService.getLocationsByPostCode(nonExistingLocation.getPostCode());
      fail();
    } catch (LocationDoesntExistException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetLocationsByPostCodeStringExisting() {
    try {
      found =
        locationService.getLocationsByPostCode(
          existingLocation.getPostCode().getPostCode()
        );
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }

    assertEquals(found.get(0).getAddress(), existingLocation.getAddress());
    assertEquals(found.get(0).getPostCode(), existingLocation.getPostCode());
    assertEquals(found.get(0).getLatitude(), existingLocation.getLatitude());
    assertEquals(found.get(0).getLongitude(), existingLocation.getLongitude());
  }

  @Test
  public void testGetLocationsByPostCodeStringNonExisting() {
    try {
      locationService.getLocationsByPostCode(
        nonExistingLocation.getPostCode().getPostCode()
      );
      fail();
    } catch (LocationDoesntExistException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testSaveLocationExisting() {
    try {
      locationService.saveLocation(existingLocation);
      fail();
    } catch (LocationAlreadyExistsException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testSaveLocationNonExisting() {
    try {
      foundLocation = locationService.saveLocation(nonExistingLocation);
    } catch (Exception e) {
      fail();
    }

    assertEquals(foundLocation.getAddress(), nonExistingLocation.getAddress());
    assertEquals(
      foundLocation.getPostCode(),
      nonExistingLocation.getPostCode()
    );
    assertEquals(
      foundLocation.getLatitude(),
      nonExistingLocation.getLatitude()
    );
    assertEquals(
      foundLocation.getLongitude(),
      nonExistingLocation.getLongitude()
    );
  }

  @Test
  public void testUpdateLocationExisting() {
    try {
      foundLocation = locationService.updateLocation(existingLocation);
    } catch (Exception e) {
      fail();
    }

    assertEquals(foundLocation.getAddress(), existingLocation.getAddress());
    assertEquals(foundLocation.getPostCode(), existingLocation.getPostCode());
    assertEquals(foundLocation.getLatitude(), existingLocation.getLatitude());
    assertEquals(foundLocation.getLongitude(), existingLocation.getLongitude());
  }

  @Test
  public void testUpdateLocationNonExisting() {
    try {
      locationService.updateLocation(nonExistingLocation);
      fail();
    } catch (LocationDoesntExistException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testExistsByIdExisting() {
    try {
      assertTrue(locationService.locationExists(existingLocation));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testExistsByIdNonExisting() {
    try {
      assertFalse(locationService.locationExists(nonExistingLocation));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetLocationByIdExisting() {
    try {
      foundLocation = locationService.getLocationById(existingLocation.getId());
    } catch (Exception e) {
      fail();
    }

    assertEquals(foundLocation.getAddress(), existingLocation.getAddress());
    assertEquals(foundLocation.getPostCode(), existingLocation.getPostCode());
    assertEquals(foundLocation.getLatitude(), existingLocation.getLatitude());
    assertEquals(foundLocation.getLongitude(), existingLocation.getLongitude());
  }

  @Test
  public void testGetLocationByIdNonExisting() {
    try {
      locationService.getLocationById(nonExistingLocation.getId());
      fail();
    } catch (LocationDoesntExistException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testDeleteLocationExisting() {
    try {
      locationService.deleteLocation(existingLocation);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testDeleteLocationNonExisting() {
    try {
      locationService.deleteLocation(nonExistingLocation);
      fail();
    } catch (LocationDoesntExistException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetLocationsByLatitudeExisting() {
    try {
      found =
        locationService.getLocationsByLatitude(existingLocation.getLatitude());
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }

    assertEquals(found.get(0).getAddress(), existingLocation.getAddress());
    assertEquals(found.get(0).getPostCode(), existingLocation.getPostCode());
    assertEquals(found.get(0).getLatitude(), existingLocation.getLatitude());
    assertEquals(found.get(0).getLongitude(), existingLocation.getLongitude());
  }

  @Test
  public void testGetLocationsByLatitudeNonExisting() {
    try {
      locationService.getLocationsByLatitude(nonExistingLocation.getLatitude());
      fail();
    } catch (LocationDoesntExistException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetLocationsByLongitudeExisting() {
    try {
      found =
        locationService.getLocationsByLongitude(
          existingLocation.getLongitude()
        );
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }

    assertEquals(found.get(0).getAddress(), existingLocation.getAddress());
    assertEquals(found.get(0).getPostCode(), existingLocation.getPostCode());
    assertEquals(found.get(0).getLatitude(), existingLocation.getLatitude());
    assertEquals(found.get(0).getLongitude(), existingLocation.getLongitude());
  }

  @Test
  public void testGetLocationsByLongitudeNonExisting() {
    try {
      locationService.getLocationsByLongitude(
        nonExistingLocation.getLongitude()
      );
      fail();
    } catch (LocationDoesntExistException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetLocationsInDistance() {
    try {
      found =
        locationService.getLocationsInDistance(
          existingLocation.getLongitude(),
          existingLocation.getLatitude(),
          1.0
        );
    } catch (Exception e) {
      fail();
    }

    assertEquals(found.get(0).getAddress(), existingLocation.getAddress());
    assertEquals(found.get(0).getPostCode(), existingLocation.getPostCode());
    assertEquals(found.get(0).getLatitude(), existingLocation.getLatitude());
    assertEquals(found.get(0).getLongitude(), existingLocation.getLongitude());
  }

  @Test
  public void testGetLocationsInDistanceNonExisting() {
    try {
      locationService.getLocationsInDistance(
        nonExistingLocation.getLongitude(),
        nonExistingLocation.getLatitude(),
        1.0
      );
      fail();
    } catch (LocationDoesntExistException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetLocationsInDistanceThrowsException() {
    try {
      locationService.getLocationsInDistance(0.0, 0.0, 0.0);
      fail();
    } catch (IllegalArgumentException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }
}
