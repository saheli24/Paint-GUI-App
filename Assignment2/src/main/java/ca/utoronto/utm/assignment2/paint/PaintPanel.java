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
    private DrawingTool currentTool = new CircleTool();
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
            model.setCurrentCircle(c);
        }

        @Override
        public void dragged(MouseEvent e) {
            Circle c = model.getCurrentCircle();
            if(c != null) {
                Point centre = c.getCentre();
                double dx = e.getX() - centre.x;
                double dy = e.getY() - centre.y;
                double radius = Math.sqrt(dx * dx + dy * dy);
                c.setRadius(radius);
                model.notifyObserversOfChange();
            }
        }

        @Override
        public void released(MouseEvent e) {
            Circle c = model.getCurrentCircle();
            if(c != null){
                model.addCircle(c);
                System.out.println("Added Circle");
                model.clearCurrentCircle();
            }
        }
    }

    public class RectangleTool implements DrawingTool {

        @Override
        public void pressed(MouseEvent e) {
            System.out.println("Started Rectangle");

            // creates a Point for the original (x, y) at the mouse location
            Point origin = new Point(e.getX(), e.getY());
            Color shapeColor = model.getCurrentColor() != null ? model.getCurrentColor() : Color.BLACK;

            // creates a Rectangle with the origin, and a height and width of 0
            rectangle = new Rectangle(origin, 0, 0, shapeColor, model.ifFillStyle(), model.getCurrentThickness());
        }

        @Override
        public void dragged(MouseEvent e) {
            if (rectangle != null) {
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
        }

        @Override
        public void released(MouseEvent e) {
            if (rectangle != null) {
                // add the Rectangle to the list of Rectangles in the Model
                model.addRectangle(rectangle);
                System.out.println("Added Rectangle");
                rectangle = null;
            }
        }
    }

    public class SquareTool implements DrawingTool {

        @Override
        public void pressed(MouseEvent e) {
            System.out.println("Started Square");

            // original press point
            squareStart = new Point(e.getX(), e.getY());

            Color shapeColor = model.getCurrentColor() != null ? model.getCurrentColor() : Color.BLACK;

            // create a new square at that origin with size 0
            square = new Square(new Point(squareStart.x, squareStart.y), 0, shapeColor, model.ifFillStyle(), model.getCurrentThickness());
        }

        @Override
        public void dragged(MouseEvent e) {
            if (square != null) {

                double currentX = e.getX();
                double currentY = e.getY();

                // distance dragged
                double dx = currentX - squareStart.x;
                double dy = currentY - squareStart.y;

                // side length = min of dx, dy
                double side = Math.min(Math.abs(dx), Math.abs(dy));

                // adjust origin for top/left drag
                double newX = dx >= 0 ? squareStart.x : squareStart.x - side;
                double newY = dy >= 0 ? squareStart.y : squareStart.y - side;

                // update square
                square.setOrigin(new Point(newX, newY));
                square.setWidth(side);

                // redraw
                model.notifyObserversOfChange();
            }
        }

        @Override
        public void released(MouseEvent e) {
            if (square != null) {
                model.addSquare(square);
                System.out.println("Added Square");
                square = null;
            }
        }
    }

    public class SquiggleTool implements DrawingTool {

        @Override
        public void pressed(MouseEvent e) {
            model.startSquiggle();
        }

        @Override
        public void dragged(MouseEvent e) {
            model.addPointToCurrentSquiggle(new Point(e.getX(), e.getY()));
        }

        @Override
        public void released(MouseEvent e) {
            model.endSquiggle();
        }
    }

    public class PolylineTool implements DrawingTool {

        @Override
        public void pressed(MouseEvent e) {

        }

        @Override
        public void dragged(MouseEvent e) {

        }

        @Override
        public void released(MouseEvent e) {

        }
    }

    public class OvalTool implements DrawingTool {

        @Override
        public void pressed(MouseEvent e) {
            System.out.println("Started Oval");
            Point origin = new Point(e.getX(), e.getY());
            Color shapeColor = model.getCurrentColor() != null ? model.getCurrentColor() : Color.BLACK;
            Oval oval = new Oval(origin, 0, 0, shapeColor, model.ifFillStyle(), model.getCurrentThickness());
            model.setCurrentOval(oval);
        }

        @Override
        public void dragged(MouseEvent e) {
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
        public void released(MouseEvent e) {
            Oval oval = model.getCurrentOval();
            if (oval != null) {
                model.addOval(oval);
                model.clearCurrentOval();
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
            Triangle t = new Triangle(start, 0, 0,  shapeColor, model.ifFillStyle(), model.getCurrentThickness());
            model.setCurrentTriangle(t);
        }

        @Override
        public void dragged(MouseEvent e) {
            Triangle t = model.getCurrentTriangle();
            if (t != null) {
                double startX = t.getOrigin().x;
                double startY = t.getOrigin().y;
                double currX = e.getX();
                double currY = e.getY();
                double width = currX - startX;
                double height = currY - startY;

                // Keep the width and height signed if you want to allow dragging in all directions
                t.setWidth(width);
                t.setHeight(height);

                model.notifyObserversOfChange();
            }
        }

        @Override
        public void released(MouseEvent e) {
            Triangle t = model.getCurrentTriangle();
            if (t != null) {
                model.addTriangle(t);
                model.clearCurrentTriangle();
                System.out.println("Added Triangle");
            }
        }
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        // Later when we learn about inner classes...
        // https://docs.oracle.com/javafx/2/events/DraggablePanelsExample.java.htm


        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

                if(mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
                     currentTool.pressed(mouseEvent);
                } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
                     currentTool.dragged(mouseEvent);
                } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
                    currentTool.released(mouseEvent);
                }


    }
    @Override
    public void update(Observable o, Object arg) {

                GraphicsContext g2d = this.getGraphicsContext2D();
                g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
                // Draw Lines
                g2d.setStroke(Color.BLACK);
                boolean isFilled = model.ifFillStyle();
                for (Squiggle squiggle : model.getSquiggles()) {
                    ArrayList<Point> points = squiggle.getPoints();
                    g2d.setStroke(squiggle.getColor()); // use squiggle's color
                    g2d.setLineWidth(squiggle.getThickness());
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
                    Color c = current_s.getColor() != null ? current_s.getColor() : Color.BLACK;
                    g2d.setStroke(new Color(c.getRed(), c.getGreen(), c.getBlue(), 1)); // semi-transparent
                    g2d.setLineWidth(current_s.getThickness());
                    for (int i = 0; i < pts.size() - 1; i++) {
                        Point p1 = pts.get(i);
                        Point p2 = pts.get(i + 1);
                        g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
                    }
                }

                // Draw Circles
                ArrayList<Circle> circles = this.model.getCircles();

            for (Circle c : model.getCircles()) {
                double x = c.getCentre().x - c.getRadius();
                double y = c.getCentre().y - c.getRadius();
                double diameter = c.getRadius() * 2;

                if (c.isFilled()) {
                    g2d.setFill(c.getColor());
                    g2d.fillOval(x, y, diameter, diameter);
                } else {
                    g2d.setStroke(c.getColor());
                    g2d.setLineWidth(c.getThickness());
                    g2d.strokeOval(x, y, diameter, diameter);
                }
            }

                Circle current = model.getCurrentCircle();
                if(current != null) {
                    double x = current.getCentre().x - current.getRadius();
                    double y = current.getCentre().y - current.getRadius();
                    double diameter = current.getRadius() * 2;
                    // Get the circle's color from the model
                    Color c = current.getColor();
                    double t = current.getThickness();

                    if (isFilled) {
                        g2d.setFill(new Color(c.getRed(), c.getGreen(), c.getBlue(), 0.3));
                        g2d.setLineWidth(t);
                        g2d.fillOval(x, y, diameter, diameter);
                    } else {
                        g2d.setStroke(c);
                        g2d.setLineWidth(t);
                        g2d.strokeOval(x, y, diameter, diameter);
                    }
                }

                // list of all following rectangles  to be drawn
                ArrayList<Rectangle> rectangles = this.model.getRectangles();

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

                    if (rectangle.isFilled()) {
                        g2d.setFill(rectangle.getColor());
                        g2d.fillRect(drawX, drawY, drawWidth, drawHeight);
                    } else {
                        g2d.setStroke(rectangle.getColor());
                        g2d.setLineWidth(rectangle.getThickness());
                        g2d.strokeRect(drawX, drawY, drawWidth, drawHeight);
                    }
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

                    Color c = this.rectangle.getColor(); // rectangle currently being dragged
                    double t = this.rectangle.getThickness();
                    if (isFilled) {
                        g2d.setFill(new Color(c.getRed(), c.getGreen(), c.getBlue(), 0.3));
                        g2d.fillRect(drawX, drawY, drawWidth, drawHeight);
                    } else {
                        g2d.setStroke(c);
                        g2d.setLineWidth(t);
                        g2d.strokeRect(drawX, drawY, drawWidth, drawHeight);
                    }

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


                for (Square square : this.model.getSquares()) {
                    double x = square.getOrigin().x;
                    double y = square.getOrigin().y;
                    double side = square.getWidth();

                    double drawX = side >= 0 ? x : x + side;
                    double drawY = side >= 0 ? y : y + side;
                    double drawSide = Math.abs(side);

                    if (square.isFilled()) {
                        g2d.setFill(square.getColor());
                        g2d.fillRect(drawX, drawY, drawSide, drawSide);
                    } else {
                        g2d.setStroke(square.getColor());
                        g2d.setLineWidth(square.getThickness());
                        g2d.strokeRect(drawX, drawY, drawSide, drawSide);
                    }
                }

                if (this.square != null) {
                    double x = this.square.getOrigin().x;
                    double y = this.square.getOrigin().y;
                    double side = this.square.getWidth();

                    // adjust origin for top/left drag
                    double drawX = side >= 0 ? x : x + side;
                    double drawY = side >= 0 ? y : y + side;
                    double drawSide = Math.abs(side);

                    // Semi-transparent fill for live square
                    Color c = this.square.getColor();
                    double t = this.square.getThickness();
                    if (isFilled) {
                        g2d.setFill(new Color(c.getRed(), c.getGreen(), c.getBlue(), 0.3));
                        g2d.fillRect(drawX, drawY, drawSide, drawSide);
                    } else {
                        g2d.setStroke(c);
                        g2d.setLineWidth(t);
                        g2d.strokeRect(drawX, drawY, drawSide, drawSide);
                    }


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



        // Draw finalized ovals
                for (Oval oval : model.getOvals()) {
                    double x = oval.getOrigin().x;
                    double y = oval.getOrigin().y;
                    double width = oval.getWidth();
                    double height = oval.getHeight();

                    double drawX = width >= 0 ? x : x + width;
                    double drawY = height >= 0 ? y : y + height;
                    double drawWidth = Math.abs(width);
                    double drawHeight = Math.abs(height);
                    Color c = oval.getColor(); // use the oval's color
                    if (oval.isFilled()) {
                        g2d.setFill(oval.getColor());
                        g2d.fillOval(drawX, drawY, drawWidth, drawHeight);
                    } else {
                        g2d.setStroke(oval.getColor());
                        g2d.setLineWidth(oval.getThickness());
                        g2d.strokeOval(drawX, drawY, drawWidth, drawHeight);
                    }
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

                    Color c = currentOval.getColor();
                    double t = currentOval.getThickness();
                    if (isFilled) {
                        g2d.setFill(new Color(c.getRed(), c.getGreen(), c.getBlue(), 0.3));
                        g2d.fillOval(drawX, drawY, drawWidth, drawHeight);
                    } else {
                        g2d.setStroke(c);
                        g2d.setLineWidth(t);
                        g2d.strokeOval(drawX, drawY, drawWidth, drawHeight);
                    }
                }


                g2d.setFill(Color.PURPLE);
                for (Triangle triangle : model.getTriangles()) {
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

                    if (triangle.isFilled()) {
                        g2d.setFill(triangle.getColor());
                        g2d.fillPolygon(xPoints, yPoints, 3);
                    } else {
                        g2d.setStroke(triangle.getColor());
                        g2d.setLineWidth(triangle.getThickness());
                        g2d.strokePolygon(xPoints, yPoints, 3);
                    }
                }

                Triangle currentTriangle = model.getCurrentTriangle();
                if (currentTriangle != null) {
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

                    Color c = currentTriangle.getColor() != null ? currentTriangle.getColor() : Color.PURPLE;
                    double t = currentTriangle.getThickness();
                    if (isFilled) {
                        g2d.setFill(new Color(currentTriangle.getColor().getRed(),
                                currentTriangle.getColor().getGreen(),
                                currentTriangle.getColor().getBlue(), 0.3));
                        g2d.fillPolygon(xPoints, yPoints, 3);
                    } else {
                        g2d.setStroke(currentTriangle.getColor());
                        g2d.setLineWidth(t);
                        g2d.strokePolygon(xPoints, yPoints, 3);
                    }
                }
    }
}

