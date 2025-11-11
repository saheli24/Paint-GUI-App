package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * A panel that allows the user to choose the thickness of lines, such as the thickness
 * of a squiggly line or the thickness of the outline of a shape.
 *
 * @author Danial Irfan | irfanda3
 */


public class LineThicknessChooserPanel extends HBox {


    private View view;
    private List<Button> buttons = new ArrayList<>();
    private double thickness = 1;


    public LineThicknessChooserPanel (View view) {


        this.view = view;
        this.setSpacing(5);
        this.setPadding(new Insets(5));


        double[] ThicknessValues = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};


        for (double value: ThicknessValues) {
            // creates buttons
            Button button = new Button(String.valueOf(value));
            button.setMinSize(50, 30);
            button.setMaxSize(20, 20);
            button.setOnAction(e -> setThickness(button));
            buttons.add(button);
            this.getChildren().add(button);
        }


    }

    public void setThickness(Button button) {
        this.thickness = Double.parseDouble(button.getText());
        view.getPaintModel().setCurrentThickness(this.thickness);
    }

    public double getThickness() {
        return this.thickness;
    }


}
