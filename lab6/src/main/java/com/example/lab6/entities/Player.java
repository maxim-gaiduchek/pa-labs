package com.example.lab6.entities;

import com.example.lab6.Combination;
import com.example.lab6.algorithms.MinimaxAlgorithm;
import com.example.lab6.algorithms.MinimaxResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Player {

    private int points = 0;
    private int tries = 3;
    private int roundPoints = 0;
    private boolean[] changes = {true, true, true, true, true};
    private List<Integer> dices = new ArrayList<>(List.of(0, 0, 0, 0, 0));

    // getters

    public int getPoints() {
        return points;
    }

    public int getTries() {
        return tries;
    }

    public int getRoundPoints() {
        return roundPoints;
    }

    public List<Integer> getDices() {
        return dices;
    }

    public boolean getChanges(int i) {
        return changes[i];
    }

    public boolean hasNotChanges() {
        for (boolean changes : this.changes) {
            if (changes) {
                return false;
            }
        }
        return true;
    }

    // setters

    public void addPoints() {
        if (roundPoints == Integer.MAX_VALUE) {
            points = Integer.MAX_VALUE;
        } else {
            points += roundPoints;
        }
        roundPoints = 0;
    }

    public void negateChange(int i) {
        changes[i] = !changes[i];
    }

    // game

    public void randomize() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            if (!changes[i]) {
                continue;
            }
            dices.set(i, random.nextInt(1, 7));
        }
        Arrays.fill(changes, false);
    }

    public void reset() {
        tries = 3;
        roundPoints = 0;
        Arrays.fill(changes, true);
        dices = new ArrayList<>(List.of(0, 0, 0, 0, 0));
    }

    public Combination throwDices() {
        randomize();
        Combination combination = Combination.calcCombination(dices);
        roundPoints = combination.getPoints(tries);
        tries -= 1;
        return combination;
    }

    public void findOptimal(int tryNumber) {
        if (tryNumber == 0) {
            Arrays.fill(changes, true);
            return;
        }

        Arrays.fill(changes, false);
        MinimaxResult result = MinimaxAlgorithm.findOptimal(roundPoints, changes, dices, true, 3 - tryNumber);
        changes = result.getChanges();
    }

    // core

    @Override
    public String toString() {
        return "Player{" +
                "points=" + points +
                ", tries=" + tries +
                ", roundPoints=" + roundPoints +
                ", changed=" + Arrays.toString(changes) +
                ", dices=" + dices +
                '}';
    }
}
