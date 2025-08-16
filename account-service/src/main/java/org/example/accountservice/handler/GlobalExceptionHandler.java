package org.example.accountservice.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorData> handleConflictException(ConflictException ex, HttpServletRequest request) {
        ErrorData errorData = new ErrorData(HttpStatus.CONFLICT.value(), ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(errorData, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorData> handleBadRequestException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ErrorData errorData = new ErrorData(HttpStatus.BAD_REQUEST.value(), "Validation error", request.getRequestURI());
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorData.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(errorData, HttpStatus.BAD_REQUEST);
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
                validationErrors = new ArrayList<>();
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
}
