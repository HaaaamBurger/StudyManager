package org.studymanager.main.exceptions;

import jakarta.persistence.EntityExistsException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String validationMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(status.value())
                .body(ExceptionResponse.builder()
                        .title(HttpStatus.valueOf(status.value()).getReasonPhrase())
                        .status(status.value())
                        .detail(validationMessage)
                        .instance(request.getDescription(false).replace("uri=", ""))
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        return ResponseEntity
                .status(status.value())
                .body(ExceptionResponse.builder()
                        .title(HttpStatus.valueOf(status.value()).getReasonPhrase())
                        .status(status.value())
                        .detail(ex.getMessage())
                        .instance(request.getDescription(false).replace("uri=", ""))
                        .build());
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ExceptionResponse> handleEntityExistsException(EntityExistsException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        return ResponseEntity
                .status(status.value())
                .body(ExceptionResponse.builder()
                        .title(HttpStatus.valueOf(status.value()).getReasonPhrase())
                        .status(status.value())
                        .detail(ex.getMessage())
                        .instance(request.getDescription(false).replace("uri=", ""))
                        .build());
    }
}
