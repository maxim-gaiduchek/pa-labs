package com.example.lab6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game {

    private boolean playerTurn = true; // System.currentTimeMillis() % 2 == 0;
    private int roundsCounter = 10;
    private int playerPoints = 0;
    private int botPoints = 0;

    private int triesCounter = 3;
    private int pointsInRoundCounter = 0;
    private boolean[] changed = {true, true, true, true, true};
    private List<Integer> dices = new ArrayList<>(List.of(0, 0, 0, 0, 0));

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public boolean hasPlayerWon() {
        return playerPoints > botPoints;
    }

    public boolean hasBotWon() {
        return playerPoints < botPoints;
    }

    public int getRoundsCounter() {
        return roundsCounter;
    }

    public int getTriesCounter() {
        return triesCounter;
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

    public int getBotPoints() {
        return botPoints;
    }

    public int getPointsInRoundCounter() {
        return pointsInRoundCounter;
    }

    public int getDice(int i) {
        return dices.get(i);
    }

    public boolean getChanged(int i) {
        return changed[i];
    }

    public boolean hasChanged() {
        for (boolean changed : this.changed) {
            if (changed) {
                return true;
            }
        }
        return false;
    }

    public void negateChanged(int i) {
        changed[i] = !changed[i];
    }

    public void setChanged(boolean changed, int i) {
        this.changed[i] = changed;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void decrementRoundsCounter() {
        roundsCounter--;
    }

    public void throwDices() {
        randomize();
        pointsInRoundCounter = dices.stream().mapToInt(Integer::intValue).sum();
        Arrays.fill(changed, false);
        triesCounter -= 1;
    }

    public void randomize() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            if (!changed[i]) {
                continue;
            }
            dices.set(i, random.nextInt(1, 7));
        }
    }

    public void endTurn() {
        playerTurn = false;
        playerPoints += pointsInRoundCounter;
        triesCounter = 3;
        pointsInRoundCounter = 0;
        changed = new boolean[]{true, true, true, true, true};
        dices = new ArrayList<>(List.of(0, 0, 0, 0, 0));
    }

    /*public void playBot() {
        roundsCounter--;
        updateRoundLabel();
        playerTurn = true;
        if (roundsCounter == 0) {
            endGame();
        } else {
            throwDicesButton.setDisable(false);
        }
    }*/

    public void reset() {
        playerTurn = true; // System.currentTimeMillis() % 2 == 0;
        roundsCounter = 10;
        playerPoints = 0;
        botPoints = 0;
        triesCounter = 3;
        pointsInRoundCounter = 0;
        Arrays.fill(changed, true);
    }
}
