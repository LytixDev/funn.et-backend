package edu.ntnu.idatt2105.funn.security;

import edu.ntnu.idatt2105.funn.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for authentication.
 * @author Callum G.
 * @version 1.0 - 26.3.2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Auth {

  private String username;

  private Role role;
}
