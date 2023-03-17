package edu.ntnu.idatt2105.placeholder.service.location;

import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.PostCodeAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.PostCodeDoesntExistException;
import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import lombok.NonNull;

import java.util.List;

public interface PostCodeService {
  PostCode savePostCode(@NonNull PostCode postcode) throws PostCodeAlreadyExistsException, DatabaseException, NullPointerException;

  boolean postCodeExists(@NonNull PostCode postcode) throws NullPointerException;

  PostCode updatePostCode(@NonNull PostCode postcode) throws PostCodeDoesntExistException, DatabaseException, NullPointerException;

  void deletePostCode(@NonNull PostCode postcode) throws PostCodeDoesntExistException, DatabaseException, NullPointerException;

  List<PostCode> getAllPostCodes() throws DatabaseException;

  List<String> getCitiesByPostCode(@NonNull String postcode) throws DatabaseException, NullPointerException;

  List<String> getPostCodesByCity(@NonNull String city) throws DatabaseException, NullPointerException;
}
