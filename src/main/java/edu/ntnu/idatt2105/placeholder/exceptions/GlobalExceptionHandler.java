package edu.ntnu.idatt2105.placeholder.exceptions;

import edu.ntnu.idatt2105.placeholder.exceptions.location.LocationAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.LocationDoesntExistException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.PostCodeAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.location.PostCodeDoesntExistException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.EmailAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UsernameAlreadyExistsException;
import java.nio.file.AccessDeniedException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * A global exception handler which converts
 * unhandled exceptions to a response object
 *
 * @author Carl. G
 * @version 1.0 - 18.03.2023
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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
  public ResponseEntity<Object> handleConflict(
    RuntimeException ex,
    WebRequest request
  ) {
    ExceptionResponse response = new ExceptionResponse(ex.getMessage());
    return handleExceptionInternal(
      ex,
      response,
      new HttpHeaders(),
      HttpStatus.CONFLICT,
      request
    );
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
    }
  )
  public ResponseEntity<Object> handleSpecificObjectDoesNotExist(
    RuntimeException ex,
    WebRequest request
  ) {
    ExceptionResponse response = new ExceptionResponse(ex.getMessage());
    return handleExceptionInternal(
      ex,
      response,
      new HttpHeaders(),
      HttpStatus.NOT_FOUND,
      request
    );
  }

  /**
   * Handles exceptions from when a requested resource was not found
   * Returns a 404 not found response with a custom message
   *
   * @param ex The exception that was thrown
   * @param request The request that caused the exception
   * @return A response entity with the exception message
   */
  @ExceptionHandler(value = { ObjectNotFoundException.class })
  public ResponseEntity<Object> handleNotFound(
    RuntimeException ex,
    WebRequest request
  ) {
    ExceptionResponse response = new ExceptionResponse("resourceNotFound");
    return handleExceptionInternal(
      ex,
      response,
      new HttpHeaders(),
      HttpStatus.NOT_FOUND,
      request
    );
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
  public ResponseEntity<Object> handleDatabaseException(
    RuntimeException ex,
    WebRequest request
  ) {
    ExceptionResponse response = new ExceptionResponse("internalServerError");
    return handleExceptionInternal(
      ex,
      response,
      new HttpHeaders(),
      HttpStatus.INTERNAL_SERVER_ERROR,
      request
    );
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
  public ResponseEntity<Object> handleNotAuthorized(
    RuntimeException ex,
    WebRequest request
  ) {
    ExceptionResponse response = new ExceptionResponse("notAuthorized");
    return handleExceptionInternal(
      ex,
      response,
      new HttpHeaders(),
      HttpStatus.UNAUTHORIZED,
      request
    );
  }

  /**
   * Handles exceptions where the user does not have the
   * sufficient permissions to access a resource
   * Returns a 403 forbidden response with a custom message
   *
   * @param ex The exception that was thrown
   * @param request The request that caused the exception
   * @return A response entity with the exception message
   */
  @ExceptionHandler(value = { PermissionDeniedDataAccessException.class })
  public ResponseEntity<Object> handlePermissionDenied(
    RuntimeException ex,
    WebRequest request
  ) {
    ExceptionResponse response = new ExceptionResponse("noPermission");
    return handleExceptionInternal(
      ex,
      response,
      new HttpHeaders(),
      HttpStatus.FORBIDDEN,
      request
    );
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
  public ResponseEntity<Object> handleNullPointerException(
    RuntimeException ex,
    WebRequest request
  ) {
    ExceptionResponse response = new ExceptionResponse("nullPointer");
    return handleExceptionInternal(
      ex,
      response,
      new HttpHeaders(),
      HttpStatus.BAD_REQUEST,
      request
    );
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
  public ResponseEntity<Object> handleIllegalArgumentException(
    RuntimeException ex,
    WebRequest request
  ) {
    ExceptionResponse response = new ExceptionResponse("internalServerError");
    return handleExceptionInternal(
      ex,
      response,
      new HttpHeaders(),
      HttpStatus.INTERNAL_SERVER_ERROR,
      request
    );
  }
}
