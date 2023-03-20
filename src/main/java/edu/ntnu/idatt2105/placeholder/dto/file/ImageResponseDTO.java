package edu.ntnu.idatt2105.placeholder.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.core.io.Resource;

/**
 * Data transfer object for image.
 * Used to transfer image data between the backend and the application.
 * @author Callum G.
 * @version 1.0 - 20.3.2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponseDTO {

  @NonNull
  Resource image;

  @NonNull
  String alt;
}
