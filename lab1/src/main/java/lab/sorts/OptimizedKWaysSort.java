package lab.sorts;

import lab.files.FileUtils;

import java.io.*;
import java.util.List;

public class OptimizedKWaysSort {

    private static final String FILES_B_PATH = "modified/B/";
    private static final String FILE_C_PATH = "modified/C.lab";

    public static String sort(String dataFilePath, int numberOfFiles) {
        try {
            splitFiles(dataFilePath, numberOfFiles);
            sortFiles(numberOfFiles);
            merge(numberOfFiles);

            return FILE_C_PATH;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void splitFiles(String dataFilePath, int numberOfFiles) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(dataFilePath));

        FileUtils.deleteFiles(FILES_B_PATH, numberOfFiles);

        List<BufferedWriter> writers = FileUtils.createWriters(FILES_B_PATH, numberOfFiles);

        if (writers == null) {
            return;
        }

        int writerFileCount = 0;
        String line;

        while ((line = reader.readLine()) != null) {
            BufferedWriter writer = writers.get(writerFileCount);

            writer.write(line + "\n");
            writer.flush();

            writerFileCount = (writerFileCount + 1) % numberOfFiles;
        }

        FileUtils.closeWriters(writers);
        reader.close();
    }

    public static void sortFiles(int numberOfFiles) throws IOException {
        for (int i = 0; i < numberOfFiles; i++) {
            String path = FILES_B_PATH + i + ".lab";
            BufferedReader reader = new BufferedReader(new FileReader(path));

            List<Integer> numbers = reader.lines()
                    .map(Integer::parseInt)
                    .sorted()
                    .toList();

            FileUtils.writeToFile(path, numbers, false);
            reader.close();
        }
    }

    public static void merge(int numberOfFiles) throws IOException {
        List<BufferedReader> readers = FileUtils.createReaders(FILES_B_PATH, numberOfFiles);

        if (readers == null) {
            return;
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_C_PATH, true));
        int endedReadersCount = 0;

        while (endedReadersCount < readers.size()) {
            int min = Integer.MAX_VALUE;
            Integer minIndex = null;

            for (int i = 0; i < readers.size(); i++) {
                BufferedReader reader = readers.get(i);
                String line = FileUtils.readLine(reader);

                if (line == null) {
                    endedReadersCount++;
                    continue;
                }

                if (Integer.parseInt(line) < min) {
                    min = Integer.parseInt(line);
                    minIndex = i;
                }
            }

            if (minIndex != null) {
                writer.write(min + "\n");
                writer.flush();
                readers.get(minIndex).readLine();
            }
        }

        FileUtils.closeReaders(readers);
        writer.close();
    }
}
