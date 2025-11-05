package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public interface DrawingTool {
    void pressed(MouseEvent e);
    void dragged(MouseEvent e);
    void released(MouseEvent e);
}
