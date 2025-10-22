package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.Observable;

public class PaintModel extends Observable {
        private ArrayList<Point> points=new ArrayList<Point>();
        private ArrayList<Circle> circles=new ArrayList<Circle>();
        private ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();

        public void addPoint(Point p){
                this.points.add(p);
                this.setChanged();
                this.notifyObservers();
        }

        public ArrayList<Point> getPoints(){
                return points;
        }

        public void addCircle(Circle c){
                this.circles.add(c);
                this.setChanged();
                this.notifyObservers();
        }

        public ArrayList<Circle> getCircles(){
                return circles;
        }

        /**
         * Adds a Rectangle to the list of displayed Rectangles.
         * Then notifies the rest of the observers.
         *
         * @param rectangle the rectangle to be added to the list of models
         *
         */
        public void addRectangle(Rectangle rectangle) {
                rectangles.add(rectangle);
                this.setChanged();
                this.notifyObservers();
        }

        /**
         * Returns the ArrayList of Rectangles
         *
         * @return rectangles
         *
         */
        public ArrayList<Rectangle> getRectangles(){
                return rectangles;
        }
}
