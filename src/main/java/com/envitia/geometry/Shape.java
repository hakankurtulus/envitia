package com.envitia.geometry;

/**
 * Interface representing a geometric shape in a 2D coordinate system.
 * <p>
 * This interface defines the common behavior for all geometric shapes
 * in the system. It provides methods to determine the relationship
 * between shapes and points, such as containment.
 * </p>
 */
public interface Shape {

    /**
     * Determines if the shape contains the specified point.
     * <p>
     * A point is considered to be contained by a shape if it is strictly
     * inside the shape's boundaries. Points that lie exactly on the
     * boundary of the shape are not considered to be contained by the shape.
     * </p>
     *
     * @param point the point to check for containment
     * @return true if the point is strictly inside the shape, false otherwise
     */
    boolean containsPoint(Point point);
}
