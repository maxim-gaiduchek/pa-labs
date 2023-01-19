package lab;

import lab.algorithms.GeneticAlgorithm;
import lab.entities.Path;
import lab.utils.Timer;

import java.util.Random;

public class Main {

    private static final int CITIES_NUMBER = 300;
    private static final int PATH_MIN_VALUE = 5;
    private static final int PATH_MAX_VALUE = 150;

    public static void main(String[] args) {
        //int[][] matrix = generateMatrix();
        int[][] matrix = {
                {107, 54, 50, 40, 136, 141, 76, 26, 5, 87, 137, 5, 100, 64, 80, 133, 82, 105, 92, 150},
                {140, 80, 143, 108, 40, 124, 145, 35, 114, 109, 40, 29, 117, 27, 149, 62, 46, 109, 100, 122},
                {126, 137, 68, 108, 105, 15, 96, 89, 47, 8, 48, 55, 6, 89, 69, 117, 135, 145, 132, 142},
                {20, 56, 37, 20, 143, 142, 73, 37, 56, 46, 72, 8, 6, 45, 52, 113, 112, 20, 97, 139},
                {18, 146, 46, 125, 48, 70, 149, 6, 94, 68, 12, 88, 101, 59, 120, 68, 142, 45, 111, 101},
                {20, 90, 61, 39, 136, 121, 76, 120, 124, 23, 131, 22, 140, 122, 5, 22, 67, 39, 149, 16},
                {28, 33, 78, 62, 11, 50, 16, 105, 7, 40, 111, 47, 86, 80, 67, 46, 44, 15, 76, 102},
                {150, 149, 104, 35, 14, 61, 107, 134, 105, 116, 16, 78, 150, 51, 101, 20, 141, 50, 50, 94},
                {93, 55, 92, 105, 121, 102, 15, 34, 117, 133, 67, 148, 58, 107, 27, 92, 31, 66, 113, 41},
                {132, 44, 71, 115, 87, 119, 55, 92, 23, 33, 57, 113, 34, 56, 113, 115, 78, 56, 43, 57},
                {83, 62, 149, 87, 94, 57, 71, 63, 48, 69, 9, 41, 73, 61, 49, 84, 71, 114, 96, 92},
                {39, 141, 21, 46, 136, 102, 82, 74, 122, 18, 73, 96, 65, 15, 113, 24, 50, 138, 12, 99},
                {62, 44, 41, 123, 140, 135, 127, 71, 142, 20, 64, 98, 133, 111, 145, 82, 77, 13, 65, 24},
                {94, 124, 48, 136, 18, 17, 72, 43, 25, 60, 140, 58, 36, 45, 52, 44, 142, 30, 106, 99},
                {96, 33, 58, 91, 129, 29, 129, 80, 136, 31, 90, 35, 108, 42, 100, 119, 58, 55, 70, 8},
                {28, 109, 9, 48, 21, 11, 14, 84, 51, 40, 47, 7, 142, 79, 90, 25, 148, 70, 147, 94},
                {92, 35, 110, 23, 128, 129, 10, 118, 31, 107, 97, 129, 122, 34, 55, 128, 16, 99, 109, 95},
                {35, 107, 116, 27, 131, 18, 75, 9, 14, 87, 125, 16, 37, 32, 115, 22, 140, 95, 142, 97},
                {52, 10, 57, 23, 8, 15, 37, 77, 116, 140, 72, 27, 82, 80, 117, 15, 56, 54, 101, 6},
                {120, 63, 57, 133, 116, 73, 31, 128, 95, 34, 132, 67, 96, 137, 58, 37, 47, 80, 72, 94}
        };
        double mutationProbability = 0.3;
        int parts = 4;
        Timer timer = new Timer();
        System.out.println("Genetic algorithm in process...");
        timer.start();
        Path path = GeneticAlgorithm.solve(matrix, mutationProbability, parts);
        timer.stop();
        System.out.println("Time spent: " + timer.getTime());
        System.out.println("Best cost: " + path.getCost());
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