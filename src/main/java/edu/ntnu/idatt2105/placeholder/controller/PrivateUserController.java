package edu.ntnu.idatt2105.placeholder.controller;

import edu.ntnu.idatt2105.placeholder.dto.user.UserDTO;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.placeholder.mapper.user.UserMapper;
import edu.ntnu.idatt2105.placeholder.model.user.User;
import edu.ntnu.idatt2105.placeholder.service.user.UserService;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/private/user")
@EnableAutoConfiguration
@CrossOrigin
public class PrivateUserController {

  private final UserService userService;

  private Logger logger = Logger.getLogger(
    PublicUserController.class.getName()
  );

  @Autowired
  public PrivateUserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  public ResponseEntity<UserDTO> getUser(
    @AuthenticationPrincipal String username
  ) {
    try {
      User authenticatedUser = userService.getUserByUsername(username);
      UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(authenticatedUser);
      return ResponseEntity.ok(userDTO);
    } catch (UserDoesNotExistsException e) {
      e.printStackTrace();
      return ResponseEntity.notFound().build();
    }
  }
}
