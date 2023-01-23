package com.example.lab6.entities;

import com.example.lab6.Combination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Player {

    private int points = 0;
    private int tries = 3;
    private int roundPoints = 0;
    private boolean[] changed = {true, true, true, true, true};
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

    public boolean[] getChanged() {
        return changed;
    }

    public List<Integer> getDices() {
        return dices;
    }

    public List<Integer> getSortedDices() {
        return dices.stream().sorted().toList();
    }

    public int getDice(int i) {
        return dices.get(i);
    }

    public boolean getChanged(int i) {
        return changed[i];
    }

    public boolean hasNotChanged() {
        for (boolean changed : this.changed) {
            if (changed) {
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

    public void negateChanged(int i) {
        changed[i] = !changed[i];
    }

    public void setChanged(boolean changed, int i) {
        this.changed[i] = changed;
    }

    // game

    public void randomize() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            if (!changed[i]) {
                continue;
            }
            dices.set(i, random.nextInt(1, 7));
        }
        Arrays.fill(changed, false);
    }

    public void reset() {
        tries = 3;
        roundPoints = 0;
        Arrays.fill(changed, true);
        dices = new ArrayList<>(List.of(0, 0, 0, 0, 0));
    }

    public Combination throwDices() {
        randomize();
        Combination combination;
        List<Integer> sortedDices = getSortedDices();
        boolean allSame = true;
        int sameCount = 1, firstPartCount = 1;
        boolean straight = true;
        int previous = sortedDices.get(0);
        for (int i = 1; i < sortedDices.size(); i++) {
            int num = sortedDices.get(i);
            if (previous == num) {
                sameCount++;
                if (allSame) {
                    firstPartCount++;
                }
            } else {
                allSame = false;
                if (sameCount < 4) {
                    sameCount = 1;
                }
            }
            if (!allSame && straight && previous != num - 1) {
                straight = false;
            }
            previous = sortedDices.get(i);
        }

        if (allSame) {
            combination = Combination.GENERAL;
            roundPoints = tries == 3 ? Integer.MAX_VALUE : 60;
        } else if (sameCount == 4) {
            combination = Combination.FOUR;
            roundPoints = tries == 3 ? 45 : 40;
        } else if (firstPartCount + sameCount == 5) {
            combination = Combination.FULL_HOUSE;
            roundPoints = tries == 3 ? 35 : 30;
        } else if (straight) {
            combination = Combination.STRAIGHT;
            roundPoints = tries == 3 ? 25 : 20;
        } else {
            combination = Combination.NONE;
        }
        tries -= 1;
        return combination;
    }

    public void playBot(int tryNumber) {
        if (tryNumber == 0) {
            Arrays.fill(changed, true);
            return;
        }

        changed = minimax(0, changed, true, 3 - tryNumber);
    }

    private boolean[] minimax(int score, boolean[] changed, boolean maxing, int depth) {
        if (depth <= 1) {
            return changed;
        }

        if (maxing) {

        } else {

        }

        return changed;
    }

    // core

    @Override
    public String toString() {
        return "Player{" +
                "points=" + points +
                ", tries=" + tries +
                ", roundPoints=" + roundPoints +
                ", changed=" + Arrays.toString(changed) +
                ", dices=" + dices +
                '}';
    }
}
