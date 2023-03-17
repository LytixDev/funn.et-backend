package edu.ntnu.idatt2105.placeholder.service.location;

import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import java.util.List;

public interface PostCodeService {
  PostCode savePostcode(PostCode postcode);

  PostCode getPostcodeById(Long id);

  PostCode updatePostcode(PostCode postcode);

  void deletePostcode(PostCode postcode);

  List<PostCode> getAllPostcodes();

  List<String> getCitiesByPostcode(String postcode);

  List<String> getPostcodesByCity(String city);
}
