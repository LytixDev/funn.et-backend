package edu.ntnu.idatt2105.placeholder.controller;

import edu.ntnu.idatt2105.placeholder.dto.user.UserDTO;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UsernameAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.mapper.user.UserMapper;
import edu.ntnu.idatt2105.placeholder.model.user.User;
import edu.ntnu.idatt2105.placeholder.service.user.UserService;
import java.util.Arrays;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/public/user")
@EnableAutoConfiguration
@CrossOrigin
public class UserController {

  private final UserService userService;

  private Logger logger = Logger.getLogger(UserController.class.getName());

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{username}")
  public ResponseEntity<UserDTO> getUser(@PathVariable String username) {
    try {
      UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(
        userService.getUserByUsername(username)
      );
      return ResponseEntity.ok(userDTO);
    } catch (UserDoesNotExistsException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("")
  public ResponseEntity<String> createUser(User user) {
    try {
      userService.saveUser(user);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body(e.getMessage());
    }
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
