package edu.ntnu.idatt2105.placeholder.filtering;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2105.placeholder.model.listing.Category;
import edu.ntnu.idatt2105.placeholder.model.listing.Listing;
import edu.ntnu.idatt2105.placeholder.model.location.Location;
import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import edu.ntnu.idatt2105.placeholder.model.user.Role;
import edu.ntnu.idatt2105.placeholder.model.user.User;
import edu.ntnu.idatt2105.placeholder.repository.listing.ListingRepository;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ListingSortTest {

  @Autowired
  private ListingRepository listingRepository;

  private SearchSpecification<Listing> searchSpecification;

  private Listing listing1;

  private Listing listing2;

  private Listing listing3;

  private Listing listing4;

  @Before
  public void setUp() {
    User user1 = new User(
      "username",
      "email",
      "firstName",
      "lastName",
      "password",
      Role.USER
    );

    User user2 = new User(
      "username2",
      "email2",
      "firstName2",
      "lastName2",
      "password2",
      Role.USER
    );

    PostCode postCode = new PostCode("0000", "Oslo");

    Location location = Location
      .builder()
      .id(1L)
      .address("Testveien 1")
      .postCode(postCode)
      .latitude(59.9127D)
      .longitude(10.7461D)
      .build();

    PostCode postCode2 = new PostCode("5000", "Bergen");

    Location location2 = Location
      .builder()
      .id(2L)
      .address("Testveien 2")
      .postCode(postCode2)
      .latitude(70.9127D)
      .longitude(10.7461D)
      .build();

    listing1 =
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
        .user(user1)
        .location(location)
        .build();

    listing2 =
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
        .user(user1)
        .location(location)
        .build();

    listing3 =
      Listing
        .builder()
        .id(3L)
        .title("Test3")
        .briefDescription("Test3")
        .fullDescription("Test3")
        .price(2000)
        .category(Category.FURNITURE)
        .expirationDate(LocalDate.of(2022, 12, 31))
        .publicationDate(LocalDate.of(2022, 6, 30))
        .user(user2)
        .location(location2)
        .build();

    listing4 =
      Listing
        .builder()
        .id(4L)
        .title("Test4")
        .briefDescription("Test4")
        .fullDescription("Test4")
        .price(2000)
        .category(Category.SPORTS)
        .expirationDate(LocalDate.of(2023, 6, 30))
        .publicationDate(LocalDate.of(2023, 1, 31))
        .user(user2)
        .location(location2)
        .build();

    listingRepository.save(listing1);
    listingRepository.save(listing2);
    listingRepository.save(listing3);
    listingRepository.save(listing4);
  }

  @Test
  public void testSortAsc() {
    SortRequest sortRequest = new SortRequest("title", SortDirection.ASC);
    SearchRequest searchRequest = new SearchRequest();
    searchRequest.getSortRequests().add(sortRequest);

    searchSpecification = new SearchSpecification<>(searchRequest);

    Pageable pageable = SearchSpecification.getPageable(searchRequest);

    Page<Listing> found = listingRepository.findAll(
      searchSpecification,
      pageable
    );

    assertEquals(4, found.getTotalElements());
    assertEquals(listing1.getTitle(), found.getContent().get(0).getTitle());
    assertEquals(listing2.getTitle(), found.getContent().get(1).getTitle());
    assertEquals(listing3.getTitle(), found.getContent().get(2).getTitle());
    assertEquals(listing4.getTitle(), found.getContent().get(3).getTitle());
  }

  @Test
  public void testSortDesc() {
    SortRequest sortRequest = new SortRequest("title", SortDirection.DESC);
    SearchRequest searchRequest = new SearchRequest();
    searchRequest.getSortRequests().add(sortRequest);

    searchSpecification = new SearchSpecification<>(searchRequest);

    Pageable pageable = SearchSpecification.getPageable(searchRequest);

    Page<Listing> found = listingRepository.findAll(
      searchSpecification,
      pageable
    );

    assertEquals(4, found.getTotalElements());
    assertEquals(listing4.getTitle(), found.getContent().get(0).getTitle());
    assertEquals(listing3.getTitle(), found.getContent().get(1).getTitle());
    assertEquals(listing2.getTitle(), found.getContent().get(2).getTitle());
    assertEquals(listing1.getTitle(), found.getContent().get(3).getTitle());
  }
}