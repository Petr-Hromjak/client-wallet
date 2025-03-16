package com.clientwallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler is a Spring {@link RestControllerAdvice} that handles exceptions
 * globally for all controllers in the application. It provides custom handling for
 * validation exceptions that occur during the request processing.
 * <p>
 * Specifically, this class catches {@link MethodArgumentNotValidException} exceptions
 * which are thrown when validation annotations (e.g., @NotNull, @Size) on method parameters fail.
 * It processes these exceptions and returns a structured response with detailed error messages.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles MethodArgumentNotValidException, which is thrown when validation annotations
     * on request body or method parameters fail.
     *
     * @param ex The exception containing validation error details.
     * @return ResponseEntity containing a map of field error messages and HTTP status 400 (Bad Request).
     * <p>
     * This method iterates over the field errors from the exception, extracts the field name
     * and corresponding error message, and returns them in a map with the appropriate response status.
     * </p>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Loop through the field errors and extract the error messages
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        // Return the errors as a response with status 400 (Bad Request)
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
