package ca.utoronto.utm.assignment2.paint;
import javafx.scene.paint.Color;
import java.util.ArrayList;

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

    public double getThickness() {return this.thickness;}

    public void setThickness(double thickness) {this.thickness = thickness;}
}