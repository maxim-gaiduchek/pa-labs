package com.example.lab6.algorithms;

import com.example.lab6.Combination;

import java.util.ArrayList;
import java.util.List;

public class MinimaxAlgorithm {

    private MinimaxAlgorithm() {
    }

    public static MinimaxResult findOptimal(int score, boolean[] changes, List<Integer> dices, boolean maxing, int depth) {
        if (depth <= 0) {
            return new MinimaxResult(score, changes);
        }

        MinimaxResult result = new MinimaxResult(Integer.MIN_VALUE, null);
        boolean[] caseChanges;
        if (maxing) {
            for (int i = 0; i <= 5; i++) {
                for (String change : createChanges("", 5, i, 0)) {
                    caseChanges = new boolean[5];
                    for (int j = 0; j < 5; j++) {
                        caseChanges[j] = change.charAt(j) == '1';
                    }
                    MinimaxResult caseResult = findOptimal(score, caseChanges, dices, false, depth - 1);
                    if (result.compareTo(caseResult) < 0) {
                        result = caseResult;
                    }
                }
            }
        } else {
            for (List<Integer> combination : createDicesCombinations(new ArrayList<>(), dices, changes, 5, 0)) {
                Combination caseCombination = Combination.calcCombination(combination);
                int caseScore = caseCombination.getPoints(depth);
                MinimaxResult caseResult = findOptimal(caseScore, changes, combination, true, depth);
                if (result.compareTo(caseResult) < 0) {
                    result = caseResult;
                }
            }
        }

        return result;
    }

    private static List<String> createChanges(String head, int n, int ones, int usedOnes) {
        if (head.length() == n) {
            return List.of(head);
        }

        List<String> changes = new ArrayList<>();
        if (head.length() - usedOnes < n - ones) {
            changes.addAll(createChanges(head + "0", n, ones, usedOnes));
        }
        if (usedOnes < ones) {
            changes.addAll(createChanges(head + "1", n, ones, usedOnes + 1));
        }
        return changes;
    }

    private static List<List<Integer>> createDicesCombinations(List<Integer> head, List<Integer> dices,
                                                               boolean[] changes, int n, int count) {
        if (head.size() == n) {
            return List.of(head);
        }
        if (!changes[count]) {
            List<Integer> combination = new ArrayList<>(head);
            combination.add(dices.get(count));
            return createDicesCombinations(combination, dices, changes, n, count + 1);
        }

        List<List<Integer>> combinations = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            List<Integer> combination = new ArrayList<>(head);
            combination.add(i);
            combinations.addAll(createDicesCombinations(combination, dices, changes, n, count + 1));
        }
        return combinations;
    }
}
