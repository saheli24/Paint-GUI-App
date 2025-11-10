package ca.utoronto.utm.assignment2.paint;
import javafx.scene.paint.Color;


public class Circle {
        private Point centre;
        private double radius;
        private Color color; // new field
        private double thickness; // new field

        public Circle(Point centre, int radius, Color color, double thickness){
                this.centre = centre;
                this.radius = radius;
                this.color = color; // default
                this.thickness = thickness; // default value

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
        * Sets the thickness of this object's outline.
        *
        * @param thickness the new outline thickness.
        */

        public void setThickness(double thickness) {
            this.thickness = thickness;
        }

        /**
        * Sets the thickness of this object's outline.
        *
        * @return the outline thickness.
        */

        public double getThickness() {
                return this.thickness;
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
