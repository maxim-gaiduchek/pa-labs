package lab;

import lab.controllers.WindowController;
import lab.entities.GraphNode;
import lab.views.GraphPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private static final int GRAPH_SIZE = 200;
    private static final int GRAPH_MIN_DEGREE = 1;
    private static final int GRAPH_MAX_DEGREE = 20;

    public static void main(String[] args) {
        List<GraphNode> nodes = generateGraph();
        GraphPanel panel = new GraphPanel(nodes);
        WindowController controller = new WindowController();

        controller.setPanel(panel);
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
                    node.setNodes(IntStream.rangeClosed(GRAPH_MIN_DEGREE, GRAPH_MAX_DEGREE)
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
            /*for (int j = 0; j < random.nextInt(GRAPH_MIN_DEGREE, GRAPH_MAX_DEGREE + 1); j++) {

            }*/
        }

        return nodes;
    }

    private static GraphNode getNode(List<GraphNode> nodes, int i) {
        Random random = new Random();
        GraphNode node;
        int index;
        do {
            index = random.nextInt(nodes.size());
            node = nodes.get(index);
        } while (index == i);
        return node;
    }
}
