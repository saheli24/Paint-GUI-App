package ca.utoronto.utm.assignment2.paint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;


/**
 * Represents a drawable shape.
 * All shapes must implement a draw method that accepts a GraphicsContext object and opacity.
 *
 * @author Anas H. | habiban4
 *
*/
public interface Shape {

    /**
     * Draws this shape on the given GraphicsContext.
     *
     * @param g the GraphicsContext to draw on
     * @param opacity the opacity to draw this shape with
     *
     * @author Anas H. | habiban4
     *
     */
    void draw(GraphicsContext g, double opacity);

    void handleDrag(MouseEvent e);

    void handleRelease(PaintModel model);

    Shape clone();
}
