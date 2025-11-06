package ca.utoronto.utm.assignment2.paint;
import javafx.scene.paint.Color;


public class Circle {
        private Point centre;
        private double radius;
        private Color color; // new field

        public Circle(Point centre, int radius, Color color){
                this.centre = centre;
                this.radius = radius;
                this.color = color; // default

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

}
