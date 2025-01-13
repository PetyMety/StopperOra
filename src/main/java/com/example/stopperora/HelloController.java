package com.example.stopperora;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StopperController {

    @FXML
    private Label reszTime;
    @FXML
    private Label actualTime;
    @FXML
    private Button startButton;
    @FXML
    private Button resetButton;
    private LocalDateTime startTime;
    private LocalDateTime stopTime;
    private Boolean isRunning = false;
    private Timer timer;
    private List<String> reszIdok = new ArrayList<>();

    @FXML
    public void startStopper() {
        if(startButton.getText().equalsIgnoreCase("Start")){
            isRunning= true;
            startTime = LocalDateTime.now();
            startButton.setText("Stop");
            resetButton.setText("Részidő");
            timer= new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    stopTime=LocalDateTime.now();
                    Platform.runLater(() -> {
                        actualTime.setText(String.valueOf(getElapsedTime()));
                    });

                }},0, 100);
        }else{
            startButton.setText("Start");
            resetButton.setText("Reset");
            stopTime = LocalDateTime.now();
            isRunning= false;
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
    }
    public String getElapsedTime() {
        LocalDateTime endTime = isRunning? LocalDateTime.now(): stopTime;
        Duration duration = Duration.between(startTime, endTime);
        return String.format("%02d:%02d.%03d", duration.toMinutes() % 60, duration.getSeconds() % 60, duration.toMillis() % 1000);
    }
    @FXML
    public void resetStopper() {
        if (resetButton.getText().equalsIgnoreCase("Reset")) {
            isRunning = false;
            startTime = null;
            stopTime = null;
            actualTime.setText("00:00.000");
            reszTime.setText("");
            reszIdok.clear();
        } else if (resetButton.getText().equalsIgnoreCase("Részidő")) {
            if (isRunning) {
                reszIdok.add(actualTime.getText());
                String timeString = "";
                for (String s : reszIdok)
                {
                    timeString += s + "\n";
                }
                reszTime.setText(timeString);
            }
        }
    }
}