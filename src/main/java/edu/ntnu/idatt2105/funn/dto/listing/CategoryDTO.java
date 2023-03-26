package edu.ntnu.idatt2105.funn.dto.listing;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Data transfer object for Category.
 * Used for creating, updating and returning categories.
 * @author Callum G.
 * @version 1.0 - 25.03.2023
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CategoryDTO {

  @NotBlank
  private Long id;

  @NonNull
  @NotBlank
  private String name;
}
