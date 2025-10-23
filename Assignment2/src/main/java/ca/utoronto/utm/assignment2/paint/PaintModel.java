package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.Observable;

public class PaintModel extends Observable {
        private ArrayList<Point> points=new ArrayList<Point>();
        private ArrayList<Circle> circles=new ArrayList<Circle>();
        private ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
        private ArrayList<Squiggle> squiggles = new ArrayList<>();

        private Circle currentCircle;
        private Squiggle currentSquiggle;

        public void addPoint(Point p){
                this.points.add(p);
                this.setChanged();
                this.notifyObservers();
        }

        public ArrayList<Point> getPoints(){
                return points;
        }

    public void startSquiggle() {
        currentSquiggle = new Squiggle();
    }

    public void addPointToCurrentSquiggle(Point p) {
        if (currentSquiggle != null) {
            currentSquiggle.addPoint(p);
            notifyObserversOfChange();
        }
    }

    public void endSquiggle() {
        if (currentSquiggle != null) {
            squiggles.add(currentSquiggle);
            currentSquiggle = null;
            setChanged();
            notifyObservers();
        }
    }

    public ArrayList<Squiggle> getSquiggles() {
        return squiggles;
    }
        public void addCircle(Circle c){
                this.circles.add(c);
                this.setChanged();
                this.notifyObservers();
        }

        public ArrayList<Circle> getCircles(){
                return circles;
        }

    public Squiggle getCurrentSquiggle() {
            return currentSquiggle;
    }

    public Circle getCurrentCircle() {
        return currentCircle;
    }

    public void setCurrentCircle(Circle c) {
        this.currentCircle = c;
        notifyObserversOfChange();
    }

    public void clearCurrentCircle() {
        currentCircle = null;
        notifyObserversOfChange();

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

        /**
         * Notifies all observers that the model has changed for mid-construction shapes,
         * like when dragging a rectangle or circle, without adding it permanently to the model.
         * Allowing the view to provide live feedback (ghost shapes) as the user drags.
         */
        public void notifyObserversOfChange() {
            this.setChanged();
            this.notifyObservers();
        }
}
