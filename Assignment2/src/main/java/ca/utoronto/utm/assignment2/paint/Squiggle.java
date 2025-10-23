package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

public class Squiggle {
    private ArrayList<Point> points = new ArrayList<>();

    public void addPoint(Point p) {
        points.add(p);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }
}