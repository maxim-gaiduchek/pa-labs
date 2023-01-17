package lab.entities;

import java.util.List;
import java.util.Objects;

public class Result {

    private List<Cell> path;
    private int iterations = 0;
    private int states = 0;
    private int savedStates = 0;

    public Result() {
    }

    public Result(List<Cell> path, int iterations, int states, int savedStates) {
        this.path = path;
        this.iterations = iterations;
        this.states = states;
        this.savedStates = savedStates;
    }

    public List<Cell> getPath() {
        return path;
    }

    public boolean hasPath() {
        return path != null;
    }

    public int getIterations() {
        return iterations;
    }

    public int getStates() {
        return states;
    }

    public int getSavedStates() {
        return savedStates;
    }

    public void setPath(List<Cell> path) {
        this.path = path;
    }

    public void increaseIterations() {
        iterations++;
    }

    public void increaseStates() {
        states++;
    }

    public void increaseSavedStates() {
        savedStates++;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Result) obj;
        return Objects.equals(this.path, that.path) &&
                this.iterations == that.iterations &&
                this.states == that.states &&
                this.savedStates == that.savedStates;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, iterations, states, savedStates);
    }

    @Override
    public String toString() {
        return "Result[" +
                "path=" + path + ", " +
                "iterations=" + iterations + ", " +
                "states=" + states + ", " +
                "savedStates=" + savedStates + ']';
    }
}
