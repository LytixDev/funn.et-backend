package edu.ntnu.idatt2105.placeholder.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.EmailAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UsernameAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.model.user.Role;
import edu.ntnu.idatt2105.placeholder.model.user.User;
import edu.ntnu.idatt2105.placeholder.repository.user.UserRepository;
import edu.ntnu.idatt2105.placeholder.service.user.UserService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  UserService userService;

  @MockBean
  UserRepository userRepository;

  @Before
  public void setUp()
    throws UsernameAlreadyExistsException, EmailAlreadyExistsException, DatabaseException {
    User testUser = new User(
      "testUsername",
      "test@email.com",
      "testFirstName",
      "testLastName",
      "testPassword",
      Role.USER
    );
    userService.saveUser(testUser);
  }

  @Test
  public void testGetUserByUsername() {
    ResponseEntity<User> response = restTemplate.exchange(
      "/api/v1/public/user/testUsername",
      HttpMethod.GET,
      null,
      new ParameterizedTypeReference<User>() {}
    );

    System.out.println(response.toString());

    User user = response.getBody();

    assertNotNull(user);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
