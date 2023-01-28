package lab.entities;

public class Maze {

    private final Cell[][] grid;

    public Maze(Cell[][] grid) {
        this.grid = grid;
    }

    public Cell getCell(int x, int y) {
        return grid[y][x];
    }

    public Cell.Type getCellType(int x, int y) {
        return getCell(x, y).getType();
    }

    public int getHeight() {
        return grid.length;
    }

    public int getWidth() {
        return grid[0].length;
    }
}
