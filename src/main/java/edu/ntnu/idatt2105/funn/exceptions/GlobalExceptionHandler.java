package edu.ntnu.idatt2105.funn.exceptions;

import edu.ntnu.idatt2105.funn.exceptions.location.LocationAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.location.LocationDoesntExistException;
import edu.ntnu.idatt2105.funn.exceptions.location.PostCodeAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.location.PostCodeDoesntExistException;
import edu.ntnu.idatt2105.funn.exceptions.user.EmailAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.funn.exceptions.user.UsernameAlreadyExistsException;
import java.util.Arrays;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * A global exception handler which converts
 * unhandled exceptions to a response object
 * Logs the exception message to the console
 *
 * @author Carl. G
 * @version 1.1 - 20.03.2023
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * Handles exceptions that is from conflicts with the database
   * Returns a 409 conflict response with the message that the exception contains
   *
   * @param ex The exception that was thrown
   * @param request The request that caused the exception
   * @return A response entity with the exception message
   */
  @ExceptionHandler(
    value = {
      IllegalStateException.class,
      EmailAlreadyExistsException.class,
      UsernameAlreadyExistsException.class,
      LocationAlreadyExistsException.class,
      PostCodeAlreadyExistsException.class,
    }
  )
  public ResponseEntity<ExceptionResponse> handleConflict(Exception ex, WebRequest request) {
    LOGGER.error(ex.getMessage());
    ExceptionResponse response = new ExceptionResponse(ex.getClass().getSimpleName());
    return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.CONFLICT);
  }

  /**
   * Handles exceptions from when trying to find a user that does not found
   * Returns a 404 not found response with the message that the exception contains
   *
   * @param ex The exception that was thrown
   * @param request The request that caused the exception
   * @return A response entity with the exception message
   */
  @ExceptionHandler(
    value = {
      UserDoesNotExistsException.class,
      LocationDoesntExistException.class,
      PostCodeDoesntExistException.class,
      ObjectNotFoundException.class,
    }
  )
  public ResponseEntity<ExceptionResponse> handleSpecificObjectDoesNotExist(
    Exception ex,
    WebRequest request
  ) {
    LOGGER.error(ex.getMessage());
    ExceptionResponse response = new ExceptionResponse(ex.getClass().getSimpleName());
    return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
  }

  /**
   * Handles exceptions that is from conflicts with the database
   * Returns a 500 internal server error response with the message
   * that the exception contains
   *
   * @param ex The exception that was thrown
   * @param request The request that caused the exception
   * @return A response entity with the exception message
   */
  @ExceptionHandler(value = { DatabaseException.class })
  public ResponseEntity<ExceptionResponse> handleDatabaseException(
    Exception ex,
    WebRequest request
  ) {
    LOGGER.error(ex.getMessage());
    ExceptionResponse response = new ExceptionResponse(ex.getClass().getSimpleName());
    return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handles exceptions from when the user is not logged in
   * Returns a 401 not auhtorized response with a custom message
   *
   * @param ex The exception that was thrown
   * @param request The request that caused the exception
   * @return A response entity with the exception message
   */
  @ExceptionHandler(value = { AccessDeniedException.class })
  public ResponseEntity<ExceptionResponse> handleNotAuthorized(Exception ex, WebRequest request) {
    LOGGER.error(ex.getMessage());
    ExceptionResponse response = new ExceptionResponse(ex.getClass().getSimpleName());
    return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
  }

  /**
   * Handles exceptions where a null object
   * was tried to be accessed
   * Returns a 500 internal server error response with custom message
   *
   * @param ex The exception that was thrown
   * @param request The request that caused the exception
   * @return A response entity with the exception message
   */
  @ExceptionHandler(value = { NullPointerException.class })
  public ResponseEntity<ExceptionResponse> handleNullPointerException(
    Exception ex,
    WebRequest request
  ) {
    LOGGER.error(ex.getMessage());
    ExceptionResponse response = new ExceptionResponse(ex.getClass().getSimpleName());
    return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles general exceptions which have not been handled yet
   * Returns a 500 internal server error response with a custom message
   *
   * @param ex The exception that was thrown
   * @param request The request that caused the exception
   * @return A response entity with the exception message
   */
  @ExceptionHandler(value = { Exception.class })
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(
    Exception ex,
    WebRequest request
  ) {
    LOGGER.error(ex.getMessage());
    ex.printStackTrace();
    ExceptionResponse response = new ExceptionResponse(Exception.class.getSimpleName());
    return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
