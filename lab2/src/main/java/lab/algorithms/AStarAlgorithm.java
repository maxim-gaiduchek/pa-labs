package lab.algorithms;

import lab.entities.Cell;
import lab.entities.Maze;
import lab.entities.Result;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class AStarAlgorithm {

    private static final int[] DIR_X = {-1, 0, 1, 0};
    private static final int[] DIR_Y = {0, -1, 0, 1};

    public static Result solve(Maze maze, Cell start, Cell end) {
        if (start.getType() != Cell.Type.PATH || end.getType() != Cell.Type.PATH) {
            return new Result();
        }
        if (start.equals(end)) {
            return new Result(new ArrayList<>(0), 0, 0, 0);
        }

        Result result = new Result();
        boolean[][] visited = new boolean[maze.getHeight()][maze.getWidth()];
        visited[start.getY()][start.getX()] = true;
        Deque<QueueItem> queue = new ArrayDeque<>();
        queue.add(new QueueItem(start));

        while (!queue.isEmpty()) {
            QueueItem item = queue.poll();
            Cell cell = item.cell;
            result.increaseIterations();

            if (cell.equals(end)) {
                result.setPath(item.path.stream().map(i -> i.cell).toList());
                return result;
            }

            calcManhattanDistance(item, end);
            for (int i = 0; i < 4; i++) {
                result.increaseStates();
                int x = cell.getX() + DIR_X[i];
                int y = cell.getY() + DIR_Y[i];

                if (!isInside(maze, x, y) || visited[y][x]) {
                    continue;
                }

                Cell neighbourCell = maze.getCell(x, y);

                if (neighbourCell.getType() != Cell.Type.PATH) {
                    continue;
                }

                result.increaseSavedStates();
                visited[y][x] = true;
                QueueItem neighbourItem = new QueueItem(neighbourCell, item);
                neighbourItem.path.add(neighbourItem);
                calcManhattanDistance(neighbourItem, end);
                addToQueue(neighbourItem, queue);
            }
        }
        return new Result();
    }

    private static void calcManhattanDistance(QueueItem item, Cell end) {
        int x = Math.abs(end.getX() - item.cell.getX());
        int y = Math.abs(end.getY() - item.cell.getY());
        item.distance = x + y;
    }

    private static void addToQueue(QueueItem item, Deque<QueueItem> queue) {
        if (queue.isEmpty()) {
            queue.add(item);
            return;
        }

        List<QueueItem> queueList = new ArrayList<>(queue);
        int index = 0;
        QueueItem queueItem = queueList.get(0);

        while (index < queue.size() - 1 &&
                item.distance + item.path.size() > queueItem.distance + queueItem.path.size()) {
            queueItem = queueList.get(++index);
        }
        queueList.add(index, item);
        queue.clear();
        queue.addAll(queueList);
    }

    private static boolean isInside(Maze maze, int x, int y) {
        return 0 <= x && x < maze.getWidth() &&
                0 <= y && y < maze.getHeight();
    }

    private static class QueueItem {

        private final Cell cell;
        private int distance;
        private final List<QueueItem> path;

        public QueueItem(Cell cell) {
            this.cell = cell;
            path = new ArrayList<>();
        }

        public QueueItem(Cell cell, QueueItem previous) {
            this.cell = cell;
            path = new ArrayList<>(previous.path);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            QueueItem item = (QueueItem) o;

            if (distance != item.distance) return false;
            if (!cell.equals(item.cell)) return false;
            return path.equals(item.path);
        }

        @Override
        public int hashCode() {
            int result = cell.hashCode();
            result = 31 * result + distance;
            result = 31 * result + path.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "QueueItem{" +
                    "cell=" + cell +
                    ", distance=" + distance +
                    ", path=" + path +
                    '}';
        }
    }
}
