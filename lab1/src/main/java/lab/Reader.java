package lab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Reader {

    private final File file;
    private final Scanner scanner;
    private int maxValue = Integer.MIN_VALUE;
    private boolean closed = false;

    Reader(File file) throws FileNotFoundException {
        this.file = file;
        scanner = new Scanner(new FileReader(file));
    }

    // getters

    String getFilePath() {
        return file.getPath();
    }

    public int getMaxValue() {
        return maxValue;
    }

    int nextInt() {
        return scanner.nextInt();
    }

    boolean hasNext() {
        return scanner.hasNext();
    }

    boolean isClosed() {
        return closed;
    }

    // setters

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    void close() {
        scanner.close();
        closed = true;
    }
}
