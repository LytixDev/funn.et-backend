package edu.ntnu.idatt2105.funn.filtering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.ntnu.idatt2105.funn.model.listing.Category;
import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.model.location.Location;
import edu.ntnu.idatt2105.funn.model.location.PostCode;
import edu.ntnu.idatt2105.funn.model.user.Role;
import edu.ntnu.idatt2105.funn.model.user.User;
import edu.ntnu.idatt2105.funn.repository.listing.ListingRepository;
import edu.ntnu.idatt2105.funn.repository.location.LocationRepository;
import edu.ntnu.idatt2105.funn.repository.location.PostCodeRepository;
import edu.ntnu.idatt2105.funn.repository.user.UserRepository;
import edu.ntnu.idatt2105.funn.service.location.LocationService;
import edu.ntnu.idatt2105.funn.service.location.LocationServiceImpl;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ListingFilterTest {

  @TestConfiguration
  static class ListingFilterTestContextConfiguration {

    @Bean
    public LocationService locationService() {
      return new LocationServiceImpl();
    }
  }

  @Autowired
  private ListingRepository listingRepository;

  @Autowired
  private LocationRepository locationRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PostCodeRepository postCodeRepository;

  @Autowired
  private LocationService locationService;

  private SearchSpecification<Listing> searchSpecification;

  private Listing listing1;

  private Listing listing2;

  private Listing listing3;

  private Listing listing4;

  @Before
  public void setUp() {
    listingRepository.deleteAll();

    userRepository.deleteAll();

    locationRepository.deleteAll();

    postCodeRepository.deleteAll();

    User user1 = new User(
      "username",
      "email",
      "firstName",
      "lastName",
      "password",
      new HashSet<>(),
      new HashSet<>(),
      new HashSet<>(),
      Role.USER
    );

    User user2 = new User(
      "username2",
      "email2",
      "firstName2",
      "lastName2",
      "password2",
      new HashSet<>(),
      new HashSet<>(),
      new HashSet<>(),
      Role.USER
    );

    PostCode postCode = new PostCode(0000, "Oslo", new HashSet<>());

    Location location = Location
      .builder()
      .address("Testveien 1")
      .postCode(postCode)
      .latitude(59.9127D)
      .longitude(10.7461D)
      .listings(new HashSet<>())
      .build();

    PostCode postCode2 = new PostCode(5000, "Bergen", new HashSet<>());

    Location location2 = Location
      .builder()
      .address("Testveien 2")
      .postCode(postCode2)
      .latitude(70.9127D)
      .longitude(10.7461D)
      .listings(new HashSet<>())
      .build();

    listing1 =
      Listing
        .builder()
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

    postCodeRepository.save(postCode);
    postCodeRepository.save(postCode2);

    locationRepository.save(location);
    locationRepository.save(location2);

    userRepository.save(user1);
    userRepository.save(user2);

    listingRepository.save(listing1);
    listingRepository.save(listing2);
    listingRepository.save(listing3);
    listingRepository.save(listing4);
  }

  @Test
  public void testListingFilterSpecificationEqual() {
    FilterRequest filterRequest = FilterRequest
      .builder()
      .value(listing1.getTitle())
      .fieldType(FieldType.STRING)
      .keyWord("title")
      .operator(Operator.EQUAL)
      .build();

    SearchRequest searchRequest = new SearchRequest();
    searchRequest.getFilterRequests().add(filterRequest);

    searchSpecification = new SearchSpecification<>(searchRequest);

    Page<Listing> found = listingRepository.findAll(searchSpecification, Pageable.unpaged());

    assertEquals(found.getSize(), 1);
    assertEquals(found.getContent().get(0).getTitle(), listing1.getTitle());
    assertEquals(found.getContent().get(0).getBriefDescription(), listing1.getBriefDescription());
    assertEquals(found.getContent().get(0).getFullDescription(), listing1.getFullDescription());
    assertEquals(found.getContent().get(0).getPrice(), listing1.getPrice());
    assertEquals(found.getContent().get(0).getCategory(), listing1.getCategory());
  }

  @Test
  public void testListingFilterSpecificationNotEqual() {
    FilterRequest filterRequest = FilterRequest
      .builder()
      .value(listing1.getTitle())
      .fieldType(FieldType.STRING)
      .keyWord("title")
      .operator(Operator.NOT_EQUAL)
      .build();

    SearchRequest searchRequest = new SearchRequest();
    searchRequest.getFilterRequests().add(filterRequest);

    searchSpecification = new SearchSpecification<>(searchRequest);

    Page<Listing> found = listingRepository.findAll(searchSpecification, Pageable.unpaged());

    assertEquals(found.getSize(), 3);
    assertEquals(found.getContent().get(0).getTitle(), listing2.getTitle());
    assertEquals(found.getContent().get(1).getTitle(), listing3.getTitle());
    assertEquals(found.getContent().get(2).getTitle(), listing4.getTitle());
  }

  @Test
  public void testListingFilterSpecificationGreaterThan() {
    FilterRequest filterRequest = FilterRequest
      .builder()
      .value(listing1.getPrice())
      .fieldType(FieldType.DOUBLE)
      .keyWord("price")
      .operator(Operator.GREATER_THAN)
      .build();

    SearchRequest searchRequest = new SearchRequest();
    searchRequest.getFilterRequests().add(filterRequest);

    searchSpecification = new SearchSpecification<>(searchRequest);

    Page<Listing> found = listingRepository.findAll(searchSpecification, Pageable.unpaged());

    assertEquals(found.getSize(), 3);
    assertEquals(found.getContent().get(0).getTitle(), listing2.getTitle());
    assertEquals(found.getContent().get(1).getTitle(), listing3.getTitle());
    assertEquals(found.getContent().get(2).getTitle(), listing4.getTitle());
  }

  @Test
  public void testListingFilterSpecificationLessThan() {
    FilterRequest filterRequest = FilterRequest
      .builder()
      .value(listing2.getPrice())
      .fieldType(FieldType.DOUBLE)
      .keyWord("price")
      .operator(Operator.LESS_THAN)
      .build();

    SearchRequest searchRequest = new SearchRequest();
    searchRequest.getFilterRequests().add(filterRequest);

    searchSpecification = new SearchSpecification<>(searchRequest);

    Page<Listing> found = listingRepository.findAll(searchSpecification, Pageable.unpaged());

    assertEquals(found.getSize(), 1);
    assertEquals(found.getContent().get(0).getTitle(), listing1.getTitle());
  }

  @Test
  public void testListingFilterSpecificationGreaterThanEqual() {
    FilterRequest filterRequest = FilterRequest
      .builder()
      .value(listing1.getPrice())
      .fieldType(FieldType.DOUBLE)
      .keyWord("price")
      .operator(Operator.GREATER_THAN_EQUAL)
      .build();

    SearchRequest searchRequest = new SearchRequest();
    searchRequest.getFilterRequests().add(filterRequest);

    searchSpecification = new SearchSpecification<>(searchRequest);

    Page<Listing> found = listingRepository.findAll(searchSpecification, Pageable.unpaged());

    assertEquals(found.getSize(), 4);
    assertEquals(found.getContent().get(0).getTitle(), listing1.getTitle());
    assertEquals(found.getContent().get(1).getTitle(), listing2.getTitle());
    assertEquals(found.getContent().get(2).getTitle(), listing3.getTitle());
    assertEquals(found.getContent().get(3).getTitle(), listing4.getTitle());
  }

  @Test
  public void testListingFilterSpecificationLessThanEqual() {
    FilterRequest filterRequest = FilterRequest
      .builder()
      .value(listing2.getPrice())
      .fieldType(FieldType.DOUBLE)
      .keyWord("price")
      .operator(Operator.LESS_THAN_EQUAL)
      .build();

    SearchRequest searchRequest = new SearchRequest();
    searchRequest.getFilterRequests().add(filterRequest);

    searchSpecification = new SearchSpecification<>(searchRequest);

    Page<Listing> found = listingRepository.findAll(searchSpecification, Pageable.unpaged());

    assertEquals(found.getSize(), 4);
    assertEquals(found.getContent().get(0).getTitle(), listing1.getTitle());
    assertEquals(found.getContent().get(1).getTitle(), listing2.getTitle());
    assertEquals(found.getContent().get(2).getTitle(), listing3.getTitle());
    assertEquals(found.getContent().get(3).getTitle(), listing4.getTitle());
  }

  @Test
  public void testListingFilterSpecificationBetween() {
    FilterRequest filterRequest = FilterRequest
      .builder()
      .value(LocalDate.of(2019, 6, 30))
      .valueTo(LocalDate.of(2021, 12, 31))
      .fieldType(FieldType.DATE)
      .keyWord("publicationDate")
      .operator(Operator.BETWEEN)
      .build();

    SearchRequest searchRequest = new SearchRequest();
    searchRequest.getFilterRequests().add(filterRequest);

    searchSpecification = new SearchSpecification<>(searchRequest);

    Page<Listing> found = listingRepository.findAll(searchSpecification, Pageable.unpaged());

    assertEquals(found.getSize(), 2);
    assertEquals(found.getContent().get(0).getTitle(), listing1.getTitle());
    assertEquals(found.getContent().get(1).getTitle(), listing2.getTitle());
  }

  @Test
  public void testListingFilterSpecificationIn() {
    FilterRequest filterRequest = FilterRequest
      .builder()
      .values(List.of(listing1.getTitle(), listing2.getTitle(), listing3.getTitle()))
      .fieldType(FieldType.STRING)
      .keyWord("title")
      .operator(Operator.IN)
      .build();

    SearchRequest searchRequest = new SearchRequest();
    searchRequest.getFilterRequests().add(filterRequest);

    searchSpecification = new SearchSpecification<>(searchRequest);

    Page<Listing> found = listingRepository.findAll(searchSpecification, Pageable.unpaged());

    assertEquals(found.getSize(), 3);
    assertEquals(found.getContent().get(0).getTitle(), listing1.getTitle());
    assertEquals(found.getContent().get(1).getTitle(), listing2.getTitle());
    assertEquals(found.getContent().get(2).getTitle(), listing3.getTitle());
  }

  @Test
  public void testListingFilterSpecificationNotIn() {
    FilterRequest filterRequest = FilterRequest
      .builder()
      .values(List.of(listing1.getTitle(), listing2.getTitle(), listing3.getTitle()))
      .fieldType(FieldType.STRING)
      .keyWord("title")
      .operator(Operator.NOT_IN)
      .build();

    SearchRequest searchRequest = new SearchRequest();
    searchRequest.getFilterRequests().add(filterRequest);

    searchSpecification = new SearchSpecification<>(searchRequest);

    Page<Listing> found = listingRepository.findAll(searchSpecification, Pageable.unpaged());

    assertEquals(found.getSize(), 1);
    assertEquals(found.getContent().get(0).getTitle(), listing4.getTitle());
  }

  @Test
  public void testListingFilterSpecificationLike() {
    FilterRequest filterRequest = FilterRequest
      .builder()
      .value("Test")
      .fieldType(FieldType.STRING)
      .keyWord("title")
      .operator(Operator.LIKE)
      .build();

    SearchRequest searchRequest = new SearchRequest();
    searchRequest.getFilterRequests().add(filterRequest);

    searchSpecification = new SearchSpecification<>(searchRequest);

    Page<Listing> found = listingRepository.findAll(searchSpecification, Pageable.unpaged());

    assertEquals(found.getSize(), 4);
    assertEquals(found.getContent().get(0).getTitle(), listing1.getTitle());
    assertEquals(found.getContent().get(1).getTitle(), listing2.getTitle());
    assertEquals(found.getContent().get(2).getTitle(), listing3.getTitle());
    assertEquals(found.getContent().get(3).getTitle(), listing4.getTitle());
  }

  @Test
  public void testListingFilterSpecificationUser() {
    FilterRequest filterRequest = FilterRequest
      .builder()
      .value(listing1.getUser().getUsername())
      .fieldType(FieldType.STRING)
      .keyWord("user")
      .operator(Operator.EQUAL)
      .build();

    SearchRequest searchRequest = new SearchRequest();
    searchRequest.getFilterRequests().add(filterRequest);

    searchSpecification = new SearchSpecification<>(searchRequest);

    Page<Listing> found = listingRepository.findAll(searchSpecification, Pageable.unpaged());

    assertEquals(found.getSize(), 2);
    assertEquals(found.getContent().get(0).getTitle(), listing1.getTitle());
    assertEquals(found.getContent().get(1).getTitle(), listing2.getTitle());
  }

  @Test
  public void testListingFilterSpecificationLocation() {
    FilterRequest filterRequest;

    try {
      filterRequest =
        FilterRequest
          .builder()
          .values(
            locationService
              .getLocationsInDistance(
                listing1.getLocation().getLongitude(),
                listing1.getLocation().getLatitude(),
                10
              )
              .stream()
              .map(Location::getId)
              .collect(Collectors.toList())
          )
          .fieldType(FieldType.LONG)
          .keyWord("location")
          .operator(Operator.IN)
          .build();
    } catch (Exception e) {
      e.printStackTrace();
      fail();
      return;
    }

    SearchRequest searchRequest = new SearchRequest();
    searchRequest.getFilterRequests().add(filterRequest);

    searchSpecification = new SearchSpecification<>(searchRequest);

    Page<Listing> found = listingRepository.findAll(searchSpecification, Pageable.unpaged());

    assertEquals(found.getSize(), 2);
    assertEquals(found.getContent().get(0).getTitle(), listing1.getTitle());
    assertEquals(found.getContent().get(1).getTitle(), listing2.getTitle());
  }
}
