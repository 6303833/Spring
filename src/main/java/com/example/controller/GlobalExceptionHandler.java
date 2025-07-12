package com.example.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.example.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.validation.ConstraintViolationException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, String>> handleDeserialization(HttpMessageNotReadableException ex) {
	    Map<String, String> errors = new HashMap<>();
	    Throwable cause = ex.getCause();
	    if (cause instanceof InvalidFormatException invalidFormatEx) {
	        String fieldName = invalidFormatEx.getPath().get(0).getFieldName();
	        Class<?> targetType = invalidFormatEx.getTargetType();
	        if (targetType.isEnum()) {
	            errors.put(fieldName, "Invalid status value. Allowed values: ACTIVE, COMPLETED, CANCELLED");
	        } 
	        if (targetType.getSimpleName().equals("LocalDate")) {
	            errors.put(fieldName, "Date must be in yyyy-MM-dd format");
	        }
	    }
	    return ResponseEntity.badRequest().body(errors);
	}


	
	
    // Handle @Valid validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    // Handle ConstraintViolationException (e.g. from @RequestParam)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolations(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(cv ->
            errors.put("param", cv.getMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    // Handle not found exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Catch all for other unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherErrors(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected error: " + ex.getMessage());
    }
}