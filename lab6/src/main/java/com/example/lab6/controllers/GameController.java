package com.example.lab6.controllers;

import com.example.lab6.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GameController {

    @FXML
    private Button throwDicesButton;
    @FXML
    private Label playerLabel;
    @FXML
    private Label botLabel;
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

    private boolean playerTurn;

    @FXML
    private void throwDices() {
        calcAndShowResult();
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

        return 0;
    }

    private void setDiceImage(ImageView diceImage, int value) {
        InputStream in = Main.class.getResourceAsStream("images/dice" + value + ".png");
        diceImage.setImage(new Image(Objects.requireNonNull(in)));
    }
}