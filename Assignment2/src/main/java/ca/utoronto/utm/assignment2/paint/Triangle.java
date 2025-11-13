package ca.utoronto.utm.assignment2.paint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Triangle implements Shape {
    private Point origin;
    private double width;
    private double height;
    private Color color; // new field
    private boolean filled;
    private double thickness;

    public Triangle(Point origin, double width, double height,  Color color, boolean filled, double thickness) {
        this.origin = origin;
        this.width = width;
        this.height = height;
        this.color = color; // default
        this.filled = filled;
        this.thickness = thickness;
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

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isFilled() {return filled;}

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

    /**
     * Draws a triangle.
     *
     * @param g
     * @param opacity
     *
     * @author Anas H. | habiban4
     *
     */
    public void draw(GraphicsContext g, double opacity) {
        double drawX = width >= 0 ? origin.x : origin.x + width;
        double drawY = height >= 0 ? origin.y : origin.y + height;
        double drawWidth = Math.abs(width);
        double drawHeight = Math.abs(height);

        double[] xPoints;
        double[] yPoints;

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

        Color drawColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
        double t = this.getThickness();

        if (filled) {
            g.setFill(drawColor);
            g.fillPolygon(xPoints, yPoints, 3);
        } else {
            g.setStroke(drawColor);
            g.setLineWidth(t);
            g.strokePolygon(xPoints, yPoints, 3);
        }
    }

    @Override
    public void handleDrag(MouseEvent e) {
        double startX = this.getOrigin().x;
        double startY = this.getOrigin().y;
        double currX = e.getX();
        double currY = e.getY();

        double width = currX - startX;
        double height = currY - startY;

        this.setWidth(width);
        this.setHeight(height);
    }

    @Override
    public void handleRelease(PaintModel model) {
        model.saveState();
        model.addShape(this);
        model.clearCurrentShape();
        System.out.println("Added Triangle");
    }


    @Override
    public Shape clone() {
        return new Triangle(
                new Point(origin.x, origin.y), // deep copy of the origin point
                width,
                height,
                Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()),
                filled,
                thickness
        );
    }

}

