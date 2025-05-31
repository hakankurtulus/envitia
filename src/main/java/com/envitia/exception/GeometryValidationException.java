package com.envitia.exception;

/**
 * Base exception for all geometry validation errors in the application.
 * <p>
 * This runtime exception is thrown when geometric entities (such as points, rectangles)
 * fail validation checks. It provides information about the specific validation error
 * that occurred and can optionally wrap a cause exception.
 * </p>
 * <p>
 * This exception is used throughout the application to signal invalid geometric data
 * or operations, allowing for consistent error handling related to geometry validation.
 * </p>
 */
public class GeometryValidationException extends RuntimeException {

   /**
    * Constructs a new geometry validation exception with the specified detail message.
    * <p>
    * The detail message provides specific information about the validation error
    * that occurred. This message is saved for later retrieval by the {@link #getMessage()} method.
    * </p>
    *
    * @param message the detail message explaining the validation error
    */
   public GeometryValidationException(String message) {
        super(message);
    }
}
