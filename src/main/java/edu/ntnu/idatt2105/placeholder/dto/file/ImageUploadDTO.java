package edu.ntnu.idatt2105.placeholder.dto.file;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
public class ImageUploadDTO {

  @NonNull
  MultipartFile image;

  @NonNull
  String alt;
}
