package edu.ntnu.idatt2105.placeholder.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/swagger-ui/index.html", "v3/api-docs").csrf().disable()
                .authorizeHttpRequests((request) -> request.anyRequest().permitAll());
        return http.build();
    }
}
