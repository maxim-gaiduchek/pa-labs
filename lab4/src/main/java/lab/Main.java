package lab;

import lab.controllers.WindowController;
import lab.entities.GraphNode;
import lab.views.GraphPanel;
import lab.views.ProcessPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private static final int GRAPH_SIZE = 200;
    private static final int GRAPH_MIN_DEGREE = 1;
    private static final int GRAPH_MAX_DEGREE = Math.min(GRAPH_SIZE - 1, 20);

    public static void main(String[] args) {
        List<GraphNode> nodes = generateGraph();
        WindowController controller = new WindowController();
        ProcessPanel processPanel = new ProcessPanel();
        controller.setPanel(processPanel);
        controller.showPanel();
        GraphPanel graphPanel = new GraphPanel(nodes);
        controller.setPanel(graphPanel);
        controller.showPanel();
    }

    private static List<GraphNode> generateGraph() {
        List<GraphNode> nodes = new ArrayList<>(GRAPH_SIZE);
        for (int i = 0; i < GRAPH_SIZE; i++) {
            nodes.add(new GraphNode(i));
        }
        Random random = new Random();
        for (int i = 0; i < GRAPH_SIZE; i++) {
            GraphNode node = nodes.get(i);
            if (i == 0) {
                for (int j = 0; j < GRAPH_MAX_DEGREE; j++) {
                    node.setNodes(IntStream.rangeClosed(1, GRAPH_MAX_DEGREE)
                            .mapToObj(index -> {
                                GraphNode graphNode = nodes.get(index);
                                graphNode.addNode(node);
                                return graphNode;
                            })
                            .collect(Collectors.toSet())
                    );
                }
                continue;
            }
            int size = node.getDegree();
            if (size >= GRAPH_MAX_DEGREE) {
                continue;
            }
            for (int j = node.getDegree(); j < random.nextInt(GRAPH_MIN_DEGREE, GRAPH_MAX_DEGREE + 1); j++) {
                GraphNode nextNode;
                int index;
                do {
                    index = random.nextInt(nodes.size());
                    nextNode = nodes.get(index);
                } while (index == i || nextNode.getDegree() >= GRAPH_MAX_DEGREE || node.containsNode(nextNode));
                node.addNode(nextNode);
                nextNode.addNode(node);
            }
        }

        return nodes;
    }
}
