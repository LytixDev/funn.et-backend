package edu.ntnu.idatt2105.funn.dto.listing;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Data transfer object for creating category.
 * Used to transfer category data between the backend and the application.
 * @author Callum G.
 * @version 1.0 - 26.3.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryCreateDTO {

  @NonNull
  @NotBlank
  private String name;
}
