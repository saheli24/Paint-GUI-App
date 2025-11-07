package ca.utoronto.utm.assignment2.paint;

import javafx.scene.paint.Color;

/**
 * Represents a geometric rectangle that can be drawn on the canvas.
 * Stores its origin point, width, and height.
 *
 * @author Anas H. | habiban4
 *
 */
public class Rectangle {
    private Point origin;
    private double width;
    private double height;
    private Color color; // new field
    private boolean filled;

    /**
     * Constructs a new Rectangle with the specified origin, width, and height.
     *
     * @param origin starting (x, y) of the rectangle
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     *
     */
    public Rectangle(Point origin, double width, double height, Color color, boolean filled) {
        this.origin = origin;
        this.width = width;
        this.height = height;
        this.color = color; // default
        this.filled = filled;
    }

    /**
     * Sets the color of this object.
     *
     * @param color the new Color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns the current color of this object.
     *
     * @return the Color of this object
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Returns the area of the following Rectangle
     *
     * @return the width * height
     *
     */
    public double getArea() {
        return width * height;
    }

    /**
     * Returns the origin of the following Rectangle
     *
     * @return origin
     *
     */
    public Point getOrigin() {
        return origin;
    }

    /**
     * Sets a new origin for the following Rectangle
     *
     * @param origin the new origin value of the rectangle
     */
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    /**
     * Returns the width of the following Rectangle
     *
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the new width of the following Rectangle
     *
     * @param width the new width value of the rectangle in pixels
     *
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Returns the height of the following Rectangle
     *
     * @return height
     *
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height of this rectangle.
     *
     * @param height the new height value of the rectangle in pixels
     */
    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isFilled() { return filled; }
}
