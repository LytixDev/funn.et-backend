package edu.ntnu.idatt2105.funn.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Data transfer object for authentication.
 * Used to transfer login data between the backend and the application.
 * @author Callum G.
 * @version 1.0 - 13.3.2023
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AuthenticateDTO {

  @NonNull
  private String username;

  @NonNull
  private String password;
}
