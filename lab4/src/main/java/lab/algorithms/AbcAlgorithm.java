package lab.algorithms;

import lab.entities.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AbcAlgorithm {

    private static final int MAX_ITERATIONS = 10000;
    private static final int EXPLORER_BEES = 2;
    private static final int TOTAL_BEES = 30;

    public static void paint(List<Node> nodes) {
        List<Node> nodesCopy = copyGraphNodes(nodes), resultNodes;
        Colors colors = new Colors();
        calcChromaticNumber(nodesCopy, colors);
        resultNodes = copyGraphNodesWithColors(nodesCopy);
        int bestChromaticNumber = colors.getChromaticNumber();

        for (int i = 0; i < MAX_ITERATIONS; i += 1) {
            System.out.printf("Iteration: %d, Best chromatic number: %d, Last chromatic number: %d\n",
                    i, bestChromaticNumber, colors.getChromaticNumber());

            nodesCopy = copyGraphNodes(nodes);
            colors = new Colors();
            calcChromaticNumber(nodesCopy, colors);
            if (colors.getChromaticNumber() >= bestChromaticNumber) {
                continue;
            }
            resultNodes = copyGraphNodesWithColors(nodesCopy);
            bestChromaticNumber = colors.getChromaticNumber();
        }
        nodes.clear();
        nodes.addAll(resultNodes);

        System.out.println("\nFinal iteration");
        System.out.printf("Iteration: %d, Best chromatic number: %d, Last chromatic number: %d\n",
                MAX_ITERATIONS, bestChromaticNumber, colors.getChromaticNumber());
    }

    private static List<Node> copyGraphNodes(List<Node> nodes) {
        List<Node> newNodes = nodes.stream().map(node -> new Node(node.getId())).toList();
        for (Node newNode : newNodes) {
            newNode.setNodes(
                    nodes.get(newNode.getId()).getNodes().stream()
                            .map(node -> newNodes.get(node.getId()))
                            .collect(Collectors.toSet())
            );
        }
        return newNodes;
    }

    private static List<Node> copyGraphNodesWithColors(List<Node> nodes) {
        return copyGraphNodes(nodes).stream()
                .peek(node -> node.setColor(nodes.get(node.getId()).getColor()))
                .toList();
    }

    private static void calcChromaticNumber(List<Node> nodes, Colors colors) {
        List<Node> availableNodes = new ArrayList<>(nodes);
        while (nodes.stream().anyMatch(node -> !node.isPained())) {
            List<Node> selectedNodes = selectNodes(availableNodes);
            paintSelectedNodes(selectedNodes, colors);
        }
    }

    private static List<Node> selectNodes(List<Node> nodes) {
        List<Node> selectedNodes = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < EXPLORER_BEES && !nodes.isEmpty(); i++) {
            int index = random.nextInt(nodes.size());
            Node node = nodes.get(index);
            selectedNodes.add(node);
            nodes.remove(index);
        }
        return selectedNodes;
    }

    private static void paintSelectedNodes(List<Node> selectedNodes, Colors colors) {
        int[] degrees = selectedNodes.stream()
                .mapToInt(Node::getDegree)
                .toArray();
        int totalDegrees = Arrays.stream(degrees).sum();
        double[] nectarValues = Arrays.stream(degrees)
                .mapToDouble(degree -> (double) degree / totalDegrees)
                .toArray();
        int onlookerBeesCount = TOTAL_BEES - EXPLORER_BEES;
        int[] beesCounts = new int[nectarValues.length];
        for (int i = 0; i < nectarValues.length; i++) {
            beesCounts[i] = (int) (onlookerBeesCount * nectarValues[i]);
            onlookerBeesCount -= beesCounts[i];
        }
        for (int i = 0; i < selectedNodes.size(); i++) {
            Node node = selectedNodes.get(i);
            List<Node> neighbourNodes = new ArrayList<>(node.getNodes());
            for (int j = 0; j < neighbourNodes.size() && j < beesCounts[i] - 1; j++) {
                paintNode(neighbourNodes.get(j), colors);
            }
            paintNode(node, colors);
        }
    }

    private static void paintNode(Node node, Colors colors) {
        if (node.isPained()) {
            return;
        }
        if (colors.hasNoColors()) {
            node.setColor(colors.increaseColors());
            return;
        }
        for (int i = 0; i <= colors.getColor(); i++) {
            final int color = i;
            if (node.getNodes().stream().noneMatch(neighbourNodes -> neighbourNodes.getColor() == color)) {
                node.setColor(color);
                return;
            }
        }
        node.setColor(colors.increaseColors());
    }
}
