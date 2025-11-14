package ca.utoronto.utm.assignment2.paint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Represents a geometric square that can be drawn on the canvas.
 * Stores its origin point, width, and height. Both the width and
 * height are the same values. A square is a shape.
 *
 * @author Anas H. | habiban4
 *
 */
public class Square implements Shape {
    private Point origin;
    private double width;
    private double height;
    private boolean filled;
    private Color color;
    private Color fillColor;
    private double thickness;
    private Point startPoint;
    private boolean multiColor = false;


    /**
     * Constructs a new Square with the specified origin and side
     *
     * @param origin starting (x, y) of the square
     * @param side   the width and height of the square
     */
    public Square(Point origin, double side, Color borderColor, Color fillColor, boolean filled, double thickness) {
        this.origin = origin;
        this.width = side;
        this.height = side;
        this.color = borderColor;
        this.fillColor = fillColor;
        this.filled = filled;
        this.thickness = thickness;
        this.multiColor = (fillColor != null && ! fillColor.equals(borderColor));
    }

    /**
     * Sets the color of this object.
     *
     * @param color the new Color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns the current color of this object.
     *
     * @return the Color of this object
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Returns the origin of the following square
     *
     * @return origin
     *
     */
    public Point getOrigin() {
        return origin;
    }

    /**
     * Sets a new origin for the following square
     *
     * @param origin the new origin value of the square
     */
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    /**
     * Returns the width of the following square
     *
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the new width of the following square. The height is
     * also set to be the same value as the width.
     *
     * @param width the new width value of the Square in pixels
     *
     */
    public void setWidth(double width) {
        this.width = width;
        this.height = width;
    }

    /**
     * Returns the height of the following square
     *
     * @return width
     *
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Sets the height of this square. The width is also set
     * to be the same value of the height.
     *
     * @param height the new height value of the square in pixels
     */
    public void setHeight(double height) {
        this.height = height;
        this.width = height;
    }

    public Point getStartPoint() {return this.startPoint;}

    public void setStartPoint(Point p) {this.startPoint = p;}

    public boolean isFilled() { return filled; }

    /**
     * Returns the line thickness of this object or its outline.
     *
     * @return the line thickness of this object or its outline
     */
    public double getThickness() {return this.thickness;}


    /**
     * Sets the thickness of this object or its line thickness.
     *
     * @param thickness the new thickness to set
     */
    public void setThickness(double thickness) {this.thickness = thickness;}

    public void startMultiColor(Color borderColor, Color fillColor) {
        this.color = borderColor;
        this.fillColor = fillColor;
        this.multiColor = true;
    }

    public void stopMultiColor() {
        this.fillColor = this.color;
        this.multiColor = false;
    }

    public boolean isMultiColor() {
        return multiColor;
    }

    /**
     * Draws a Square.
     *
     * @param g
     * @param opacity
     *
     * @author Anas H. | habiban4
     *
     */
    public void draw(GraphicsContext g, double opacity) {
        if (color == null) {
            color = Color.BLACK;
        }
        if (fillColor == null) {
            fillColor = color;
        }
        double x = getOrigin().x;
        double y = getOrigin().y;
        double side = getWidth();

        double drawX = side >= 0 ? x : x + side;
        double drawY = side >= 0 ? y : y + side;
        double drawSide = Math.abs(side);

        Color borderDrawColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
        Color fillDrawColor = multiColor ? new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), opacity): borderDrawColor;
        double t = this.getThickness();

        if (filled) {
            g.setFill(fillDrawColor);
            g.fillRect(drawX, drawY, drawSide, drawSide);
        } if (thickness > 0 && (multiColor || !filled)) {
            g.setStroke(borderDrawColor);
            g.setLineWidth(t);
            g.strokeRect(drawX, drawY, drawSide, drawSide);
        }
    }

    @Override
    public void handleDrag(MouseEvent e) {
        Point origin = this.getOrigin();

        double dx = e.getX() - origin.x;
        double dy = e.getY() - origin.y;

        double side = Math.min(Math.abs(dx), Math.abs(dy));

        double newX = dx >= 0 ? origin.x : origin.x - side;
        double newY = dy >= 0 ? origin.y : origin.y - side;

        this.setOrigin(new Point(newX, newY));
        this.setWidth(side);
        this.setHeight(side);
    }

    @Override
    public void handleRelease(PaintModel model) {
        model.saveState();
        model.addShape(this);
        model.clearCurrentShape();
        System.out.println("Added Square");
    }

    @Override
    public Shape clone() {
        Square s = new Square(new Point(origin.x, origin.y), width, Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()),
                fillColor != null ? Color.color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), fillColor.getOpacity()) :
                        Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()), filled, thickness);
        if (multiColor) {
            s.startMultiColor(
                    Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()),
                    fillColor != null ? Color.color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), fillColor.getOpacity()) :
                            Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()));
        }
        return s;
        }
}