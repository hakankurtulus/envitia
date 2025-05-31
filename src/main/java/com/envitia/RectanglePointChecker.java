package com.envitia;

import com.envitia.exception.GeometryValidationException;
import com.envitia.service.GeometryService;
import com.envitia.service.impl.GeometryServiceImpl;
import lombok.AllArgsConstructor;

/**
 * Main class for checking if a point is inside a rectangle.
 * <p>
 * This class provides a simplified API for point containment checks.
 * A point is considered inside a rectangle if and only if:
 * <ol>
 *   <li>The point is fully enclosed by all edges of the rectangle (points on the edge are not considered inside)</li>
 *   <li>The shape is a valid rectangle (non-rectangular shapes always return false)</li>
 *   <li>The rectangle can be represented with more than 4 points, as long as all points are on the edges
 *       and all four corners are present</li>
 * </ol>
 * </p>
 */
@AllArgsConstructor
public class RectanglePointChecker {

    private final GeometryService geometryService;

    /**
     * Constructs a RectanglePointChecker with the default GeometryService implementation.
     * <p>
     * This constructor initializes the checker with a new instance of {@link GeometryServiceImpl}.
     * </p>
     */
    public RectanglePointChecker() {
        this(new GeometryServiceImpl());
    }

    /**
     * Checks if a point is inside a rectangle.
     * <p>
     * A point is considered inside a rectangle if and only if:
     * <ol>
     *   <li>The point is fully enclosed by all edges of the rectangle (points on the edge are not considered inside)</li>
     *   <li>The shape is a valid rectangle (non-rectangular shapes always return false)</li>
     *   <li>The rectangle can be represented with more than 4 points, as long as all points are on the edges
     *       and all four corners are present</li>
     * </ol>
     * </p>
     * 
     * @param rectangle the coordinates of the rectangle as an array of [x,y] points
     * @param point the coordinates of the point as an array [x,y]
     * @return true if the point is inside the rectangle, false otherwise (including if the point is on the edge
     *         or if the shape is not a valid rectangle)
     * @throws GeometryValidationException if the rectangle or point coordinates are invalid
     */
    public boolean isInsideRectangle(int[][] rectangle, int[] point) {
        return geometryService.isPointInRectangle(rectangle, point);
    }
}
