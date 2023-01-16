package lab.files;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileUtils {

    public static void generateDataFile(String fileName, int minRange, int maxRange, long maxSize) throws IOException {
        Writer writer = new FileWriter(fileName);
        Random random = new Random();

        while (new File(fileName).length() < maxSize) {
            writer.write(random.nextInt(minRange, maxRange + 1) + "\n");
            writer.flush();
        }

        writer.close();
    }

    public static void deleteFiles(String path, int numberOfFiles) {
        for (int i = 0; i < numberOfFiles; i++) {
            new File(path + i + ".lab").delete();
        }
    }

    public static void resetFiles(String path, int numberOfFiles) {
        deleteFiles(path, numberOfFiles);

        for (int i = 0; i < numberOfFiles; i++) {
            new File(path + i + ".lab");
        }
    }

    public static List<BufferedWriter> createWriters(String path, int numberOfFiles) {
        try {
            List<BufferedWriter> writers = new ArrayList<>();

            for (int i = 0; i < numberOfFiles; i++) {
                File file = new File(path + i + ".lab");

                writers.add(new BufferedWriter(new FileWriter(file)));
            }

            return writers;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void writeToFile(String path, List<Integer> numbers) throws IOException {
        writeToFile(path, numbers, true);
    }

    public static void writeToFile(String path, List<Integer> numbers, boolean append) throws IOException {
        Writer writer = new FileWriter(path, append);

        for (int n : numbers) {
            writer.write(n + "\n");
            writer.flush();
        }

        writer.close();
    }

    public static void closeWriters(List<BufferedWriter> writers) {
        try {
            for (BufferedWriter writer : writers) {
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getIfEmptyFileIsSingle(String path, int numberOfFiles) {
        int previousFile = -1;

        for (int i = 0; i < numberOfFiles; i++) {
            if (new File(path + i + ".lab").length() > 0) {
                if (previousFile < 0) {
                    previousFile = i;
                } else {
                    return null;
                }
            }
        }

        return previousFile >= 0 ? path + previousFile + ".lab" : null;
    }

    public static List<BufferedReader> createReaders(String path, int numberOfFiles) {
        try {
            List<BufferedReader> readers = new ArrayList<>();

            for (int i = 0; i < numberOfFiles; i++) {
                File file = new File(path + i + ".lab");

                if (file.length() <= 0) {
                    continue;
                }

                readers.add(new BufferedReader(new FileReader(file)));
            }

            return readers;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String readLine(BufferedReader reader) throws IOException {
        reader.mark(100);
        String line = reader.readLine();
        reader.reset();

        return line;
    }

    public static void closeReaders(List<BufferedReader> readers) {
        try {
            for (BufferedReader reader : readers) {
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
