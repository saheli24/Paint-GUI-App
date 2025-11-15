package ca.utoronto.utm.assignment2.paint;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
    private Font currentFont = new Font("30px Arial", 20);
    private final ArrayList<ArrayList<Shape>> undoStack = new ArrayList<>();
    private final ArrayList<ArrayList<Shape>> redoStack = new ArrayList<>();
    private boolean multiColorMode = false;
    private Color primaryColor = Color.BLACK;
    private Color secondaryColor = Color.WHITE;

    public void saveState() {
        // Make a deep copy of the shapes list
        ArrayList<Shape> copy = new ArrayList<>();
        for (Shape s : shapes) {
            copy.add(s.clone()); // You need a clone method in Shape
        }
        undoStack.add(copy);
        redoStack.clear(); // Clear redo after a new action
    }

    // Undo last action
    public void undo() {
        if (!undoStack.isEmpty()) {
            ArrayList<Shape> copy = new ArrayList<>();
            for (Shape s : shapes) copy.add(s.clone());
            redoStack.add(copy);

            ArrayList<Shape> lastState = undoStack.remove(undoStack.size() - 1);
            shapes.clear();
            for (Shape s : lastState) shapes.add(s.clone());

            notifyObserversOfChange();
        }
    }

    // Redo last undone action
    public void redo() {
        if (!redoStack.isEmpty()) {
            ArrayList<Shape> copy = new ArrayList<>();
            for (Shape s : shapes) copy.add(s.clone());
            undoStack.add(copy);

            ArrayList<Shape> nextState = redoStack.remove(redoStack.size() - 1);
            shapes.clear();
            for (Shape s : nextState) shapes.add(s.clone());

            notifyObserversOfChange();
        }
    }

    // Reset to an empty canvas and default settings
    public void reset() {
        shapes.clear();
        currentShapes.clear();
        currentFillStyle = true;
        currentColor = Color.BLACK;
        currentThickness = 1;
        undoStack.clear();
        redoStack.clear();

        notifyObserversOfChange();
    }


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
        String fontName = this.currentFont.getName();
        this.currentFont = new Font(fontName, currentThickness*10);
        notifyObserversOfChange();
    }

    public double getCurrentThickness() { return this.currentThickness; }

    public void setCurrentFont(String font) {
        this.currentFont = new Font(font, currentThickness*10);
        System.out.println(this.currentFont.getName());
        notifyObserversOfChange();
    }

    public Font getCurrentFont() {return this.currentFont;}


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
    public boolean isMultiColorMode() {
        return multiColorMode;
    }

    public void setMultiColorMode(boolean multiColorMode) {
        this.multiColorMode = multiColorMode;
        notifyObserversOfChange();
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(Color primaryColor) {
        this.primaryColor = primaryColor;
        notifyObserversOfChange();
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(Color secondaryColor) {
        this.secondaryColor = secondaryColor;
        notifyObserversOfChange();
    }

    public void notifyObserversOfChange() {
        setChanged();
        notifyObservers();
    }
}
