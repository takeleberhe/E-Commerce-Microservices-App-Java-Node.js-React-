package com.ecommerce.PaymentService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling application-wide exceptions.
 * This class will handle various types of exceptions thrown in the application
 * and return appropriate HTTP responses with error messages.
 */
@RestControllerAdvice // This annotation tells Spring to treat this class as a global exception handler.
public class GlobalExceptionHandler {

    /**
     * Handles validation errors for request payloads.
     * When a request payload doesn't meet validation constraints (like @NotNull, @Size, etc.),
     * this method will be triggered to handle the error.
     *
     * @param ex MethodArgumentNotValidException thrown during validation.
     * @return A ResponseEntity with error details (field names and error messages).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class) // This tells Spring to handle MethodArgumentNotValidException.
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>(); // Create a map to store error messages for each invalid field.

        // Loop through all the field errors in the exception and store them in the map.
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage()); // Field name and its error message.
        }
        // Return a BAD_REQUEST response (400) with the error details.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Handles custom PaymentProcessingException.
     * This method is called when a PaymentProcessingException is thrown during payment processing.
     *
     * @param ex PaymentProcessingException thrown during payment processing.
     * @return A ResponseEntity with a message indicating payment failure.
     */
    @ExceptionHandler(PaymentProcessingException.class) // This tells Spring to handle PaymentProcessingException.
    public ResponseEntity<String> handlePaymentProcessingException(PaymentProcessingException ex) {
        // Return an INTERNAL_SERVER_ERROR response (500) with the message from the exception.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Payment processing failed: " + ex.getMessage());
    }

    /**
     * Handles generic exceptions.
     * This method is called when any other unhandled exception is thrown in the application.
     *
     * @param ex Any unhandled exception.
     * @return A ResponseEntity with a generic error message.
     */
    @ExceptionHandler(Exception.class) // This tells Spring to handle all other exceptions.
    public ResponseEntity<String> handleGenericException(Exception ex) {
        // Return an INTERNAL_SERVER_ERROR response (500) with a generic error message.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + ex.getMessage());
    }
}
