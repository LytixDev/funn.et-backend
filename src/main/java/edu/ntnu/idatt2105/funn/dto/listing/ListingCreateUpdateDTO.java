package edu.ntnu.idatt2105.funn.dto.listing;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.ntnu.idatt2105.funn.model.listing.Category;
import edu.ntnu.idatt2105.funn.model.listing.Status;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

/**
 * Base data transfer object for creating and updating listings.
 * Used to transfer listing data between the backend and the application.
 * @author Carl G.
 * @version 1.0 - 27.3.2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = false)
public class ListingCreateUpdateDTO {

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

  private Status status;

  private double price;

  @NonNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate publicationDate;

  @NonNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate expirationDate;

  MultipartFile[] images;

  String[] imageAlts;
}
