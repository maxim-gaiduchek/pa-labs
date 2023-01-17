package lab.factories;

import lab.entities.Cell;
import lab.entities.Maze;

import java.util.Random;

public class MazeFactory {

    public Maze generate(int height, int width) {
        if (height <= 0) {
            throw new IllegalArgumentException("Height is <= 0");
        }
        if (width <= 0) {
            throw new IllegalArgumentException("Height is <= 0");
        }

        Random random = new Random();
        Cell[][] grid = new Cell[height][width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int num = random.nextInt(Cell.Type.values().length);
                grid[y][x] = new Cell(x, y, Cell.Type.getByOrdinal(num));
            }
        }

        return new Maze(grid);
    }

    public Maze parse(String mazeStr) {
        if ("".equals(mazeStr)) {
            throw new IllegalArgumentException("Maze string is empty");
        }

        String[] lines = mazeStr.split("\n");
        int height = lines.length, width = lines[0].length();
        Cell[][] grid = new Cell[height][width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                char ch = lines[y].charAt(x);
                grid[y][x] = new Cell(x, y, Cell.Type.getByChar(ch));
            }
        }

        return new Maze(grid);
    }
}
