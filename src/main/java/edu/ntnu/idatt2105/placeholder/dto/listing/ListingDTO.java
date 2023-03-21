package edu.ntnu.idatt2105.placeholder.dto.listing;

import com.fasterxml.jackson.annotation.JsonFormat;

import edu.ntnu.idatt2105.placeholder.dto.file.ImageResponseDTO;
import edu.ntnu.idatt2105.placeholder.dto.file.ImageUploadDTO;
import edu.ntnu.idatt2105.placeholder.model.listing.Category;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for listing.
 * Used to transfer listing data between the backend and the application.
 * @author Nicolai H. B., Callum G.
 * @version 1.0 - 18.3.2023
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ListingDTO {

  private Long id;

  @NonNull
  @NotBlank
  private String username;

  @NonNull
  private Long locationId;

  @NonNull
  @NotBlank
  private String title;

  @NonNull
  @NotBlank
  private String briefDescription;

  private String fullDescription;

  @NonNull
  @NotBlank
  private Category category;

  private double price;

  @NonNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate publicationDate;

  @NonNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate expirationDate;

  private List<ImageResponseDTO> imageResponse;

  private List<ImageUploadDTO> imageUpload;
}
