package ca.utoronto.utm.assignment2.paint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle implements Shape {
    private Point origin;
    private double width;
    private double height;
    private Color color; // new field
    private boolean filled;

    public Triangle(Point origin, double width, double height,  Color color, boolean filled) {
        this.origin = origin;
        this.width = width;
        this.height = height;
        this.color = color; // default
        this.filled = filled;
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

    public boolean isFilled() { return filled; }

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

        if (filled) {
            g.setFill(drawColor);
            g.fillPolygon(xPoints, yPoints, 3);
        } else {
            g.setStroke(drawColor);
            g.strokePolygon(xPoints, yPoints, 3);
        }
    }
}

