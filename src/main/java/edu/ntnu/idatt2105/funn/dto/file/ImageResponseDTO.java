package edu.ntnu.idatt2105.funn.dto.file;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Data transfer object for image.
 * Used to transfer image data between the backend and the application.
 * @author Callum G.
 * @version 1.1 - 23.3.2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponseDTO {

  @NonNull
  @NotBlank
  Long id;

  @NonNull
  @NotBlank
  String url;

  @NonNull
  @NotBlank
  String alt;
}
