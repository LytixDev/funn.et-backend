package edu.ntnu.idatt2105.placeholder.dto.location;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Data transfer object for location.
 * Used to transfer location data between the backend and the application.
 * @author Callum G.
 * @version 1.0 - 21.3.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationCreateDTO {

  @NonNull
  @NotBlank
  private String address;

  @NotBlank
  private double latitude;

  @NotBlank
  private double longitude;

  @NotBlank
  private int postCode;

  @NonNull
  @NotBlank
  private String city;
}
