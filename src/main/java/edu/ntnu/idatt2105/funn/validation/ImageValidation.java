package edu.ntnu.idatt2105.funn.validation;

import edu.ntnu.idatt2105.funn.model.file.ImageFileTypes;
import edu.ntnu.idatt2105.funn.validation.rules.ImageValidationRules;
import java.util.Arrays;
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
      Arrays
        .stream(ImageFileTypes.values())
        .anyMatch(type -> type.getFileType().equals(image.getContentType())) &&
      isBetween(image.getSize(), 1, ImageValidationRules.IMAGE_SIZE_MAX.getValue())
    );
  }

  /**
   * Validates images.
   * @param images The images to validate.
   * @return True if the images are valid, false otherwise.
   */
  public static boolean validateImages(MultipartFile[] images) {
    for (MultipartFile image : images) if (!validateImage(image)) return false;

    return true;
  }

  /**
   * Validate an image alt.
   * @param imageAlt The image alt to validate.
   * @return True if the image alt is valid, false otherwise.
   */
  public static boolean validateImageAlt(String imageAlt) {
    if (isNotNullOrEmpty(imageAlt)) return isSmallerThan(
      imageAlt,
      ImageValidationRules.IMAGE_ALT_MAX_LENGTH.getValue()
    );

    return true;
  }

  /**
   * Validate an image alt.
   * @param imageAlt The image alt to validate.
   * @return True if the image alt is valid, false otherwise.
   */
  public static boolean validateImageAlts(String[] imageAlts) {
    for (String imageAlt : imageAlts) if (!validateImageAlt(imageAlt)) return false;

    return true;
  }
}
