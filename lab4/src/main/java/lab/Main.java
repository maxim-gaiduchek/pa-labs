package lab;

import lab.algorithms.AbcAlgorithm;
import lab.controllers.WindowController;
import lab.entities.Node;
import lab.utils.Timer;
import lab.views.GraphPanel;
import lab.views.ProcessPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static final int GRAPH_SIZE = 200;
    private static final int GRAPH_MIN_DEGREE = 1;
    private static final int GRAPH_MAX_DEGREE = Math.min(GRAPH_SIZE - 1, 20);

    public static void main(String[] args) {
        WindowController controller = new WindowController();
        ProcessPanel processPanel = new ProcessPanel();
        controller.setPanel(processPanel);
        controller.showPanel();
        List<Node> nodes = generateGraph();
        Timer timer = new Timer();

        System.out.println("Drawing graph by ABC...");
        timer.start();
        AbcAlgorithm.paint(nodes);
        timer.stop();
        System.out.println("Time spent: " + timer.getTime());

        GraphPanel graphPanel = new GraphPanel(nodes);
        controller.setPanel(graphPanel);
        controller.showPanel();
    }

    private static List<Node> generateGraph() {
        List<Node> nodes = new ArrayList<>(GRAPH_SIZE);
        for (int i = 0; i < GRAPH_SIZE; i++) {
            nodes.add(new Node(i));
        }
        Random random = new Random();
        for (int i = 0; i < GRAPH_SIZE; i++) {
            Node node = nodes.get(i);
            int size = node.getDegree();
            if (size >= GRAPH_MAX_DEGREE) {
                continue;
            }
            for (int j = node.getDegree(); j < random.nextInt(GRAPH_MIN_DEGREE, GRAPH_MAX_DEGREE + 1); j++) {
                Node nextNode;
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
