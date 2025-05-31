package com.envitia;

import com.envitia.exception.GeometryValidationException;
import com.envitia.service.GeometryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RectanglePointCheckerTest {

    @Mock
    private GeometryService geometryService;

    private RectanglePointChecker checker;

    @BeforeEach
    void setUp() {
        checker = new RectanglePointChecker(geometryService);
    }

    @Test
    void testIsInsideRectangleStrict_NullShape_ThrowsException() {
        // Arrange
        int[] point = {2, 2};

        // Mock the behavior - the service would throw an exception for a null shape
        when(geometryService.isPointInRectangle(null, point))
                .thenThrow(new GeometryValidationException("Rectangle coordinates cannot be null"));

        // Act & Assert
        assertThrows(GeometryValidationException.class, () -> checker.isInsideRectangle(null, point));
    }

    @Test
    void testIsInsideRectangleStrict_NullPoint_ThrowsException() {
        // Arrange
        int[][] shape = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};

        // Mock the behavior - the service would throw an exception for a null point
        when(geometryService.isPointInRectangle(shape, null))
                .thenThrow(new GeometryValidationException("Point coordinates cannot be null"));

        // Act & Assert
        assertThrows(GeometryValidationException.class, () -> checker.isInsideRectangle(shape, null));
    }

    @Test
    void testIsInsideRectangleStrict_PointInside_ReturnsTrue() {
        // Arrange
        int[][] shape = {{0, 0}, {0, 5}, {5, 5}, {5, 0}}; // Rectangle
        int[] point = {2, 2}; // Inside

        // Mock the behavior
        when(geometryService.isPointInRectangle(shape, point)).thenReturn(true);

        // Act & Assert
        assertTrue(checker.isInsideRectangle(shape, point));
    }

    @Test
    void testIsInsideRectangleStrict_PointOnEdge_ReturnsFalse() {
        // Arrange
        int[][] shape = {{0, 0}, {0, 5}, {5, 5}, {5, 0}}; // Rectangle
        int[] point = {0, 2}; // On edge

        // Mock the behavior - the service returns false for points on the edge
        when(geometryService.isPointInRectangle(shape, point)).thenReturn(false);

        // Act & Assert
        assertFalse(checker.isInsideRectangle(shape, point));
    }

    @Test
    void testIsInsideRectangleStrict_NonRectangularShape_ReturnsFalse() {
        // Arrange
        int[][] shape = {{0, 0}, {0, 5}, {5, 5}, {2, 2}}; // Not a rectangle (triangle-like)
        int[] point = {1, 1}; // Inside the shape

        // Mock the behavior - the service would throw an exception for a non-rectangular shape
        when(geometryService.isPointInRectangle(shape, point)).thenReturn(false);
        assertFalse(checker.isInsideRectangle(shape, point));
    }

    @Test
    void testIsInsideRectangleStrict_RectangleWithMoreThan4Points_ReturnsTrue() {
        // Arrange
        int[][] shape = {{0, 0}, {0, 2}, {0, 5}, {5, 5}, {5, 2}, {5, 0}}; // Rectangle with 6 points
        int[] point = {2, 2}; // Inside

        // Mock the behavior
        when(geometryService.isPointInRectangle(shape, point)).thenReturn(true);

        // Act & Assert
        assertTrue(checker.isInsideRectangle(shape, point));
    }

    @Test
    void testIsInsideRectangleStrict_PointOutside_ReturnsFalse() {
        // Arrange
        int[][] shape = {{0, 0}, {0, 5}, {5, 5}, {5, 0}}; // Rectangle
        int[] point = {10, 10}; // Outside

        // Mock the behavior
        when(geometryService.isPointInRectangle(shape, point)).thenReturn(false);

        // Act & Assert
        assertFalse(checker.isInsideRectangle(shape, point));
    }
}
