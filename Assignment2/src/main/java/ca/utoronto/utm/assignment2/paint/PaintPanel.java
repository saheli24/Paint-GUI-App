package ca.utoronto.utm.assignment2.paint;
import javafx.scene.canvas.Canvas;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class PaintPanel extends Canvas implements EventHandler<MouseEvent>, Observer {
    private String mode = "Circle";
    private DrawingTool currentTool = new CircleTool();
    private PaintModel model;

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
        }
        System.out.println(this.mode);
    }

    public class CircleTool implements DrawingTool {

        @Override
        public void pressed(MouseEvent e) {
            System.out.println("Started Circle");
            Point centre = new Point(e.getX(), e.getY());
            Color shapeColor = model.getCurrentColor() != null ? model.getCurrentColor() : Color.BLACK;
            Circle c = new Circle(centre, 0, shapeColor, model.ifFillStyle(), model.getCurrentThickness());
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
            Color shapeColor = model.getCurrentColor() != null ? model.getCurrentColor() : Color.BLACK;

            Rectangle r = new Rectangle(origin, 0, 0, shapeColor, model.ifFillStyle(), model.getCurrentThickness());
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
            Color shapeColor = model.getCurrentColor() != null ? model.getCurrentColor() : Color.BLACK;

            Square s = new Square(start, 0, shapeColor, model.ifFillStyle(), model.getCurrentThickness());
            s.setStartPoint(start);

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
            Squiggle s = new Squiggle(model.getCurrentColor(), model.getCurrentThickness());
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
                currentPolyline = new Polyline(model.getCurrentColor(), model.getCurrentThickness());
                currentPolyline.addVertex(p);
                model.addShape(currentPolyline);
                model.saveState();
            } else {
                currentPolyline.setColor(model.getCurrentColor());
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
            if (currentPolyline != null) {
                currentPolyline.draw(g, 1.0);
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
            Color shapeColor = model.getCurrentColor() != null ? model.getCurrentColor() : Color.BLACK;
            Oval oval = new Oval(origin, 0, 0, shapeColor, model.ifFillStyle(), model.getCurrentThickness());
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
            Color shapeColor = model.getCurrentColor() != null ? model.getCurrentColor() : Color.BLACK;
            Triangle t = new Triangle(start, 0, 0, shapeColor, model.ifFillStyle(), model.getCurrentThickness());
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
            s.draw(g2d, 0.3);
        }

        currentTool.drawFeedback(g2d);
    }
}

