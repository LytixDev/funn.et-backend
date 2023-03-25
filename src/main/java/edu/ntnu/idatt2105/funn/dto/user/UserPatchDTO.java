package edu.ntnu.idatt2105.funn.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Nicolai H. B.
 * @version 1.0 - 24.3.2023
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class UserPatchDTO {

  private String email;

  private String firstName;

  private String lastName;

  private String oldPassword;

  private String newPassword;
}
