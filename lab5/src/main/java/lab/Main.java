package lab;

import lab.algorithms.GeneticAlgorithm;
import lab.utils.Timer;

import java.util.Random;

public class Main {

    private static final int CITIES_NUMBER = 300;
    private static final int PATH_MIN_VALUE = 5;
    private static final int PATH_MAX_VALUE = 150;

    public static void main(String[] args) {
        int[][] matrix = generateMatrix();
        /*int[][] matrix = {
                {45, 110, 9, 58, 82, 97, 109, 33, 41, 49},
                {68, 71, 9, 149, 64, 120, 21, 149, 68, 53},
                {78, 67, 146, 41, 30, 121, 37, 119, 32, 121},
                {127, 94, 35, 102, 62, 94, 11, 51, 14, 110},
                {17, 83, 115, 120, 30, 50, 37, 125, 15, 55},
                {144, 134, 137, 111, 73, 58, 37, 121, 77, 43},
                {44, 135, 31, 35, 87, 32, 66, 53, 23, 96},
                {42, 118, 74, 30, 75, 133, 62, 127, 74, 59},
                {56, 68, 136, 102, 114, 142, 82, 56, 52, 66},
                {136, 100, 51, 51, 110, 142, 96, 122, 10, 31}
        };*/
        double mutationProbability = 0.3;
        int parents = 4;
        Timer timer = new Timer();
        System.out.println("Genetic algorithm in process...");
        timer.start();
        GeneticAlgorithm.solve(matrix, mutationProbability, parents);
        timer.stop();
        System.out.println("Time spent: " + timer.getTime());
    }

    private static int[][] generateMatrix() {
        int[][] matrix = new int[CITIES_NUMBER][CITIES_NUMBER];
        Random random = new Random();
        for (int i = 0; i < CITIES_NUMBER; i++) {
            for (int j = 0; j < CITIES_NUMBER; j++) {
                if (i == j) {
                    matrix[i][j] = Integer.MAX_VALUE;
                    continue;
                }
                matrix[i][j] = random.nextInt(PATH_MIN_VALUE, PATH_MAX_VALUE);
            }
        }
        return matrix;
    }
}