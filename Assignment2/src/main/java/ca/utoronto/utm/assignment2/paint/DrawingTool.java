package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public interface DrawingTool {
    void pressed(MouseEvent e);
    void dragged(MouseEvent e);
    void released(MouseEvent e);

    default void updateMousePoint(MouseEvent e) {}
    default void discardGhost(PaintModel model) {}
    default void resumeAfterUndoRedo() {}
    default void drawFeedback(GraphicsContext g) {}
}
