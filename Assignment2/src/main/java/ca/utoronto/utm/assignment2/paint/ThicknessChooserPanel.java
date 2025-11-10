package ca.utoronto.utm.assignment2.paint;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.List;

public class ThicknessChooserPanel extends HBox {
    private View view;
    private List<Button> buttons = new ArrayList<>();
    private double currentThickness = 1;

    public ThicknessChooserPanel(View view) {
        this.view = view;
        this.setSpacing(10);
        this.setPadding(new Insets(10));
        double[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (double value: values) {
            Button button = new Button();
            button.setText(String.valueOf(value));
            button.setMinSize(60, 30);
            button.setMaxSize(60, 30);
            button.setOnAction(e -> setValue(button));

            buttons.add(button);
            this.getChildren().add(button);
        }

    }

    private double setValue(Button button) {
        currentThickness = Double.parseDouble(button.getText());
        this.view.getModel().setCurrentThickness(currentThickness);
        return currentThickness;
    }
}
