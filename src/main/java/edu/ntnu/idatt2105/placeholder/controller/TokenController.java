package edu.ntnu.idatt2105.placeholder.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import edu.ntnu.idatt2105.placeholder.dto.user.AuthenticateDTO;
import edu.ntnu.idatt2105.placeholder.service.user.UserService;
import java.time.Duration;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/token")
@EnableAutoConfiguration
@CrossOrigin
public class TokenController {

  private final UserService userService;

  @Autowired
  public TokenController(UserService userService) {
    this.userService = userService;
  }

  // keyStr is hardcoded here for testing purpose
  // in a real scenario, it should either be stored in a database or injected from the environment
  public static final String JWT_TOKEN_SECRET = System.getenv(
    "JWT_TOKEN_SECRET"
  );
  private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(
    System.getenv("JWT_TOKEN_VALIDITY") != null
      ? Long.parseLong(System.getenv("JWT_TOKEN_VALIDITY"))
      : 30
  );

  @PostMapping(value = "")
  @ResponseStatus(value = HttpStatus.CREATED)
  public String generateToken(final @RequestBody AuthenticateDTO authenticate)
    throws Exception {
    // if username and password are valid, issue an access token
    // note that subsequent requests need this token
    if (
      userService.authenticateUser(
        authenticate.getUsername(),
        authenticate.getPassword()
      )
    ) {
      return generateToken(authenticate.getUsername());
    }

    throw new ResponseStatusException(
      HttpStatus.UNAUTHORIZED,
      "Access denied, wrong credentials...."
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
