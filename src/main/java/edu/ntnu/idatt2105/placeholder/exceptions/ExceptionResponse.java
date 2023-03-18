package edu.ntnu.idatt2105.placeholder.exceptions;

import lombok.Data;

/**
 * A response object for exceptions
 * that are not handled.
 * Detail is a message about why the exception occured.
 *
 * @author Carl. G
 * @version 1.0 - 18.03.2023
 */
@Data
public class ExceptionResponse {

  private final String detail;
}
