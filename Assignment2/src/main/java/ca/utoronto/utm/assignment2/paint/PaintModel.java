package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.Observable;

public class PaintModel extends Observable {
        private ArrayList<Point> points=new ArrayList<Point>();
        private ArrayList<Circle> circles=new ArrayList<Circle>();
        private ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
        private ArrayList<Square> squares = new ArrayList<>();
        private ArrayList<Squiggle> squiggles = new ArrayList<>();


        private ArrayList<Oval> ovals = new ArrayList<>();
        private Oval currentOval;

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
         * Adds a square to the list of displayed squares.
         * Then notifies the rest of the observers.
         *
         * @param square the square to be added to the list of models
         *
         */
        public void addSquare(Square square) {
            squares.add(square);
            this.setChanged();
            this.notifyObservers();
        }

        /**
         * Returns the ArrayList of squares
         *
         * @return squares
         *
         */
        public ArrayList<Square> getSquares() {
            return squares;
        }


        /**
         * Adds a completed Oval to the list of drawn ovals and updates observers.
         *
         * @param oval the Oval object to be added to the canvas
         */
        public void addOval(Oval oval) {
            ovals.add(oval);
            notifyObserversOfChange();
        }

        /**
         * Sets the currently active Oval that is being drawn and updates observers.
         *
         * @param oval the Oval currently being drawn
         */
        public void setCurrentOval(Oval oval) {
            this.currentOval = oval;
            notifyObserversOfChange();
        }

        /**
         * Returns the Oval currently being drawn on the canvas.
         *
         * @return the current Oval, or null if none is being drawn
         */
        public Oval getCurrentOval() {
            return currentOval;
        }

        /**
         * Clears the reference to the current Oval when drawing is complete.
         */
        public void clearCurrentOval() {
            this.currentOval = null;
        }

        /**
         * Returns a list of all completed Ovals drawn on the canvas.
         *
         * @return a list of all Ovals
         */
        public ArrayList<Oval> getOvals() {
            return ovals;
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
