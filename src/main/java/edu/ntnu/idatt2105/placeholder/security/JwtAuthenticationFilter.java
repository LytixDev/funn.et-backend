package edu.ntnu.idatt2105.placeholder.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import edu.ntnu.idatt2105.placeholder.controller.TokenController;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final Logger LOGGER = LogManager.getLogger(
    JwtAuthenticationFilter.class
  );

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    LOGGER.info("doFilterInternal() called");
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    // if Bearer auth header does not exist, continue with unauthenticated user context
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    // if Bearer auth header exists, validate token, and extract userId from token.
    // Note that we have added userId as subject to the token when it is generated
    // Note also that the token comes in this format 'Bearer token'
    String jwtToken = authHeader.substring(7);
    final String username = validateJwtTokenAndGetUsername(jwtToken);
    if (username == null) {
      // validation failed or token expired
      filterChain.doFilter(request, response);
      return;
    }

    //Add user details to the authentication context
    SecurityContextHolder
      .getContext()
      .setAuthentication(
        new UsernamePasswordAuthenticationToken(
          username,
          null,
          Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        )
      );

    // then, continue with authenticated user context
    filterChain.doFilter(request, response);
  }

  public String validateJwtTokenAndGetUsername(final String token) {
    try {
      final Algorithm hmac512 = Algorithm.HMAC512(
        TokenController.JWT_TOKEN_SECRET
      );
      final JWTVerifier verifier = JWT.require(hmac512).build();
      return verifier.verify(token).getSubject();
    } catch (final JWTVerificationException verificationEx) {
      LOGGER.warn("token is invalid: {}", verificationEx.getMessage());
      return null;
    }
  }
}
