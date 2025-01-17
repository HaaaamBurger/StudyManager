package org.studymanager.main.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityExistsException;
import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.studymanager.main.exception.dto.ExceptionResponse;

/**
 * Global exception handler for handling and customizing the response of exception
 * in the application. This class extends {@link ResponseEntityExceptionHandler}
 * to provide default and custom exception handling for RESTful APIs.
 *
 * <p>It uses {@link RestControllerAdvice} to apply globally across controllers.
 *
 * @author Melnyk Oleksandr
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handles {@link MissingServletRequestParameterException} by returning a custom error response.
   *
   * <p>This method is invoked when a required request parameter is missing from the request.
   * It logs the exception details and constructs a response with the appropriate status code
   * and a message indicating the missing parameter.
   *
   * @param ex      the {@link MissingServletRequestParameterException} that was thrown
   * @param headers the HTTP headers associated with the request
   * @param status  the HTTP status code to return in the response
   * @param request the current web request
   * @return a {@link ResponseEntity} containing the error response with the status code
   * and a message about the missing parameter
   */
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
          MissingServletRequestParameterException ex,
          HttpHeaders headers,
          HttpStatusCode status,
          WebRequest request
  ) {
    log.error("[MissingServletRequestParameterException]: {}", ex.getMessage());
    return ResponseEntity
            .status(status.value())
            .body(exceptionResponseBuilder(ex.getMessage(), status, request));
  }

  /**
   * Handles validation errors for method arguments annotated with {@code @Valid}.
   *
   * @param ex      the {@link MethodArgumentNotValidException} containing validation errors.
   * @param headers the HTTP headers.
   * @param status  the HTTP status code.
   * @param request the web request.
   * @return a {@link ResponseEntity} containing a detailed error response.
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException ex,
          HttpHeaders headers,
          HttpStatusCode status,
          WebRequest request
  ) {

    String validationMessage = ex.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .collect(Collectors.joining(", "));

    log.error("[MethodArgumentNotValid]: {}", validationMessage);
    return ResponseEntity
            .status(status.value())
            .body(exceptionResponseBuilder(validationMessage, status, request));
  }

  /**
   * Handles errors related to unreadable HTTP message content.
   *
   * @param ex      the {@link HttpMessageNotReadableException}.
   * @param headers the HTTP headers.
   * @param status  the HTTP status code.
   * @param request the web request.
   * @return a {@link ResponseEntity} containing the error response.
   */
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
          HttpMessageNotReadableException ex,
          HttpHeaders headers,
          HttpStatusCode status,
          WebRequest request
  ) {
    log.error("[HttpMessageNotReadable]: {}", ex.getMessage());
    return ResponseEntity
            .status(status.value())
            .body(exceptionResponseBuilder(ex.getMessage(), status, request));
  }

  /**
   * Handles cases where an entity already exists in the database.
   *
   * @param ex      the {@link EntityExistsException}.
   * @param request the web request.
   * @return a {@link ResponseEntity} with {@code 409 Conflict} status.
   */
  @ExceptionHandler(EntityExistsException.class)
  public ResponseEntity<ExceptionResponse> handleEntityExistsException(
          EntityExistsException ex,
          WebRequest request
  ) {
    log.error("[EntityExistsException]: {}", ex.getMessage());

    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(exceptionResponseBuilder(
                    ex.getMessage(),
                    HttpStatusCode.valueOf(HttpStatus.CONFLICT.value()), request)
            );
  }

  /**
   * Handles cases where a JWT token has expired.
   *
   * @param ex      the {@link ExpiredJwtException}.
   * @param status  the HTTP status code.
   * @param request the web request.
   * @return a {@link ResponseEntity} with the specified status.
   */
  @ExceptionHandler(ExpiredJwtException.class)
  public ResponseEntity<ExceptionResponse> handleExpiredJwtException(
          ExpiredJwtException ex,
          HttpStatusCode status,
          WebRequest request
  ) {
    log.error("[ExpiredJwtException]: {}", ex.getMessage());
    return ResponseEntity
            .status(status.value())
            .body(exceptionResponseBuilder(ex.getMessage(), status, request));
  }

  /**
   * Handles cases where a username is not found during authentication.
   *
   * @param ex      the {@link UsernameNotFoundException}.
   * @param request the web request.
   * @return a {@link ResponseEntity} with {@code 404 Not Found} status.
   */
  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ExceptionResponse> handleUsernameNotFoundException(
          UsernameNotFoundException ex,
          WebRequest request
  ) {
    log.error("[UsernameNotFoundException]: {}", ex.getMessage());
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(exceptionResponseBuilder(
                    ex.getMessage(),
                    HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()), request));
  }

  /**
   * Handles cases where access to a resource is denied.
   *
   * @param ex      the {@link AccessDeniedException}.
   * @param request the web request.
   * @return a {@link ResponseEntity} with {@code 403 Forbidden} status.
   */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ExceptionResponse> handleAccessDeniedException(
          AccessDeniedException ex,
          WebRequest request
  ) {
    log.error("[AccessDeniedException]: {}", ex.getMessage());
    return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(exceptionResponseBuilder(
                    ex.getMessage(),
                    HttpStatusCode.valueOf(HttpStatus.FORBIDDEN.value()), request)
            );
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(
          IllegalArgumentException ex,
          WebRequest request
  ) {
    log.error("[IllegalArgumentException]: {}", ex.getMessage());
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(exceptionResponseBuilder(
                    ex.getMessage(),
                    HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), request)
            );
  }

  //****************************************************************************
  //*******************************Utils****************************************
  //****************************************************************************
  /**
   * Builds a detailed {@link ExceptionResponse} object for the provided exception details.
   *
   * @param exceptionMessage the message of the exception.
   * @param status           the HTTP status code.
   * @param request          the web request.
   * @return a detailed {@link ExceptionResponse} object.
   */
  public ExceptionResponse exceptionResponseBuilder(
          String exceptionMessage,
          HttpStatusCode status,
          WebRequest request
  ) {
    return ExceptionResponse.builder()
            .title(HttpStatus.valueOf(status.value()).getReasonPhrase())
            .status(status.value())
            .detail(exceptionMessage)
            .instance(request.getDescription(false).replace("uri=", ""))
            .build();
  }
}
