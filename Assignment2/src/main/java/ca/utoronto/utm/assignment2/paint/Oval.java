package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Represents a geometric oval that can be drawn on the canvas.
 * Stores its origin point (top-left corner of the bounding box), width, and height.
 *
 * @author Saheli | sahasah1
 */
public class Oval implements Shape {
    private Point origin;  // top-left corner
    private double width;
    private double height;
    private Color color; // new field
    private boolean filled;
    private double thickness;
    /**
     * Constructs a new Oval with the specified origin, width, and height.
     *
     * @param origin the top-left corner (x, y) of the bounding box for Oval
     * @param width  the horizontal diameter of Oval
     * @param height the vertical diameter of Oval
     */
    public Oval(Point origin, double width, double height, Color color, boolean filled, double thickness) {
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
     * Draws the Oval.
     *
     * @param g
     * @param opacity
     *
     * @author Anas H. | habiban4
     *
     */
    public void draw(GraphicsContext g, double opacity) {
        double drawX = width >= 0 ? origin.x : origin.x + width;
        double drawY = height >= 0 ? origin.y : origin.y + height;
        double drawWidth = Math.abs(width);
        double drawHeight = Math.abs(height);

        Color drawColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
        double t = this.getThickness();

        if (filled) {
            g.setFill(drawColor);
            g.fillOval(drawX, drawY, drawWidth, drawHeight);
        } else {
            g.setStroke(drawColor);
            g.setLineWidth(t);
            g.strokeOval(drawX, drawY, drawWidth, drawHeight);
        }
    }

    @Override
    public void handleDrag(MouseEvent e) {
        double width = e.getX() - this.getOrigin().x;
        double height = e.getY() - this.getOrigin().y;

        this.setWidth(width);
        this.setHeight(height);
    }

    @Override
    public void handleRelease(PaintModel model) {
        model.saveState();
        model.addShape(this);
        model.clearCurrentShape();

        System.out.println("Added Oval");
    }

    @Override
    public Shape clone() {
        return new Oval(
                new Point(origin.x, origin.y), // deep copy of Point
                width,
                height,
                Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()), // new Color
                filled,
                thickness
        );
    }
}
