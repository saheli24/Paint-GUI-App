package ca.utoronto.utm.assignment2.paint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Squiggle implements Shape {
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
    public Color getColor() {return this.color;}

    public double getThickness() {return this.thickness;}

    public void setThickness(double thickness) {this.thickness = thickness;}

    /**
     * Draws a squiggle.
     *
     * @param g
     * @param opacity
     *
     * @author Anas H. | habiban4
     *
     */
    public void draw(GraphicsContext g, double opacity) {
        if (points == null || points.size() < 2) return;

        Color drawColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
        double t = this.getThickness();
        g.setLineWidth(t);
        g.setStroke(drawColor);

        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}