package com.envitia.validation;

import com.envitia.exception.GeometryValidationException;
import com.envitia.geometry.CornerType;
import com.envitia.geometry.Point;
import com.envitia.geometry.Rectangle;

import java.util.List;

import static com.envitia.geometry.CornerType.*;
import static java.util.Objects.isNull;

public class GeometryValidator {

    /**
     * The required number of dimensions for a point (x and y coordinates).
     */
    private static final int REQUIRED_POINT_DIMENSIONS = 2;

    /**
     * The minimum number of vertices required to form a valid rectangle.
     */
    private static final int MINIMUM_RECTANGLE_VERTICES = 4;

    /**
     * Private constructor to prevent instantiation.
     */
    private GeometryValidator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Validates that the provided point coordinates are valid.
     * <p>
     * This method checks that:
     * <ul>
     *   <li>The coordinates array is not null</li>
     *   <li>The coordinates array has exactly 2 elements (x and y coordinates)</li>
     * </ul>
     * </p>
     *
     * @param coordinates the array containing the point coordinates [x, y]
     * @throws GeometryValidationException if the coordinates are null or don't have exactly 2 elements
     */
    public static void validatePointCoordinates(int[] coordinates) {
        if (isNull(coordinates)) {
            throw new GeometryValidationException("Point coordinates cannot be null");
        }
        if (coordinates.length != REQUIRED_POINT_DIMENSIONS) {
            throw new GeometryValidationException(
                "Point coordinates must have exactly " + REQUIRED_POINT_DIMENSIONS + " elements, found: " + coordinates.length);
        }
    }

    /**
     * Validates that the provided rectangle coordinates are valid.
     * <p>
     * This method checks that:
     * <ul>
     *   <li>The coordinates array is not null</li>
     *   <li>Each point in the coordinates array is valid (by delegating to {@link #validatePointCoordinates})</li>
     * </ul>
     * </p>
     * <p>
     * Note that this method only validates the basic structure of the coordinates array.
     * It does not check if the points actually form a valid rectangle. For that,
     * use {@link #isValidRectangle} after creating a Rectangle object.
     * </p>
     *
     * @param coordinates a 2D array where each element is a point [x, y] defining the rectangle
     * @throws GeometryValidationException if the coordinates are null or any point is invalid
     */
    public static void validateRectangleCoordinates(int[][] coordinates) {
        if (isNull(coordinates)) {
            throw new GeometryValidationException("Rectangle coordinates cannot be null");
        }
        for (int[] point : coordinates) {
            validatePointCoordinates(point);
        }
    }


    /**
     * Validates if the given rectangle is a valid rectangle.
     * This validation ensures that:
     * 1. The rectangle has proper dimensions (width and height > 0)
     * 2. All provided vertices are actually on the rectangle's perimeter
     * 3. All four corners of the rectangle are included in the vertices list
     * @param rectangle the rectangle to validate
     * @return true if the rectangle is valid, false otherwise
     */
    public static boolean isValidRectangle(Rectangle rectangle) {
        List<Point> vertices = rectangle.getVertices();
        Point minPoint = rectangle.getMinPoint();
        Point maxPoint = rectangle.getMaxPoint();

        if (vertices.size() < MINIMUM_RECTANGLE_VERTICES) {
            return false;
        }

        // Check if all points are on the edges of the rectangle, and we have all four corners
        return areAllPointsOnRectangleEdges(vertices, minPoint, maxPoint) &&
                hasAllFourRectangleCorners(vertices, minPoint, maxPoint);
    }

    private static boolean areAllPointsOnRectangleEdges(List<Point> vertices, Point minPoint, Point maxPoint) {
        for (Point vertex : vertices) {
            if (!isPointOnRectangleEdge(vertex, minPoint, maxPoint)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isPointOnRectangleEdge(Point point, Point minPoint, Point maxPoint) {
        int x = point.x();
        int y = point.y();

        boolean onHorizontalEdge = (y == minPoint.y() || y == maxPoint.y()) &&
                (x >= minPoint.x() && x <= maxPoint.x());
        boolean onVerticalEdge = (x == minPoint.x() || x == maxPoint.x()) &&
                (y >= minPoint.y() && y <= maxPoint.y());

        return onHorizontalEdge || onVerticalEdge;
    }

    private static boolean hasAllFourRectangleCorners(List<Point> vertices, Point minPoint, Point maxPoint) {
        return hasRectangleCorner(vertices, minPoint, maxPoint, BOTTOM_LEFT) &&
                hasRectangleCorner(vertices, minPoint, maxPoint, TOP_LEFT) &&
                hasRectangleCorner(vertices, minPoint, maxPoint, BOTTOM_RIGHT) &&
                hasRectangleCorner(vertices, minPoint, maxPoint, TOP_RIGHT);
    }

    private static boolean hasRectangleCorner(List<Point> vertices, Point minPoint, Point maxPoint, CornerType cornerType) {
        for (Point p : vertices) {
            if (isRectangleCorner(p, minPoint, maxPoint, cornerType)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isRectangleCorner(Point point, Point minPoint, Point maxPoint, CornerType cornerType) {
        return switch (cornerType) {
            case BOTTOM_LEFT -> point.x() == minPoint.x() && point.y() == minPoint.y();
            case TOP_LEFT -> point.x() == minPoint.x() && point.y() == maxPoint.y();
            case BOTTOM_RIGHT -> point.x() == maxPoint.x() && point.y() == minPoint.y();
            case TOP_RIGHT -> point.x() == maxPoint.x() && point.y() == maxPoint.y();
        };
    }
}
