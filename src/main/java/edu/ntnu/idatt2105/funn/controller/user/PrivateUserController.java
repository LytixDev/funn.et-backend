package edu.ntnu.idatt2105.funn.controller.user;

import edu.ntnu.idatt2105.funn.dto.user.UserDTO;
import edu.ntnu.idatt2105.funn.dto.user.UserPatchDTO;
import edu.ntnu.idatt2105.funn.exceptions.PermissionDeniedException;
import edu.ntnu.idatt2105.funn.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.funn.mapper.user.UserMapper;
import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.model.user.Role;
import edu.ntnu.idatt2105.funn.model.user.User;
import edu.ntnu.idatt2105.funn.service.listing.ListingService;
import edu.ntnu.idatt2105.funn.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for private user endpoints.
 * @author Thomas S, Callum Gran, Nicolai H. B.
 * @version 1.2 - 24.03.2023
 */
@RestController
@RequestMapping(value = "/api/v1/private/user")
@EnableAutoConfiguration
@RequiredArgsConstructor
public class PrivateUserController {

  private static final Logger LOGGER = LoggerFactory.getLogger(PrivateUserController.class);

  private final UserService userService;

  /**
   * Get the authenticated user.
   * @param username The username of the authenticated user.
   * @return The authenticated user.
   * @throws UserDoesNotExistsException If the user does not exist.
   */
  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
    summary = "Get the authenticated user",
    description = "Get the authenticated user",
    tags = { "user" }
  )
  public ResponseEntity<UserDTO> getUser(@AuthenticationPrincipal String username)
    throws UserDoesNotExistsException {
    LOGGER.info("GET request for user: {}", username);
    User authenticatedUser = userService.getUserByUsername(username);

    UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(authenticatedUser);

    LOGGER.info("Mapped to UserDTO: {}", userDTO);

    return ResponseEntity.ok(userDTO);
  }

  @PatchMapping(
    value = "/{username}",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @Operation(
    summary = "Update the user with the given username",
    description = "Update the user with the given username",
    tags = { "user" }
  )
  public ResponseEntity<UserDTO> updateUser(
    @AuthenticationPrincipal String tokenUsername,
    @PathVariable String username,
    @RequestBody UserPatchDTO userUpdateDTO
  ) throws UserDoesNotExistsException, PermissionDeniedException, BadCredentialsException {
    LOGGER.info("PATCH request for user: {}", username);
    User authenticatedUser = userService.getUserByUsername(tokenUsername);
    if (
      !tokenUsername.equals(username) && authenticatedUser.getRole() != Role.ADMIN
    ) throw new PermissionDeniedException();

    User userToUpdate = userService.getUserByUsername(username);

    User updatedUser = userService.partialUpdate(
      userToUpdate,
      userUpdateDTO.getEmail(),
      userUpdateDTO.getFirstName(),
      userUpdateDTO.getLastName(),
      userUpdateDTO.getOldPassword(),
      userUpdateDTO.getNewPassword()
    );
    UserDTO updatedUserDTO = UserMapper.INSTANCE.userToUserDTO(updatedUser);
    LOGGER.info("Mapped to UserDTO: {}", updatedUserDTO);
    return ResponseEntity.ok(updatedUserDTO);
  }
}
