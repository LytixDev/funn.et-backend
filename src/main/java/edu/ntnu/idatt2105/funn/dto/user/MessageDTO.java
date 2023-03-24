package edu.ntnu.idatt2105.funn.dto.user;

import edu.ntnu.idatt2105.funn.model.user.Chat;
import jakarta.validation.constraints.NotBlank;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Class representing a data transfer object for a message.
 * @author Callum G.
 * @version 1.0 - 23.03.2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO {

  private Long id;

  @NonNull
  @NotBlank
  private String message;

  private String username;

  private Timestamp timestamp;
}
