package edu.ntnu.idatt2105.placeholder.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.EmailAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UsernameAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.model.user.User;
import edu.ntnu.idatt2105.placeholder.repository.user.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Service class for user operations on the user repository.
 * @author Callum G.
 * @version 1.0
 * @date 13.3.2023
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;

    /**
     * Checks if a user with the given username exists.
     * @param username the username to check.
     * @return predicate if user exists.
     * @throws NullPointerException if username is null
     */
    public boolean usernameExists(@NonNull String username) throws NullPointerException {
        return userRepository.findByUsername(username).isPresent();
    }

    /**
     * Checks if a user with the given email exists.
     * @param email the email to check.
     * @return predicate if user exists.
     * @throws NullPointerException if email is null
     */
    public boolean emailExists(@NonNull String email) throws NullPointerException {
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * Gets the user with the given username.
     * @param username the username to check.
     * @return user with the given username.
     * @throws UserDoesNotExistsException if user does not exist.
     */
    public User getUserByUsername(@NonNull String username) throws UserDoesNotExistsException {
        return userRepository.findByUsername(username).orElseThrow(UserDoesNotExistsException::new);
    }

    /**
     * Gets the user with the given email.
     * @param email the email to check.
     * @return user with the given email.
     * @throws UserDoesNotExistsException if user does not exist.
     */
    public User getUserByEmail(@NonNull String email) throws UserDoesNotExistsException {
        return userRepository.findByEmail(email).orElseThrow(UserDoesNotExistsException::new);
    }
    
    /**
     * Saves a user to the database.
     * @param user the user to save.
     * @return the saved user.
     * @throws UsernameAlreadyExistsException if a user with the given username already exists.
     * @throws EmailAlreadyExistsException if a user with the given email already exists.
     * @throws DatabaseException if an error occurred while saving the user.
     * @throws NullPointerException if user is null.
     */
    public User saveUser(@NonNull User user) throws UsernameAlreadyExistsException, EmailAlreadyExistsException, DatabaseException, NullPointerException {
        if (usernameExists(user.getUsername()))
            throw new UsernameAlreadyExistsException("A user with the username "+ user.getUsername() + " already exists.");

        if (emailExists(user.getEmail()))
            throw new EmailAlreadyExistsException("A user with the email "+ user.getEmail() + " already exists.");
        
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new DatabaseException("An error occurred while saving the user.");
        }
    }

    /**
     * Deletes a user from the database.
     * @param user the user to delete.
     * @throws DatabaseException if an error occurred while deleting the user.
     * @throws NullPointerException if user is null.
     */
    public void deleteUser(@NonNull User user) throws DatabaseException {
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            throw new DatabaseException("An error occurred while deleting the user, are you sure it exists?");
        }
    }

    /**
     * Deletes a user from the database by username.
     * @param username the username of the user to delete.
     * @throws UserDoesNotExistsException if the user does not exist.
     * @throws NullPointerException if username is null.
     */
    public void deleteUserByUsername(@NonNull String username) throws UserDoesNotExistsException {
        userRepository.delete(getUserByUsername(username));
    }

    /**
     * Deletes a user from the database by email.
     * @param email the email of the user to delete.
     * @throws UserDoesNotExistsException if the user does not exist.
     * @throws NullPointerException if email is null.
     */
    public void deleteUserByEmail(@NonNull String email) throws UserDoesNotExistsException {
        userRepository.delete(getUserByEmail(email));
    }

    /**
     * Updates a user in the database.
     * @param user the user to update.
     * @return the updated user.
     * @throws UserDoesNotExistsException if the user does not exist.
     * @throws NullPointerException if user is null.
     */
    public User updateUser(@NonNull User user) throws UserDoesNotExistsException {
        return userRepository.save(getUserByUsername(user.getUsername()));
    }

    /**
     * Gets all users from the database.
     * @return a list of all users.
     * @throws DatabaseException if an error occurred while getting the users.
     */
    public List<User> getAllUsers() throws DatabaseException {
        List<User> users = userRepository.findAll();
        
        if (users.isEmpty())
            throw new DatabaseException("No users found in the database.");

        return users;
    }
}