package ca.utoronto.utm.assignment2.paint;

/**
 * Represents a geometric oval that can be drawn on the canvas.
 * Stores its origin point (top-left corner of the bounding box), width, and height.
 *
 * @author Saheli | sahasah1
 */
public class Oval {
    private Point origin;  // top-left corner
    private double width;
    private double height;

    /**
     * Constructs a new Oval with the specified origin, width, and height.
     *
     * @param origin the top-left corner (x, y) of the bounding box for Oval
     * @param width  the horizontal diameter of Oval
     * @param height the vertical diameter of Oval
     */
    public Oval(Point origin, double width, double height) {
        this.origin = origin;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the origin (top-left corner) of the bounding box of Oval.
     *
     * @return the origin point
     */
    public Point getOrigin() {
        return origin;
    }

    /**
     * Sets a new origin for the Oval
     *
     * @param origin the new origin value of the oval
     */
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    /**
     * Returns the width (horizontal diameter) of Oval.
     *
     * @return the width of the oval
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the width (horizontal diameter) of Oval.
     *
     * @param width the new width value in pixels
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Returns the height (vertical diameter) of Oval.
     *
     * @return the height of the oval
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height (vertical diameter) of Oval.
     *
     * @param height the new height value in pixels
     */
    public void setHeight(double height) {
        this.height = height;
    }
}
