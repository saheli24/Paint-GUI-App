package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;


public class View implements EventHandler<ActionEvent> {

        private PaintModel paintModel;
        private PaintPanel paintPanel;
        private ShapeChooserPanel shapeChooserPanel;
        private FillStyleChooserPanel fillStylePanel;
        public View(PaintModel model, Stage stage) {

            ColorChooserPanel colorChooserPanel = new ColorChooserPanel(this);
            ThicknessChooserPanel thicknessChooserPanel1 = new ThicknessChooserPanel(this);

            this.paintModel = model;

            this.paintPanel = new PaintPanel(this.paintModel);
            this.shapeChooserPanel = new ShapeChooserPanel(this);
            this.fillStylePanel = new FillStyleChooserPanel(this);
            VBox leftPanel = new VBox();
            leftPanel.setSpacing(10);
            leftPanel.getChildren().addAll(this.shapeChooserPanel, this.fillStylePanel, thicknessChooserPanel1);

            BorderPane root = new BorderPane();

            MenuBar menuBar = createMenuBar();
            Button undoArrow = new Button("⮪ Undo");
            undoArrow.setOnAction(e -> {
                paintModel.undo();
                paintPanel.undoRedoUpdatePolyline(); // restore Polyline ghost
                flashButton(undoArrow);
            });
            Button redoArrow = new Button("Redo ⮫");
            redoArrow.setOnAction(e -> {
                paintModel.redo();
                paintPanel.undoRedoUpdatePolyline(); // restore Polyline ghost
                flashButton(redoArrow);
            });
            HBox topBar = new HBox();
            topBar.getChildren().addAll(menuBar, undoArrow, redoArrow);
            topBar.setSpacing(5);

            root.setTop(topBar);
            root.setCenter(this.paintPanel);
            root.setLeft(leftPanel);
            root.setRight(colorChooserPanel); // add color panel on the right side
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Paint");
            stage.show();
        }

        public PaintModel getPaintModel() {
                return this.paintModel;
        }

        // ugly way to do this?
        public void setMode(String mode){
            this.paintPanel.setMode(mode);
        }
        private MenuBar createMenuBar() {

                MenuBar menuBar = new MenuBar();
                Menu menu;
                MenuItem menuItem;

                // A menu for File

                menu = new Menu("File");

                menuItem = new MenuItem("New");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Open");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Save");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menu.getItems().add(new SeparatorMenuItem());

                menuItem = new MenuItem("Exit");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuBar.getMenus().add(menu);

                // Another menu for Edit

                menu = new Menu("Edit");

                menuItem = new MenuItem("Cut");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Copy");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Paste");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Undo");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Redo");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuBar.getMenus().add(menu);

                return menuBar;
        }
        private void flashButton(Button button) {
            double width = button.getWidth();
            double height = button.getHeight();

            // lock button size temporarily
            button.setMinSize(width, height);
            button.setMaxSize(width, height);

            button.setStyle(
                    "-fx-background-color: grey;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: white;" +
                            "-fx-border-color: black;" +
                            "-fx-border-width: 2px;" +
                            "-fx-border-radius: 5px;" +
                            "-fx-background-radius: 5px;"
            );

            javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.millis(200));
            pause.setOnFinished(e -> button.setStyle("")); // reset to normal
            pause.play();
        }



    @Override
    public void handle(ActionEvent event) {
        String command = ((MenuItem) event.getSource()).getText();
        switch (command) {
            case "Exit":
                Platform.exit();
                break;
            case "Undo":
                paintModel.undo();
                paintPanel.undoRedoUpdatePolyline(); // restore Polyline ghost
                break;
            case "Redo":
                paintModel.redo();
                paintPanel.undoRedoUpdatePolyline(); // restore Polyline ghost
                break;
            case "New":
                // clear canvas
                paintModel.getShapes().clear();
                paintModel.notifyObserversOfChange();
                break;
        }
    }


    public PaintModel getModel() {
            return this.paintModel;
        }
        public void setFillStyle(String style) {
            this.paintModel.setCurrentFillStyle(style);
        }


}
