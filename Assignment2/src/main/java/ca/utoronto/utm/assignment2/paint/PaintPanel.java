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
        // Discard any unfinished polyline if switching tools
        if (currentTool instanceof PolylineTool polyTool) {
            polyTool.discardGhost();
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
            if (!current.isEmpty() && current.get(0) instanceof Circle) {
                Circle c = (Circle) current.get(0);
                Point centre = c.getCentre();
                double dx = e.getX() - centre.x;
                double dy = e.getY() - centre.y;
                c.setRadius(Math.sqrt(dx * dx + dy * dy));
                model.notifyObserversOfChange();
            }
        }

        @Override
        public void released(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty() && current.get(0) instanceof Circle) {
                Circle c = (Circle) current.get(0);
                model.saveState();
                model.addShape(c);
                model.clearCurrentShape();
                System.out.println("Added Circle");
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
            if (!current.isEmpty() && current.get(0) instanceof Rectangle) {
                Rectangle r = (Rectangle) current.get(0);
                Point start = r.getOrigin();

                double width = e.getX() - start.x;
                double height = e.getY() - start.y;

                r.setWidth(width);
                r.setHeight(height);

                model.notifyObserversOfChange();
            }
        }

        @Override
        public void released(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty() && current.get(0) instanceof Rectangle) {
                Rectangle r = (Rectangle) current.get(0);
                model.saveState();
                model.addShape(r);
                model.clearCurrentShape();
                System.out.println("Added Rectangle");
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
            if (!current.isEmpty() && current.get(0) instanceof Square) {
                Square s = (Square) current.get(0);
                Point start = s.getStartPoint();

                double dx = e.getX() - start.x;
                double dy = e.getY() - start.y;

                double side = Math.min(Math.abs(dx), Math.abs(dy));

                double newX = dx >= 0 ? start.x : start.x - side;
                double newY = dy >= 0 ? start.y : start.y - side;

                s.setOrigin(new Point(newX, newY));
                s.setWidth(side);
                s.setHeight(side);

                model.notifyObserversOfChange();
            }
        }

        @Override
        public void released(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty() && current.get(0) instanceof Square) {
                Square s = (Square) current.get(0);
                model.saveState();
                model.addShape(s);
                model.clearCurrentShape();
                System.out.println("Added Square");
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
            Shape s = model.getCurrentShapes().get(0);
            if (s instanceof Squiggle sq) {
                sq.addPoint(new Point(e.getX(), e.getY()));
                model.notifyObserversOfChange();
            }
        }
        @Override
        public void released(MouseEvent e) {
            Shape s = model.getCurrentShapes().get(0);
            model.saveState();
            model.addShape(s);
            model.clearCurrentShape();
        }
    }


    public class PolylineTool implements DrawingTool {
        private Polyline currentPolyline;
        private Point lastPoint;   // last placed vertex
        private Point mousePoint;  // current mouse position for live feedback

        @Override
        public void pressed(MouseEvent e) {
            Point p = new Point(e.getX(), e.getY());

            if (currentPolyline == null) {
                // Start a new polyline
                currentPolyline = new Polyline(model.getCurrentColor(), model.getCurrentThickness());
                currentPolyline.addPoint(p);

                // Add to model once for undo/redo
                model.addShape(currentPolyline);
                model.saveState();
            } else {
                // Update color/thickness for live feedback
                currentPolyline.setColor(model.getCurrentColor());
                currentPolyline.setThickness(model.getCurrentThickness());

                // Add vertex to existing polyline
                currentPolyline.addPoint(p);
            }

            lastPoint = p;
            mousePoint = new Point(p.x, p.y);
            model.setCurrentShape(currentPolyline);
            model.notifyObserversOfChange();
        }

        @Override
        public void dragged(MouseEvent e) {
            if (currentPolyline != null) {
                mousePoint = new Point(e.getX(), e.getY());
                model.notifyObserversOfChange();
            }
        }

        @Override
        public void released(MouseEvent e) {
            // No-op: vertices are added on click
        }

        public void updateMousePoint(MouseEvent e) {
            if (currentPolyline != null) {
                mousePoint = new Point(e.getX(), e.getY());
                model.notifyObserversOfChange();
            }
        }

        public void discardGhost() {
            if (currentPolyline != null) {
                mousePoint = null;
                model.clearCurrentShape();
                model.notifyObserversOfChange();
            }
        }


        public void drawFeedback(GraphicsContext g) {
            if (currentPolyline == null || lastPoint == null || mousePoint == null) return;

            // Update color/thickness for ghost
            currentPolyline.setColor(model.getCurrentColor());
            currentPolyline.setThickness(model.getCurrentThickness());

            ArrayList<Point> points = currentPolyline.getPoints();

            // Draw finalized segments
            if (points.size() > 1) {
                g.setStroke(currentPolyline.getColor());
                g.setLineWidth(currentPolyline.getThickness());
                for (int i = 0; i < points.size() - 1; i++) {
                    Point p1 = points.get(i);
                    Point p2 = points.get(i + 1);
                    g.strokeLine(p1.x, p1.y, p2.x, p2.y);
                }
            }

            // Draw ghost segment
            Color c = currentPolyline.getColor();
            g.setStroke(new Color(c.getRed(), c.getGreen(), c.getBlue(), 0.4));
            g.setLineWidth(currentPolyline.getThickness());
            g.strokeLine(lastPoint.x, lastPoint.y, mousePoint.x, mousePoint.y);
        }

        /**
         * Restore live feedback after undo or redo
         */
        public void resumeAfterUndoRedo() {
            ArrayList<Shape> shapes = model.getShapes();
            if (!shapes.isEmpty() && shapes.get(shapes.size() - 1) instanceof Polyline lastPolyline) {
                currentPolyline = lastPolyline;

                ArrayList<Point> pts = lastPolyline.getPoints();
                if (!pts.isEmpty()) {
                    lastPoint = pts.get(pts.size() - 1);
                    mousePoint = new Point(lastPoint.x, lastPoint.y);
                }

                model.setCurrentShape(currentPolyline);
            } else {
                currentPolyline = null;
                lastPoint = null;
                mousePoint = null;
                model.clearCurrentShape();
            }
            model.notifyObserversOfChange();
        }
    }


    public void undoRedoUpdatePolyline() {
        if (currentTool instanceof PolylineTool polyTool) {
            polyTool.resumeAfterUndoRedo();
        }
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
            if (!current.isEmpty() && current.get(0) instanceof Oval) {
                Oval oval = (Oval) current.get(0);
                double width = e.getX() - oval.getOrigin().x;
                double height = e.getY() - oval.getOrigin().y;
                oval.setWidth(width);
                oval.setHeight(height);
                model.notifyObserversOfChange();
            }
        }

        @Override
        public void released(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty() && current.get(0) instanceof Oval) {
                Oval oval = (Oval) current.get(0);
                model.saveState();
                model.addShape(oval);
                model.clearCurrentShape();
                 
                System.out.println("Added Oval");
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
            if (!current.isEmpty() && current.get(0) instanceof Triangle) {
                Triangle t = (Triangle) current.get(0);
                double startX = t.getOrigin().x;
                double startY = t.getOrigin().y;
                double currX = e.getX();
                double currY = e.getY();
                double width = currX - startX;
                double height = currY - startY;
                t.setWidth(width);
                t.setHeight(height);
                model.notifyObserversOfChange();
            }
        }

        @Override
        public void released(MouseEvent e) {
            ArrayList<Shape> current = model.getCurrentShapes();
            if (!current.isEmpty() && current.get(0) instanceof Triangle) {
                Triangle t = (Triangle) current.get(0);
                model.saveState();
                model.addShape(t);
                model.clearCurrentShape();
                 
                System.out.println("Added Triangle");
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
            if (currentTool instanceof PolylineTool polyTool) {
                polyTool.updateMousePoint(mouseEvent);
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
        if (currentTool instanceof PolylineTool polyTool) {
            polyTool.drawFeedback(g2d);
        }
    }
}

