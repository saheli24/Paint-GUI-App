package ca.utoronto.utm.assignment2.paint;
/**
 * Represents a geometric triangle that can be drawn on the canvas.
 * The triangle is defined by a top-left corner, the width, and the height.
 * The triangle can be drawn with a given color, fill style, and line thickness.
 * @author Prem Patel/pate2652
 */
public class Triangle {
    private Point origin;
    private double width;
    private double height;

    /**
     * Constructs a new Triangle with the specified origin, width, and height.
     * @param origin the top-left corner (x, y) of the Triangle.
     * @param width  the width of the triangle
     * @param height is the height of this triangle.
     */
    public Triangle(Point origin, double width, double height) {
        this.origin = origin;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the origin (top-left corner) of the Triangle.
     * @return the origin point
     */
    public Point getOrigin() {
        return origin;
    }

    /**
     * Sets a new origin for the Triangle.
     * @param origin the new origin point of the Triangle
     */
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    /**
     * Returns the width of the Triangle.
     * @return is the width of the Triangle
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the width of the Triangle.
     * @param width is the new width of the Triangle.
     *
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Returns the height of the Triangle.
     *
     * @return is the height of the triangle.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height of the Triangle.
     *
     * @param height is the new height of the Triangle
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Returns the x coordinates of the triangle’s three vertices.
     * Top vertex is centered horizontally, bottom two form the base.
     * @return is an array of the three x coordinates.
     */
    public int[] getXPoints() {
        int[] xPoints = new int[3];
        xPoints[0] = (int) (origin.x + width / 2);       // top vertex
        xPoints[1] = (int) origin.x;                     // bottom-left
        xPoints[2] = (int) (origin.x + width);           // bottom-right
        return xPoints;
    }

    /**
     * Returns the y coordinates of the triangle’s three vertices.
     * @return is an array of the three y coordinates.
     */
    public int[] getYPoints() {
        int[] yPoints = new int[3];
        yPoints[0] = (int) origin.y;                     // top vertex
        yPoints[1] = (int) (origin.y + height);          // bottom-left
        yPoints[2] = (int) (origin.y + height);          // bottom-right
        return yPoints;
    }
}
