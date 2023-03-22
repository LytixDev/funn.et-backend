package edu.ntnu.idatt2105.placeholder.controller.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import edu.ntnu.idatt2105.placeholder.dto.user.AuthenticateDTO;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.placeholder.service.user.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller for generating JWT tokens.
 * @author Thomas S., Callum G.
 * @version 1.1 - 22.3.2023
 */
@RestController
@RequestMapping(value = "/api/v1/public/token")
@EnableAutoConfiguration
@RequiredArgsConstructor
public class TokenController {

  private final UserService userService;

  private static final Logger LOGGER = LoggerFactory.getLogger(
    TokenController.class
  );

  // keyStr is hardcoded here for testing purpose
  // in a real scenario, it should either be stored in a database or injected from the environment
  public static final String JWT_TOKEN_SECRET = Dotenv
    .load()
    .get("JWT_TOKEN_SECRET");

  private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(
    Dotenv.load().get("JWT_TOKEN_VALIDITY") != null
      ? Long.parseLong(Dotenv.load().get("JWT_TOKEN_VALIDITY"))
      : 30
  );

  /**
   * Generate a JWT token for the given user.
   * @param authenticate The user to generate a token for.
   * @return The generated token.
   * @throws UserDoesNotExistsException if the user does not exist.
   * @throws BadCredentialsException if the user credentials are wrong.
   * @throws ResponseStatusException if the user credentials are wrong.
   */
  @PostMapping(value = "")
  @ResponseStatus(value = HttpStatus.CREATED)
  public String generateToken(@RequestBody AuthenticateDTO authenticate)
    throws UserDoesNotExistsException, BadCredentialsException, ResponseStatusException {
    LOGGER.info("Authenticating user: {}", authenticate.getUsername());

    if (
      userService.authenticateUser(
        authenticate.getUsername(),
        authenticate.getPassword()
      )
    ) {
      LOGGER.info("User authenticated: {}", authenticate.getUsername());
      return generateToken(authenticate.getUsername());
    }

    LOGGER.info("Wrong credentials: {}", authenticate.getUsername());
    throw new ResponseStatusException(
      HttpStatus.UNAUTHORIZED,
      "Access denied, wrong credentials..."
    );
  }

  /**
   * Generate a JWT token for the given user.
   * @param userId The user to generate a token for.
   * @return The generated token.
   */
  public String generateToken(final String userId) {
    final Instant now = Instant.now();
    final Algorithm hmac512 = Algorithm.HMAC512(JWT_TOKEN_SECRET);

    return JWT
      .create()
      .withSubject(userId)
      .withIssuer("idatt2105_project_funn")
      .withIssuedAt(now)
      .withExpiresAt(now.plusMillis(JWT_TOKEN_VALIDITY.toMillis()))
      .sign(hmac512);
  }
}
