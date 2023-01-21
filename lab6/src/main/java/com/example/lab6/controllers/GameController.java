package com.example.lab6.controllers;

import com.example.lab6.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GameController {

    @FXML
    private Button throwDicesButton;
    @FXML
    private Button endTurnButton;
    @FXML
    private ImageView diceImage0;
    @FXML
    private ImageView diceImage1;
    @FXML
    private ImageView diceImage2;
    @FXML
    private ImageView diceImage3;
    @FXML
    private ImageView diceImage4;

    @FXML
    private Label playerLabel;
    @FXML
    private Label botLabel;
    @FXML
    private Label roundsLabel;
    @FXML
    private Label triesLabel;
    @FXML
    private Label roundPointsLabel;

    private boolean playerTurn = true; // System.currentTimeMillis() % 2 == 0;
    private int roundsCounter = 10;
    private int playerPoints = 0;
    private int botPoints = 0;

    private int triesCounter = 3;
    private int pointsInRoundCounter = 0;

    @FXML
    private void throwDices() {
        pointsInRoundCounter = calcAndShowResult();
        triesCounter -= 1;
        updateRoundPointsLabel();
        updatePointsLabel();
        updateTriesLabel();
        if (triesCounter == 0) {
            throwDicesButton.setDisable(true);
        }
        endTurnButton.setDisable(false);
    }

    @FXML
    private void endTurn() {
        throwDicesButton.setDisable(true);
        endTurnButton.setDisable(true);
        playerTurn = false;
        playerPoints += pointsInRoundCounter;
        triesCounter = 3;
        pointsInRoundCounter = 0;
        updateRoundPointsLabel();
        updatePointsLabel();
        updateTriesLabel();
        playBot();
    }

    private void playBot() {

    }

    private List<Integer> randomize() {
        Random random = new Random();
        List<Integer> dices = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            dices.add(random.nextInt(1, 7));
        }
        return dices;
    }

    private int calcAndShowResult() {
        List<Integer> dices = randomize();
        setDiceImage(diceImage0, dices.get(0));
        setDiceImage(diceImage1, dices.get(1));
        setDiceImage(diceImage2, dices.get(2));
        setDiceImage(diceImage3, dices.get(3));
        setDiceImage(diceImage4, dices.get(4));

        return dices.stream().mapToInt(Integer::intValue).sum();
    }

    private void setDiceImage(ImageView diceImage, int value) {
        InputStream in = Main.class.getResourceAsStream("images/dice" + value + ".png");
        diceImage.setImage(new Image(Objects.requireNonNull(in)));
    }

    private void updateRoundLabel() {
        roundsLabel.setText("Раундів залишилось: " + roundsCounter);
    }

    private void updateTriesLabel() {
        triesLabel.setText("К-сть спроб залишилось: " + triesCounter);
    }

    private void updateRoundPointsLabel() {
        roundPointsLabel.setText("К-сть очок за раунд: " + pointsInRoundCounter);
    }

    private void updatePointsLabel() {
        String ending = pointsInRoundCounter > 0 ? (" + " + pointsInRoundCounter ): "";
        playerLabel.setText("Очок: " + playerPoints + ending);
    }

    private void endGame() {
        String prompt;
        if (playerPoints > botPoints) {
            prompt = "Ти вийграв!";
        } else if (playerPoints < botPoints) {
            prompt = "Ти програв(";
        } else {
            prompt = "Нічия";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, prompt);
        alert.showAndWait();

        playerTurn = true; // System.currentTimeMillis() % 2 == 0;
        roundsCounter = 10;
        playerPoints = 0;
        botPoints = 0;
        triesCounter = 3;
        pointsInRoundCounter = 0;
        updateRoundLabel();
        updateTriesLabel();
        updateRoundPointsLabel();
        if (!playerTurn) {
            throwDices();
        }
    }
}