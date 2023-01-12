package lab;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;

public class FileGenerator {

    public static void generateFileBySize(String fileName, int minRange, int maxRange, long maxSize) throws IOException {
        Writer writer = new FileWriter(fileName);
        Random random = new Random();

        while (new File(fileName).length() < maxSize) {
            writer.write(random.nextInt(minRange, maxRange + 1) + "\n");
            writer.flush();
        }

        writer.close();
    }

    public static void generateFileByLinesCount(String fileName, int minRange, int maxRange, long maxCount) throws IOException {
        Writer writer = new FileWriter(fileName);
        Random random = new Random();

        for (long i = 0; i < maxCount; i++) {
            writer.write(random.nextInt(minRange, maxRange + 1) + "\n");
            writer.flush();
        }

        writer.close();
    }
}
