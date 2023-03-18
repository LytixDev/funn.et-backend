package edu.ntnu.idatt2105.placeholder.dto.listing;

import edu.ntnu.idatt2105.placeholder.model.listing.Category;
import java.util.Date;
import lombok.*;

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
  private String title;

  @NonNull
  private String brief_description;

  private String full_description;

  @NonNull
  private Category category;

  private double price;

  @NonNull
  private Date publicationDate;

  @NonNull
  private Date expirationDate;

  private byte[] image;
}
