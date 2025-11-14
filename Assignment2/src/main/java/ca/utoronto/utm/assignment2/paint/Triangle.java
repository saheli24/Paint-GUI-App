package ca.utoronto.utm.assignment2.paint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Triangle implements Shape {
    private Point origin;
    private double width;
    private double height;
    private Color color;
    private Color fillColor;
    private boolean filled;
    private double thickness;
    private boolean multiColor = false;

    public Triangle(Point origin, double width, double height,  Color borderColor, Color fillColor, boolean filled, double thickness) {
        this.origin = origin;
        this.width = width;
        this.height = height;
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

        Color borderDrawColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
        Color fillDrawColor = multiColor ? new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), opacity): borderDrawColor;
        double t = this.getThickness();

        if (filled) {
            g.setFill(fillDrawColor);
            g.fillPolygon(xPoints, yPoints, 3);
        }  if (thickness > 0 && (multiColor || ! filled)) {
            g.setStroke(borderDrawColor);
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
        Triangle t = new Triangle(new Point(origin.x, origin.y), width, height,
                Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()),
                fillColor != null ? Color.color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), fillColor.getOpacity()) :
                        Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()), filled, thickness);
        if (multiColor) {
            t.startMultiColor(
                    Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()),
                    fillColor != null ? Color.color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), fillColor.getOpacity()) :
                            Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity())
            );
        }
        return t;
    }

}

