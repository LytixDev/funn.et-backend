package edu.ntnu.idatt2105.placeholder.service.user;

import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.EmailAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UsernameAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.model.user.User;
import java.util.List;
import org.springframework.security.authentication.BadCredentialsException;

public interface UserService {
  public boolean usernameExists(String username) throws NullPointerException;

  public boolean emailExists(String email) throws NullPointerException;

  public User getUserByUsername(String username)
    throws UserDoesNotExistsException;

  public User getUserByEmail(String email) throws UserDoesNotExistsException;

  public User saveUser(User user)
    throws UsernameAlreadyExistsException, EmailAlreadyExistsException, DatabaseException;

  public void deleteUser(User user) throws DatabaseException;

  public void deleteUserByUsername(String username)
    throws UserDoesNotExistsException;

  public void deleteUserByEmail(String email) throws UserDoesNotExistsException;

  public User updateUser(User user) throws UserDoesNotExistsException;

  public List<User> getAllUsers() throws DatabaseException;

  public boolean authenticateUser(String username, String password)
    throws UserDoesNotExistsException, BadCredentialsException;
}
