package ca.utoronto.utm.assignment2.paint;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
//Need to figure out how to highlight buttons for bug
public class ShapeChooserPanel extends GridPane implements EventHandler<ActionEvent> {

        private View view;
        private List<Button> buttons = new ArrayList<>(); // store buttons
        private Button selectedButton = null; // track button currently selected

        public ShapeChooserPanel(View view) {

                this.view = view;

                String[] buttonLabels = { "Circle", "Rectangle", "Square", "Squiggle", "Polyline" };

                int row = 0;
                for (String label : buttonLabels) {
                        Button button = new Button(label);
                        button.setMinWidth(100);
                        this.add(button, 0, row);
                        row++;
                        button.setOnAction(this);
                }
        }

        @Override
        public void handle(ActionEvent event) {
            Button clickedButton = (Button) event.getSource();
            String command = clickedButton.getText();
            view.setMode(command);
            System.out.println(command);

            if (selectedButton != null) {
                selectedButton.setStyle(""); // clears the style
            }

            clickedButton.setStyle(
                    "-fx-background-color: grey; " +            // button fill
                            "-fx-font-weight: bold; " +         // bold text
                            "-fx-text-fill: white; " +          // text color
                            "-fx-border-color: black; " +       // black outline
                            "-fx-border-width: 2px; " +         // border thickness
                            "-fx-border-radius: 5px; " +        // rounded corners
                            "-fx-background-radius: 5px;"       // background match border radius
            );

            selectedButton = clickedButton;
        }

}


