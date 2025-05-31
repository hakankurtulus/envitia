package com.envitia.geometry;

import com.envitia.validation.GeometryValidator;
import java.util.Arrays;
import java.util.List;
import lombok.Value;

/**
 * Immutable representation of a rectangle in a 2D coordinate system.
 * <p>
 * This class implements the {@link Shape} interface and represents a rectangle
 * defined by a set of vertices. The rectangle is characterized by its minimum and
 * maximum points (forming a bounding box) and the list of vertices that define its perimeter.
 * </p>
 * <p>
 * A valid rectangle must have at least 4 points, with all points lying on the edges
 * of the rectangle, and must include all four corners. The rectangle supports additional
 * points on its edges beyond the four corners.
 * </p>
 */
@Value
public class Rectangle implements Shape {

    /**
     * The minimum point (bottom-left corner) of the rectangle's bounding box.
     */
    Point minPoint;

    /**
     * The maximum point (top-right corner) of the rectangle's bounding box.
     */
    Point maxPoint;

    /**
     * The list of vertices that define the perimeter of the rectangle.
     * This list is immutable and contains at least 4 points.
     */
    List<Point> vertices;

    /**
     * Creates a new Rectangle from a list of vertices.
     * <p>
     * This constructor calculates the bounding box (minPoint and maxPoint)
     * from the provided vertices.
     * </p>
     *
     * @param vertices the list of points that define the rectangle's perimeter
     */
    private Rectangle(List<Point> vertices) {
        this.vertices = List.copyOf(vertices); // Create an immutable copy

        // Calculate bounds from vertices
        Point[] bounds = calculateBounds(vertices);
        this.minPoint = bounds[0];
        this.maxPoint = bounds[1];
    }

    /**
     * {@inheritDoc}
     * <p>
     * A point is considered inside the rectangle if its coordinates are strictly
     * greater than the minimum point's coordinates and strictly less than the
     * maximum point's coordinates. Points on the edges are not considered inside.
     * </p>
     */
    @Override
    public boolean containsPoint(Point point) {
        return point.x() > minPoint.x() &&
                point.x() < maxPoint.x() &&
                point.y() > minPoint.y() &&
                point.y() < maxPoint.y();
    }

    /**
     * Creates a Rectangle from an array of coordinate pairs.
     * <p>
     * This factory method validates the input coordinates and converts them
     * to a list of Point objects before creating a Rectangle.
     * </p>
     *
     * @param coordinates a 2D array where each element is a point [x,y] defining the rectangle
     * @return a new Rectangle instance
     * @throws com.envitia.exception.GeometryValidationException if the coordinates are invalid
     */
    public static Rectangle fromCoordinateArray(int[][] coordinates) {
        GeometryValidator.validateRectangleCoordinates(coordinates);
        List<Point> points = Arrays.stream(coordinates)
                .map(Point::fromArray)
                .toList();
        return new Rectangle(points);
    }

    /**
     * Calculates the minimum and maximum points (bounding box) from a list of vertices.
     *
     * @param vertices the list of points to calculate bounds from
     * @return an array containing the minimum point at index 0 and the maximum point at index 1
     */
    private Point[] calculateBounds(List<Point> vertices) {
        Point first = vertices.getFirst();
        int minX = first.x(), maxX = first.x();
        int minY = first.y(), maxY = first.y();

        for (Point vertex : vertices) {
            minX = Math.min(minX, vertex.x());
            maxX = Math.max(maxX, vertex.x());
            minY = Math.min(minY, vertex.y());
            maxY = Math.max(maxY, vertex.y());
        }
        return new Point[] { new Point(minX, minY), new Point(maxX, maxY) };
    }
}
