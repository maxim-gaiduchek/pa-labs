package lab.algorithms;

import lab.entities.Path;

import java.util.*;
import java.util.stream.IntStream;

public class GeneticAlgorithm {

    private static final int POPULATION_SIZE = 1000;
    private static final int BREEDING_POOL_SIZE = POPULATION_SIZE / 10;
    private static final long MAX_ITERATIONS = (long) Math.pow(10, 8);

    public static Path solve(int[][] matrix, double mutationProbability, int parts) {
        Path[] generation = createInitialGeneration(matrix);
        Random random = new Random();
        for (long i = 0; i < MAX_ITERATIONS; i++) {
            Path[] breedingPool = Arrays.stream(generation)
                    .sorted(Comparator.comparing(Path::getCost))
                    .limit(BREEDING_POOL_SIZE)
                    .toArray(Path[]::new);
            Path[] best = Arrays.stream(breedingPool)
                    .limit(BREEDING_POOL_SIZE / 5)
                    .toArray(Path[]::new);
            double[] probabilities = getProbabilities(breedingPool);
            Path[] newPopulation = new Path[matrix.length];

            for (int j = 0; j < matrix.length; j++) {
                if (j < BREEDING_POOL_SIZE / 5) {
                    newPopulation[j] = best[j];
                    continue;
                }

                Path first = breedingPool[choosePath(probabilities)];
                Path second = breedingPool[choosePath(probabilities)];
                newPopulation[j] = crossover(matrix, parts, first, second);
                if (random.nextDouble() < mutationProbability) {
                    mutate(newPopulation[j]);
                }
            }
            generation = newPopulation;
            System.out.printf("Iteration: %d, Best cost: %d\n",
                    i, getBestPath(generation).getCost());
        }

        return getBestPath(generation);
    }

    private static Path[] createInitialGeneration(int[][] matrix) {
        Path[] generation = new Path[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            List<Integer> pathList = new ArrayList<>(IntStream.range(0, matrix.length).boxed().toList());
            Collections.shuffle(pathList);
            int[] path = pathList.stream().mapToInt(Integer::intValue).toArray();
            generation[i] = new Path(path, calcCost(matrix, path));
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

    private static void mutate(Path path) {
        Random random = new Random();
        int first = random.nextInt(path.getPath().length - 1);
        int second = random.nextInt(path.getPath().length - 1);
        int temp = path.getPath()[first];
        path.getPath()[first] = path.getPath()[second];
        path.getPath()[second] = temp;
    }

    private static Path crossover(int[][] matrix, int parts, Path first, Path second) {
        int partSize = matrix.length / parts;
        int[] firstGenes = new int[matrix.length / 2];
        for (int i = 0; i < matrix.length / 2; i++) {
            firstGenes[i] = first.getPath()[2 * partSize * (i / partSize) + i % partSize];
        }

        int[] path = new int[matrix.length];
        int currentSecond = 0;
        for (int i = 0; i < matrix.length; i++) {
            if ((i / partSize) % 2 == 0) {
                path[i] = first.getPath()[i];
                continue;
            }

            while (Arrays.stream(firstGenes).boxed().toList().contains(second.getPath()[currentSecond])) {
                currentSecond++;
            }
            path[i] = second.getPath()[currentSecond++];
        }

        return new Path(path, calcCost(matrix, path));
    }

    private static int choosePath(double[] probabilities) {
        Random random = new Random();
        double probability = random.nextDouble();
        double sum = 0.0;
        for (int i = 0; i < probabilities.length; i++) {
            sum += probabilities[i];
            if (sum > probability) {
                return i;
            }
        }
        return probabilities.length - 1;
    }

    private static double[] getProbabilities(Path[] paths) {
        double[] values = new double[paths.length];
        double sum = 0.0;
        for (int i = 0; i < paths.length; i++) {
            values[i] = 1.0 / paths[i].getCost();
            sum += values[i];
        }
        for (int i = 0; i < values.length; i++) {
            values[i] /= sum;
        }

        return values;
    }

    private static Path getBestPath(Path[] generation) {
        return Arrays.stream(generation)
                .min(Comparator.comparing(Path::getCost))
                .orElse(null);
    }
}
