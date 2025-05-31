package com.envitia.geometry;

/**
 * Enum representing the four corners of a rectangle in a 2D coordinate system.
 * <p>
 * This enum defines the four possible corners of a rectangle, which are used
 * for validation and geometric operations. The corners are defined relative to
 * the rectangle's orientation in the standard Cartesian coordinate system where
 * x increases to the right and y increases upward.
 * </p>
 */
public enum CornerType {
    /**
     * The bottom-left corner of the rectangle (minimum x, minimum y).
     */
    BOTTOM_LEFT,

    /**
     * The top-left corner of the rectangle (minimum x, maximum y).
     */
    TOP_LEFT,

    /**
     * The bottom-right corner of the rectangle (maximum x, minimum y).
     */
    BOTTOM_RIGHT,

    /**
     * The top-right corner of the rectangle (maximum x, maximum y).
     */
    TOP_RIGHT
}
