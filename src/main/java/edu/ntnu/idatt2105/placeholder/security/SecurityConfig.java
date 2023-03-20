package edu.ntnu.idatt2105.placeholder.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configures spring security
 *
 * @author Carl G, Thomas S.
 * @version 1.1 - 20.3.2023
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * Permits all requests to `swagger/**` and `api/v1/public/**`.
   *
   * @param http HttpSecurity - http object to build configurations on
   * @throws Exception thrown if an error occurs when permitting requests
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf()
      .disable()
      .cors(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(auth ->
        auth
          .requestMatchers("/swagger/**", "/api/v1/public/**")
          .permitAll()
          .anyRequest()
          .authenticated()
      )
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
