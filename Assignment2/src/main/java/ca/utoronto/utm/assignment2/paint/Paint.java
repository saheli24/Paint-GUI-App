package ca.utoronto.utm.assignment2.paint;


import javafx.application.Application;
import javafx.stage.Stage;
// 1) While the mouse is being dragged, dynamically draw a temporary rectangle from the initial click point to the current mouse position (mid-construction view).
// 2) On mouse release, finalize the rectangle by adding it to the canvas or shapes list.
// Required to implement a Rectangle class in order to achieve US1.004
// Classes I need to implement/use in order to complete: Rectangle Class, Paint Model, Paint Panel, Point
public class Paint extends Application {

        PaintModel model; // Model
        View view; // View + Controller

        public static void main(String[] args) {
                launch(args);
        }

        @Override
        public void start(Stage stage) throws Exception {

                this.model = new PaintModel();

                // View + Controller
                this.view = new View(model, stage);
        }
}
