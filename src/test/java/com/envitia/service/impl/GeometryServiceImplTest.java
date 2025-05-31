package com.envitia.service.impl;

import com.envitia.exception.GeometryValidationException;
import com.envitia.service.GeometryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the GeometryServiceImpl class.
 */
public class GeometryServiceImplTest {

    private GeometryService geometryService;

    @BeforeEach
    void setUp() {
        geometryService = new GeometryServiceImpl();
    }

    /**
     * Provides test cases for points that should be inside rectangles.
     */
    static Stream<Arguments> validPointsProvider() {
        return Stream.of(
                // Simple square with a point at a center
                Arguments.of(
                        new int[][]{{-1, -1}, {-1, 1}, {1, 1}, {1, -1}, {-1, -1}},
                        new int[]{0, 0},
                        "Point [0,0] should be inside the rectangle"
                ),
                // Rectangle with a point inside
                Arguments.of(
                        new int[][]{{1, 1}, {10, 7}, {10, 1}, {1, 7}},
                        new int[]{2, 6},
                        "Point [2,6] should be inside the rectangle"
                ),
                // Rectangle with extra points on the edges and point inside
                Arguments.of(
                        new int[][]{{1, 1}, {1, 3}, {3, 3}, {5, 3}, {5, 2}, {5, 1}},
                        new int[]{4, 2},
                        "Point [4,2] should be inside the rectangle"
                )
        );
    }

    /**
     * Provides test cases for points that should not be inside rectangles.
     */
    static Stream<Arguments> invalidPointsProvider() {
        return Stream.of(
                // Non-rectangular shape
                Arguments.of(
                        new int[][]{{1, 1}, {2, 7}, {10, 7}, {9, 1}},
                        new int[]{8, 2},
                        "This shape is not a proper rectangle"
                ),
                // Point on edge of rectangle
                Arguments.of(
                        new int[][]{{0, 0}, {3, 0}, {3, 10}, {0, 10}},
                        new int[]{2, 10},
                        "Point [2,10] should be outside the rectangle (on edge)"
                )
        );
    }

    @Test
    void testIsPointInRectangle_NullRectangle() {
        int[] point = {2, 2};

        assertThrows(GeometryValidationException.class, () ->
                geometryService.isPointInRectangle(null, point));
    }

    @Test
    void testIsPointInRectangle_NullPoint() {
        int[][] rectangle = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};

        assertThrows(GeometryValidationException.class, () ->
                geometryService.isPointInRectangle(rectangle, null));
    }

    @Test
    void testIsPointInRectangle_PointInside() {
        int[][] rectangle = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        int[] point = {2, 2};

        assertTrue(geometryService.isPointInRectangle(rectangle, point));
    }

    @Test
    void testIsPointInRectangle_PointOutside() {
        int[][] rectangle = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        int[] point = {6, 6};

        assertFalse(geometryService.isPointInRectangle(rectangle, point));
    }

    @Test
    void testIsPointInRectangle_PointOnEdge() {
        int[][] rectangle = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        int[] point = {0, 2};

        // Points on the edge should not be considered inside
        assertFalse(geometryService.isPointInRectangle(rectangle, point));
    }

    @Test
    void testIsPointInRectangle_PointOnCorner() {
        int[][] rectangle = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        int[] point = {0, 0};

        // Points on the corner (which is also on the edge) should not be considered inside
        assertFalse(geometryService.isPointInRectangle(rectangle, point));
    }

    @Test
    void testIsPointInRectangle_InvalidRectangle() {
        int[][] rectangle = {{0, 0}, {0, 5}, {3, 3}, {5, 0}}; // Not a valid rectangle
        int[] point = {2, 2};

        // If the shape is not a rectangle, isPointInRectangle should return false
        assertFalse(geometryService.isPointInRectangle(rectangle, point));
    }

    @Test
    void testIsPointInRectangle_TooFewVertices() {
        int[][] rectangle = {{0, 0}, {0, 5}, {5, 5}}; // Only 3 vertices
        int[] point = {2, 2};

        assertFalse(geometryService.isPointInRectangle(rectangle, point));
    }

    @Test
    void testIsPointInRectangle_InvalidPointArray() {
        int[][] rectangle = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        int[] point = {2}; // Missing y coordinate

        assertThrows(GeometryValidationException.class, () -> 
            geometryService.isPointInRectangle(rectangle, point));
    }

    @Test
    void testIsPointInRectangle_ContainsNullPoint() {
        int[][] rectangle = {{0, 0}, {0, 5}, null, {5, 5}, {5, 0}};
        int[] point = {2,2}; // Missing y coordinate

        assertThrows(GeometryValidationException.class, () ->
                geometryService.isPointInRectangle(rectangle, point));
    }

    @ParameterizedTest
    @MethodSource("validPointsProvider")
    void testValidPoints(int[][] shape, int[] point, String message) {
        // Act & Assert
        assertTrue(geometryService.isPointInRectangle(shape, point), message);
    }

    @ParameterizedTest
    @MethodSource("invalidPointsProvider")
    void testInvalidPoints(int[][] shape, int[] point, String message) {
        // Act & Assert
        assertFalse(geometryService.isPointInRectangle(shape, point), message);
    }
}
