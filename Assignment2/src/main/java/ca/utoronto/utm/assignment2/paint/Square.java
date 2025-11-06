package ca.utoronto.utm.assignment2.paint;
import javafx.scene.paint.Color;

/**
 * Represents a geometric square that can be drawn on the canvas.
 * Stores its origin point, width, and height. Both the width and
 * height are the same values.
 *
 * @author Anas H. | habiban4
 *
 */
public class Square {
    private Point origin;
    private double width;
    private double height;
    private Color color; // new field


    /**
     * Constructs a new Square with the specified origin and side
     *
     * @param origin starting (x, y) of the square
     * @param side the width and height of the square
     *
     */
    public Square(Point origin, double side, Color color) {
        this.origin = origin;
        this.width = side;
        this.height = side;
        this.color = color; // default

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
     * Returns the width of the following square
     *
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the new width of the following square. The height is
     * also set to be the same value as the width.
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
     * Sets the height of this square. The width is also set
     * to be the same value of the height.
     *
     * @param height the new height value of the square in pixels
     */
    public void setHeight(double height) {
        this.height = height;
        this.width = height;
    }

}