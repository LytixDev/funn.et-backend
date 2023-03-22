package edu.ntnu.idatt2105.funn.service.location;

import edu.ntnu.idatt2105.funn.exceptions.DatabaseException;
import edu.ntnu.idatt2105.funn.exceptions.location.PostCodeAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.location.PostCodeDoesntExistException;
import edu.ntnu.idatt2105.funn.model.location.PostCode;
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

  boolean postCodeExists(@NonNull PostCode postcode) throws NullPointerException;

  PostCode updatePostCode(@NonNull PostCode postcode)
    throws PostCodeDoesntExistException, DatabaseException, NullPointerException;

  void deletePostCode(@NonNull PostCode postcode)
    throws PostCodeDoesntExistException, DatabaseException, NullPointerException;

  List<PostCode> getAllPostCodes() throws DatabaseException;

  List<String> getCitiesByPostCode(@NonNull Integer postcode)
    throws DatabaseException, NullPointerException;

  List<Integer> getPostCodesByCity(@NonNull String city)
    throws DatabaseException, NullPointerException;
}
