package edu.ntnu.idatt2105.placeholder.service;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import edu.ntnu.idatt2105.placeholder.exceptions.location.PostCodeAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.PostCodeDoesntExistException;
import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import edu.ntnu.idatt2105.placeholder.model.location.PostCodeId;
import edu.ntnu.idatt2105.placeholder.repository.location.PostCodeRepository;
import edu.ntnu.idatt2105.placeholder.service.location.PostCodeService;
import edu.ntnu.idatt2105.placeholder.service.location.PostCodeServiceImpl;
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
public class PostCodeServiceIntegrationTest {

  @TestConfiguration
  static class PostCodeServiceTestContextConfiguration {

    @Bean
    public PostCodeService postCodeService() {
      return new PostCodeServiceImpl();
    }
  }

  @Autowired
  private PostCodeService postCodeService;

  @MockBean
  private PostCodeRepository postCodeRepository;

  private PostCode oslo;

  private PostCode trondheim;

  private PostCode bergen;

  private PostCode found;

  private List<String> foundStrings;

  private List<PostCode> foundPostCodeList;

  @Before
  public void setUp() {
    oslo = new PostCode("0000", "Oslo");
    trondheim = new PostCode("7000", "Trondheim");
    bergen = new PostCode("5000", "Bergen");

    when(postCodeRepository.save(oslo)).thenReturn(oslo);
    when(postCodeRepository.save(trondheim)).thenReturn(trondheim);
    when(postCodeRepository.save(bergen)).thenReturn(bergen);

    when(postCodeRepository.existsById(new PostCodeId("0000", "Oslo")))
      .thenReturn(true);
    when(postCodeRepository.existsById(new PostCodeId("7000", "Trondheim")))
      .thenReturn(true);
    when(postCodeRepository.existsById(new PostCodeId("5000", "Bergen")))
      .thenReturn(false);

    when(postCodeRepository.findAll()).thenReturn(List.of(oslo, trondheim));

    doNothing()
      .when(postCodeRepository)
      .deleteById(new PostCodeId("0000", "Oslo"));
    doNothing()
      .when(postCodeRepository)
      .deleteById(new PostCodeId("7000", "Trondheim"));
    doNothing()
      .when(postCodeRepository)
      .deleteById(new PostCodeId("5000", "Bergen"));

    when(postCodeRepository.findCitiesByPostCode("0000"))
      .thenReturn(Optional.of(List.of("Oslo")));
    when(postCodeRepository.findCitiesByPostCode("7000"))
      .thenReturn(Optional.of(List.of("Trondheim")));
    when(postCodeRepository.findCitiesByPostCode("5000"))
      .thenReturn(Optional.of(List.of()));

    when(postCodeRepository.findPostCodesByCity("Oslo"))
      .thenReturn(Optional.of(List.of("0000")));
    when(postCodeRepository.findPostCodesByCity("Trondheim"))
      .thenReturn(Optional.of(List.of("7000")));
    when(postCodeRepository.findPostCodesByCity("Bergen"))
      .thenReturn(Optional.of(List.of()));
  }

  @Test
  public void testSaveExistingPostCode() {
    try {
      found = postCodeService.savePostCode(oslo);
      fail();
    } catch (PostCodeAlreadyExistsException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testSaveNewPostCode() {
    try {
      found = postCodeService.savePostCode(bergen);
    } catch (Exception e) {
      fail();
    }

    assertEquals(found.getPostCode(), bergen.getPostCode());
    assertEquals(found.getCity(), bergen.getCity());
  }

  @Test
  public void testGetAllPostCodes() {
    try {
      foundPostCodeList = postCodeService.getAllPostCodes();
    } catch (Exception e) {
      fail();
    }

    assertEquals(foundPostCodeList.size(), 2);
    assertEquals(foundPostCodeList.get(0).getPostCode(), oslo.getPostCode());
    assertEquals(foundPostCodeList.get(0).getCity(), oslo.getCity());
    assertEquals(
      foundPostCodeList.get(1).getPostCode(),
      trondheim.getPostCode()
    );
    assertEquals(foundPostCodeList.get(1).getCity(), trondheim.getCity());
  }

  @Test
  public void testUpdateExistingPostCode() {
    try {
      found = postCodeService.updatePostCode(oslo);
    } catch (Exception e) {
      fail();
    }

    assertEquals(found.getPostCode(), oslo.getPostCode());
    assertEquals(found.getCity(), oslo.getCity());
  }

  @Test
  public void testUpdateNonExistingPostCode() {
    try {
      found = postCodeService.updatePostCode(bergen);
      fail();
    } catch (PostCodeDoesntExistException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testDeleteExistingPostCode() {
    try {
      postCodeService.deletePostCode(oslo);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testDeleteNonExistingPostCode() {
    try {
      postCodeService.deletePostCode(bergen);
      fail();
    } catch (PostCodeDoesntExistException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetCitiesByPostCode() {
    try {
      foundStrings = postCodeService.getCitiesByPostCode("0000");
    } catch (Exception e) {
      fail();
    }

    assertEquals(foundStrings.size(), 1);
    assertEquals(foundStrings.get(0), oslo.getCity());
  }

  @Test
  public void testGetCitiesByNonExistingPostCode() {
    try {
      foundStrings = postCodeService.getCitiesByPostCode("5000");
      fail();
    } catch (Exception e) {
      return;
    }
  }

  @Test
  public void testGetPostCodesByCity() {
    try {
      foundStrings = postCodeService.getPostCodesByCity("Oslo");
    } catch (Exception e) {
      fail();
    }

    assertEquals(foundStrings.size(), 1);
    assertEquals(foundStrings.get(0), oslo.getPostCode());
  }

  @Test
  public void testGetPostCodesByNonExistingCity() {
    try {
      foundStrings = postCodeService.getPostCodesByCity("Bergen");
      fail();
    } catch (Exception e) {
      return;
    }
  }
}
