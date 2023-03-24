package edu.ntnu.idatt2105.funn.dto.file;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

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
public class ImageUploadDTO {

  @NonNull
  @NotBlank
  MultipartFile image;

  @NonNull
  @NotBlank
  String alt;
}
