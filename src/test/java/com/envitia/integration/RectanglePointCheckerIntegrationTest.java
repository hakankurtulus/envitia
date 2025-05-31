package com.envitia.integration;

import com.envitia.RectanglePointChecker;
import com.envitia.exception.GeometryValidationException;
import com.envitia.service.impl.GeometryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the RectanglePointChecker with real GeometryService implementation.
 * These tests verify the end-to-end functionality of checking if a point is inside a rectangle.
 */
public class RectanglePointCheckerIntegrationTest {

    private RectanglePointChecker checker;

    @BeforeEach
    void setUp() {
        checker = new RectanglePointChecker(new GeometryServiceImpl());
    }

    @Test
    void testEndToEnd_NullRectangleThrowsException() {
        int[] point = {2, 3};

        assertThrows(GeometryValidationException.class, () -> checker.isInsideRectangle(null, point));
    }

    @Test
    void testEndToEnd_NullPointThrowsException() {
        int[][] rectangle = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};

        assertThrows(GeometryValidationException.class, () -> checker.isInsideRectangle(rectangle, null));
    }

    @Test
    void testEndToEnd_InvalidPointArrayThrowsException() {
        int[][] rectangle = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        int[] point = {2}; // Missing y coordinate

        assertThrows(GeometryValidationException.class, () -> checker.isInsideRectangle(rectangle, point));
    }

    @Test
    void testEndToEnd_InvalidRectangleArrayThrowsException() {
        int[][] rectangle = {{0}, {0, 5}, {5, 5}, {5, 0}};
        int[] point = {2, 3}; // Missing y coordinate

        assertThrows(GeometryValidationException.class, () -> checker.isInsideRectangle(rectangle, point));
    }

    @Test
    void testEndToEnd_PointInsideRectangle() {
        // Rectangle with corners at (0,0), (0,5), (5,0), (5,5)
        int[][] rectangle = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        int[] point = {2, 3};

        assertTrue(checker.isInsideRectangle(rectangle, point));
    }

    @Test
    void testEndToEnd_PointOutsideRectangle() {
        // Rectangle with corners at (0,0), (0,5), (5,0), (5,5)
        int[][] rectangle = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        int[] point = {6, 6};

        assertFalse(checker.isInsideRectangle(rectangle, point));
    }

    @Test
    void testEndToEnd_PointOnRectangleEdge() {
        // Rectangle with corners at (0,0), (0,5), (5,0), (5,5)
        int[][] rectangle = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        int[] point = {0, 3};

        // Points on the edge should not be considered inside
        assertFalse(checker.isInsideRectangle(rectangle, point));
    }

    @Test
    void testEndToEnd_PointOnRectangleCorner() {
        // Rectangle with corners at (0,0), (0,5), (5,0), (5,5)
        int[][] rectangle = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        int[] point = {0, 0};

        // Points on the corner (which is also on the edge) should not be considered inside
        assertFalse(checker.isInsideRectangle(rectangle, point));
    }

    @Test
    void testEndToEnd_InvalidRectangleReturnsFalse() {
        // Not a valid rectangle (non-rectangular shape)
        int[][] rectangle = {{0, 0}, {0, 5}, {3, 3}, {5, 0}};
        int[] point = {2, 2};

        // If the shape is not a rectangle, isInsideRectangle should return false
        assertFalse(checker.isInsideRectangle(rectangle, point));
    }

    @Test
    void testEndToEnd_TooFewVerticesThrowsException() {
        // Only 3 vertices
        int[][] rectangle = {{0, 0}, {0, 5}, {5, 5}};
        int[] point = {2, 2};

        assertFalse(checker.isInsideRectangle(rectangle, point));
    }

    @Test
    void testEndToEnd_RectangleWithMoreThan4Points() {
        // Rectangle with 6 points (extra points on edges)
        int[][] rectangle = {{0, 0}, {0, 2}, {0, 5}, {5, 5}, {5, 2}, {5, 0}};
        int[] point = {2, 2};

        // Point inside a valid rectangle with more than 4 points should return true
        assertTrue(checker.isInsideRectangle(rectangle, point));
    }
}
