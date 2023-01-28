package com.example.lab6;

import java.util.List;

public class Combination {

    public static final Combination GENERAL = new Combination("Генерал", Integer.MAX_VALUE, 60);
    public static final Combination FOUR = new Combination("Чотири", 45, 40);
    public static final Combination FULL_HOUSE = new Combination("Фулл хауз", 35, 30);
    public static final Combination STRAIGHT = new Combination("Стрейт", 25, 20);

    private final String title;
    private final int maxPoints;
    private final int minPoints;

    Combination(String title, int maxPoints, int minPoints) {
        this.title = title;
        this.maxPoints = maxPoints;
        this.minPoints = minPoints;
    }

    public String getTitle() {
        return title;
    }

    public int getPoints(int tryNumber) {
        return tryNumber == 3 ? maxPoints : minPoints;
    }

    public static Combination calcCombination(List<Integer> dices) {
        List<Integer> sortedDices = dices.stream().sorted().toList();
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
            return Combination.GENERAL;
        } else if (sameCount == 4) {
            return Combination.FOUR;
        } else if (firstPartCount + sameCount == 5) {
            return Combination.FULL_HOUSE;
        } else if (straight) {
            return Combination.STRAIGHT;
        }

        int prev = sortedDices.get(0), sum = prev;
        int maxNum = prev, maxSum = sum;
        for (int i = 1; i < sortedDices.size(); i++) {
            int num = sortedDices.get(i);
            if (prev == num) {
                sum += num;
            } else {
                sum = num;
            }
            if (sum > maxSum) {
                maxSum = sum;
                maxNum = num;
            }
            prev = num;
        }
        int count = maxSum / maxNum;
        String pattern = switch (count) {
            case 1 -> "Одна %d-ка";
            case 2 -> "Дві %d-ки";
            case 3 -> "Три %d-ки";
            case 4 -> "Чотири %d-ки";
            case 5 -> "П'ять %d-ок";
            case 6 -> "Шість %d-ок";
            default -> throw new IllegalArgumentException("Count > 6");
        };
        return new Combination(pattern.formatted(maxNum), maxSum, maxSum);
    }
}
