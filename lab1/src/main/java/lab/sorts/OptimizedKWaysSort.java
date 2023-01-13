package lab.sorts;

import lab.files.FileUtils;

import java.io.*;
import java.util.List;

public class OptimizedKWaysSort {

    private static final String FILES_B_PATH = "modified/B/";
    private static final String FILES_C_PATH = "modified/C/";

    public static void sort() {

    }

    public static void splitFiles(String filePath, int numberOfFiles) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            FileUtils.deleteFiles(FILES_B_PATH, numberOfFiles);
            List<BufferedWriter> writers = createWriters(FILES_B_PATH, numberOfFiles);

            int currentFile = 0;
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                BufferedWriter currentWriter = writers.get(currentFile);

                currentWriter.write(line + "\n");
                currentFile = (currentFile + 1) % numberOfFiles;
            }

            FileUtils.closeWriters(writers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sortFiles(int batch, String filePath) {
        try {
            for (int i = 0; i < batch; i++) {
                String path = FILE_DIR + i + filePath;
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
                List<String> lines = bufferedReader.lines()
                        .map(Integer::parseInt)
                        .sorted()
                        .map(String::valueOf)
                        .toList();

                rewriteToFile(lines, path);
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mergeAllFiles(int batch) {
        try {
            mergeFiles(batch, FILES_B_PATH, FILES_C_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void mergeFiles(int batch, String readFrom, String writeTo) throws IOException {
        List<BufferedReader> readers = FileUtils.createReaders(batch, readFrom);
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_DIR + 0 + writeTo, true));

        int finishedBuffers = 0;
        while (finishedBuffers != readers.size()) {
            int min = Integer.MAX_VALUE;
            Integer minIndex = null;
            for (BufferedReader reader : readers) {
                String line = FileUtils.readLine(reader);
                if (line != null && Integer.parseInt(line) < min) {
                    min = Integer.parseInt(line);
                    minIndex = readers.indexOf(reader);
                }
                if (line == null) {
                    finishedBuffers++;
                }
            }
            if (minIndex != null) {
                writer.write(min + "\n");
                readers.get(minIndex).readLine();
            }
        }

        FileUtils.closeReaders(readers);
        writer.close();
    }
}
