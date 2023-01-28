package com.example.lab6.algorithms;

import java.util.Arrays;

public class MinimaxResult implements Comparable<MinimaxResult> {

    private final int score;
    private final boolean[] changes;

    MinimaxResult(int score, boolean[] changes) {
        this.score = score;
        this.changes = changes;
    }

    public boolean[] getChanges() {
        return changes;
    }

    @Override
    public int compareTo(MinimaxResult o) {
        if (score == Integer.MIN_VALUE) return -1;
        return score - o.score;
    }

    @Override
    public String toString() {
        return "MinimaxResult{" +
                "score=" + score +
                ", changes=" + Arrays.toString(changes) +
                '}';
    }
}
