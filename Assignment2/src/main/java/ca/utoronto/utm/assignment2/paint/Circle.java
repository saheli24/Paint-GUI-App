package ca.utoronto.utm.assignment2.paint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class Circle implements Shape {
        private Point centre;
        private double radius;
        private Color color;
        private boolean filled;
        private double thickness;

        public Circle(Point centre, int radius, Color color, boolean filled, double thickness) {
                this.centre = centre;
                this.radius = radius;
                this.color = color;
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

                Color drawColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
                double t = this.getThickness();

                if (filled) {
                        g.setFill(drawColor);
                        g.fillOval(x, y, diameter, diameter);
                } else {
                        g.setStroke(drawColor);
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
            return new Circle(
                    new Point(centre.x, centre.y), // make a new Point copy
                    (int) radius,
                    Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()),
                    filled,
                    thickness
            );
        }
}
