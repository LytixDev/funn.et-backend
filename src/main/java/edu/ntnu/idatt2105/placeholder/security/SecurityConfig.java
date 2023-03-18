package edu.ntnu.idatt2105.placeholder.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configures spring security
 *
 * @author Carl G.
 * @version 1.0
 * @date 17.3.2023
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * Permits all requests to `swagger-ui/index.html` and `v3/api-docs`.
   *
   * @param http HttpSecurity - http object to build configurations on
   * @throws Exception thrown if an error occurs when permitting requests
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf()
      .disable()
      .cors()
      .and()
      .authorizeHttpRequests()
      .requestMatchers(
        "/token",
        "/swagger-ui/index.html",
        "v3/api-docs",
        "api/public"
      )
      .permitAll()
      .anyRequest()
      .authenticated()
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .addFilterBefore(
        new JwtAuthenticationFilter(),
        UsernamePasswordAuthenticationFilter.class
      );

    return http.build();
  }
}
