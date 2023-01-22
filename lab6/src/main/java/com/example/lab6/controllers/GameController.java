package com.example.lab6.controllers;

import com.example.lab6.Game;
import com.example.lab6.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.Objects;

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
    private Label combinationLabel;
    @FXML
    private Label triesLabel;
    @FXML
    private Label roundPointsLabel;

    private final Game game = new Game();

    @FXML
    private void throwDices() {
        if (!game.isPlayerTurn()) {
            return;
        }

        game.throwDices();
        setDiceImage(diceImage0, game.getDice(0));
        setDiceImage(diceImage1, game.getDice(1));
        setDiceImage(diceImage2, game.getDice(2));
        setDiceImage(diceImage3, game.getDice(3));
        setDiceImage(diceImage4, game.getDice(4));
        diceImage0.setOpacity(1);
        diceImage1.setOpacity(1);
        diceImage2.setOpacity(1);
        diceImage3.setOpacity(1);
        diceImage4.setOpacity(1);
        updateRoundPointsLabel();
        updatePointsLabel();
        updateTriesLabel();
        throwDicesButton.setDisable(true);
        endTurnButton.setDisable(false);
    }

    @FXML
    private void endTurn() {
        throwDicesButton.setDisable(true);
        endTurnButton.setDisable(true);
        game.endTurn();
        updateRoundPointsLabel();
        updatePointsLabel();
        updateTriesLabel();
        playBot();
    }

    private void playBot() {
        game.decrementRoundsCounter();
        updateRoundLabel();
        game.setPlayerTurn(true);
        if (game.getRoundsCounter() == 0) {
            endGame();
        } else {
            throwDicesButton.setDisable(false);
        }
    }

    private void endGame() {
        String prompt;
        if (game.hasPlayerWon()) {
            prompt = "Ти вийграв!";
        } else if (game.hasBotWon()) {
            prompt = "Ти програв(";
        } else {
            prompt = "Нічия";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, prompt);
        alert.showAndWait();

        game.reset();
        updateRoundLabel();
        updateTriesLabel();
        updateRoundPointsLabel();
        updatePointsLabel();
        throwDicesButton.setDisable(false);
    }

    private void setDiceImage(ImageView diceImage, int value) {
        InputStream in = Main.class.getResourceAsStream("images/dice" + value + ".png");
        diceImage.setImage(new Image(Objects.requireNonNull(in)));
    }

    private void updateRoundLabel() {
        roundsLabel.setText("Раундів залишилось: " + game.getRoundsCounter());
    }

    private void updateTriesLabel() {
        triesLabel.setText("К-сть спроб залишилось: " + game.getTriesCounter());
    }

    private void updateRoundPointsLabel() {
        roundPointsLabel.setText("К-сть очок за раунд: " + game.getPointsInRoundCounter());
    }

    private void updatePointsLabel() {
        String ending = game.getPointsInRoundCounter() > 0 ? (" + " + game.getPointsInRoundCounter()) : "";
        playerLabel.setText("Очок: " + game.getPlayerPoints() + ending);
        botLabel.setText("Очок: " + game.getBotPoints());
    }

    @FXML
    private void selectDice0() {
        if (game.getTriesCounter() == 3 || game.getTriesCounter() == 0) {
            return;
        }

        game.negateChanged(0);
        diceImage0.setOpacity(game.getChanged(0) ? 0.5 : 1);
        throwDicesButton.setDisable(!game.hasChanged());
    }

    @FXML
    private void selectDice1() {
        if (game.getTriesCounter() == 3 || game.getTriesCounter() == 0) {
            return;
        }

        game.negateChanged(1);
        diceImage1.setOpacity(game.getChanged(1) ? 0.5 : 1);
        throwDicesButton.setDisable(!game.hasChanged());
    }

    @FXML
    private void selectDice2() {
        if (game.getTriesCounter() == 3 || game.getTriesCounter() == 0) {
            return;
        }

        game.negateChanged(2);
        diceImage2.setOpacity(game.getChanged(2) ? 0.5 : 1);
        throwDicesButton.setDisable(!game.hasChanged());
    }

    @FXML
    private void selectDice3() {
        if (game.getTriesCounter() == 3 || game.getTriesCounter() == 0) {
            return;
        }

        game.negateChanged(3);
        diceImage3.setOpacity(game.getChanged(3) ? 0.5 : 1);
        throwDicesButton.setDisable(!game.hasChanged());
    }

    @FXML
    private void selectDice4() {
        if (game.getTriesCounter() == 3 || game.getTriesCounter() == 0) {
            return;
        }

        game.negateChanged(4);
        diceImage4.setOpacity(game.getChanged(4) ? 0.5 : 1);
        throwDicesButton.setDisable(!game.hasChanged());
    }
}