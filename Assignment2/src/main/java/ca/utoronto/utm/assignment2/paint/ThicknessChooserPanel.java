package ca.utoronto.utm.assignment2.paint;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;

/**
 * Lets the user choose the thickness of a line or the outline of a shape while drawing.
 *
 * @author irfanda3 | Danial Irfan
 */
public class ThicknessChooserPanel extends GridPane {
    private View view;
    private List<Button> buttons = new ArrayList<>();
    private double currentThickness = 1;
    private Button selectedButton = null;


    public ThicknessChooserPanel(View view) {
        this.view = view;

        this.setHgap(10); // horizontal space between buttons
        this.setVgap(10); // vertical space between rows
        this.setPadding(new Insets(10));

        double[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int col = 0;
        int row = 0;

        for (double value : values) {
            Button button = new Button(String.valueOf(value));
            button.setMinSize(40, 30);
            button.setMaxSize(40, 30);
            button.setOnAction(e -> setValue(button));

            buttons.add(button);
            this.add(button, col, row); // add to grid (col, row)

            col++;
            if (col == 2) { // move to next row after 2 buttons
                col = 0;
                row++;
            }
        }
    }

    private double setValue(Button button) {
        currentThickness = Double.parseDouble(button.getText());
        this.view.getModel().setCurrentThickness(currentThickness);

        if (selectedButton != null) {
            selectedButton.setStyle("");  // clear previous style
        }

        button.setStyle(
                "-fx-background-color: grey;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 5px;" +
                        "-fx-background-radius: 5px;"
        );

        selectedButton = button;
        return currentThickness;
    }
}
