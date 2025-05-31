package com.envitia.geometry;

import com.envitia.exception.GeometryValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Point class.
 */
public class PointTest {

    @Test
    void testPointConstructor() {
        Point point = new Point(5, 10);
        assertEquals(5, point.x());
        assertEquals(10, point.y());
    }

    @Test
    void testFromArray_ValidArray() {
        int[] coordinates = {5, 10};
        Point point = Point.fromArray(coordinates);
        assertEquals(5, point.x());
        assertEquals(10, point.y());
    }

    @Test
    void testFromArray_TooFewElements() {
        int[] coordinates = {5};
        assertThrows(GeometryValidationException.class, () -> Point.fromArray(coordinates));
    }

    @Test
    void testFromArray_TooManyElements() {
        int[] coordinates = {5, 10, 15};
        assertThrows(GeometryValidationException.class, () -> Point.fromArray(coordinates));
    }

    @Test
    void testEquality() {
        Point point1 = new Point(5, 10);
        Point point2 = new Point(5, 10);
        Point point3 = new Point(10, 5);

        assertEquals(point1, point2);
        assertNotEquals(point1, point3);
    }

    @Test
    void testHashCode() {
        Point point1 = new Point(5, 10);
        Point point2 = new Point(5, 10);

        assertEquals(point1.hashCode(), point2.hashCode());
    }

    @Test
    void testToString() {
        Point point = new Point(5, 10);
        String toString = point.toString();
        
        assertTrue(toString.contains("5"));
        assertTrue(toString.contains("10"));
    }

    @Test
    void testFromArray_NullArray() {
        assertThrows(GeometryValidationException.class, () -> Point.fromArray(null));
    }

    @Test
    void testFromArray_NegativeValues() {
        int[] invalidCoordinates = {-5, 10}; // Assuming negative values are invalid
        Point point = Point.fromArray(invalidCoordinates);
        assertEquals(-5, point.x());
        assertEquals(10, point.y());
    }
}