package lab.entities;

public class Cell {

    private final int x, y;
    private final Type type;

    public Cell(int x, int y, Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Type getType() {
        return type;
    }

    public boolean hasCoordinates(int x, int y) {
        return this.x == x && this.y == y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (x != cell.x) return false;
        if (y != cell.y) return false;
        return type == cell.type;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", type=" + type +
                '}';
    }

    public enum Type {
        PATH('0'), WALL('█');

        private final char ch;

        Type(char ch) {
            this.ch = ch;
        }

        public char getChar() {
            return ch;
        }

        public static Type getByOrdinal(int index) {
            for (int i = 0; i < values().length; i++) {
                if (index == i) {
                    return values()[i];
                }
            }
            return null;
        }

        public static Type getByChar(char ch) {
            for (Type type : values()) {
                if (type.getChar() == ch) {
                    return type;
                }
            }

            throw new IllegalArgumentException("No such type: '" + ch + "'");
        }
    }
}
