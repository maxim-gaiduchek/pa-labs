package com.example.lab6.controllers;

import com.example.lab6.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.*;

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
    private boolean[] change = {true, true, true, true, true};
    private List<Integer> dices = new ArrayList<>(List.of(0, 0, 0, 0, 0));

    @FXML
    private void throwDices() {
        if (!playerTurn) {
            return;
        }

        calcAndShowResult(change);
        pointsInRoundCounter = dices.stream().mapToInt(Integer::intValue).sum();
        Arrays.fill(change, false);
        diceImage0.setOpacity(1);
        diceImage1.setOpacity(1);
        diceImage2.setOpacity(1);
        diceImage3.setOpacity(1);
        diceImage4.setOpacity(1);
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
        change = new boolean[]{true, true, true, true, true};
        dices = new ArrayList<>(List.of(0, 0, 0, 0, 0));
        updateRoundPointsLabel();
        updatePointsLabel();
        updateTriesLabel();
        playBot();
    }

    private void playBot() {
        roundsCounter--;
        updateRoundLabel();
        playerTurn = true;
        if (roundsCounter == 0) {
            endGame();
        } else {
            throwDicesButton.setDisable(false);
        }
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
        updatePointsLabel();
        throwDicesButton.setDisable(false);
    }

    private void calcAndShowResult(boolean[] change) {
        randomize(change);
        setDiceImage(diceImage0, dices.get(0));
        setDiceImage(diceImage1, dices.get(1));
        setDiceImage(diceImage2, dices.get(2));
        setDiceImage(diceImage3, dices.get(3));
        setDiceImage(diceImage4, dices.get(4));
    }

    private void randomize(boolean[] change) {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            if (!change[i]) {
                continue;
            }
            dices.set(i, random.nextInt(1, 7));
        }
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
        String ending = pointsInRoundCounter > 0 ? (" + " + pointsInRoundCounter) : "";
        playerLabel.setText("Очок: " + playerPoints + ending);
        botLabel.setText("Очок: " + botPoints);
    }

    @FXML
    private void selectDice0() {
        if (triesCounter == 3 || triesCounter == 0) {
            return;
        }

        change[0] = !change[0];
        diceImage0.setOpacity(change[0] ? 0.5 : 1);
    }

    @FXML
    private void selectDice1() {
        if (triesCounter == 3 || triesCounter == 0) {
            return;
        }

        change[1] = !change[1];
        diceImage1.setOpacity(change[1] ? 0.5 : 1);
    }

    @FXML
    private void selectDice2() {
        if (triesCounter == 3 || triesCounter == 0) {
            return;
        }

        change[2] = !change[2];
        diceImage2.setOpacity(change[2] ? 0.5 : 1);
    }

    @FXML
    private void selectDice3() {
        if (triesCounter == 3 || triesCounter == 0) {
            return;
        }

        change[3] = !change[3];
        diceImage3.setOpacity(change[3] ? 0.5 : 1);
    }

    @FXML
    private void selectDice4() {
        if (triesCounter == 3 || triesCounter == 0) {
            return;
        }

        change[4] = !change[4];
        diceImage4.setOpacity(change[4] ? 0.5 : 1);
    }
}