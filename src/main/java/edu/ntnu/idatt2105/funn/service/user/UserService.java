package edu.ntnu.idatt2105.funn.service.user;

import edu.ntnu.idatt2105.funn.exceptions.DatabaseException;
import edu.ntnu.idatt2105.funn.exceptions.user.EmailAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.funn.exceptions.user.UsernameAlreadyExistsException;
import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.model.user.User;
import java.util.List;
import java.util.Set;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

/**
 * Interface for the user service.
 * @author Callum G.
 * @version 1.0 - 13.3.2023
 */
@Service
public interface UserService {
  public boolean usernameExists(String username) throws NullPointerException;

  public boolean emailExists(String email) throws NullPointerException;

  public User getUserByUsername(String username) throws UserDoesNotExistsException;

  public User getUserByEmail(String email) throws UserDoesNotExistsException;

  public User saveUser(User user)
    throws UsernameAlreadyExistsException, EmailAlreadyExistsException, DatabaseException;

  public void deleteUser(User user) throws DatabaseException;

  public void deleteUserByUsername(String username) throws UserDoesNotExistsException;

  public void deleteUserByEmail(String email) throws UserDoesNotExistsException;

  public User updateUser(User user) throws UserDoesNotExistsException;

  public User partialUpdate(
    User user,
    String email,
    String firstName,
    String lastName,
    String oldPassword,
    String newPassword
  ) throws UserDoesNotExistsException, BadCredentialsException;

  public List<User> getAllUsers() throws DatabaseException;

  public boolean authenticateUser(String username, String password)
    throws UserDoesNotExistsException, BadCredentialsException;

  public void favoriteOrUnfavoriteListing(String username, Listing listing) throws UserDoesNotExistsException;

  public Set<Listing> getFavoriteListings(String username) throws UserDoesNotExistsException;

  public boolean isFavoriteByUser(String username, Listing listing)
    throws UserDoesNotExistsException;
}
