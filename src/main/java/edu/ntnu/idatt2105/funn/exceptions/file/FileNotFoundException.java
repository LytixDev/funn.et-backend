package edu.ntnu.idatt2105.funn.exceptions.file;

/**
 * Exception for when a file is not found.
 * @author Callum G.
 * @version 1.0 - 20.03.2023
 */
public class FileNotFoundException extends Exception {

  /**
   * Default constructor.
   * Has a default message.
   */
  public FileNotFoundException() {
    super("File not found");
  }

  /**
   * Constructor with message.
   * @param message The message to be displayed.
   */
  public FileNotFoundException(String message) {
    super(message);
  }
}
