package lab.entities;

import java.util.HashSet;
import java.util.Set;

public class GraphNode {

    private final int id;
    private Set<GraphNode>nodes;
    private int color = 0;

    public GraphNode(int id) {
        this.id = id;
        nodes = new HashSet<>();
    }

    public GraphNode(int id, Set<GraphNode> nodes) {
        this.id = id;
        this.nodes = nodes;
    }

    public GraphNode(int id, int color) {
        this.id = id;
        nodes = new HashSet<>();
        this.color = color;
    }

    // getters

    public int getId() {
        return id;
    }

    public Set<GraphNode> getNodes() {
        return nodes;
    }

    public int getColor() {
        return color;
    }

    // setters

    public void setNodes(Set<GraphNode> nodes) {
        this.nodes = nodes;
    }

    public void addNode(GraphNode node) {
        nodes.add(node);
    }

    public void setColor(int color) {
        this.color = color;
    }

    // core

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GraphNode graphNode = (GraphNode) o;

        if (color != graphNode.color) return false;
        return nodes.equals(graphNode.nodes);
    }

    @Override
    public String toString() {
        return "GraphNode{" +
                "id=" + id +
                "color=" + color +
                '}';
    }
}
