package lab.algorithms;

import lab.entities.Cell;
import lab.entities.Maze;

import java.util.*;

public class BfsAlgorithm {

    private static final int[] DIR_X = {-1, 0, 1, 0};
    private static final int[] DIR_Y = {0, -1, 0, 1};

    public static List<Cell> solve(Maze maze, Cell start, Cell end) {
        if (start.getType() != Cell.Type.PATH || end.getType() != Cell.Type.PATH) {
            return null;
        }
        if (start.equals(end)) {
            return new ArrayList<>(0);
        }

        boolean[][] visited = new boolean[maze.getHeight()][maze.getWidth()];
        visited[start.getY()][start.getX()] = true;

        Deque<QueueItem> queue = new ArrayDeque<>();
        queue.add(new QueueItem(start));

        while (!queue.isEmpty()) {
            QueueItem item = queue.poll();
            Cell cell = item.cell;

            if (cell.equals(end)) {
                return getPath(item);
            }

            for (int i = 0; i < 4; i++) {
                int x = cell.getX() + DIR_X[i];
                int y = cell.getY() + DIR_Y[i];

                if (!isInside(maze, x, y) || visited[y][x]) {
                    continue;
                }

                Cell neighbourCell = maze.getCell(x, y);

                if (neighbourCell.getType() != Cell.Type.PATH || visited[y][x]) {
                    continue;
                }

                visited[y][x] = true;
                queue.add(new QueueItem(neighbourCell, item));
            }
        }

        return null;
    }

    private static List<Cell> getPath(QueueItem item) {
        Deque<Cell> deque = new ArrayDeque<>();

        while (item != null) {
            deque.addFirst(item.cell);
            item = item.previous;
        }

        return new ArrayList<>(deque);
    }

    private static boolean isInside(Maze maze, int x, int y) {
        return 0 <= x && x < maze.getWidth() &&
                0 <= y && y < maze.getHeight();
    }

    private static class QueueItem {

        private final Cell cell;
        private final QueueItem previous;

        public QueueItem(Cell cell) {
            this.cell = cell;
            previous = null;
        }

        public QueueItem(Cell cell, QueueItem previous) {
            this.cell = cell;
            this.previous = previous;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            QueueItem queueItem = (QueueItem) o;

            if (!cell.equals(queueItem.cell)) return false;
            return Objects.equals(previous, queueItem.previous);
        }

        @Override
        public int hashCode() {
            int result = cell.hashCode();
            result = 31 * result + (previous != null ? previous.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "QueueCell{" +
                    "cell=" + cell +
                    ", previous=" + previous +
                    '}';
        }
    }
}
