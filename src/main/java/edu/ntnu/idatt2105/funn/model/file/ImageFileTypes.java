package edu.ntnu.idatt2105.funn.model.file;

/**
 * Enum for image file types.
 * Represents the file types that are allowed to be uploaded.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public enum ImageFileTypes {
  /**
   * JPEG file type.
   */
  JPEG("image/jpeg"),
  /**
   * PNG file type.
   */
  PNG("image/png");

  /**
   * The file type.
   */
  private final String fileType;

  /**
   * Constructor for ImageFileTypes.
   * @param fileType The file type to be used.
   */
  ImageFileTypes(String fileType) {
    this.fileType = fileType;
  }

  /**
   * Getter for file type.
   * @return The file type.
   */
  public String getFileType() {
    return fileType;
  }
}
