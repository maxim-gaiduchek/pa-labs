package lab.algorithms;

import lab.entities.Individual;

import java.util.*;
import java.util.stream.IntStream;

public class GeneticAlgorithm {

    private static final int POPULATION_SIZE = 1000;
    private static final long MAX_ITERATIONS = (long) Math.pow(10, 8);

    public static Individual solve(int[][] matrix, double mutationProbability) {
        List<Individual> population = createInitialGeneration(matrix);
        for (long i = 0; i < MAX_ITERATIONS; i++) {
            reproduce(population, matrix, mutationProbability);
            removeWeak(population);

            System.out.printf("Iteration: %d, Best cost: %d\n",
                    i, getBestPath(population).getCost());
        }

        return getBestPath(population);
    }

    private static List<Individual> createInitialGeneration(int[][] matrix) {
        List<Individual> generation = new ArrayList<>(POPULATION_SIZE);
        for (int i = 0; i < POPULATION_SIZE; i++) {
            List<Integer> pathList = new ArrayList<>(IntStream.range(0, matrix.length).boxed().toList());
            Collections.shuffle(pathList);
            int[] path = pathList.stream().mapToInt(Integer::intValue).toArray();
            generation.add(new Individual(path, calcCost(matrix, path)));
        }
        return generation;
    }

    private static int calcCost(int[][] matrix, int[] path) {
        int sum = 0;
        for (int i = 0; i < path.length - 1; i++) {
            sum += matrix[path[i]][path[i + 1]];
        }
        return sum + matrix[path[path.length - 1]][path[0]];
    }

    private static void reproduce(List<Individual> population, int[][] matrix, double mutationProbability) {
        Random random = new Random();
        int parent0Index = random.nextInt(population.size());
        int parent1Index = random.nextInt(population.size());
        while (parent0Index == parent1Index) {
            parent1Index = random.nextInt(population.size());
        }
        Individual parent0 = population.get(parent0Index);
        Individual parent1 = population.get(parent1Index);
        int splitPoint = random.nextInt(1, parent0.getPath().length);
        Individual child0 = crossover(parent0, parent1, splitPoint, matrix);
        Individual child1 = crossover(parent1, parent0, splitPoint, matrix);
        population.add(child0);
        population.add(child1);
        mutate(child0, mutationProbability);
        mutate(child1, mutationProbability);
    }

    private static Individual crossover(Individual parent0, Individual parent1, int splitPoint, int[][] matrix) {
        List<Integer> path = new ArrayList<>(Arrays.stream(parent0.getPath()).limit(splitPoint).boxed().toList());
        for (int i = splitPoint; i < parent1.getPath().length; i++) {
            if (path.contains(parent1.getPath()[i])) {
                continue;
            }
            path.add(parent1.getPath()[i]);
        }
        if (path.size() < parent0.getPath().length) {
            for (int i = splitPoint; i < parent0.getPath().length; i++) {
                if (path.contains(parent0.getPath()[i])) {
                    continue;
                }
                path.add(parent0.getPath()[i]);
            }
        }
        int[] pathArr = path.stream().mapToInt(num -> num).toArray();
        return new Individual(pathArr, calcCost(matrix, pathArr));
    }

    private static void mutate(Individual individual, double mutationProbability) {
        Random random = new Random();
        if (random.nextDouble() < mutationProbability) {
            return;
        }
        int first = random.nextInt(individual.getPath().length - 1);
        int second = random.nextInt(individual.getPath().length - 1);
        int temp = individual.getPath()[first];
        individual.getPath()[first] = individual.getPath()[second];
        individual.getPath()[second] = temp;
    }

    private static void removeWeak(List<Individual> population) {
        population.sort(Comparator.comparing(Individual::getCost));
        population.remove(population.size() - 1);
        population.remove(population.size() - 1);
    }

    private static Individual getBestPath(List<Individual> population) {
        return population.stream()
                .min(Comparator.comparing(Individual::getCost))
                .orElse(null);
    }
}
