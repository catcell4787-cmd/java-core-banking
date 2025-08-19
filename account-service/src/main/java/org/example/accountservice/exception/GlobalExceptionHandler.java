package org.example.accountservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorData> handleConflictException(ConflictException ex, HttpServletRequest request) {
        ErrorData errorData = new ErrorData(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                request.getRequestURI());
        logger.error(errorData.toString());
        return new ResponseEntity<>(errorData, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorData> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorData errorData = new ErrorData(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI());
        logger.error(errorData.toString());
        return new ResponseEntity<>(errorData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorData> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ErrorData errorData = new ErrorData(
                HttpStatus.BAD_REQUEST.value(),
                "Validation error",
                request.getRequestURI());
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorData.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(errorData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorData> handleUnauthorizedException(AuthenticationException ex, HttpServletRequest request) {
        ErrorData errorData = new ErrorData(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        logger.error(errorData.toString());
        return new ResponseEntity<>(errorData, HttpStatus.UNAUTHORIZED);
    }

    @Data
    public static class ErrorData {

        private LocalDateTime timestamp;
        private int status;
        private String message;
        private String path;
        private List<ValidationError> validationErrors;

        public ErrorData() {
            this.timestamp = LocalDateTime.now();
        }

        public ErrorData(int status, String message, String path) {
            this();
            this.status = status;
            this.message = message;
            this.path = path;
        }

        public void addValidationError(String field, String message) {
            if (this.validationErrors == null) {
                this.validationErrors = new ArrayList<>();
            }
            validationErrors.add(new ValidationError(field, message));
        }
    }

    @Data
    @AllArgsConstructor
    public static class ValidationError {
        private String field;
        private String message;
    }

    public static class ConflictException extends RuntimeException {
        public ConflictException(String message) {
            super(message);
        }
    }

    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    public static class AuthenticationException extends RuntimeException {
        public AuthenticationException(String message) {
            super(message);
        }
    }
}
