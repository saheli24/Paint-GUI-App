package ca.utoronto.utm.assignment2.paint;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Squiggle {
    private ArrayList<Point> points = new ArrayList<>();
    private Color color = Color.BLACK; // default

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
}