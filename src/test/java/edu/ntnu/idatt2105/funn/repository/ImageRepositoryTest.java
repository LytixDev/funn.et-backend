package edu.ntnu.idatt2105.funn.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.ntnu.idatt2105.funn.model.file.Image;
import edu.ntnu.idatt2105.funn.model.listing.Category;
import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.model.listing.Status;
import edu.ntnu.idatt2105.funn.model.location.Location;
import edu.ntnu.idatt2105.funn.model.location.PostCode;
import edu.ntnu.idatt2105.funn.model.user.Role;
import edu.ntnu.idatt2105.funn.model.user.User;
import edu.ntnu.idatt2105.funn.repository.file.ImageRepository;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ImageRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ImageRepository imageRepository;

  private Image image;

  private Image image2;

  private Image image3;

  private Image image4;

  private Image image5;

  private Listing listing;

  private Listing listing2;

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
      new HashSet<>(),
      new HashSet<>(),
      Role.USER
    );

    User user2 = new User(
      "username2",
      "email2",
      "firstName",
      "lastName",
      "password",
      new HashSet<>(),
      new HashSet<>(),
      new HashSet<>(),
      new HashSet<>(),
      Role.USER
    );

    PostCode postCode = new PostCode(1234, "Oslo", new HashSet<>());

    Category category = new Category(null, "Books", new HashSet<>());

    Category category2 = new Category(null, "Sports", new HashSet<>());

    user = entityManager.persist(user);
    entityManager.flush();

    user2 = entityManager.persist(user2);
    entityManager.flush();

    postCode = entityManager.persist(postCode);
    entityManager.flush();

    category = entityManager.persist(category);
    entityManager.flush();

    category2 = entityManager.persist(category2);
    entityManager.flush();

    Location location = Location
      .builder()
      .id(null)
      .address("Testveien 1")
      .postCode(postCode)
      .latitude(59.9127D)
      .longitude(10.7461D)
      .listings(new HashSet<>())
      .build();

    Location location2 = Location
      .builder()
      .id(null)
      .address("Testveien 2")
      .postCode(postCode)
      .latitude(59.9127D)
      .longitude(10.7461D)
      .listings(new HashSet<>())
      .build();

    location = entityManager.persist(location);
    entityManager.flush();
    location2 = entityManager.persist(location2);
    entityManager.flush();

    listing =
      Listing
        .builder()
        .id(null)
        .title("Test")
        .briefDescription("Test")
        .fullDescription("Test")
        .price(1000)
        .category(category)
        .expirationDate(LocalDate.of(2021, 12, 31))
        .publicationDate(LocalDate.of(2020, 12, 31))
        .user(user)
        .status(Status.ACTIVE)
        .location(location)
        .build();

    listing2 =
      Listing
        .builder()
        .id(null)
        .title("Test2")
        .briefDescription("Test2")
        .fullDescription("Test2")
        .price(2000)
        .category(category2)
        .expirationDate(LocalDate.of(2021, 12, 31))
        .publicationDate(LocalDate.of(2020, 12, 31))
        .user(user2)
        .status(Status.ACTIVE)
        .location(location2)
        .build();

    entityManager.persist(listing);
    entityManager.flush();
    entityManager.persist(listing2);
    entityManager.flush();

    image = Image.builder().alt("Alt").listingId(1L).build();
    image2 = Image.builder().alt("Alt2").listingId(1L).build();
    image3 = Image.builder().alt("Alt3").listingId(1L).build();
    image4 = Image.builder().alt("Alt4").listingId(2L).build();
    image5 = Image.builder().alt("Alt5").listingId(2L).build();
  }

  @Test
  public void testFindImagesByListingId() {
    image = entityManager.persist(image);
    image2 = entityManager.persist(image2);
    image3 = entityManager.persist(image3);
    image4 = entityManager.persist(image4);
    image5 = entityManager.persist(image5);

    entityManager.flush();

    List<Image> images = null;

    try {
      images =
        imageRepository
          .findAllByListingId(1L)
          .orElseThrow(() -> new RuntimeException("No images found"));
    } catch (Exception e) {
      fail();
      return;
    }

    assertEquals(3, images.size());
    assertEquals(image.getAlt(), images.get(0).getAlt());
    assertEquals(image2.getAlt(), images.get(1).getAlt());
    assertEquals(image3.getAlt(), images.get(2).getAlt());
  }
}
