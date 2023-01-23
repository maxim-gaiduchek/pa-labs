package com.example.lab6.controllers;

import com.example.lab6.Combination;
import com.example.lab6.Game;
import com.example.lab6.Main;
import com.example.lab6.entities.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.List;
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

    @FXML
    private void throwDices() {
        if (!Game.isPlayerTurn() || Game.getRoundsCounter() == 0) {
            return;
        }

        Player player = Game.getCurrentPlayer();
        Combination combination = player.throwDices();
        combinationLabel.setText(combination.getTitle());
        setDiceImages(player.getDices());
        updatePointsLabel(Game.isPlayerTurn(), player.getPoints(), player.getRoundPoints());
        updateRoundPointsLabel(player.getRoundPoints());
        updateTriesLabel(player.getTries());
        throwDicesButton.setDisable(true);
        if (player.getTries() == 2 && combination == Combination.GENERAL) {
            endGame();
        } else {
            endTurnButton.setDisable(false);
            updateRoundPointsLabel(player.getRoundPoints());
        }
    }

    @FXML
    private void endTurn() {
        if (!Game.isPlayerTurn() || Game.getRoundsCounter() == 0) {
            return;
        }

        throwDicesButton.setDisable(true);
        endTurnButton.setDisable(true);
        Player player = Game.getCurrentPlayer();
        player.addPoints();
        updatePointsLabel(Game.isPlayerTurn(), player.getPoints(), player.getRoundPoints());
        Game.endTurn();
        player = Game.getCurrentPlayer();
        updateRoundPointsLabel(player.getRoundPoints());
        updatePointsLabel(Game.isPlayerTurn(), player.getPoints(), player.getRoundPoints());
        updateTriesLabel(player.getTries());
        while (!Game.isPlayerTurn()) {
            playBot();
        }
        updateRoundLabel();
        if (Game.getRoundsCounter() == 0) {
            endGame();
        } else {
            throwDicesButton.setDisable(false);
        }
    }

    private void playBot() {
        if (Game.isPlayerTurn() || Game.getRoundsCounter() == 0) {
            return;
        }

        Player player = Game.getCurrentPlayer();
        for (int i = 0; i < 3; i++) {
            player.playBot(i);
            if (player.hasNotChanged()) {
                break;
            }
            Combination combination = player.throwDices();
            combinationLabel.setText(combination.getTitle());
            setDiceImages(player.getDices());
            updatePointsLabel(Game.isPlayerTurn(), player.getPoints(), player.getRoundPoints());
            updateRoundPointsLabel(player.getRoundPoints());
            updateTriesLabel(player.getTries());
        }
        Game.endTurn();
    }

    private void endGame() {
        String prompt;
        if (Game.hasPlayerWon()) {
            prompt = "Ти вийграв!";
        } else if (Game.hasBotWon()) {
            prompt = "Ти програв(";
        } else {
            prompt = "Нічия";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, prompt);
        alert.showAndWait();

        Game.reset();
        Player player = Game.getCurrentPlayer();
        updateRoundLabel();
        updateTriesLabel(player.getTries());
        updateRoundPointsLabel(player.getPoints());
        updatePointsLabel(true, 0, 0);
        updatePointsLabel(false, 0, 0);
        endTurnButton.setDisable(true);
        throwDicesButton.setDisable(true);
        while (!Game.isPlayerTurn()) {
            playBot();
        }
        throwDicesButton.setDisable(false);
    }

    private void setDiceImages(List<Integer> dices) {
        setDiceImage(diceImage0, dices.get(0));
        setDiceImage(diceImage1, dices.get(1));
        setDiceImage(diceImage2, dices.get(2));
        setDiceImage(diceImage3, dices.get(3));
        setDiceImage(diceImage4, dices.get(4));
        diceImage0.setOpacity(1);
        diceImage1.setOpacity(1);
        diceImage2.setOpacity(1);
        diceImage3.setOpacity(1);
        diceImage4.setOpacity(1);
    }

    private void setDiceImage(ImageView diceImage, int value) {
        InputStream in = Main.class.getResourceAsStream("images/dice" + value + ".png");
        diceImage.setImage(new Image(Objects.requireNonNull(in)));
    }

    private void updateRoundLabel() {
        roundsLabel.setText("Раундів залишилось: " + Game.getRoundsCounter());
    }

    private void updateTriesLabel(int tries) {
        triesLabel.setText("Спроб залишилось: " + tries);
    }

    private void updateRoundPointsLabel(int points) {
        roundPointsLabel.setText("Очок за раунд: " + points);
    }

    private void updatePointsLabel(boolean playerTurn, int points, int roundPoints) {
        Label label = playerTurn ? playerLabel : botLabel;
        String ending = roundPoints > 0 ? (" + " + roundPoints) : "";
        label.setText("Очок: " + points + ending);
    }

    @FXML
    private void selectDice0() {
        Player player = Game.getCurrentPlayer();

        if (player.getTries() == 3 || player.getTries() == 0) {
            return;
        }

        player.negateChanged(0);
        diceImage0.setOpacity(player.getChanged(0) ? 0.5 : 1);
        throwDicesButton.setDisable(player.hasNotChanged());
    }

    @FXML
    private void selectDice1() {
        Player player = Game.getCurrentPlayer();

        if (player.getTries() == 3 || player.getTries() == 0) {
            return;
        }

        player.negateChanged(1);
        diceImage1.setOpacity(player.getChanged(1) ? 0.5 : 1);
        throwDicesButton.setDisable(player.hasNotChanged());
    }

    @FXML
    private void selectDice2() {
        Player player = Game.getCurrentPlayer();

        if (player.getTries() == 3 || player.getTries() == 0) {
            return;
        }

        player.negateChanged(2);
        diceImage2.setOpacity(player.getChanged(2) ? 0.5 : 1);
        throwDicesButton.setDisable(player.hasNotChanged());
    }

    @FXML
    private void selectDice3() {
        Player player = Game.getCurrentPlayer();

        if (player.getTries() == 3 || player.getTries() == 0) {
            return;
        }

        player.negateChanged(3);
        diceImage3.setOpacity(player.getChanged(3) ? 0.5 : 1);
        throwDicesButton.setDisable(player.hasNotChanged());
    }

    @FXML
    private void selectDice4() {
        Player player = Game.getCurrentPlayer();

        if (player.getTries() == 3 || player.getTries() == 0) {
            return;
        }

        player.negateChanged(4);
        diceImage4.setOpacity(player.getChanged(4) ? 0.5 : 1);
        throwDicesButton.setDisable(player.hasNotChanged());
    }
}