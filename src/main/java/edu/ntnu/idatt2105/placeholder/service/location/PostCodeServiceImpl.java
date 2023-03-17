package edu.ntnu.idatt2105.placeholder.service.location;

import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.PostCodeAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.PostCodeDoesntExistException;
import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import edu.ntnu.idatt2105.placeholder.model.location.PostCodeId;
import edu.ntnu.idatt2105.placeholder.repository.location.PostCodeRepository;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for the post code repository.
 * @author Callum G.
 * @version 1.0 - 17.03.2023
 */
@Service
@RequiredArgsConstructor
public class PostCodeServiceImpl implements PostCodeService {

  @Autowired
  private PostCodeRepository postCodeRepository;

  /**
   * Checks if a post code exists in the database.
   * @param postcode The post code to check.
   * @return True if the post code exists, false otherwise.
   */
  @Override
  public boolean postCodeExists(@NonNull PostCode postcode)
    throws NullPointerException {
    return postCodeRepository.existsById(
      new PostCodeId(postcode.getPostCode(), postcode.getCity())
    );
  }

  /**
   * Saves a post code to the database.
   * @param postcode The post code to save.
   * @return The saved post code.
   * @throws PostCodeAlreadyExistsException If the post code already exists.
   * @throws DatabaseException If there is an error saving the post code.
   * @throws NullPointerException If the post code is null.
   */
  @Override
  public PostCode savePostCode(@NonNull PostCode postcode)
    throws PostCodeAlreadyExistsException, DatabaseException, NullPointerException {
    if (postCodeExists(postcode)) throw new PostCodeAlreadyExistsException(
      "Postcode already exists"
    );

    try {
      return postCodeRepository.save(postcode);
    } catch (Exception e) {
      throw new DatabaseException("Error saving postcode");
    }
  }

  /**
   * Gets a post code from the database.
   * @param postcode The post code to get.
   * @return The post code.
   * @throws PostCodeDoesntExistException If the post code does not exist.
   * @throws DatabaseException If there is an error getting the post code.
   * @throws NullPointerException If the post code is null.
   */
  @Override
  public PostCode updatePostCode(@NonNull PostCode postcode)
    throws PostCodeDoesntExistException, DatabaseException, NullPointerException {
    if (!postCodeExists(postcode)) throw new PostCodeDoesntExistException(
      "Postcode does not exist"
    );

    try {
      return postCodeRepository.save(postcode);
    } catch (Exception e) {
      throw new DatabaseException("Error updating postcode");
    }
  }

  /**
   * Deletes a post code from the database.
   * @param postcode The post code to delete.
   * @throws PostCodeDoesntExistException If the post code does not exist.
   * @throws DatabaseException If there is an error deleting the post code.
   * @throws NullPointerException If the post code is null.
   */
  @Override
  public void deletePostCode(@NonNull PostCode postcode)
    throws PostCodeDoesntExistException, DatabaseException, NullPointerException {
    if (!postCodeExists(postcode)) throw new PostCodeDoesntExistException(
      "Postcode does not exist"
    );

    try {
      postCodeRepository.delete(postcode);
    } catch (Exception e) {
      throw new DatabaseException("Error deleting postcode");
    }
  }

  /**
   * Gets all post codes from the database.
   * @return A list of all post codes.
   * @throws DatabaseException If there is an error retrieving the post codes.
   */
  @Override
  public List<PostCode> getAllPostCodes() throws DatabaseException {
    try {
      return postCodeRepository.findAll();
    } catch (Exception e) {
      throw new DatabaseException("Error retrieving postcodes");
    }
  }

  /**
   * Gets all cities from the database.
   * @return A list of all cities.
   * @throws DatabaseException If there is an error retrieving the cities.
   * @throws NullPointerException If the post code is null.
   */
  @Override
  public List<String> getCitiesByPostCode(@NonNull String postcode)
    throws DatabaseException, NullPointerException {
    return postCodeRepository
      .findCitiesByPostCode(postcode)
      .orElseThrow(() -> new DatabaseException("No cities found for postcode.")
      );
  }

  /**
   * Gets all post codes from the database.
   * @return A list of all post codes.
   * @throws DatabaseException If there is an error retrieving the post codes.
   * @throws NullPointerException If the post code is null.
   */
  @Override
  public List<String> getPostCodesByCity(@NonNull String city)
    throws DatabaseException, NullPointerException {
    return postCodeRepository
      .findPostCodesByCity(city)
      .orElseThrow(() ->
        new DatabaseException("No postcodes found for city found.")
      );
  }
}
