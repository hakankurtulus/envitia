# Rectangle Point Checker

A Java utility for checking if a point is inside a rectangle. This library provides functionality to validate rectangles and determine if a given point is strictly inside a rectangle (not on the edge).

## Overview

The Rectangle Point Checker utility allows you to:
- Check if a point is strictly inside a rectangle
- Validate if a shape is a proper rectangle
- Handle various edge cases and input validations
- Extend the system with custom geometry service implementations

## Requirements

- Java 21 or higher
- Maven 3.6 or higher

## Installation

Clone the repository and build the project using Maven:

```bash
git clone https://github.com/hakankurtulus/envitia.git
cd envitia
mvn clean install
```

## Usage

### Basic Usage

```java
import com.envitia.RectanglePointChecker;

public class Example {
    public static void main(String[] args) {
        // Create an instance of the RectanglePointChecker
        RectanglePointChecker checker = new RectanglePointChecker();

        // Define a rectangle as an array of ordered coordinate pairs [x, y]
        // The rectangle is defined by its vertices
        int[][] rectangle = {
            {0, 0},   // Bottom-left corner
            {0, 3},   // Top-left corner
            {4, 3},   // Top-right corner
            {4, 0}    // Bottom-right corner
        };

        // Define a point as a coordinate pair [x, y]
        int[] pointInside = {2, 1};
        int[] pointOnEdge = {0, 1};
        int[] pointOutside = {5, 5};

        // Check if points are inside the rectangle
        boolean isInside = checker.isInsideRectangle(rectangle, pointInside);
        boolean isOnEdge = checker.isInsideRectangle(rectangle, pointOnEdge);
        boolean isOutside = checker.isInsideRectangle(rectangle, pointOutside);

        System.out.println("Point inside: " + isInside);     // true
        System.out.println("Point on edge: " + isOnEdge);    // false
        System.out.println("Point outside: " + isOutside);   // false
    }
}
```

### Advanced Usage

The library can handle rectangles with more than 4 points, as long as all points are on the edges of a valid rectangle:

```java
// Create an instance of the RectanglePointChecker
RectanglePointChecker checker = new RectanglePointChecker();

// A rectangle with extra points on edges
int[][] rectangleWithExtraPoints = {
    {0, 0},   // Bottom-left corner
    {0, 2},   // Point on left edge
    {0, 4},   // Top-left corner
    {3, 4},   // Top-right corner
    {3, 2},   // Point on right edge
    {3, 0}    // Bottom-right corner
};

int[] point = {1, 2};  // Point inside

boolean result = checker.isInsideRectangle(rectangleWithExtraPoints, point);
// result will be true
```

## API Documentation

### `RectanglePointChecker.isInsideRectangle(int[][] rectangle, int[] point)`

Checks if a point is strictly inside a rectangle (not on the edge).

**Parameters:**
- `rectangle`: An array of ordered coordinate pairs [x, y] which form a rectangle
- `point`: A coordinate pair [x, y] to check

**Returns:**
- `true` if the point is inside the rectangle
- `false` if the point is on the edge, outside the rectangle, or if the shape is not a valid rectangle

**Throws:**
- `GeometryValidationException` if:
  - Rectangle or point is null
  - Point array doesn't have exactly 2 elements (x and y coordinates)

### `RectanglePointChecker()`

Default constructor that initializes the checker with the default GeometryService implementation.

### `RectanglePointChecker(GeometryService geometryService)`

Constructor that initializes the checker with a custom GeometryService implementation.

**Parameters:**
- `geometryService`: A custom implementation of the GeometryService interface

## Important Note on Point Containment

While some geometry libraries consider points on the edge to be "inside" a shape, the Rectangle Point Checker utility specifically checks if a point is **strictly inside** the rectangle (not on the edge). This is by design and consistent with the API documentation.

Points that lie exactly on the edge of a rectangle will always return `false` when checked with `isInsideRectangle()`. This behavior is consistent across all usage scenarios.

## Rectangle Validation

A shape is considered a valid rectangle if:
1. It has at least 4 points
2. All points are on the edges of the rectangle
3. It has all four corners defined (top-left, top-right, bottom-left, bottom-right)

## Architecture and Design

The project follows a layered architecture with clear separation of concerns:

### Domain Layer
- **Shape Interface**: Defines the common behavior for all geometric shapes
- **Rectangle Class**: Implements the Shape interface for rectangles
- **Point Class**: Represents a 2D point with x and y coordinates

### Service Layer
- **GeometryService Interface**: Defines operations for geometry calculations
- **GeometryServiceImpl**: Implements the GeometryService interface

### Validation Layer
- **GeometryValidator**: Provides validation for geometry objects
- **GeometryValidationException**: Exception thrown for validation errors

### Facade Layer
- **RectanglePointChecker**: Provides a simplified API for point containment checks


## Testing

The project includes comprehensive tests covering various scenarios:
- Input validation (null checks)
- Shape validation (valid and invalid rectangles)
- Point position testing (inside, on edge, outside)

Run the tests using Maven:

```bash
mvn test
```