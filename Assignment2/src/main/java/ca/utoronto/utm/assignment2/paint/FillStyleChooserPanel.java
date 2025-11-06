package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class FillStyleChooserPanel extends GridPane implements EventHandler<ActionEvent> {
    private View view;
    private Button selectedButton = null;

    public FillStyleChooserPanel(View view) {
        this.view = view;

        Button solidButton = new Button("Solid");
        solidButton.setUserData("solid");
        solidButton.setMinWidth(100);
        solidButton.setOnAction(this);
        this.add(solidButton, 0, 0);

        Button outlineButton = new Button("Outline");
        outlineButton.setUserData("outline");
        outlineButton.setMinWidth(100);
        outlineButton.setOnAction(this);
        this.add(outlineButton, 0, 1);
    }

    @Override
    public void handle(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String style = (String) clickedButton.getUserData();

        view.getModel().setCurrentFillStyle(style);
        System.out.println("Fill style set to: " + style);

        // update button highlight
        if (selectedButton != null) {
            selectedButton.setStyle("");
        }

        clickedButton.setStyle(
                "-fx-background-color: grey; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-color: black; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px;"
        );

        selectedButton = clickedButton;
    }
}

