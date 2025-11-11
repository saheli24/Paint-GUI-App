package ca.utoronto.utm.assignment2.paint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a geometric square that can be drawn on the canvas.
 * Stores its origin point, width, and height. Both the width and
 * height are the same values. A square is a shape.
 *
 * @author Anas H. | habiban4
 *
 */
public class Square implements Shape {
    private Point origin;
    private double width;
    private double height;
    private boolean filled;
    private Color color; // new field
    private double thickness;
    private Point startPoint;


    /**
     * Constructs a new Square with the specified origin and side
     *
     * @param origin starting (x, y) of the square
     * @param side   the width and height of the square
     */
    public Square(Point origin, double side, Color color, boolean filled, double thickness) {
        this.origin = origin;
        this.width = side;
        this.height = side;
        this.color = color; // default
        this.filled = filled;
        this.thickness = thickness;
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

    public Point getStartPoint() {return this.startPoint;}

    public void setStartPoint(Point p) {this.startPoint = p;}

    public boolean isFilled() { return filled; }

    public double getThickness() {return this.thickness;}

    public void setThickness(double thickness) {this.thickness = thickness;}

    /**
     * Draws a Square.
     *
     * @param g
     * @param opacity
     *
     * @author Anas H. | habiban4
     *
     */
    public void draw(GraphicsContext g, double opacity) {
        double x = getOrigin().x;
        double y = getOrigin().y;
        double side = getWidth(); // assume width = height for square

        double drawX = side >= 0 ? x : x + side;
        double drawY = side >= 0 ? y : y + side;
        double drawSide = Math.abs(side);

        Color drawColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
        double t = this.getThickness();

        if (filled) {
            g.setFill(drawColor);
            g.fillRect(drawX, drawY, drawSide, drawSide);
        } else {
            g.setStroke(drawColor);
            g.setLineWidth(t);
            g.strokeRect(drawX, drawY, drawSide, drawSide);
        }
    }
}