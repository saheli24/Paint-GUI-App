package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.Observable;
import javafx.scene.paint.Color;

/**
 * Presents the paint board. The paint board has
 * shapes of all different colors and sizes
 * on it.
 *
 * @author Anas H. | habiban4
 *
 */
public class PaintModel extends Observable {
    private final ArrayList<Shape> shapes = new ArrayList<>();
    private final ArrayList<Shape> currentShapes = new ArrayList<>();
    private boolean currentFillStyle = true;
    private Color currentColor = Color.BLACK;


    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void addShape(Shape s) {
        if (s != null) {
            shapes.add(s);
            notifyObserversOfChange();
        }
    }

    public ArrayList<Shape> getCurrentShapes() {
        return currentShapes;
    }

    public void setCurrentShape(Shape s) {
        currentShapes.clear();
        if (s != null) currentShapes.add(s);
        notifyObserversOfChange();
    }

    public void clearCurrentShape() {
        currentShapes.clear();
        notifyObserversOfChange();
    }

    public ArrayList<Shape> getAllShapes() {
        ArrayList<Shape> all = new ArrayList<>();
        all.addAll(shapes);
        all.addAll(currentShapes);
        return all;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(Color c) {
        currentColor = (c != null) ? c : Color.BLACK;
        notifyObserversOfChange();
    }

    public boolean ifFillStyle() {
        return currentFillStyle;
    }

    public void setCurrentFillStyle(String fillStyle) {
        this.currentFillStyle = fillStyle.equalsIgnoreCase("Solid");
        notifyObserversOfChange();
    }

    public void notifyObserversOfChange() {
        setChanged();
        notifyObservers();
    }
}
