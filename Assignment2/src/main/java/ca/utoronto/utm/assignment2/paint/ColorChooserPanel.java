package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
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
    private ColorPicker colorPicker;

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

        Color[] colors = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.PURPLE };

        for (Color color : colors) {
            Button btn = new Button();
            applyButtonStyle(btn, color, false);
            btn.setMinSize(40, 40);
            btn.setMaxSize(40, 40);
            btn.setOnAction(e -> selectColor(btn, color));

            buttons.add(btn);
            this.getChildren().add(btn);
        }

        pickerButton = new Button("...");
        pickerButton.setFont(Font.font(18));
        pickerButton.setMinSize(40, 40);
        pickerButton.setMaxSize(40, 40);
        this.getChildren().add(pickerButton);

        colorPicker = new ColorPicker();
        colorPicker.setVisible(false);
        colorPicker.setManaged(false);
        colorPicker.setOnAction(e -> {
            Color picked = colorPicker.getValue();
            view.getPaintModel().setCurrentColor(picked);
            applyButtonStyle(pickerButton, picked, true);

            if (selectedButton != null) {
                applyButtonStyle(selectedButton, getButtonColor(selectedButton), false);
                selectedButton = null;
            }
        });

        pickerButton.setOnAction(e -> colorPicker.show());
        this.getChildren().add(colorPicker);
    }

    /**
     * Handles color selection from a preset button.
     * Updates the PaintModel and highlights the selected button.
     *
     * @param btn the clicked button
     * @param color the color with the button
     */
    private void selectColor(Button btn, Color color) {
        view.getPaintModel().setCurrentColor(color);

        if (selectedButton != null) {
            applyButtonStyle(selectedButton, getButtonColor(selectedButton), false);
        }

        applyButtonStyle(btn, color, true);
        selectedButton = btn;

        applyButtonStyle(pickerButton, colorPicker.getValue(), false);
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
}
