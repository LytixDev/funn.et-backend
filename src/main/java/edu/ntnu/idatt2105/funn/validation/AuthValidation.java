package edu.ntnu.idatt2105.funn.validation;

import edu.ntnu.idatt2105.funn.model.user.Role;
import edu.ntnu.idatt2105.funn.security.Auth;

/**
 * Class for validation of the Auth class.
 * Not actual authentication validation.
 * This class is used to validate objects of the Auth class.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public class AuthValidation extends BaseValidation {

  /**
   * Validates the Auth object.
   * @param auth The Auth object to validate.
   * @return True if the Auth object is valid, false otherwise.
   */
  public static boolean validateAuth(Auth auth) {
    if (auth == null) return false;

    return auth.getRole() != null && UserValidation.validateUsername(auth.getUsername());
  }

  /**
   * Validates the Auth object and checks if the Auth object has the correct role.
   * @param auth The Auth object to validate.
   * @param role The role to check for.
   * @return True if the Auth object is valid and has the correct role, false otherwise.
   */
  public static boolean hasRole(Auth auth, Role role) {
    boolean valid = validateAuth(auth);
    valid &= auth.getRole() == role;

    return valid;
  }

  /**
   * Validates the Auth object and checks if the Auth object has the correct role or is the correct user.
   * @param auth The Auth object to validate.
   * @param role The role to check for.
   * @param username The username to check for.
   * @return True if the Auth object is valid and has the correct role or is the correct user, false otherwise.
   */
  public static boolean hasRoleOrIsUser(Auth auth, Role role, String username) {
    boolean valid = validateAuth(auth);
    valid &= auth.getRole() == role || auth.getUsername().equals(username);

    return valid;
  }
}
