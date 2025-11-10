package ca.utoronto.utm.assignment2.paint;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * A squiggly line made of individual points.
 *
 * @author Danial Irfan | irfanda3, with help from Salehi
 */

public class Squiggle {
    private ArrayList<Point> points = new ArrayList<>();
    private Color color;
    private double thickness;

    /**
     * Constructs a Squiggle with a given color.
     * If null, defaults to BLACK.
     *
     * @param color the color of the squiggle
     */
    public Squiggle(Color color, double thickness) {
        this.color = (color != null) ? color : Color.BLACK;
        this.thickness = thickness;
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public ArrayList<Point> getPoints() {
        return points;
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
}