package edu.ntnu.idatt2105.funn.dto.user;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatDTO {

  @NonNull
  @NotBlank
  private Long id;

  @NonNull
  @NotBlank
  private UserDTO messager;

  @NonNull
  @NotBlank
  private UserDTO listingUser;

  @NonNull
  @NotBlank
  private Long listingId;

  @NonNull
  @NotBlank
  private List<MessageDTO> messages;
}
