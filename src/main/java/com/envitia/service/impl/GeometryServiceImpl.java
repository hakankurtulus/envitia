package com.envitia.service.impl;

import com.envitia.geometry.Point;
import com.envitia.geometry.Rectangle;
import com.envitia.service.GeometryService;
import com.envitia.validation.GeometryValidator;

/**
 * Default implementation of the {@link GeometryService} interface.
 * <p>
 * This implementation provides the core geometric operations for the application,
 * including point-in-rectangle checks. It delegates to domain objects for specific
 * geometric calculations and uses the {@link GeometryValidator} for validation.
 * </p>
 */
public class GeometryServiceImpl implements GeometryService {

    /**
     * {@inheritDoc}
     * <p>
     * This implementation converts the raw coordinate arrays to domain objects
     * ({@link Rectangle} and {@link Point}), validates that the shape is a valid
     * rectangle, and then delegates to the rectangle's containsPoint method.
     * </p>
     * <p>
     * If the shape is not a valid rectangle (as determined by {@link GeometryValidator#isValidRectangle}),
     * this method returns false.
     * </p>
     *
     * @throws com.envitia.exception.GeometryValidationException if the input coordinates are invalid
     */
    @Override
    public boolean isPointInRectangle(int[][] rectangleCoordinates, int[] pointCoordinates) {
        Rectangle rectangle = Rectangle.fromCoordinateArray(rectangleCoordinates);
        Point point = Point.fromArray(pointCoordinates);
        if (!GeometryValidator.isValidRectangle(rectangle)) {
            return false;
        }
        return rectangle.containsPoint(point);
    }
}
