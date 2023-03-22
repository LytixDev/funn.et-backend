package edu.ntnu.idatt2105.placeholder.controller.user;

import edu.ntnu.idatt2105.placeholder.dto.user.RegisterDTO;
import edu.ntnu.idatt2105.placeholder.dto.user.UserDTO;
import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.EmailAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UsernameAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.mapper.user.RegisterMapper;
import edu.ntnu.idatt2105.placeholder.mapper.user.UserMapper;
import edu.ntnu.idatt2105.placeholder.model.user.User;
import edu.ntnu.idatt2105.placeholder.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for public user endpoints.
 * @author Thomas S, Callum Gran
 * @version 1.1 - 22.03.2023
 */
@RestController
@RequestMapping(value = "/api/v1/public/user")
@EnableAutoConfiguration
@CrossOrigin
@RequiredArgsConstructor
public class PublicUserController {

  private final UserService userService;

  private static final Logger LOGGER = LoggerFactory.getLogger(
    PrivateUserController.class
  );

  /**
   * Get a user by username.
   * @param username The username of the user.
   * @return The user.
   * @throws UserDoesNotExistsException If the user does not exist.
   */
  @GetMapping("/{username}")
  public ResponseEntity<UserDTO> getUser(@PathVariable String username)
    throws UserDoesNotExistsException {
    LOGGER.info("GET request for user: {}", username);
    User user = userService.getUserByUsername(username);

    LOGGER.info("User found: {}", user);

    UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(user);

    LOGGER.info("Mapped user to userDTO: {}", userDTO);

    return ResponseEntity.ok(userDTO);
  }

  /**
   * Create a new user.
   * @param registerUser The user to create.
   * @return The created user.
   * @throws UsernameAlreadyExistsException If the username already exists.
   * @throws EmailAlreadyExistsException If the email already exists.
   * @throws DatabaseException If the database fails.
   */
  @PostMapping
  public ResponseEntity<String> createUser(
    @RequestBody RegisterDTO registerUser
  )
    throws UsernameAlreadyExistsException, EmailAlreadyExistsException, DatabaseException {
    LOGGER.info("POST request for user: {}", registerUser);

    User user = RegisterMapper.INSTANCE.registerDTOtoUser(registerUser);

    LOGGER.info("Mapped registerDTO to user: {}", user);

    user = userService.saveUser(user);

    LOGGER.info("User saved: {}", user);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
