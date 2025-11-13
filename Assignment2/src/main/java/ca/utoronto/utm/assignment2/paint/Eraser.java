package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Eraser extends Squiggle {
    /**
     * Constructs an eraser.
     *
     * @param thickness   the thickness of the eraser.
     */

    public Eraser(double thickness) {
        super(new Color(244/255f, 244/255f, 244/255f, 0), thickness*2);
    }

    public void draw(GraphicsContext g) {
        super.draw(g, 1);
    }
}
