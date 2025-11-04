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
    private String mode = "Circle";
    private PaintModel model;

    // @habiban4
    public Rectangle rectangle;
    public Square square;
    private Point squareStart;


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
                     Circle c = new Circle(centre, 0);
                     c.setColor(model.getCurrentColor()); // assign color here
                     model.setCurrentCircle(c);
                } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
                    Circle c = model.getCurrentCircle();
                    if(c != null) {
                        Point centre = c.getCentre();
                        double dx = mouseEvent.getX() - centre.x;
                        double dy = mouseEvent.getY() - centre.y;
                        double radius = Math.sqrt(dx * dx + dy * dy);
                        c.setRadius(radius);
                        model.notifyObserversOfChange();
                    }

                } else if (mouseEventType.equals(MouseEvent.MOUSE_MOVED)) {

                } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
                    Circle c = model.getCurrentCircle();
                    if(c != null){

                        // Problematic notion of radius and centre!!

                        model.addCircle(c);
                        System.out.println("Added Circle");
                        model.clearCurrentCircle();
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
                    rectangle.setColor(model.getCurrentColor());

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

                        //notify observers of mid-construction shapes
                        this.model.notifyObserversOfChange();

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
            case "Square":
                if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
                    System.out.println("Started Square");

                    // original press point
                    squareStart = new Point(mouseEvent.getX(), mouseEvent.getY());

                    // create a new square at that origin with size 0
                    this.square = new Square(new Point(squareStart.x, squareStart.y), 0);

                    square.setColor(model.getCurrentColor());

                } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
                    if (this.square != null) {

                        double currentX = mouseEvent.getX();
                        double currentY = mouseEvent.getY();

                        // distance dragged
                        double dx = currentX - squareStart.x;
                        double dy = currentY - squareStart.y;

                        // side length = min of dx, dy
                        double side = Math.min(Math.abs(dx), Math.abs(dy));

                        // adjust origin for top/left drag
                        double newX = dx >= 0 ? squareStart.x : squareStart.x - side;
                        double newY = dy >= 0 ? squareStart.y : squareStart.y - side;

                        // update square
                        this.square.setOrigin(new Point(newX, newY));
                        this.square.setWidth(side);

                        // redraw
                        this.model.notifyObserversOfChange();
                    }

                } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
                    if (this.square != null) {
                        this.model.addSquare(this.square);
                        System.out.println("Added Square");
                        this.square = null;
                    }
                }
                break;

            case "Squiggle":
                if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
                    model.startSquiggle();
                    Squiggle current = model.getCurrentSquiggle();
                    if (current != null) {
                        current.setColor(model.getCurrentColor()); // set color
                    }
                } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
                    model.addPointToCurrentSquiggle(new Point(mouseEvent.getX(), mouseEvent.getY()));
                } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
                    model.endSquiggle();
                }
                break;
            case "Polyline": break;

            case "Oval":
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    System.out.println("Started Oval");
                    Point origin = new Point(mouseEvent.getX(), mouseEvent.getY());
                    Oval oval = new Oval(origin, 0, 0);
                    oval.setColor(model.getCurrentColor());
                    model.setCurrentOval(oval);
                } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    Oval oval = model.getCurrentOval();
                    if (oval != null) {
                        // Calculate current width and height based on mouse position
                        double width = mouseEvent.getX() - oval.getOrigin().x;
                        double height = mouseEvent.getY() - oval.getOrigin().y;
                        oval.setWidth(width);
                        oval.setHeight(height);
                        model.notifyObserversOfChange();
                    }

                } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
                    Oval oval = model.getCurrentOval();
                    if (oval != null) {
                        model.addOval(oval);
                        model.clearCurrentOval();
                        System.out.println("Added Oval");
                    }
                }
                break;

            case "Triangle":
                if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
                    System.out.println("Started Triangle");
                    Point start = new Point(mouseEvent.getX(), mouseEvent.getY());
                    Triangle t = new Triangle(start, 0, 0);
                    t.setColor(model.getCurrentColor());
                    model.setCurrentTriangle(t);
                }
                else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
                    Triangle t = model.getCurrentTriangle();
                    if (t != null) {
                        double startX = t.getOrigin().x;
                        double startY = t.getOrigin().y;
                        double currX = mouseEvent.getX();
                        double currY = mouseEvent.getY();
                        double width = currX - startX;
                        double height = currY - startY;

                        // Keep the width and height signed if you want to allow dragging in all directions
                        t.setWidth(width);
                        t.setHeight(height);

                        model.notifyObserversOfChange();
                    }
                }
                else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
                    Triangle t = model.getCurrentTriangle();
                    if (t != null) {
                        model.addTriangle(t);
                        model.clearCurrentTriangle();
                        System.out.println("Added Triangle");
                    }
                }
                break;


            default: break;
        }
    }
    @Override
    public void update(Observable o, Object arg) {

        GraphicsContext g2d = this.getGraphicsContext2D();
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());

        // Draw all completed squiggles
        for (Squiggle squiggle : model.getSquiggles()) {
            g2d.setStroke(squiggle.getColor());
            g2d.setLineWidth(2); // optional, make it a bit thicker
            ArrayList<Point> points = squiggle.getPoints();
            for (int i = 0; i < points.size() - 1; i++) {
                Point p1 = points.get(i);
                Point p2 = points.get(i + 1);
                g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        // Draw the squiggle currently being dragged
        Squiggle current_s = model.getCurrentSquiggle();
        if (current_s != null) {
            g2d.setStroke(current_s.getColor());
            g2d.setLineWidth(2);
            ArrayList<Point> pts = current_s.getPoints();
            for (int i = 0; i < pts.size() - 1; i++) {
                Point p1 = pts.get(i);
                Point p2 = pts.get(i + 1);
                g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        // Draw Circles
        for (Circle c : model.getCircles()) {
            g2d.setFill(c.getColor());
            double x = c.getCentre().x;
            double y = c.getCentre().y;
            double radius = c.getRadius();
            g2d.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        }

        Circle currentCircle = model.getCurrentCircle();
        if (currentCircle != null) {
            Color ghost = currentCircle.getColor().deriveColor(0, 1, 1, 0.3);
            g2d.setFill(ghost);
            double x = currentCircle.getCentre().x - currentCircle.getRadius();
            double y = currentCircle.getCentre().y - currentCircle.getRadius();
            double diameter = currentCircle.getRadius() * 2;
            g2d.fillOval(x, y, diameter, diameter);
            g2d.setStroke(Color.GRAY);
            g2d.strokeOval(x, y, diameter, diameter);
        }

        // Draw all completed rectangles
        ArrayList<Rectangle> rectangles = this.model.getRectangles();
        for (Rectangle rectangle : rectangles) {
            g2d.setFill(rectangle.getColor());
            double x = rectangle.getOrigin().x;
            double y = rectangle.getOrigin().y;
            double width = rectangle.getWidth();
            double height = rectangle.getHeight();
            double drawX = width >= 0 ? x : x + width;
            double drawY = height >= 0 ? y : y + height;
            double drawWidth = Math.abs(width);
            double drawHeight = Math.abs(height);
            g2d.fillRect(drawX, drawY, drawWidth, drawHeight);
        }

        // Draw the rectangle currently being dragged (mid-construction feedback)
        if (this.rectangle != null) {
            Color ghost = this.rectangle.getColor().deriveColor(0, 1, 1, 0.3);
            g2d.setFill(ghost);
            double x = this.rectangle.getOrigin().x;
            double y = this.rectangle.getOrigin().y;
            double width = this.rectangle.getWidth();
            double height = this.rectangle.getHeight();
            double drawX = width >= 0 ? x : x + width;
            double drawY = height >= 0 ? y : y + height;
            double drawWidth = Math.abs(width);
            double drawHeight = Math.abs(height);

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

        // Draw all completed squares
        for (Square square : this.model.getSquares()) {
            g2d.setFill(square.getColor());
            double x = square.getOrigin().x;
            double y = square.getOrigin().y;
            double side = square.getWidth();
            double drawX = side >= 0 ? x : x + side;
            double drawY = side >= 0 ? y : y + side;
            double drawSide = Math.abs(side);
            g2d.fillRect(drawX, drawY, drawSide, drawSide);
        }

        // Draw the square currently being dragged (mid-construction feedback)
        if (this.square != null) {
            Color ghost = this.square.getColor().deriveColor(0, 1, 1, 0.3);
            g2d.setFill(ghost);

            double x = this.square.getOrigin().x;
            double y = this.square.getOrigin().y;
            double side = this.square.getWidth();
            double drawX = side >= 0 ? x : x + side;
            double drawY = side >= 0 ? y : y + side;
            double drawSide = Math.abs(side);

            g2d.fillRect(drawX, drawY, drawSide, drawSide);

            // Diagonal dashed lines for guidance
            g2d.setStroke(Color.LIGHTGRAY);
            g2d.setLineDashes(5); // dashed line
            g2d.strokeLine(drawX, drawY, drawX + drawSide, drawY + drawSide);
            g2d.strokeLine(drawX, drawY + drawSide, drawX + drawSide, drawY);
            g2d.setLineDashes(null); // reset to solid lines

            // Display top-left coordinates + side
            g2d.setFill(Color.BLACK);
            g2d.fillText(
                    String.format("(%.0f, %.0f) h & w: %.0f", drawX, drawY, drawSide),
                    drawX + 5, drawY - 5
            );

            g2d.setStroke(Color.BLACK); // reset stroke
        }

        // Draw all completed ovals
        for (Oval oval : model.getOvals()) {
            g2d.setFill(oval.getColor());
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
            Color ghost = currentOval.getColor().deriveColor(0, 1, 1, 0.3);
            g2d.setFill(ghost);
            double x = currentOval.getOrigin().x;
            double y = currentOval.getOrigin().y;
            double width = currentOval.getWidth();
            double height = currentOval.getHeight();
            double drawX = width >= 0 ? x : x + width;
            double drawY = height >= 0 ? y : y + height;
            double drawWidth = Math.abs(width);
            double drawHeight = Math.abs(height);
            g2d.fillOval(drawX, drawY, drawWidth, drawHeight);
            g2d.setStroke(currentOval.getColor().darker());
            g2d.strokeOval(drawX, drawY, drawWidth, drawHeight);
        }

        // Draw all completed triangles
        for (Triangle triangle : model.getTriangles()) {
            g2d.setFill(triangle.getColor());
            double x = triangle.getOrigin().x;
            double y = triangle.getOrigin().y;
            double width = triangle.getWidth();
            double height = triangle.getHeight();
            double drawX = width >= 0 ? x : x + width;
            double drawY = height >= 0 ? y : y + height;
            double drawWidth = Math.abs(width);
            double drawHeight = Math.abs(height);

            double[] xPoints, yPoints;
            if (width >= 0 && height >= 0) {
                xPoints = new double[]{drawX, drawX + drawWidth / 2, drawX + drawWidth};
                yPoints = new double[]{drawY + drawHeight, drawY, drawY + drawHeight};
            } else if (width < 0 && height >= 0) {
                xPoints = new double[]{drawX + drawWidth, drawX + drawWidth / 2, drawX};
                yPoints = new double[]{drawY + drawHeight, drawY, drawY + drawHeight};
            } else if (width >= 0 && height < 0) {
                xPoints = new double[]{drawX, drawX + drawWidth / 2, drawX + drawWidth};
                yPoints = new double[]{drawY, drawY + drawHeight, drawY};
            } else {
                xPoints = new double[]{drawX + drawWidth, drawX + drawWidth / 2, drawX};
                yPoints = new double[]{drawY, drawY + drawHeight, drawY};
            }

            g2d.fillPolygon(xPoints, yPoints, 3);
        }

        // Draw the triangle currently being dragged (mid-construction feedback)
        Triangle currentTriangle = model.getCurrentTriangle();
        if (currentTriangle != null) {
            Color ghost = currentTriangle.getColor().deriveColor(0, 1, 1, 0.3);
            g2d.setFill(ghost);

            double x = currentTriangle.getOrigin().x;
            double y = currentTriangle.getOrigin().y;
            double width = currentTriangle.getWidth();
            double height = currentTriangle.getHeight();
            double drawX = width >= 0 ? x : x + width;
            double drawY = height >= 0 ? y : y + height;
            double drawWidth = Math.abs(width);
            double drawHeight = Math.abs(height);

            double[] xPoints, yPoints;
            if (width >= 0 && height >= 0) {
                xPoints = new double[]{drawX, drawX + drawWidth / 2, drawX + drawWidth};
                yPoints = new double[]{drawY + drawHeight, drawY, drawY + drawHeight};
            } else if (width < 0 && height >= 0) {
                xPoints = new double[]{drawX + drawWidth, drawX + drawWidth / 2, drawX};
                yPoints = new double[]{drawY + drawHeight, drawY, drawY + drawHeight};
            } else if (width >= 0 && height < 0) {
                xPoints = new double[]{drawX, drawX + drawWidth / 2, drawX + drawWidth};
                yPoints = new double[]{drawY, drawY + drawHeight, drawY};
            } else {
                xPoints = new double[]{drawX + drawWidth, drawX + drawWidth / 2, drawX};
                yPoints = new double[]{drawY, drawY + drawHeight, drawY};
            }

            g2d.fillPolygon(xPoints, yPoints, 3);
            g2d.setStroke(currentTriangle.getColor().darker());
            g2d.strokePolygon(xPoints, yPoints, 3);
        }
    }
}

