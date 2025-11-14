package ca.utoronto.utm.assignment2.paint;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.List;

public class FontChooserPanel extends HBox {
    private View view;
    private List<Button> buttons = new ArrayList<>();
    private String font = "Ubuntu";
    private Button selectedButton = null;

    public FontChooserPanel (View view) {
        this.view = view;
        this.setPadding(new Insets(10));
        String[] fonts = {"Ubuntu", "Ubuntu Light", "Ubuntu Condensed", "Ubuntu Mono", "Ubuntu Thin"};
        for (String font: fonts) {
            Button button = new Button(font);
            button.setMinSize(80, 80);
            button.setOnAction(e -> setFont(button));
            buttons.add(button);
            this.getChildren().add(button);
        }
    }

    private String setFont(Button button) {
        String newFont = button.getText();
        font = newFont;

        this.view.getModel().setCurrentFont(font);


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
        return newFont;
    }
}
