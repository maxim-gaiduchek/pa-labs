package lab.algorithms;

class Colors {

    private int colors = -1;

    public Colors() {
    }

    public int increaseColors() {
        return ++colors;
    }

    public int getColor() {
        if (hasNoColors()) {
            throw new IllegalArgumentException("None colors are stored");
        }

        return colors;
    }

    public int getChromaticNumber() {
        return colors + 1;
    }

    public boolean hasNoColors() {
        return colors < 0;
    }

    @Override
    public String toString() {
        return "Colors{" +
                "currentColor=" + colors +
                '}';
    }
}
