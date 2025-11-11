package ca.utoronto.utm.assignment2.paint;
import javafx.scene.paint.Color;


public class Circle {
        private Point centre;
        private double radius;
        private Color color; // new field
        private boolean filled;
        private double thickness;

        public Circle(Point centre, int radius, Color color, boolean filled, double thickness){
                this.centre = centre;
                this.radius = radius;
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

        public double getThickness() {return this.thickness;}

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
}
