package com.envitia.geometry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Rectangle class.
 */
public class RectangleTest {

    @Test
    void testFromCoordinateArray_ValidRectangle() {
        int[][] coordinates = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        Rectangle rectangle = Rectangle.fromCoordinateArray(coordinates);

        assertEquals(new Point(0, 0), rectangle.getMinPoint());
        assertEquals(new Point(5, 5), rectangle.getMaxPoint());
        assertEquals(4, rectangle.getVertices().size());
    }

    @Test
    void testContainsPoint_PointInside() {
        int[][] coordinates = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        Rectangle rectangle = Rectangle.fromCoordinateArray(coordinates);
        Point point = new Point(2, 2);

        assertTrue(rectangle.containsPoint(point));
    }

    @Test
    void testContainsPoint_PointOutside() {
        int[][] coordinates = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        Rectangle rectangle = Rectangle.fromCoordinateArray(coordinates);
        Point point = new Point(6, 6);

        assertFalse(rectangle.containsPoint(point));
    }

    @Test
    void testContainsPoint_PointOnEdge() {
        int[][] coordinates = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        Rectangle rectangle = Rectangle.fromCoordinateArray(coordinates);
        Point point = new Point(0, 2);

        // Points on the edge should not be considered inside
        assertFalse(rectangle.containsPoint(point));
    }

    @Test
    void testContainsPoint_PointOnCorner() {
        int[][] coordinates = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        Rectangle rectangle = Rectangle.fromCoordinateArray(coordinates);
        Point point = new Point(0, 0);

        // Points on the corner (which is also on the edge) should not be considered inside
        assertFalse(rectangle.containsPoint(point));
    }

    @Test
    void testContainsPoint_PointAtMinPoint() {
        int[][] coordinates = {{-2, -1}, {-2, 5}, {5, 5}, {5, 0}};
        Rectangle rectangle = Rectangle.fromCoordinateArray(coordinates);
        Point minPoint = rectangle.getMinPoint();

        // Points exactly at the minPoint should not be considered inside
        assertFalse(rectangle.containsPoint(minPoint));
    }

    @Test
    void testContainsPoint_PointAtMaxPoint() {
        int[][] coordinates = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        Rectangle rectangle = Rectangle.fromCoordinateArray(coordinates);
        Point maxPoint = rectangle.getMaxPoint();

        // Points exactly at the maxPoint should not be considered inside
        assertFalse(rectangle.containsPoint(maxPoint));
    }

    @Test
    void testContainsPoint_PointOutsideAboveMaxY() {
        int[][] coordinates = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        Rectangle rectangle = Rectangle.fromCoordinateArray(coordinates);
        Point point = new Point(2, 6);

        // Points above the maxY should not be considered inside
        assertFalse(rectangle.containsPoint(point));
    }

    @Test
    void testContainsPoint_PointOutsideLeftOfMinX() {
        int[][] coordinates = {{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        Rectangle rectangle = Rectangle.fromCoordinateArray(coordinates);
        Point point = new Point(-1, 2);

        // Points left of the minX should not be considered inside
        assertFalse(rectangle.containsPoint(point));
    }

    @Test
    void testFromCoordinateArray_MaxPointIsGreaterThanMinPoint() {
        int[][] coordinates = {{5, 5}, {0, 5}, {0, 0}, {5, 0}};
        Rectangle rectangle = Rectangle.fromCoordinateArray(coordinates);

        assertTrue(rectangle.getMinPoint().x() < rectangle.getMaxPoint().x());
        assertTrue(rectangle.getMinPoint().y() < rectangle.getMaxPoint().y());
    }
}
