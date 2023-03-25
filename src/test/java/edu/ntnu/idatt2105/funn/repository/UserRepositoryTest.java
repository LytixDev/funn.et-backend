package edu.ntnu.idatt2105.funn.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt2105.funn.model.listing.Category;
import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.model.listing.Status;
import edu.ntnu.idatt2105.funn.model.location.Location;
import edu.ntnu.idatt2105.funn.model.location.PostCode;
import edu.ntnu.idatt2105.funn.model.user.Role;
import edu.ntnu.idatt2105.funn.model.user.User;
import edu.ntnu.idatt2105.funn.repository.user.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void testFindByUsername() {
    User user = new User(
      "username",
      "email",
      "firstName",
      "lastName",
      "password",
      new HashSet<>(),
      new HashSet<>(),
      new HashSet<>(),
      new HashSet<>(),
      Role.USER
    );
    entityManager.persist(user);
    entityManager.flush();

    User found = userRepository.findByUsername(user.getUsername()).get();

    assertEquals(user.getUsername(), found.getUsername());
  }

  @Test
  public void testFindByEmail() {
    User user = new User(
      "username",
      "email",
      "firstName",
      "lastName",
      "password",
      new HashSet<>(),
      new HashSet<>(),
      new HashSet<>(),
      new HashSet<>(),
      Role.USER
    );
    entityManager.persist(user);
    entityManager.flush();

    User found = userRepository.findByEmail(user.getEmail()).get();

    assertEquals(user.getEmail(), found.getEmail());
  }

  @Test
  public void testUserHasFavoritedListing() throws Exception {
    User user = new User(
      "username",
      "email",
      "firstName",
      "lastName",
      "password",
      new HashSet<>(),
      new HashSet<>(),
      new HashSet<>(),
      new HashSet<>(),
      Role.USER
    );
    entityManager.persist(user);

    PostCode postCode = new PostCode(1234, "Oslo", new HashSet<>());
    entityManager.persist(postCode);

    Location location = Location
      .builder()
      .address("an address")
      .postCode(postCode)
      .latitude(1.0)
      .longitude(1.0)
      .listings(new HashSet<>())
      .build();

    entityManager.persist(location);

    Listing listing = Listing
      .builder()
      .title("Test")
      .briefDescription("Test")
      .fullDescription("Test")
      .price(1000)
      .category(Category.BOOKS)
      .expirationDate(LocalDate.of(2021, 12, 31))
      .publicationDate(LocalDate.of(2020, 12, 31))
      .user(user)
      .location(location)
      .images(new ArrayList<>())
      .favoritedBy(new HashSet<>())
      .status(Status.ACTIVE)
      .build();

    entityManager.persist(listing);

    user.getFavoriteListings().add(listing);
    listing.getFavoritedBy().add(user);

    entityManager.merge(user);
    entityManager.merge(listing);

    assertTrue(
      userRepository.findUserWhoFavoritedListing(listing.getId(), user.getUsername()).isPresent()
    );
  }

  @Test
  public void testUserDoesNotHaveFavoriteListing() {
    User user = new User(
      "username",
      "email",
      "firstName",
      "lastName",
      "password",
      new HashSet<>(),
      new HashSet<>(),
      new HashSet<>(),
      new HashSet<>(),
      Role.USER
    );
    entityManager.persist(user);

    PostCode postCode = new PostCode(1234, "Oslo", new HashSet<>());
    entityManager.persist(postCode);

    Location location = Location
      .builder()
      .address("an address")
      .postCode(postCode)
      .latitude(1.0)
      .longitude(1.0)
      .listings(new HashSet<>())
      .build();

    entityManager.persist(location);

    Listing listing = Listing
      .builder()
      .title("Test")
      .briefDescription("Test")
      .fullDescription("Test")
      .price(1000)
      .category(Category.BOOKS)
      .expirationDate(LocalDate.of(2021, 12, 31))
      .publicationDate(LocalDate.of(2020, 12, 31))
      .user(user)
      .location(location)
      .images(new ArrayList<>())
      .favoritedBy(new HashSet<>())
      .status(Status.ACTIVE)
      .build();

    entityManager.persist(listing);

    assertFalse(
      userRepository.findUserWhoFavoritedListing(listing.getId(), user.getUsername()).isPresent()
    );
  }
}
