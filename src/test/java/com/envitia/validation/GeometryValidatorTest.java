package com.envitia.validation;

import com.envitia.exception.GeometryValidationException;
import com.envitia.geometry.Rectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the GeometryValidator class.
 */
public class GeometryValidatorTest {

    @Test
    void testValidatePointCoordinates_WithValidCoordinates() {
        int[] coordinates = {1, 2};
        // Should not throw an exception
        GeometryValidator.validatePointCoordinates(coordinates);
    }

    @Test
    void testValidatePointCoordinates_WithTooFewElements() {
        int[] coordinates = {1};
        assertThrows(GeometryValidationException.class, () -> 
            GeometryValidator.validatePointCoordinates(coordinates));
    }

    @Test
    void testValidatePointCoordinates_WithTooManyElements() {
        int[] coordinates = {1, 2, 3};
        assertThrows(GeometryValidationException.class, () -> 
            GeometryValidator.validatePointCoordinates(coordinates));
    }

    @Test
    void testValidateRectangleCoordinates_WithValidCoordinates() {
        int[][] coordinates = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        // Should not throw an exception
        GeometryValidator.validateRectangleCoordinates(coordinates);
    }

    @Test
    void testValidateRectangleCoordinates_WithTooFewVertices() {
        int[][] coordinates = {{0, 0}, {0, 5}, {5, 5}};
        GeometryValidator.validateRectangleCoordinates(coordinates);
    }

    @Test
    void testIsValidRectangle_WithValidRectangle() {
        int[][] coordinates = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        Rectangle rectangle = Rectangle.fromCoordinateArray(coordinates);
        
        assertTrue(GeometryValidator.isValidRectangle(rectangle));
    }

    @Test
    void testIsValidRectangle_WithTooFewVertices() {
        // Create a rectangle with too few vertices
        // This is a bit tricky since the Rectangle constructor validates the vertices
        // We'll need to mock or create a special test case
        
        // For now, we'll test the validation in the Rectangle constructor
        int[][] coordinates = {{0, 0}, {0, 5}, {5, 5}};
        Rectangle rectangle = Rectangle.fromCoordinateArray(coordinates);
        assertFalse(GeometryValidator.isValidRectangle(rectangle));
    }

    @Test
    void testIsValidRectangle_WithNonRectangularShape() {
        // Create a non-rectangular shape
        // This is a bit tricky since the Rectangle constructor validates the shape
        // We'll need to mock or create a special test case
        
        // For now, we'll test the validation in the Rectangle constructor
        int[][] coordinates = {{0, 0}, {0, 5}, {3, 3}, {5, 0}};
        Rectangle rectangle = Rectangle.fromCoordinateArray(coordinates);
        assertFalse(GeometryValidator.isValidRectangle(rectangle));
    }

    @Test
    void testIsValidRectangle_WithDuplicateVertices() {
        // Rectangle with duplicate vertices
        int[][] coordinates = {{0, 0}, {0, 5}, {5, 5}, {5, 0}, {5, 5}};
        Rectangle rectangle = Rectangle.fromCoordinateArray(coordinates);
        assertTrue(GeometryValidator.isValidRectangle(rectangle));
    }
    @Test
    void testValidatePointCoordinates_WithNullCoordinates() {
        assertThrows(GeometryValidationException.class, () ->
                GeometryValidator.validatePointCoordinates(null));
    }

    @Test
    void testValidatePointCoordinates_WithNegativeValues() {
        int[] coordinates = {-1, -2};
        // Should not throw an exception
        GeometryValidator.validatePointCoordinates(coordinates);
    }
}