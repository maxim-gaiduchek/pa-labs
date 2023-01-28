package com.example.lab6;

import com.example.lab6.entities.Player;

import java.util.List;

public class Game {

    private static int roundsCounter = 10;
    private static final List<Player> players = List.of(new Player(), new Player());
    private static int playerIndex = 0;

    private Game() {
    }

    // getters

    public static boolean isPlayerTurn() {
        return playerIndex == 0;
    }

    public static boolean hasPlayerWon() {
        Player player = players.get(0);
        for (int i = 1; i < players.size(); i++) {
            if (player.getPoints() < players.get(i).getPoints()) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasBotWon() {
        Player player = players.get(0);
        for (int i = 1; i < players.size(); i++) {
            if (player.getPoints() > players.get(i).getPoints()) {
                return false;
            }
        }
        return true;
    }

    public static int getRoundsCounter() {
        return roundsCounter;
    }

    public static Player getCurrentPlayer() {
        return players.get(playerIndex);
    }

    // setters

    public static void nextPlayer() {
        playerIndex = (playerIndex + 1) % players.size();
        if (playerIndex == 0) {
            roundsCounter--;
        }
    }

    // game

    public static void endTurn() {
        nextPlayer();
        getCurrentPlayer().reset();
    }

    public static void reset() {
        playerIndex = 0;
        roundsCounter = 10;
        for (Player player : players) {
            player.reset();
        }
    }
}
