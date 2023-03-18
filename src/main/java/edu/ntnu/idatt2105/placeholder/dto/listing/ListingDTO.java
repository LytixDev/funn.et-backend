package edu.ntnu.idatt2105.placeholder.dto.listing;

import edu.ntnu.idatt2105.placeholder.model.listing.Category;
import java.time.LocalDate;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.NonNull;

/**
 * Data transfer object for listing.
 * Used to transfer listing data between the backend and the application.
 * @author Nicolai H. B.
 * @version 1.0 - 18.3.2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListingDTO {

  @NonNull
  private Long id;

  @NonNull
  private String username;

  @NonNull
  private Long locationId;

  @NonNull
  private String title;

  @NonNull
  private String briefDescription;

  private String fullDescription;

  @NonNull
  private Category category;

  private double price;

  @NonNull
  private LocalDate publicationDate;

  @NonNull
  private LocalDate expirationDate;

  private byte[] image;
}
