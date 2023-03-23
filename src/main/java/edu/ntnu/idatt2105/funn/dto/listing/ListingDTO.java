package edu.ntnu.idatt2105.funn.dto.listing;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.ntnu.idatt2105.funn.dto.file.ImageResponseDTO;
import edu.ntnu.idatt2105.funn.model.listing.Category;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Data transfer object for listing.
 * Used to transfer listing data between the backend and the application.
 * @author Nicolai H. B., Callum G.
 * @version 1.1 - 23.3.2023
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ListingDTO {

  @NonNull
  @NotBlank
  private Long id;

  @NonNull
  @NotBlank
  private String username;

  @NonNull
  private Long location;

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
}
