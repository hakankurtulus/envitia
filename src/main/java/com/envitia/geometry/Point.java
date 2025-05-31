package com.envitia.geometry;

import com.envitia.validation.GeometryValidator;

/**
 * Represents a 2D point with x and y coordinates in a Cartesian coordinate system.
 * <p>
 * This immutable record encapsulates the concept of a point in a two-dimensional space,
 * defined by its integer x and y coordinates. Points are fundamental geometric entities
 * used to define shapes and perform geometric calculations.
 * </p>
 */
public record Point(int x, int y) {

    /**
     * Creates a Point from an array of coordinates.
     * <p>
     * This factory method validates that the input array contains exactly two elements
     * representing the x and y coordinates of the point.
     * </p>
     *
     * @param coordinates an array containing [x, y] coordinates
     * @return a new Point instance with the specified coordinates
     * @throws com.envitia.exception.GeometryValidationException if the coordinates array is null or does not have exactly 2 elements
     */
    public static Point fromArray(int[] coordinates) {
        GeometryValidator.validatePointCoordinates(coordinates);
        return new Point(coordinates[0], coordinates[1]);
    }
}
