package ca.utoronto.utm.assignment2.paint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * Represents a squiggly line made up of points that can be drawn on the canvas.
 * A squiggle is a shape.
 *
 */
public class Squiggle implements Shape {
    private ArrayList<Point> points = new ArrayList<>();
    private Color color;
    private double thickness;

    /**
     * Constructs a Squiggle with a given color.
     * If null, defaults to BLACK.
     *
     * @param color the color of the squiggle
     * @param thickness how thick the squiggle is
     */
    public Squiggle(Color color, double thickness) {
        this.color = (color != null) ? color : Color.BLACK;
        this.thickness = thickness;
    }

    /**
     * Adds a point to the squiggle.
     *
     * @param p The point to add.
     */
    public void addPoint(Point p) {
        points.add(p);
    }

    /**
     * Returns the points that makes up the squiggle.
     * @return all points
     */
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

    @Override
    public void handleDrag(MouseEvent e) {
        this.addPoint(new Point(e.getX(), e.getY()));
    }

    @Override
    public void handleRelease(PaintModel model) {
        model.saveState();
        model.addShape(this);
        model.clearCurrentShape();
    }

    @Override
    public Shape clone() {
        Squiggle copy = new Squiggle(
            Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()),
            thickness
        );

        for (Point p : points) {
            copy.addPoint(new Point(p.x, p.y));
        }

        return copy;
    }

}