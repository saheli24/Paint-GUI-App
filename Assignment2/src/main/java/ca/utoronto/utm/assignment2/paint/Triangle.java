package ca.utoronto.utm.assignment2.paint;
import javafx.scene.paint.Color;

public class Triangle {
    private Point origin;
    private double width;
    private double height;
    private Color color; // new field
    private double thickness;


    public Triangle(Point origin, double width, double height,  Color color, double thickness) {
        this.origin = origin;
        this.width = width;
        this.height = height;
        this.color = color; // default
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
     * Sets the thickness of this object's outline.
     *
     * @param thickness the new outline thickness.
     */

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    /**
     * Sets the thickness of this object's outline.
     *
     * @return the outline thickness.
     */

    public double getThickness() {
        return this.thickness;
    }

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}

