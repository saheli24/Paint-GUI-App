package ca.utoronto.utm.assignment2.paint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Represents a geometric rectangle that can be drawn on the canvas.
 * Stores its origin point, width, and height. A rectangle is a Shape.
 *
 * @author Anas H. | habiban4
 *
 */
public class Rectangle implements Shape {
    private Point origin;
    private double width;
    private double height;
    private Color color; // new field
    private boolean filled;
    private double thickness;

    /**
     * Constructs a new Rectangle with the specified origin, width, and height.
     *
     * @param origin starting (x, y) of the rectangle
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     *
     */
    public Rectangle(Point origin, double width, double height, Color color, boolean filled, double thickness) {
        this.origin = origin;
        this.width = width;
        this.height = height;
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

    /**
     * Returns the line thickness of this object or its outline.
     *
     * @return the line thickness of this object or its outline
     */
    public double getThickness() {return this.thickness;}


    /**
     * Sets the thickness of this object or its line thickness.
     *
     * @param thickness the new thickness to set
     */
    public void setThickness(double thickness) {this.thickness = thickness;}

    /**
     * Draws a rectangle.
     *
     * @param g
     * @param opacity
     *
     * @author Anas H. | habiban4
     *
     */
    public void draw(GraphicsContext g, double opacity) {
        // gets original x, y, width, and height values from Rectangle
        double x = getOrigin().x;
        double y = getOrigin().y;
        double width = getWidth();
        double height = getHeight();

        // Calculates values based on where the ending point is
        double drawX = width >= 0 ? x : x + width;
        double drawY = height >= 0 ? y : y + height;
        double drawWidth = Math.abs(width);
        double drawHeight = Math.abs(height);

        Color drawColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
        double t = this.getThickness();

        if (filled) {
            g.setFill(drawColor);
            g.fillRect(drawX, drawY, drawWidth, drawHeight);
        } else {
            g.setStroke(drawColor);
            g.setLineWidth(t);
            g.strokeRect(drawX, drawY, drawWidth, drawHeight);
        }
    }

    @Override
    public void handleDrag(MouseEvent e) {
        Point start = this.getOrigin();
        double width = e.getX() - start.x;
        double height = e.getY() - start.y;

        this.setWidth(width);
        this.setHeight(height);
    }

    @Override
    public void handleRelease(PaintModel model) {
        model.saveState();
        model.addShape(this);
        model.clearCurrentShape();

        System.out.println("Added Rectangle");
    }


    @Override
    public Shape clone() {
        return new Rectangle(
                new Point(origin.x, origin.y), // deep copy of Point
                width,
                height,
                Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()), // new Color
                filled,
                thickness
        );
    }

}
