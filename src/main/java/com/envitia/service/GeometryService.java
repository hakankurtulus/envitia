package com.envitia.service;

/**
 * Service interface for performing geometric operations.
 * <p>
 * This interface defines the contract for services that perform
 * geometric calculations and operations on shapes and points.
 * It abstracts the implementation details of geometric algorithms
 * and provides a clean API for clients.
 * </p>
 */
public interface GeometryService {

    /**
     * Determines if a point is inside a rectangle.
     * <p>
     * This method checks if the specified point is strictly inside the rectangle
     * defined by the given coordinates. A point is considered inside if it is
     * fully enclosed by the rectangle's edges (points on the edge are not considered inside).
     * </p>
     * <p>
     * The method also validates that the provided coordinates form a valid rectangle.
     * If the shape is not a valid rectangle, the method will return false.
     * </p>
     *
     * @param rectangleCoordinates a 2D array where each element is a point [x,y] defining the rectangle
     * @param pointCoordinates an array [x,y] representing the point to check
     * @return true if the point is strictly inside the rectangle, false otherwise
     * @throws com.envitia.exception.GeometryValidationException if the input coordinates are invalid
     */
    boolean isPointInRectangle(int[][] rectangleCoordinates, int[] pointCoordinates);
}
