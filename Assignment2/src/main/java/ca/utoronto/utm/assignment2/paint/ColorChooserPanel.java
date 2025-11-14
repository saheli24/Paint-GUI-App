package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.List;

/**
 * A panel that allows users to choose colors for painting.
 * Provides preset color buttons, a custom color picker, and
 * highlights the selected color. Updates the PaintModel of
 * the associated shape view when a color is selected.
 *
 * @author Saheli S. | sahasah1
 */
public class ColorChooserPanel extends VBox {

    private View view;
    private List<Button> buttons = new ArrayList<>();
    private Button selectedButton = null;
    private Button pickerButton;
    private ColorPicker primaryColorPicker;
    private ColorPicker secondaryColorPicker;
    private ToggleButton multiColorToggle;
    private Label primaryLabel;
    private Label secondaryLabel;
    /**
     * Constructs a new ColorChooserPanel linked to the given View.
     * Initializes preset color buttons and a hidden color picker.
     *
     * @param view the View object that this panel is associated with
     */
    public ColorChooserPanel(View view) {
        this.view = view;

        this.setSpacing(10);
        this.setPadding(new Insets(10));

        multiColorToggle = new ToggleButton("Multi-Color");
        multiColorToggle.setStyle("-fx-background-color: lightgray;");
        multiColorToggle.setOnAction(e -> {
            boolean multiColor = multiColorToggle.isSelected();
            view.getPaintModel().setMultiColorMode(multiColor);
            updateMultiColorUI(multiColor);

            if (multiColor) {
                multiColorToggle.setStyle("-fx-background-color: lightblue;");
            } else {
                multiColorToggle.setStyle("-fx-background-color: lightgray;");
            }
        });
        primaryLabel = new Label("Border:");
        primaryColorPicker = new ColorPicker(Color.BLACK);
        primaryColorPicker.setOnAction(e -> {
            view.getPaintModel().setPrimaryColor(primaryColorPicker.getValue());
        });

        // Secondary color picker (for fill in multi-color mode)
        secondaryLabel = new Label("Fill:");
        secondaryColorPicker = new ColorPicker(Color.WHITE);
        secondaryColorPicker.setOnAction(e -> {
            view.getPaintModel().setSecondaryColor(secondaryColorPicker.getValue());
        });

        updateMultiColorUI(false);
        HBox multiColorBox = new HBox(5);
        multiColorBox.getChildren().addAll(multiColorToggle);
        this.getChildren().add(multiColorBox);

        HBox primaryColorBox = new HBox(5);
        primaryColorBox.getChildren().addAll(primaryLabel, primaryColorPicker);
        this.getChildren().add(primaryColorBox);
        HBox secondaryColorBox = new HBox(5);
        secondaryColorBox.getChildren().addAll(secondaryLabel, secondaryColorPicker);
        this.getChildren().add(secondaryColorBox);

        Color[] colors = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.PURPLE };
        Label presetLabel = new Label("Preset Colors:");
        this.getChildren().add(presetLabel);
        FlowPane colorButtonsPane = new FlowPane();
        colorButtonsPane.setHgap(5);
        colorButtonsPane.setVgap(5);
        for (Color color : colors) {
            Button btn = new Button();
            applyButtonStyle(btn, color, false);
            btn.setMinSize(40, 40);
            btn.setMaxSize(40, 40);
            btn.setOnAction(e -> selectColor(btn, color));

            buttons.add(btn);
            this.getChildren().add(btn);
        }

        pickerButton = new Button("+");
        pickerButton.setFont(Font.font(18));
        pickerButton.setMinSize(40, 40);
        pickerButton.setMaxSize(40, 40);
        pickerButton.setOnAction(e -> primaryColorPicker.show());
        colorButtonsPane.getChildren().add(pickerButton);
        this.getChildren().add(pickerButton);

    }

    /**
     * Updates the visibility of multi-color UI elements
     */
    private void updateMultiColorUI(boolean multiColorEnabled) {
        secondaryLabel.setVisible(multiColorEnabled);
        secondaryLabel.setManaged(multiColorEnabled);
        secondaryColorPicker.setVisible(multiColorEnabled);
        secondaryColorPicker.setManaged(multiColorEnabled);
    }

    /**
     * Handles color selection from a preset button.
     * Updates the PaintModel and highlights the selected button.
     *
     * @param btn the clicked button
     * @param color the color with the button
     */
    private void selectColor(Button btn, Color color) {
        view.getPaintModel().setPrimaryColor(color);

        if (selectedButton != null) {
            applyButtonStyle(selectedButton, getButtonColor(selectedButton), false);
        }

        applyButtonStyle(btn, color, true);
        selectedButton = btn;

        applyButtonStyle(pickerButton, primaryColorPicker.getValue(), false);
    }

    /**
     * Applies a color and highlight style to a button using JavaFX
     * Background and Border.
     *
     * @param btn the button to style
     * @param color the background color for the button
     * @param highlighted whether the button should appear highlighted
     */
    private void applyButtonStyle(Button btn, Color color, boolean highlighted) {
        btn.setBackground(new Background(new BackgroundFill(color, new CornerRadii(5), null)));

        BorderStroke borderStroke = new BorderStroke(
                highlighted ? Color.BLACK : Color.TRANSPARENT,
                BorderStrokeStyle.SOLID,
                new CornerRadii(5),
                new BorderWidths(3)
        );
        btn.setBorder(new Border(borderStroke));
    }

    /**
     * Returns the current color of a button based on its Background.
     *
     * @param btn the button to query
     * @return the Color of the button, or BLACK if not available
     */
    private Color getButtonColor(Button btn) {
        try {
            BackgroundFill fill = btn.getBackground().getFills().get(0);
            return (Color) fill.getFill();
        } catch (Exception e) {
            return Color.BLACK;
        }
    }
    public void updateFromModel() {
        PaintModel model = view.getPaintModel();
        primaryColorPicker.setValue(model.getPrimaryColor());
        secondaryColorPicker.setValue(model.getSecondaryColor());
        multiColorToggle.setSelected(model.isMultiColorMode());
        updateMultiColorUI(model.isMultiColorMode());

        if (model.isMultiColorMode()) {
            multiColorToggle.setStyle("-fx-background-color: lightblue;");
        } else {
            multiColorToggle.setStyle("-fx-background-color: lightgray;");
        }
    }
}
