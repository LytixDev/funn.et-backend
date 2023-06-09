package edu.ntnu.idatt2105.funn.exceptions;

import edu.ntnu.idatt2105.funn.exceptions.file.FileNotFoundException;
import edu.ntnu.idatt2105.funn.exceptions.listing.ListingNotFoundException;
import edu.ntnu.idatt2105.funn.exceptions.location.LocationAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.location.LocationDoesntExistException;
import edu.ntnu.idatt2105.funn.exceptions.location.PostCodeAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.location.PostCodeDoesntExistException;
import edu.ntnu.idatt2105.funn.exceptions.user.EmailAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.funn.exceptions.user.UsernameAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.validation.BadInputException;
import edu.ntnu.idatt2105.funn.exceptions.validation.BadSearchException;
import java.io.IOException;
import java.net.MalformedURLException;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
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
 * @author Carl. G, Nicolai H. B.
 * @version 1.5 - 27.03.2023
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * Overrides the default handler for creating the response entity
   * @param ex Exception - an exception thrown that needs to be handled. Can be a subclass of Exception
   * @param body Object - the body of the response
   * @param headers HttpHeaders - the headers of the response
   * @param statusCode HttpStatusCode - the http status code of the response
   * @param request WebRequest - the request that caused the exception
   * @return ResponseEntity - a response entity with the exception message
   */
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
    Exception ex,
    Object body,
    HttpHeaders headers,
    HttpStatusCode statusCode,
    WebRequest request
  ) {
    ExceptionResponse response = new ExceptionResponse(ex.getClass().getSimpleName());
    return super.handleExceptionInternal(ex, response, headers, statusCode, request);
  }

  /**
   * Creates a response entity as an exception response
   * The body will be a json object with the exception message in `detail`
   * @param ex Exception - The exception that was thrown. Can be a subclass of Exception
   * @param status HttpStatus - The status code of the response
   * @return ResponseEntity - A response entity with the exception message
   */
  private ResponseEntity<ExceptionResponse> getResponseEntityWithExceptionResponse(
    Exception ex,
    HttpStatus status
  ) {
    LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
    ExceptionResponse response = new ExceptionResponse(ex.getClass().getSimpleName());
    return new ResponseEntity<>(response, new HttpHeaders(), status);
  }

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
    return getResponseEntityWithExceptionResponse(ex, HttpStatus.CONFLICT);
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
      ListingNotFoundException.class,
      LocationDoesntExistException.class,
      PostCodeDoesntExistException.class,
      ObjectNotFoundException.class,
      FileNotFoundException.class,
      MalformedURLException.class,
    }
  )
  public ResponseEntity<ExceptionResponse> handleSpecificObjectDoesNotExist(
    Exception ex,
    WebRequest request
  ) {
    return getResponseEntityWithExceptionResponse(ex, HttpStatus.NOT_FOUND);
  }

  /**
   * Handles exceptions from when logging in with wrong credentials
   * Returns a 401 not authorized response with the message that the exception contains
   *
   * @param ex The exception that was thrown
   * @param request The request that caused the exception
   * @return A response entity with the exception message
   */
  @ExceptionHandler(value = { BadCredentialsException.class })
  public ResponseEntity<ExceptionResponse> handleBadCredentialsException(
    Exception ex,
    WebRequest request
  ) {
    return getResponseEntityWithExceptionResponse(ex, HttpStatus.UNAUTHORIZED);
  }

  /**
   * Handles exceptions that is from conflicts with the database or a file handler
   * Returns a 500 internal server error response with the message
   * that the exception contains
   *
   * @param ex The exception that was thrown
   * @param request The request that caused the exception
   * @return A response entity with the exception message
   */
  @ExceptionHandler(value = { DatabaseException.class, IOException.class })
  public ResponseEntity<ExceptionResponse> handleDatabaseException(
    Exception ex,
    WebRequest request
  ) {
    return getResponseEntityWithExceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handles exceptions where a null object
   * was tried to be accessed, when an argument passed was not valid
   * or when bad request was made
   * Returns a 400 bad request response with custom message
   *
   * @param ex The exception that was thrown
   * @param request The request that caused the exception
   * @return A response entity with the exception message
   */
  @ExceptionHandler(
    value = {
      NullPointerException.class,
      IllegalArgumentException.class,
      BadSearchException.class,
      BadInputException.class,
    }
  )
  public ResponseEntity<ExceptionResponse> handleNullPointerException(
    Exception ex,
    WebRequest request
  ) {
    return getResponseEntityWithExceptionResponse(ex, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles exceptions where a user is not authorized to perform an action
   * Returns a 403 forbidden response with custom message
   * @param ex The exception that was thrown
   * @param request The request that caused the exception
   * @return A response entity with the exception message
   */
  @ExceptionHandler(value = { PermissionDeniedException.class, AccessDeniedException.class })
  public ResponseEntity<ExceptionResponse> handlePermissionDeniedException(
    Exception ex,
    WebRequest request
  ) {
    return getResponseEntityWithExceptionResponse(ex, HttpStatus.FORBIDDEN);
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
  public ResponseEntity<ExceptionResponse> handleRemainingExceptions(
    Exception ex,
    WebRequest request
  ) {
    return getResponseEntityWithExceptionResponse(
      new Exception(new Throwable(ex.getClass().getSimpleName() + ": " + ex.getMessage())),
      HttpStatus.INTERNAL_SERVER_ERROR
    );
  }
}
