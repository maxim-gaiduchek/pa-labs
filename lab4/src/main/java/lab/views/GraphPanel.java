package lab.views;

import lab.entities.Node;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class GraphPanel extends JPanel {

    private static final int GRAPH_NODES_WIDTH = 20;
    private static final int GRAPH_NODES_HEIGHT = 10;
    private static final int GRAPH_NODES_DOT_RADIUS = 15;

    private final List<Node> nodes;

    public GraphPanel(List<Node> nodes) {
        super();
        this.nodes = nodes;

        setSize((2 * GRAPH_NODES_WIDTH + 3) * 2 * GRAPH_NODES_DOT_RADIUS,
                (2 * GRAPH_NODES_HEIGHT + 3) * 2 * GRAPH_NODES_DOT_RADIUS);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int colorsCount = nodes.stream()
                .mapToInt(Node::getColor)
                .max()
                .orElse(-1);
        Set<Color> colors = new HashSet<>();
        Random random = new Random();
        for (int i = 0; i <= colorsCount; i++) {
            colors.add(new Color(random.nextFloat(), random.nextFloat(), random.nextFloat()));
        }
        List<Color> colorsList = new ArrayList<>(colors);

        Graphics2D g2d = (Graphics2D) g;
        for (Node node : nodes) {
            Point point = getPoint(node);
            int x = point.x - GRAPH_NODES_DOT_RADIUS, y = point.y - GRAPH_NODES_DOT_RADIUS;
            g2d.setColor(colorsList.get(node.getColor()));
            g2d.fillOval(x, y, 2 * GRAPH_NODES_DOT_RADIUS, 2 * GRAPH_NODES_DOT_RADIUS);
        }
        g2d.setColor(Color.BLACK);
        for (Node node : nodes) {
            Point point0 = getPoint(node);
            for (Node nextNode : node.getNodes()) {
                Point point = getPoint(nextNode);
                g2d.drawLine(point0.x, point0.y, point.x, point.y);
            }
        }
    }

    private Point getPoint(Node node) {
        int id = node.getId();
        int x = GRAPH_NODES_DOT_RADIUS * 3 + 4 * GRAPH_NODES_DOT_RADIUS * (id % GRAPH_NODES_WIDTH);
        int y = GRAPH_NODES_DOT_RADIUS * 3 + 4 * GRAPH_NODES_DOT_RADIUS * ((id / GRAPH_NODES_WIDTH) % GRAPH_NODES_HEIGHT);
        return new Point(x, y);
    }
}
