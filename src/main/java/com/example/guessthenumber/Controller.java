package com.example.guessthenumber;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.util.Random;

public class Controller {
    @FXML private Label game_events;
    @FXML private Label tries_left;
    @FXML private TextField inputField;
    @FXML private ImageView DisplayImg;

    private int targetNumber = new Random().nextInt(101);
    private int userNumber;
    private int tries = 9;

    public void initialize() {

        System.out.println(targetNumber);

        inputField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String input = event.getCharacter();
            if (!input.matches("[0-9]")) {
                event.consume();
            }
        });

        inputField.setOnKeyPressed( event -> {

            if(event.getCode().getName().equals("Enter"))
                {
                    if(inputField.getText().isEmpty()) {
                        jiggleTextField(inputField);
                        return;
                    }

                    try { userNumber = Integer.parseInt(inputField.getText()); }
                    catch(Exception e) {e.printStackTrace();}

                    if(userNumber == targetNumber)
                    {
                        game_events.setText("Found!!!");
                        inputField.setDisable(true);
                        return;
                    }

                    else if(tries<=0)
                    {
                        tries_left.setText("Out of Tries");
                        game_events.setText("it was " + targetNumber);
                        inputField.setDisable(true);
                        inputField.clear();
                        return;
                    }

                    if(userNumber>100 || userNumber<1)
                    {
                        game_events.setText("Out of Range (1-100)");
                        jiggleTextField(inputField);
                    }
                    else if(userNumber > targetNumber)
                    {
                        game_events.setText("Lower!!!");
                        tries--;
                    }

                    else if(userNumber < targetNumber)
                    {
                        game_events.setText("Higher!!!");
                        tries--;
                    }

                    else game_events.setText("WTFF");

                    tries_left.setText("Tries Left: " + (tries+1));
                    inputField.clear();
            }
        });
    }

    public static void jiggleTextField(TextField textField) {
        double jiggleDistance = 10; // Adjust the distance as needed
        Duration jiggleDuration = Duration.seconds(0.3); // Faster duration

        KeyValue keyValue1 = new KeyValue(textField.translateXProperty(), jiggleDistance, Interpolator.LINEAR);
        KeyFrame keyFrame1 = new KeyFrame(Duration.ZERO, keyValue1);

        KeyValue keyValue2 = new KeyValue(textField.translateXProperty(), -jiggleDistance, Interpolator.LINEAR);
        KeyFrame keyFrame2 = new KeyFrame(jiggleDuration.divide(6), keyValue2);

        KeyValue keyValue3 = new KeyValue(textField.translateXProperty(), jiggleDistance, Interpolator.LINEAR);
        KeyFrame keyFrame3 = new KeyFrame(jiggleDuration.divide(3), keyValue3);

        KeyValue keyValue4 = new KeyValue(textField.translateXProperty(), -jiggleDistance, Interpolator.LINEAR);
        KeyFrame keyFrame4 = new KeyFrame(jiggleDuration.divide(2), keyValue4);

        KeyValue keyValue5 = new KeyValue(textField.translateXProperty(), 0, Interpolator.LINEAR); // Reset to original position
        KeyFrame keyFrame5 = new KeyFrame(jiggleDuration, keyValue5);

        Timeline timeline = new Timeline(keyFrame1, keyFrame2, keyFrame3, keyFrame4, keyFrame5);
        timeline.setCycleCount(1); // Play the animation once
        timeline.setAutoReverse(false); // Do not reverse back
        timeline.play();
    }
    @FXML
    private void restart() {

        targetNumber = new Random().nextInt(101);
        game_events.setText("Enter a Number");
        tries_left.setText("Tries Left: 10");
        inputField.setDisable(false);
        inputField.clear();
        tries = 9;
    }
    @FXML
    private void close() {
        Platform.exit();
    }
}