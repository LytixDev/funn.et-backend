package edu.ntnu.idatt2105.funn.validation;

import edu.ntnu.idatt2105.funn.model.file.ImageFileTypes;
import edu.ntnu.idatt2105.funn.validation.rules.ImageValidationRules;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Class for image validation.
 * Used for validation of user input.
 * Checks that images to be uploaded are valid and within the allowed size.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public class ImageValidation extends BaseValidation {

  /**
   * Validate an image.
   * @param image The image to validate.
   * @return True if the image is valid, false otherwise.
   */
  public static boolean validateImage(MultipartFile image) {
    return (
      image != null &&
      EnumUtils.isValidEnum(ImageFileTypes.class, image.getContentType()) &&
      isSmallerThan(image.getSize(), ImageValidationRules.IMAGE_SIZE_MAX.getValue())
    );
  }

  /**
   * Validates images.
   * @param images The images to validate.
   * @return True if the images are valid, false otherwise.
   */
  public static boolean validateImages(MultipartFile[] images) {
    boolean valid = true;
    for (MultipartFile image : images) valid &= validateImage(image);

    return valid;
  }

  /**
   * Validate an image alt.
   * @param imageAlt The image alt to validate.
   * @return True if the image alt is valid, false otherwise.
   */
  public static boolean validateImageAlt(String imageAlt) {
    boolean valid = true;
    if (isNotNullOrEmpty(imageAlt)) {
      valid &= isSmallerThan(imageAlt, ImageValidationRules.IMAGE_ALT_MAX_LENGTH.getValue());
      valid &= imageAlt.matches(RegexPattern.IMAGE_ALT.getPattern());
    }

    return valid;
  }

  /**
   * Validate an image alt.
   * @param imageAlt The image alt to validate.
   * @return True if the image alt is valid, false otherwise.
   */
  public static boolean validateImageAlts(String[] imageAlts) {
    boolean valid = true;

    for (String imageAlt : imageAlts) valid &= validateImageAlt(imageAlt);

    return valid;
  }
}
