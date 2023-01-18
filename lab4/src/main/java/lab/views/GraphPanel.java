package lab.views;

import lab.entities.GraphNode;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class GraphPanel extends JPanel {

    public static final int GRAPH_NODES_WIDTH = 20;
    private static final int GRAPH_NODES_HEIGHT = 10;
    private static final int GRAPH_NODES_DOT_RADIUS = 10;

    private final List<GraphNode> nodes;

    public GraphPanel(List<GraphNode> nodes) {
        super();
        this.nodes = nodes;

        setSize((2 * GRAPH_NODES_WIDTH + 3) * 2 * GRAPH_NODES_DOT_RADIUS,
                (2 * GRAPH_NODES_HEIGHT + 3) * 2 * GRAPH_NODES_DOT_RADIUS);
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        for (GraphNode node : nodes) {
            Point point = getPoint(node);
            int x = point.x - GRAPH_NODES_DOT_RADIUS, y = point.y - GRAPH_NODES_DOT_RADIUS;
            g2d.setColor(Color.BLUE);
            g2d.fillOval(x, y, 2 * GRAPH_NODES_DOT_RADIUS, 2 * GRAPH_NODES_DOT_RADIUS);
        }
        for (GraphNode node : nodes) {
            Point point0 = getPoint(node);
            int x0 = point0.x, y0 = point0.y;
            g2d.setColor(Color.BLACK);
            for(GraphNode nextNode : node.getNodes()) {
                Point point = getPoint(nextNode);
                g2d.drawLine(x0, y0, point.x, point.y);
            }
        }
    }

    private Point getPoint(GraphNode node) {
        int id = node.getId();
        int x = GRAPH_NODES_DOT_RADIUS * 3 + 4 * GRAPH_NODES_DOT_RADIUS * (id % GRAPH_NODES_WIDTH);
        int y = GRAPH_NODES_DOT_RADIUS * 3 + 4 * GRAPH_NODES_DOT_RADIUS * ((id / GRAPH_NODES_WIDTH) % GRAPH_NODES_HEIGHT);
        return new Point(x, y);
    }
}
