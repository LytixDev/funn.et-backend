package edu.ntnu.idatt2105.placeholder.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import edu.ntnu.idatt2105.placeholder.dto.user.AuthenticateDTO;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.placeholder.service.user.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import java.time.Duration;
import java.time.Instant;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/v1/public/token")
@EnableAutoConfiguration
@CrossOrigin
public class TokenController {

  private final UserService userService;

  private final Logger logger = org.slf4j.LoggerFactory.getLogger(
    TokenController.class
  );

  @Autowired
  public TokenController(UserService userService) {
    this.userService = userService;
  }

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

  @PostMapping(value = "")
  @ResponseStatus(value = HttpStatus.CREATED)
  public String generateToken(AuthenticateDTO authenticate) {
    logger.info("Authenticating user: " + authenticate.getUsername());
    try {
      if (
        userService.authenticateUser(
          authenticate.getUsername(),
          authenticate.getPassword()
        )
      ) {
        logger.info("User authenticated: " + authenticate.getUsername());
        return generateToken(authenticate.getUsername());
      }
    } catch (UserDoesNotExistsException e) {
      logger.info("User does not exist: " + authenticate.getUsername());
      throw new ResponseStatusException(
        HttpStatus.UNAUTHORIZED,
        "Access denied, user does not exist..."
      );
    } catch (BadCredentialsException e) {
      logger.info("Wrong password: " + authenticate.getUsername());
      throw new ResponseStatusException(
        HttpStatus.UNAUTHORIZED,
        "Access denied, wrong password..."
      );
    } catch (Exception e) {
      logger.info("Unknown error: " + authenticate.getUsername());
      e.printStackTrace();
    }
    logger.info("Wrong credentials: " + authenticate.getUsername());

    throw new ResponseStatusException(
      HttpStatus.UNAUTHORIZED,
      "Access denied, wrong credentials..."
    );
  }

  public String generateToken(final String userId) {
    final Instant now = Instant.now();
    final Algorithm hmac512 = Algorithm.HMAC512(JWT_TOKEN_SECRET);
    // final JWTVerifier verifier = JWT.require(hmac512).build();
    return JWT
      .create()
      .withSubject(userId)
      .withIssuer("idatt2105_project_funn")
      .withIssuedAt(now)
      .withExpiresAt(now.plusMillis(JWT_TOKEN_VALIDITY.toMillis()))
      .sign(hmac512);
  }
}
