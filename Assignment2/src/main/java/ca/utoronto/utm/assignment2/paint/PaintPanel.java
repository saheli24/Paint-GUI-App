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
    private PaintModel model;

    public Circle circle; // This is VERY UGLY, should somehow fix this!!

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
        System.out.println(this.mode);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        // Later when we learn about inner classes...
        // https://docs.oracle.com/javafx/2/events/DraggablePanelsExample.java.htm

        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

        // "Circle", "Rectangle", "Square", "Squiggle", "Polyline"
        switch(this.mode) {
            case "Circle":
                if(mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
                    System.out.println("Started Circle");
                     Point centre = new Point(mouseEvent.getX(), mouseEvent.getY());
                        this.circle=new Circle(centre, 0);
                } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {

                } else if (mouseEventType.equals(MouseEvent.MOUSE_MOVED)) {

                } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
                    if(this.circle!=null){
                                // Problematic notion of radius and centre!!
                                double radius = Math.abs(this.circle.getCentre().x-mouseEvent.getX());
                                this.circle.setRadius(radius);
                                this.model.addCircle(this.circle);
                                System.out.println("Added Circle");
                                this.circle=null;
                        }
                }
                break;

            case "Rectangle":
                if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
                    System.out.println("Started Rectangle");

                    // creates a Point for the original (x, y) at the mouse location
                    Point origin = new Point(mouseEvent.getX(), mouseEvent.getY());

                    // creates a Rectangle with the origin, and a height and width of 0
                    this.rectangle = new Rectangle(origin, 0, 0);
                }

                else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
                    if (this.rectangle != null) {
                        // gets current ending (x,y) mouse values
                        double currentX = mouseEvent.getX();
                        double currentY = mouseEvent.getY();

                        // creates a "start" with the original starting points
                        Point start = this.rectangle.getOrigin();

                        // calculates both width and height
                        double width = currentX - start.x;
                        double height = currentY - start.y;

                        // sets the new width and height to the current Rectangle
                        this.rectangle.setWidth(width);
                        this.rectangle.setHeight(height);
                    }
                }
                else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
                    if (this.rectangle != null) {
                        // add the Rectangle to the list of Rectangles in the Model
                        this.model.addRectangle(this.rectangle);
                        System.out.println("Added Rectangle");
                        this.rectangle = null;
                    }
                }
                break;

            case "Square": break;

            case "Squiggle":
                if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
                    this.model.addPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));
                }
                break;
            case "Polyline": break;
            default: break;
        }
    }
    @Override
    public void update(Observable o, Object arg) {

                GraphicsContext g2d = this.getGraphicsContext2D();
                g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
                // Draw Lines
                ArrayList<Point> points = this.model.getPoints();

                g2d.setFill(Color.RED);
                for(int i=0;i<points.size()-1; i++){
                        Point p1=points.get(i);
                        Point p2=points.get(i+1);
                        g2d.strokeLine(p1.x,p1.y,p2.x,p2.y);
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

    }
}
