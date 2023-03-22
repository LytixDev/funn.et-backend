package edu.ntnu.idatt2105.placeholder.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2105.funn.model.location.Location;
import edu.ntnu.idatt2105.funn.model.location.PostCode;
import edu.ntnu.idatt2105.funn.repository.location.LocationRepository;
import java.util.HashSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LocationRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private LocationRepository locationRepository;

  @Test
  public void testFindByPostCode() {
    PostCode postCode = new PostCode(0000, "city", new HashSet<>());

    entityManager.persist(postCode);

    Location location = Location
      .builder()
      .address("address")
      .postCode(postCode)
      .latitude(1.0)
      .longitude(1.0)
      .listings(new HashSet<>())
      .build();

    location = entityManager.persist(location);

    entityManager.flush();

    Location found = locationRepository
      .findLocationsByPostCode(postCode.getPostCode())
      .get()
      .get(0);

    assertEquals(location.getPostCode(), found.getPostCode());
    assertEquals(location.getAddress(), found.getAddress());
    assertEquals(location.getLatitude(), found.getLatitude());
    assertEquals(location.getLongitude(), found.getLongitude());
  }

  @Test
  public void testFindByPostCodeObject() {
    PostCode postCode = new PostCode(2000, "city", new HashSet<>());

    entityManager.persist(postCode);

    Location location = Location
      .builder()
      .address("address")
      .postCode(postCode)
      .latitude(1.0)
      .longitude(1.0)
      .listings(new HashSet<>())
      .build();

    location = entityManager.persist(location);

    entityManager.flush();

    Location found = locationRepository.findLocationsByPostCode(postCode).get().get(0);

    assertEquals(location.getPostCode(), found.getPostCode());
    assertEquals(location.getAddress(), found.getAddress());
    assertEquals(location.getLatitude(), found.getLatitude());
    assertEquals(location.getLongitude(), found.getLongitude());
  }

  @Test
  public void testFindByCity() {
    PostCode postCode = new PostCode(4444, "city", new HashSet<>());

    entityManager.persist(postCode);

    Location location = Location
      .builder()
      .address("address")
      .postCode(postCode)
      .latitude(1.0)
      .longitude(1.0)
      .listings(new HashSet<>())
      .build();

    location = entityManager.persist(location);

    entityManager.flush();

    Location found = locationRepository.findLocationsByCity(postCode.getCity()).get().get(0);

    assertEquals(location.getPostCode(), found.getPostCode());
    assertEquals(location.getAddress(), found.getAddress());
    assertEquals(location.getLatitude(), found.getLatitude());
    assertEquals(location.getLongitude(), found.getLongitude());
  }

  @Test
  public void testFindByAddress() {
    PostCode postCode = new PostCode(5555, "city", new HashSet<>());

    entityManager.persist(postCode);

    Location location = Location
      .builder()
      .address("address")
      .postCode(postCode)
      .latitude(1.0)
      .longitude(1.0)
      .listings(new HashSet<>())
      .build();

    location = entityManager.persist(location);

    entityManager.flush();

    Location found = locationRepository.findByAddress(location.getAddress()).get().get(0);

    assertEquals(location.getPostCode(), found.getPostCode());
    assertEquals(location.getAddress(), found.getAddress());
    assertEquals(location.getLatitude(), found.getLatitude());
    assertEquals(location.getLongitude(), found.getLongitude());
  }

  @Test
  public void testFindByLatitudeAndLongitude() {
    PostCode postCode = new PostCode(1111, "city", new HashSet<>());

    entityManager.persist(postCode);

    Location location = Location
      .builder()
      .address("address")
      .postCode(postCode)
      .latitude(1.0)
      .longitude(1.0)
      .listings(new HashSet<>())
      .build();

    location = entityManager.persist(location);

    entityManager.flush();

    Location found = locationRepository
      .findByLatitudeAndLongitudeByRadius(0.0, 2.0, 0.0, 2.0)
      .get()
      .get(0);

    assertEquals(location.getPostCode(), found.getPostCode());
    assertEquals(location.getAddress(), found.getAddress());
    assertEquals(location.getLatitude(), found.getLatitude());
    assertEquals(location.getLongitude(), found.getLongitude());
  }

  @Test
  public void testFindByLatitude() {
    PostCode postCode = new PostCode(6666, "city", new HashSet<>());

    entityManager.persist(postCode);

    Location location = Location
      .builder()
      .address("address")
      .postCode(postCode)
      .latitude(1.0)
      .longitude(1.0)
      .listings(new HashSet<>())
      .build();

    location = entityManager.persist(location);

    entityManager.flush();

    Location found = locationRepository.findByLatitude(1.0).get().get(0);

    assertEquals(location.getPostCode(), found.getPostCode());
    assertEquals(location.getAddress(), found.getAddress());
    assertEquals(location.getLatitude(), found.getLatitude());
    assertEquals(location.getLongitude(), found.getLongitude());
  }

  @Test
  public void testFindByLongitude() {
    PostCode postCode = new PostCode(8888, "city", new HashSet<>());

    entityManager.persist(postCode);

    Location location = Location
      .builder()
      .address("address")
      .postCode(postCode)
      .latitude(1.0)
      .longitude(1.0)
      .listings(new HashSet<>())
      .build();

    location = entityManager.persist(location);

    entityManager.flush();

    Location found = locationRepository.findByLongitude(1.0).get().get(0);

    assertEquals(location.getPostCode(), found.getPostCode());
    assertEquals(location.getAddress(), found.getAddress());
    assertEquals(location.getLatitude(), found.getLatitude());
    assertEquals(location.getLongitude(), found.getLongitude());
  }
}
