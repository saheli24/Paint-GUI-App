package ca.utoronto.utm.assignment2.paint;

/**
 * Represents a geometric square that can be drawn on the canvas.
 * Stores its origin point and width
 *
 * @author Anas H. | habiban4
 *
 */
public class Square {
    private Point origin;
    private double width;
    private double height;

    /**
     * Constructs a new Square with the specified origin and width
     *
     * @param origin starting (x, y) of the square
     * @param width the width of the square
     *
     */
    public Square(Point origin, double width) {
        this.origin = origin;
        this.width = width;
        this.height = width;
    }

    /**
     * Returns the area of the following square
     *
     * @return the width * height
     *
     */
    public double getArea() {
        return width * height;
    }

    /**
     * Returns the origin of the following square
     *
     * @return origin
     *
     */
    public Point getOrigin() {
        return origin;
    }

    /**
     * Sets a new origin for the following square
     *
     * @param origin the new origin value of the square
     */
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    /**
     * Returns the width of the following Square
     *
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the new width of the following Square
     *
     * @param width the new width value of the Square in pixels
     *
     */
    public void setWidth(double width) {
        this.width = width;
        this.height = width;
    }

    /**
     * Returns the height of the following square
     *
     * @return width
     *
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Sets the height of this square
     *
     * @param height the new height value of the square in pixels
     */
    public void setHeight(double height) {
        this.height = height;
        this.width = height;
    }

}