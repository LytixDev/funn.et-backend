package edu.ntnu.idatt2105.funn.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt2105.funn.validation.rules.ImageValidationRules;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class ImageValidationTest {

  private MultipartFile jpgFile = new MockMultipartFile(
    "test.jpg",
    "test.jpg",
    "image/jpeg",
    "test".getBytes()
  );

  private MultipartFile pngFile = new MockMultipartFile(
    "test.png",
    "test.png",
    "image/png",
    "test".getBytes()
  );

  private MultipartFile illegalFile = new MockMultipartFile(
    "test.txt",
    "test.txt",
    "text/plain",
    "test".getBytes()
  );

  private MultipartFile[] legaFiles = { jpgFile, pngFile };

  private MultipartFile[] illegalFiles = { jpgFile, pngFile, illegalFile };

  private MultipartFile[] nullFiles = { jpgFile, pngFile, null };

  private MultipartFile[] emptyFiles = {
    jpgFile,
    pngFile,
    new MockMultipartFile("test2.jpg", "test2.jpg", "image/jpeg", new byte[0]),
  };

  private String imageAlt1 = "image1";

  private String imageAlt2 = "image2";

  private String imageAlt3 = "image3";

  private String longString =
    "message message message" +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message ";

  private String[] imageAlt = { imageAlt1, imageAlt2, imageAlt3 };

  private String[] nullImageAlt = { null, imageAlt2, imageAlt3 };

  private String[] emptyImageAlt = { "", imageAlt2, imageAlt3 };

  private String[] longImageAlt = { longString, imageAlt2, imageAlt3 };

  @Test
  public void testImageValidateJpgReturnsTrue() {
    assertTrue(ImageValidation.validateImage(jpgFile));
  }

  @Test
  public void testImageValidatePngReturnsTrue() {
    assertTrue(ImageValidation.validateImage(pngFile));
  }

  @Test
  public void testImageValidateIllegalFileReturnsFalse() {
    assertFalse(ImageValidation.validateImage(illegalFile));
  }

  @Test
  public void testImageValidateNullReturnsFalse() {
    assertFalse(ImageValidation.validateImage(null));
  }

  @Test
  public void testImageValidateEmptyReturnsFalse() {
    assertFalse(
      ImageValidation.validateImage(
        new MockMultipartFile("test2.jpg", "test2.jpg", "image/jpeg", new byte[0])
      )
    );
  }

  @Test
  public void testImageValidateToBigFileReturnsFalse() {
    assertFalse(
      ImageValidation.validateImage(
        new MockMultipartFile(
          "test2.jpg",
          "test2.jpg",
          "image/jpeg",
          new byte[ImageValidationRules.IMAGE_SIZE_MAX.getValue() + 1]
        )
      )
    );
  }

  @Test
  public void testImageValidateFilesReturnsTrue() {
    assertTrue(ImageValidation.validateImages(legaFiles));
  }

  @Test
  public void testImageValidateIllegalFilesReturnsFalse() {
    assertFalse(ImageValidation.validateImages(illegalFiles));
  }

  @Test
  public void testImageValidateNullFilesReturnsFalse() {
    assertFalse(ImageValidation.validateImages(nullFiles));
  }

  @Test
  public void testImageValidateEmptyFilesReturnsFalse() {
    assertFalse(ImageValidation.validateImages(emptyFiles));
  }

  @Test
  public void testValidateImageAltReturnsTrue() {
    assertTrue(ImageValidation.validateImageAlt(imageAlt[0]));
  }

  @Test
  public void testValidateImageAltsReturnsTrue() {
    assertTrue(ImageValidation.validateImageAlts(imageAlt));
  }

  @Test
  public void testValidateImageAltsNullReturnsTrue() {
    assertTrue(ImageValidation.validateImageAlts(nullImageAlt));
  }

  @Test
  public void testValidateImageAltsEmptyReturnsTrue() {
    assertTrue(ImageValidation.validateImageAlts(emptyImageAlt));
  }

  @Test
  public void testValidateImageAltsLongReturnsFalse() {
    assertFalse(ImageValidation.validateImageAlts(longImageAlt));
  }
}
