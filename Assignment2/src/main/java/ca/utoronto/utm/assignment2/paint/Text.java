package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.awt.*;

public class Text implements Shape {
    private Point origin;
    private Font font;
    private String string;

    public Text(Point origin, Font font, String string) {
        this.origin = origin;
        this.font = font;
        this.string = string;

    }
    @Override
    public void draw(GraphicsContext g, double opacity) {
        g.setFont(font);
        g.fillText(string, origin.x, origin.y);
    }

    @Override
    public void handleDrag(MouseEvent e) {

    }

    @Override
    public void handleRelease(PaintModel model) {
        model.saveState();
        model.addShape(this);
        model.clearCurrentShape();
    }

    @Override
    public Shape clone() {
        return null;
    }
}
