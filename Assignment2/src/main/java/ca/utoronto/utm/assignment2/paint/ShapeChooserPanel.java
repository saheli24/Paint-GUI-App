package ca.utoronto.utm.assignment2.paint;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


//Need to figure out how to highlight buttons for bug
public class ShapeChooserPanel extends GridPane implements EventHandler<ActionEvent> {

        private View view;
        private Button selectedButton = null; // track button currently selected

        public ShapeChooserPanel(View view) {

            this.view = view;

            // creates image for circle
            Image iconOne = new Image(getClass().getResourceAsStream("/icons/circle.png"));
            ImageView iconViewOne = new ImageView(iconOne);
            iconViewOne.setFitWidth(15);
            iconViewOne.setFitHeight(15);

            // sets image for circle
            Button circleButton = new Button();
            circleButton.setUserData("Circle");
            circleButton.setGraphic(iconViewOne);
            circleButton.setText("");
            circleButton.setMinWidth(100);
            this.add(circleButton, 0, 0);
            circleButton.setOnAction(this);

            // creates image for rectangle
            Image iconTwo = new Image(getClass().getResourceAsStream("/icons/rectangle.png"));
            ImageView iconViewTwo = new ImageView(iconTwo);
            iconViewTwo.setFitWidth(15);
            iconViewTwo.setFitHeight(15);

            // sets image for rectangle
            Button rectangleButton = new Button();
            rectangleButton.setUserData("Rectangle");
            rectangleButton.setGraphic(iconViewTwo);
            rectangleButton.setText("");
            rectangleButton.setMinWidth(100);
            this.add(rectangleButton, 0, 1);
            rectangleButton.setOnAction(this);

            // sets image for square
            Image iconThree = new Image(getClass().getResourceAsStream("/icons/square.png"));
            ImageView iconViewThree = new ImageView(iconThree);
            iconViewThree.setFitWidth(15);
            iconViewThree.setFitHeight(15);

            // sets image for square
            Button squareButton = new Button();
            squareButton.setUserData("Square");
            squareButton.setGraphic(iconViewThree);
            squareButton.setText("");          // hide text
            squareButton.setMinWidth(100);
            this.add(squareButton, 0, 2);
            squareButton.setOnAction(this);

            // sets image for squiggle
            Image iconFour = new Image(getClass().getResourceAsStream("/icons/squiggle.png"));
            ImageView iconViewFour = new ImageView(iconFour);
            iconViewFour.setFitWidth(15);
            iconViewFour.setFitHeight(15);

            // sets image for squiggle
            Button squiggleButton = new Button();
            squiggleButton.setUserData("Squiggle");
            squiggleButton.setGraphic(iconViewFour);
            squiggleButton.setText("");
            squiggleButton.setMinWidth(100);
            this.add(squiggleButton, 0, 3);
            squiggleButton.setOnAction(this);

            // sets image for polyline
            Image iconFive = new Image(getClass().getResourceAsStream("/icons/polyline.png"));
            ImageView iconViewFive = new ImageView(iconFive);
            iconViewFive.setFitWidth(15);
            iconViewFive.setFitHeight(15);

            // sets image for polyline
            Button polylineButton = new Button();
            polylineButton.setUserData("Polyline");
            polylineButton.setGraphic(iconViewFive);
            polylineButton.setText("");        // hide text
            polylineButton.setMinWidth(100);
            this.add(polylineButton, 0, 4);
            polylineButton.setOnAction(this);

            // sets image for oval
            Image iconSix = new Image(getClass().getResourceAsStream("/icons/oval.png"));
            ImageView iconViewSix = new ImageView(iconSix);
            iconViewSix.setFitWidth(15);
            iconViewSix.setFitHeight(15);

            Button ovalButton = new Button();
            ovalButton.setUserData("Oval");
            ovalButton.setGraphic(iconViewSix);
            ovalButton.setText("");
            ovalButton.setMinWidth(100);
            this.add(ovalButton, 0, 5);
            ovalButton.setOnAction(this);

            Image iconSeven = new Image(getClass().getResourceAsStream("/icons/triangle.png"));
            ImageView iconViewSeven = new ImageView(iconSeven);
            iconViewSeven.setFitWidth(15);
            iconViewSeven.setFitHeight(15);
            Button triangleButton = new Button();
            triangleButton.setUserData("Triangle");  // the mode that PaintPanel will recognize
            triangleButton.setGraphic(iconViewSeven);
            triangleButton.setText(""); // hide text
            triangleButton.setMinWidth(100);
            this.add(triangleButton, 0, 6); // next row in the grid
            triangleButton.setOnAction(this);


        }

        @Override
        public void handle(ActionEvent event) {
            Button clickedButton = (Button) event.getSource();
            String command = (String) clickedButton.getUserData(); // gets data of button instead of label
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


