package ca.utoronto.utm.assignment2.paint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class Circle implements Shape {
        private Point centre;
        private double radius;
        private Color color;
        private Color fillColor;
        private boolean filled;
        private double thickness;
        private boolean multiColor = false;

    public Circle(Point centre, int radius, Color borderColor, Color fillColor, boolean filled, double thickness) {
        this.centre = centre;
        this.radius = radius;
        this.color = borderColor;
        this.fillColor = fillColor;
        this.filled = filled;
        this.thickness = thickness;
        this.multiColor = (fillColor != null && !fillColor.equals(borderColor));
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

    public Point getCentre() {
        return centre;
    }

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

    public void setCentre(Point centre) {
            this.centre = centre;
    }

    public double getRadius() {
            return radius;
    }

    public void setRadius(double radius) {
            this.radius = radius;
    }

    public boolean isFilled() { return filled; }

    /**
     * Draws the circle.
     *
     * @param g
     * @param opacity
     *
     * @author Anas H. | habiban4
     *
     */
    @Override
    public void draw(GraphicsContext g, double opacity) {
            double x = getCentre().x - getRadius();
            double y = getCentre().y - getRadius();
            double diameter = getRadius() * 2;

            Color borderDrawColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
            Color fillDrawColor = multiColor ? new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), opacity) : borderDrawColor;
            double t = this.getThickness();

            if (filled) {
                    g.setFill(fillDrawColor);
                    g.fillOval(x, y, diameter, diameter);
            }  if (thickness > 0 && (multiColor || ! filled)) {
                    g.setStroke(borderDrawColor);
                    g.setLineWidth(t);
                    g.strokeOval(x, y, diameter, diameter);
            }
    }

    @Override
    public void handleDrag(MouseEvent e) {
            Point centre = this.getCentre();
            double dx = e.getX() - centre.x;
            double dy = e.getY() - centre.y;
            this.setRadius(Math.sqrt(dx * dx + dy * dy));
    }

    @Override
    public void handleRelease(PaintModel model) {
            model.saveState();
            model.addShape(this);
            model.clearCurrentShape();
            System.out.println("Added Circle");
    }

    @Override
    public Shape clone() {
        Circle c = new Circle(new Point(centre.x, centre.y), (int) radius,  Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()),
                fillColor != null ? Color.color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), fillColor.getOpacity()):
                        Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()), filled, thickness);
        if (multiColor) {
            c.startMultiColor(Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()),
                    fillColor != null ? Color.color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), fillColor.getOpacity()) :
                            Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()));
        }
        return c;
    }

}
