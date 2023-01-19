package lab.entities;

import java.util.Arrays;

public class Path {

    private final int[] path;
    private final int cost;

    public Path(int[] path, int cost) {
        this.path = path;
        this.cost = cost;
    }

    public int[] getPath() {
        return path;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Path path = (Path) o;

        if (cost != path.cost) return false;
        return Arrays.equals(this.path, path.path);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(path);
        result = 31 * result + cost;
        return result;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "cost=" + cost +
                ", path=" + Arrays.toString(path) +
                '}';
    }
}
