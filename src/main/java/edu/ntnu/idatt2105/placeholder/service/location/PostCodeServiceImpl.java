package edu.ntnu.idatt2105.placeholder.service.location;

import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.PostCodeAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.PostCodeDoesntExistException;
import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import edu.ntnu.idatt2105.placeholder.model.location.PostCodeId;
import edu.ntnu.idatt2105.placeholder.repository.location.PostCodeRepository;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCodeServiceImpl implements PostCodeService {

  @Autowired
  private PostCodeRepository postCodeRepository;

  @Override
  public boolean postCodeExists(PostCode postcode) {
    return postCodeRepository.existsById(
      new PostCodeId(postcode.getPostCode(), postcode.getCity())
    );
  }

  @Override
  public PostCode savePostCode(PostCode postcode)
    throws PostCodeAlreadyExistsException, DatabaseException {
    if (postCodeExists(postcode)) throw new PostCodeAlreadyExistsException(
      "Postcode already exists"
    );

    try {
      return postCodeRepository.save(postcode);
    } catch (Exception e) {
      throw new DatabaseException("Error saving postcode");
    }
  }

  @Override
  public PostCode updatePostCode(@NotNull PostCode postcode)
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

  @Override
  public void deletePostCode(PostCode postcode)
    throws PostCodeDoesntExistException, DatabaseException {
    if (!postCodeExists(postcode)) throw new PostCodeDoesntExistException(
      "Postcode does not exist"
    );

    try {
      postCodeRepository.delete(postcode);
    } catch (Exception e) {
      throw new DatabaseException("Error deleting postcode");
    }
  }

  @Override
  public List<PostCode> getAllPostCodes() throws DatabaseException {
    try {
      return postCodeRepository.findAll();
    } catch (Exception e) {
      throw new DatabaseException("Error retrieving postcodes");
    }
  }

  @Override
  public List<String> getCitiesByPostCode(String postcode)
    throws DatabaseException {
    return postCodeRepository
      .findCitiesByPostCode(postcode)
      .orElseThrow(() -> new DatabaseException("No cities found for postcode.")
      );
  }

  @Override
  public List<String> getPostCodesByCity(String city) throws DatabaseException {
    return postCodeRepository
      .findPostCodesByCity(city)
      .orElseThrow(() ->
        new DatabaseException("No postcodes found for city found.")
      );
  }
}
