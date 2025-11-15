package ca.utoronto.utm.assignment2.paint;
import javafx.scene.canvas.Canvas;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class PaintPanel extends Canvas implements EventHandler<MouseEvent>, Observer {
    private String mode = "Circle";
    private DrawingTool currentTool = new CircleTool();
    private PaintModel model;
    private Scanner scanner = new Scanner(System.in);
    private boolean EraserOn = false;

    public PaintPanel(PaintModel model) {
        super(500, 500);
        this.model=model;
        this.model.addObserver(this);
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, this);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, this);
        this.addEventHandler(MouseEvent.MOUSE_MOVED, this);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, this);
    }

    /**
     *  Controller
     */
    public void setMode(String mode) {
        if (currentTool != null) {
            currentTool.discardGhost(model);
        }

        this.mode = mode;
        // "Circle", "Rectangle", "Square", "Squiggle", "Polyline", "Oval", "Triangle"
        switch(this.mode) {
            case "Circle" :
                currentTool = new CircleTool();
                break;
            case "Rectangle" :
                currentTool = new RectangleTool();
                break;
            case "Square":
                currentTool = new SquareTool();
                break;
            case "Squiggle":
                currentTool = new SquiggleTool();
                break;
            case "Polyline":
                currentTool = new PolylineTool();
                break;
            case "Oval":
                currentTool = new OvalTool();
                break;
            case "Triangle":
                currentTool = new TriangleTool();
                break;
            case "Text":
                currentTool = new TextTool();
                break;
            case "Eraser":
                currentTool = new EraserTool();
        }
        if (Objects.equals(this.mode, "Eraser")) {
            EraserOn = true;
        }
        else {
            EraserOn = false;
        }
        System.out.println(this.mode);
    }

    public class CircleTool implements DrawingTool {
        @Override
        public void pressed(MouseEvent e) {
            System.out.println("Started Circle");
            Point centre = new Point(e.getX(), e.getY());
            Circle c;
            if (model.isMultiColorMode() && model.ifFillStyle()) {
                c = new Circle(centre, 0, model.getPrimaryColor(), model.getSecondaryColor(),
                        model.ifFillStyle(), model.getCurrentThickness());
            } else {
                c = new Circle(centre, 0, model.getPrimaryColor(), model.getPrimaryColor(),
                        model.ifFillStyle(), model.getCurrentThickness());
            }
            model.setCurrentShape(c);
        }

        @Override
        public void dragged(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty()) {
                Shape s = current.get(0);
                s.handleDrag(e);
                model.notifyObserversOfChange();
            }
        }

        @Override
        public void released(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty()) {
                Shape s = current.get(0);
                s.handleRelease(model);
            }
        }
    }

    public class RectangleTool implements DrawingTool {
        @Override
        public void pressed(MouseEvent e) {
            System.out.println("Started Rectangle");
            Point origin = new Point(e.getX(), e.getY());
            Rectangle r;
            if (model.isMultiColorMode() && model.ifFillStyle()) {
                r = new Rectangle(origin, 0, 0, model.getPrimaryColor(), model.getSecondaryColor(),
                        model.ifFillStyle(), model.getCurrentThickness());
            } else {
                r = new Rectangle(origin, 0, 0, model.getPrimaryColor(), model.getPrimaryColor(),
                        model.ifFillStyle(), model.getCurrentThickness());
            }
            model.setCurrentShape(r);
        }

        @Override
        public void dragged(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty()) {
                Shape s = current.get(0);
                s.handleDrag(e);
                model.notifyObserversOfChange();
            }
        }

        @Override
        public void released(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty()) {
                Shape s = current.get(0);
                s.handleRelease(model);
            }
        }
    }

    public class SquareTool implements DrawingTool {
        @Override
        public void pressed(MouseEvent e) {
            System.out.println("Started Square");
            Point start = new Point(e.getX(), e.getY());
            Square s;
            if (model.isMultiColorMode() && model.ifFillStyle()) {
                s = new Square(start, 0, model.getPrimaryColor(), model.getSecondaryColor(),
                        model.ifFillStyle(), model.getCurrentThickness());
            } else {
                s = new Square(start,0, model.getPrimaryColor(), model.getPrimaryColor(),
                        model.ifFillStyle(), model.getCurrentThickness());
            }
            model.setCurrentShape(s);
        }

        @Override
        public void dragged(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty()) {
                Shape shape = current.get(0);
                shape.handleDrag(e);
                model.notifyObserversOfChange();
            }
        }

        @Override
        public void released(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty()) {
                Shape shape = current.get(0);
                shape.handleRelease(model);
            }
        }
    }

    public class SquiggleTool implements DrawingTool {
        @Override
        public void pressed(MouseEvent e) {
            Squiggle s = new Squiggle(model.getPrimaryColor(), model.getCurrentThickness());
            s.addPoint(new Point(e.getX(), e.getY()));
            model.setCurrentShape(s);
        }

        @Override
        public void dragged(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty()) {
                Shape shape = current.get(0);
                shape.handleDrag(e);
                model.notifyObserversOfChange();
            }
        }

        @Override
        public void released(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty()) {
                Shape shape = current.get(0);
                shape.handleRelease(model);
            }
        }
    }

    public class PolylineTool implements DrawingTool {
        private Polyline currentPolyline;

        @Override
        public void pressed(MouseEvent e) {
            Point p = new Point(e.getX(), e.getY());

            if (currentPolyline == null) {
                currentPolyline = new Polyline(model.getPrimaryColor(), model.getCurrentThickness());
                currentPolyline.addVertex(p);
                model.addShape(currentPolyline);
                model.saveState();
            } else {
                currentPolyline.setColor(model.getPrimaryColor());
                currentPolyline.setThickness(model.getCurrentThickness());
                currentPolyline.addVertex(p);
            }

            model.setCurrentShape(currentPolyline);
            model.notifyObserversOfChange();
        }

        @Override
        public void dragged(MouseEvent e) {
            if (currentPolyline != null) {
                currentPolyline.updateMousePoint(new Point(e.getX(), e.getY()));
                model.notifyObserversOfChange();
            }
        }

        @Override
        public void released(MouseEvent e) {
            if (currentPolyline != null) {
                currentPolyline.handleRelease(model);
            }
        }

        @Override
        public void updateMousePoint(MouseEvent e) {
            if (currentPolyline != null) {
                currentPolyline.updateMousePoint(new Point(e.getX(), e.getY()));
                model.notifyObserversOfChange();
            }
        }

        @Override
        public void discardGhost(PaintModel model) {
            if (currentPolyline != null) {
                currentPolyline.discardGhost(model);
                currentPolyline = null;
            }
        }

        @Override
        public void resumeAfterUndoRedo() {
            if (currentPolyline != null) {
                ArrayList<Point> pts = currentPolyline.getPoints();
                if (!pts.isEmpty()) {
                    Point last = pts.get(pts.size() - 1);
                    currentPolyline.updateMousePoint(last);
                }
                model.setCurrentShape(currentPolyline);
                model.notifyObserversOfChange();
            }
        }

        @Override
        public void drawFeedback(GraphicsContext g) {
            if (currentPolyline == null || currentPolyline.getPoints().isEmpty()) return;

            ArrayList<Point> points = currentPolyline.getPoints();
            Point lastPoint = points.get(points.size() - 1);
            Point mousePoint = currentPolyline.getMousePoint();

            Color currentColor = model.getPrimaryColor();
            double currentThickness = model.getCurrentThickness();

            if (points.size() > 1) {
                g.setStroke(currentColor);
                g.setLineWidth(currentThickness);
                for (int i = 0; i < points.size() - 1; i++) {
                    Point p1 = points.get(i);
                    Point p2 = points.get(i + 1);
                    g.strokeLine(p1.x, p1.y, p2.x, p2.y);
                }
            }

            if (lastPoint != null && mousePoint != null) {
                g.setStroke(new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), 0.4));
                g.setLineWidth(currentThickness);
                g.strokeLine(lastPoint.x, lastPoint.y, mousePoint.x, mousePoint.y);
            }
        }
    }

    public void undoRedoUpdatePolyline() {
        currentTool.resumeAfterUndoRedo();
    }

    public class OvalTool implements DrawingTool {
        @Override
        public void pressed(MouseEvent e) {
            System.out.println("Started Oval");
            Point origin = new Point(e.getX(), e.getY());
            Oval oval;
            if (model.isMultiColorMode() && model.ifFillStyle()) {
                oval = new Oval(origin, 0, 0, model.getPrimaryColor(), model.getSecondaryColor(),
                        model.ifFillStyle(), model.getCurrentThickness());
            } else {
                oval = new Oval(origin, 0, 0, model.getPrimaryColor(), model.getPrimaryColor(),
                        model.ifFillStyle(), model.getCurrentThickness());
            }
            model.setCurrentShape(oval);
        }

        @Override
        public void dragged(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty()) {
                Shape shape = current.get(0);
                shape.handleDrag(e);
                model.notifyObserversOfChange();
            }
        }

        @Override
        public void released(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty()) {
                Shape shape = current.get(0);
                shape.handleRelease(model);
            }
        }
    }

    public class TriangleTool implements DrawingTool {
        @Override
        public void pressed(MouseEvent e) {
            System.out.println("Started Triangle");
            Point start = new Point(e.getX(), e.getY());
            Triangle t;
            if (model.isMultiColorMode() && model.ifFillStyle()) {
                t = new Triangle(start, 0, 0, model.getPrimaryColor(), model.getSecondaryColor(),
                        model.ifFillStyle(), model.getCurrentThickness());
            } else {
                t = new Triangle(start, 0, 0, model.getPrimaryColor(), model.getPrimaryColor(), model.ifFillStyle(), model.getCurrentThickness());
            }
            model.setCurrentShape(t);
        }

        @Override
        public void dragged(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty()) {
                Shape shape = current.get(0);
                shape.handleDrag(e);
                model.notifyObserversOfChange();
            }
        }

        @Override
        public void released(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty()) {
                Shape shape = current.get(0);
                shape.handleRelease(model);
            }
        }
    }

    public class TextTool implements DrawingTool {
        @Override
        public void pressed(MouseEvent e) {
            System.out.println("Started Text");
            String currentString = scanner.nextLine();
            Point origin = new Point(e.getX(), e.getY());
            Font font = model.getCurrentFont();
            Text text = new Text(origin, font, currentString);
            model.addShape(text);
            model.setCurrentShape(text);
        }

        public void dragged(MouseEvent e) {}

        public void released(MouseEvent e) {}
    }

    public class EraserTool implements DrawingTool {

        @Override
        public void pressed(MouseEvent e) {
            Eraser eraser = new Eraser(model.getCurrentThickness());
            eraser.addPoint(new Point(e.getX(), e.getY()));
            model.setCurrentShape(eraser);
        }

        @Override
        public void dragged(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty()) {
                Shape shape = current.get(0);
                shape.handleDrag(e);
                model.notifyObserversOfChange();
            }
        }

        @Override
        public void released(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty()) {
                Shape shape = current.getFirst();
                shape.handleRelease(model);
                System.out.println("Added Text");
            }
        }
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        // Later when we learn about inner classes...
        // https://docs.oracle.com/javafx/2/events/DraggablePanelsExample.java.htm
        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

        if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
            currentTool.pressed(mouseEvent);
        } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
            currentTool.dragged(mouseEvent);
        } else if (mouseEventType.equals(MouseEvent.MOUSE_MOVED)) {
            if (mouseEventType.equals(MouseEvent.MOUSE_MOVED)) {
                currentTool.updateMousePoint(mouseEvent);
            }
        } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            currentTool.released(mouseEvent);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        GraphicsContext g2d = this.getGraphicsContext2D();
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());

        for (Shape s : model.getShapes()) {
            s.draw(g2d, 1.0);
        }

        for (Shape s : model.getCurrentShapes()) {
            if (EraserOn) {
                s.draw(g2d,1.0);
            }
            else {
                s.draw(g2d, 0.3);
            }
        }
        currentTool.drawFeedback(g2d);
    }
    
    public void reset() {
        mode = "Circle";
        currentTool = new CircleTool();
    }
}



