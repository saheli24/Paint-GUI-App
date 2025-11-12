package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Polyline implements Shape {
    private ArrayList<Point> points = new ArrayList<>();
    private Color color;
    private double thickness;

    public Polyline(Color color, double thickness) {
        this.color = (color != null) ? color : Color.BLACK;
        this.thickness = thickness;
    }

    public void addPoint(Point p) {
        if (p != null) points.add(p);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public Color getColor() { return this.color; }
    public void setColor(Color color) { this.color = color; }

    public double getThickness() { return this.thickness; }
    public void setThickness(double thickness) { this.thickness = thickness; }

    @Override
    public void draw(GraphicsContext g, double opacity) {
        if (points == null || points.size() < 2) return;

        Color drawColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
        g.setStroke(drawColor);
        g.setLineWidth(this.thickness);

        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    @Override
    public Shape clone() {
        Polyline copy = new Polyline(
                Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()),
                thickness
        );
        for (Point p : points) {
            copy.addPoint(new Point(p.x, p.y)); // deep copy points
        }
        return copy;
    }
}
