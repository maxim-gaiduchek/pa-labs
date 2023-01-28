package lab.entities;

import java.util.HashSet;
import java.util.Set;

public class Node {

    private final int id;
    private Set<Node> nodes;
    private int color = -1;

    public Node(int id) {
        this.id = id;
        nodes = new HashSet<>();
    }

    // getters

    public int getId() {
        return id;
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public int getDegree() {
        return nodes.size();
    }

    public int getColor() {
        return color;
    }

    public boolean isPained() {
        return color >= 0;
    }

    // setters

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }

    public boolean containsNode(Node node) {
        return nodes.contains(node);
    }

    public void addNode(Node node) {
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

        Node graphNode = (Node) o;

        if (color != graphNode.color) return false;
        return nodes.equals(graphNode.nodes);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", color=" + color +
                '}';
    }
}
