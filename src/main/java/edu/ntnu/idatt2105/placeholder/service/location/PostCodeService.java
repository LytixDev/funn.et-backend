package edu.ntnu.idatt2105.placeholder.service.location;

import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.PostCodeAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.PostCodeDoesntExistException;
import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import java.util.List;
import lombok.NonNull;
import org.springframework.stereotype.Service;

/**
 * Interface for the service class for the post code repository.
 * @author Callum G.
 * @version 1.0 - 17.03.2023
 */
@Service
public interface PostCodeService {
  PostCode savePostCode(@NonNull PostCode postcode)
    throws PostCodeAlreadyExistsException, DatabaseException, NullPointerException;

  boolean postCodeExists(@NonNull PostCode postcode)
    throws NullPointerException;

  PostCode updatePostCode(@NonNull PostCode postcode)
    throws PostCodeDoesntExistException, DatabaseException, NullPointerException;

  void deletePostCode(@NonNull PostCode postcode)
    throws PostCodeDoesntExistException, DatabaseException, NullPointerException;

  List<PostCode> getAllPostCodes() throws DatabaseException;

  List<String> getCitiesByPostCode(@NonNull String postcode)
    throws DatabaseException, NullPointerException;

  List<String> getPostCodesByCity(@NonNull String city)
    throws DatabaseException, NullPointerException;
}
