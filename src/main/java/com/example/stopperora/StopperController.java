package com.example.stopperora;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class StopperController implements Initializable {

    @FXML
    private Button startButton;

    @FXML
    private Button resetButton;

    @FXML
    private Label timeLabel;

    @FXML
    private Label lapLabel;

    private LocalDateTime startTime;
    private LocalDateTime lapTime;
    private Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline = new Timeline(new KeyFrame(Duration.millis(1), e -> updateTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    @FXML
    private void handleStartButton(ActionEvent event) {
        if (startButton.getText().equals("Start")) {
            startTime = LocalDateTime.now();
            lapTime = startTime;
            timeline.play();
            startButton.setText("Stop");
            resetButton.setText("Lap");
        } else {
            timeline.stop();
            startButton.setText("Start");
            resetButton.setText("Reset");
        }
    }

    @FXML
    private void handleResetButton(ActionEvent event) {
        if (resetButton.getText().equals("Reset")) {
            startTime = null;
            lapTime = null;
            timeLabel.setText("00:00:000");
            lapLabel.setText("");
        } else {
            lapTime = LocalDateTime.now();
            lapLabel.setText(getTime(lapTime, startTime));
        }
    }

    private void updateTime() {
        if (startTime != null) {
            LocalDateTime currentTime = LocalDateTime.now();
            timeLabel.setText(getTime(currentTime, startTime));
        }
    }

    private String getTime(LocalDateTime currentTime, LocalDateTime startTime) {
        long seconds = java.time.Duration.between(startTime, currentTime).toSeconds();
        long minutes = seconds / 60;
        seconds = seconds % 60;
        long milliseconds = java.time.Duration.between(startTime, currentTime).toMillis() % 1000;
        return String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
    }
}