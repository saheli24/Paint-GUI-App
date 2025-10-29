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
// need to figure out how to display rectangle mid construction
public class PaintPanel extends Canvas implements EventHandler<MouseEvent>, Observer {
    public interface DrawingTool {
        void onMousePressed(MouseEvent e);
        void onMouseDragged(MouseEvent e);
        void onMouseReleased(MouseEvent e);
    }
    private String mode = "Circle";
    private DrawingTool currentTool = new CircleTool();
    private PaintModel model;

    public Rectangle rectangle;

    public PaintPanel(PaintModel model) {
        super(300, 300);
        this.model=model;
        this.model.addObserver(this);

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, this);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, this);
        this.addEventHandler(MouseEvent.MOUSE_MOVED, this);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, this);
    }

    /**
     *  Controller aspect of this
     */
    public void setMode(String mode){
        this.mode = mode;
        switch(this.mode) {
            case "Circle":
                this.currentTool = new CircleTool();
                break;
            case "Rectangle":
                this.currentTool = new RectangleTool();
                break;
            case "Square":
                this.currentTool = new SquareTool();
                break;
            case "Squiggle":
                this.currentTool = new SquiggleTool();
                break;
            case "Polyline":
                this.currentTool = new PolylineTool();
                break;
            case "Oval":
                this.currentTool = new OvalTool();
                break;
            case "Triangle":
                this.currentTool = new TriangleTool();
                break;
        }
        System.out.println(this.mode);
    }

    public class CircleTool implements DrawingTool{

        @Override
        public void onMousePressed(MouseEvent e) {
            System.out.println("Started Circle");
            Point centre = new Point(e.getX(), e.getY());
            Circle c = new Circle(centre, 0);
            model.setCurrentCircle(c);
        }

        @Override
        public void onMouseDragged(MouseEvent e) {
            Circle c = model.getCurrentCircle();
            if(c != null) {
                Point centre = c.getCentre();
                double radius = Math.sqrt(Math.pow(e.getX() - centre.x, 2) + Math.pow(e.getY() - centre.y, 2));
                c.setRadius(radius);
                model.notifyObserversOfChange();
            }
        }

        @Override
        public void onMouseReleased(MouseEvent e) {
            Circle c = model.getCurrentCircle();
            if(c != null){
                // Problematic notion of radius and centre!!
                model.addCircle(c);
                System.out.println("Added Circle");
                model.clearCurrentCircle();
            }
        }
    }

    public class RectangleTool implements DrawingTool{

        @Override
        public void onMousePressed(MouseEvent e) {
            System.out.println("Started Rectangle");

            // creates a Point for the original (x, y) at the mouse location
            Point origin = new Point(e.getX(), e.getY());

            // creates a Rectangle with the origin, and a height and width of 0
            rectangle = new Rectangle(origin, 0, 0);
        }

        @Override
        public void onMouseDragged(MouseEvent e) {
            // gets current ending (x,y) mouse values
            double currentX = e.getX();
            double currentY = e.getY();

            // creates a "start" with the original starting points
            Point start = rectangle.getOrigin();

            // calculates both width and height
            double width = currentX - start.x;
            double height = currentY - start.y;

            // sets the new width and height to the current Rectangle
            rectangle.setWidth(width);
            rectangle.setHeight(height);

            //notify observers of mid-construction shapes
            model.notifyObserversOfChange();
        }

        @Override
        public void onMouseReleased(MouseEvent e) {
            if (rectangle != null) {
                // add the Rectangle to the list of Rectangles in the Model
                model.addRectangle(rectangle);
                System.out.println("Added Rectangle");
                rectangle = null;
            }
        }
    }

    public class SquareTool implements DrawingTool{

        @Override
        public void onMousePressed(MouseEvent e) {

        }

        @Override
        public void onMouseDragged(MouseEvent e) {

        }

        @Override
        public void onMouseReleased(MouseEvent e) {

        }
    }

    public class SquiggleTool implements DrawingTool{

        @Override
        public void onMousePressed(MouseEvent e) {
            model.startSquiggle();
        }

        @Override
        public void onMouseDragged(MouseEvent e) {
            model.addPointToCurrentSquiggle(new Point(e.getX(), e.getY()));
        }

        @Override
        public void onMouseReleased(MouseEvent e) {
            model.endSquiggle();
        }
    }

    public class PolylineTool implements DrawingTool{

        @Override
        public void onMousePressed(MouseEvent e) {

        }

        @Override
        public void onMouseDragged(MouseEvent e) {

        }

        @Override
        public void onMouseReleased(MouseEvent e) {

        }
    }

    public class OvalTool implements DrawingTool{

        @Override
        public void onMousePressed(MouseEvent e) {
            System.out.println("Started Oval");
            Point origin = new Point(e.getX(), e.getY());
            Oval oval = new Oval(origin, 0, 0);
            model.setCurrentOval(oval);
        }

        @Override
        public void onMouseDragged(MouseEvent e) {
            Oval oval = model.getCurrentOval();
            if (oval != null) {
                // Calculate current width and height based on mouse position
                double width = e.getX() - oval.getOrigin().x;
                double height = e.getY() - oval.getOrigin().y;
                oval.setWidth(width);
                oval.setHeight(height);
                model.notifyObserversOfChange();
            }
        }

        @Override
        public void onMouseReleased(MouseEvent e) {
            Oval oval = model.getCurrentOval();
            if (oval != null) {
                model.addOval(oval);
                model.clearCurrentOval();
                System.out.println("Added Oval");
            }
        }
    }

    public class TriangleTool implements DrawingTool{

        @Override
        public void onMousePressed(MouseEvent e) {

        }

        @Override
        public void onMouseDragged(MouseEvent e) {

        }

        @Override
        public void onMouseReleased(MouseEvent e) {

        }
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        // Later when we learn about inner classes...
        // https://docs.oracle.com/javafx/2/events/DraggablePanelsExample.java.htm

        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

        // "Circle", "Rectangle", "Square", "Squiggle", "Polyline"
        if(mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
            currentTool.onMousePressed(mouseEvent);
        }
        else if(mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
            currentTool.onMouseDragged(mouseEvent);
        }
        else if(mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            currentTool.onMouseReleased(mouseEvent);
        }
    }
    @Override
    public void update(Observable o, Object arg) {

        GraphicsContext g2d = this.getGraphicsContext2D();
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
        // Draw Lines
        g2d.setStroke(Color.BLACK);
        for (Squiggle squiggle : model.getSquiggles()) {
            ArrayList<Point> points = squiggle.getPoints();
            for (int i = 0; i < points.size() - 1; i++) {
                Point p1 = points.get(i);
                Point p2 = points.get(i + 1);
                g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        // Draw the squiggle currently being dragged (live feedback)
        Squiggle current_s = model.getCurrentSquiggle();
        if (current_s != null) {
            ArrayList<Point> pts = current_s.getPoints();
            for (int i = 0; i < pts.size() - 1; i++) {
                Point p1 = pts.get(i);
                Point p2 = pts.get(i + 1);
                g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        // Draw Circles
        ArrayList<Circle> circles = this.model.getCircles();

        g2d.setFill(Color.GREEN);
        for(Circle c: this.model.getCircles()){
            double x = c.getCentre().x;
            double y = c.getCentre().y;
            double radius = c.getRadius();
            g2d.fillOval(x, y, radius, radius);
        }

        Circle current = model.getCurrentCircle();
        if(current != null) {
            double x = current.getCentre().x - current.getRadius();
            double y = current.getCentre().y - current.getRadius();
            double diameter = current.getRadius() * 2;
            g2d.setFill(Color.rgb(0, 225, 0, 0.3));
            g2d.fillOval(x, y, diameter, diameter);
            g2d.setStroke(Color.GRAY);
            g2d.strokeOval(x, y, diameter, diameter);
        }

        // list of all following rectangles  to be drawn
        ArrayList<Rectangle> rectangles = this.model.getRectangles();

        g2d.setFill(Color.HOTPINK);
        for (Rectangle rectangle : this.model.getRectangles()) {
            // gets original x, y, width, and height values from Rectangle
            double x = rectangle.getOrigin().x;
            double y = rectangle.getOrigin().y;
            double width = rectangle.getWidth();
            double height = rectangle.getHeight();

            // Calculates values based on where the ending point is
            double drawX = width >= 0 ? x : x + width;
            double drawY = height >= 0 ? y : y + height;
            double drawWidth = Math.abs(width);
            double drawHeight = Math.abs(height);

            // draw the Rectangle
            g2d.fillRect(drawX, drawY, drawWidth, drawHeight);
        }

        // Draw the rectangle currently being dragged (mid-construction feedback)
        if (this.rectangle != null) {
            double x = this.rectangle.getOrigin().x;
            double y = this.rectangle.getOrigin().y;
            double width = this.rectangle.getWidth();
            double height = this.rectangle.getHeight();

            double drawX = x;
            if (width < 0) {
                drawX = x + width;
            }

            double drawY = y;
            if (height < 0) {
                drawY = y + height;
            }

            double drawWidth = Math.abs(width);
            double drawHeight = Math.abs(height);

            // Semi-transparent fill for ghost rectangle during drag
            g2d.setFill(Color.rgb(100, 100, 255, 0.3)); // blue with 30% opacity
            g2d.fillRect(drawX, drawY, drawWidth, drawHeight);

            // Diagonal dashed lines for guidance
            g2d.setStroke(Color.LIGHTGRAY);
            g2d.setLineDashes(5); // dashed line
            g2d.strokeLine(drawX, drawY, drawX + drawWidth, drawY + drawHeight);
            g2d.strokeLine(drawX, drawY + drawHeight, drawX + drawWidth, drawY);
            g2d.setLineDashes(null); // reset to solid lines

            // Display top-left coordinates + width/height
            g2d.setFill(Color.BLACK);
            g2d.fillText(
                    String.format("(%.0f, %.0f) w: %.0f h: %.0f", drawX, drawY, drawWidth, drawHeight),
                    drawX + 5, drawY - 5
            );

            g2d.setStroke(Color.BLACK); // reset squiggle stroke (to black)


        }

        // Draw finalized ovals
        g2d.setFill(Color.ORANGE);
        for (Oval oval : model.getOvals()) {
            double x = oval.getOrigin().x;
            double y = oval.getOrigin().y;
            double width = oval.getWidth();
            double height = oval.getHeight();

            double drawX = width >= 0 ? x : x + width;
            double drawY = height >= 0 ? y : y + height;
            double drawWidth = Math.abs(width);
            double drawHeight = Math.abs(height);

            g2d.fillOval(drawX, drawY, drawWidth, drawHeight);
        }

        // Draw live oval (mid-drag)
        Oval currentOval = model.getCurrentOval();
        if (currentOval != null) {
            double x = currentOval.getOrigin().x;
            double y = currentOval.getOrigin().y;
            double width = currentOval.getWidth();
            double height = currentOval.getHeight();

            double drawX = width >= 0 ? x : x + width;
            double drawY = height >= 0 ? y : y + height;
            double drawWidth = Math.abs(width);
            double drawHeight = Math.abs(height);

            g2d.setFill(Color.rgb(255, 165, 0, 0.3)); // semi-transparent orange
            g2d.fillOval(drawX, drawY, drawWidth, drawHeight);

            g2d.setStroke(Color.DARKORANGE);
            g2d.strokeOval(drawX, drawY, drawWidth, drawHeight);
        }

    }
}
