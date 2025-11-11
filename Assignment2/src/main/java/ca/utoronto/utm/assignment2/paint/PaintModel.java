package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.Observable;
import javafx.scene.paint.Color;

/**
 * Presents the paint board. The paint board has
 * shapes of all different colors and sizes
 * on it.
 *
 * The code uses the Observable/Observer pattern, so the view and controller update automatically when the
 * model changes. Shapes are stored as individual objects, which makes it easier to modify them separately.
 * The controller and view are connected through the model, so changes in the UI are reflected on the canvas.
 *
 * @author Anas H. | habiban4
 *
 */
public class PaintModel extends Observable {
    private final ArrayList<Shape> shapes = new ArrayList<>();
    private final ArrayList<Shape> currentShapes = new ArrayList<>();
    private boolean currentFillStyle = true;
    private Color currentColor = Color.BLACK; // the currently selected color
    private double currentThickness = 1; // the currently selected thickness

    public void addShape(Shape s) {
        if (s != null) {
            shapes.add(s);
            notifyObserversOfChange();
        }
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public ArrayList<Shape> getCurrentShapes() {
        return currentShapes;
    }

    public void setCurrentShape(Shape s) {
        currentShapes.clear();
        if (s != null) {
            currentShapes.add(s);
            notifyObserversOfChange();
        }
    }

    public void clearCurrentShape() {
        currentShapes.clear();
        notifyObserversOfChange();
    }

    public void setCurrentThickness(double thickness) {
        this.currentThickness = thickness;
        notifyObserversOfChange();
    }

    public double getCurrentThickness() { return this.currentThickness; }


    public ArrayList<Shape> getAllShapes() {
        ArrayList<Shape> all = new ArrayList<>();
        all.addAll(shapes);
        all.addAll(currentShapes);
        return all;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setCurrentFillStyle(String fillStyle) {
        this.currentFillStyle = fillStyle.equalsIgnoreCase("Solid");
        notifyObserversOfChange();
    }

    public boolean getCurrentFillStyle() {
        return this.currentFillStyle;
    }

    public void setCurrentColor(Color c) {
        currentColor = (c != null) ? c : Color.BLACK;
        notifyObserversOfChange();
    }

    public boolean ifFillStyle() {
        return currentFillStyle;
    }

    public void notifyObserversOfChange() {
        setChanged();
        notifyObservers();
    }
}
