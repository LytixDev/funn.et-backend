package edu.ntnu.idatt2105.placeholder.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Data transfer object for registration.
 * Used to transfer registration data between the backend and the application.
 * @author Callum G., Carl G.
 * @version 1.1 - 20.3.2023
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RegisterDTO {

  @NonNull
  @NotBlank
  private String username;

  @NonNull
  @NotBlank
  private String email;

  @NonNull
  @NotBlank
  private String firstName;

  @NonNull
  @NotBlank
  private String lastName;

  @NonNull
  @NotBlank
  private String password;
}
