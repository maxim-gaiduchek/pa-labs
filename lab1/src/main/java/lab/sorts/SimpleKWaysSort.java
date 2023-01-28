package lab.sorts;

import lab.files.FileUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SimpleKWaysSort {

    private static final String FILES_B_PATH = "simple/B/";
    private static final String FILES_C_PATH = "simple/C/";

    public static String sort(String dataFileName, int numberOfFiles) {
        if (numberOfFiles < 2) {
            throw new IllegalArgumentException("numberOfFiles is less then 2");
        }

        splitFile(dataFileName, numberOfFiles);

        return merge(numberOfFiles);
    }

    private static void splitFile(String dataFileName, int numberOfFiles) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFileName))) {
            FileUtils.deleteFiles(FILES_B_PATH, numberOfFiles);

            long fileCounter = 0;
            List<Integer> sequences = new ArrayList<>();
            String line = bufferedReader.readLine();
            int number = Integer.parseInt(line);

            sequences.add(number);

            int previous = number;

            while ((line = bufferedReader.readLine()) != null) {
                number = Integer.parseInt(line);

                if (sequences.size() > 1 && previous > number) {
                    FileUtils.writeToFile(FILES_B_PATH + (fileCounter++ % numberOfFiles) + ".lab", sequences);
                    sequences.clear();
                    fileCounter = (fileCounter + 1) % numberOfFiles;
                }

                sequences.add(number);
                previous = number;
            }

            FileUtils.writeToFile(FILES_B_PATH + (fileCounter % numberOfFiles) + ".lab", sequences);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String merge(int numberOfFiles) {
        try {
            String result, outputPath, inputPath;
            boolean flag = true;

            do {
                outputPath = flag ? FILES_B_PATH : FILES_C_PATH;
                inputPath = flag ? FILES_C_PATH : FILES_B_PATH;

                FileUtils.resetFiles(inputPath, numberOfFiles);

                List<BufferedReader> readers = FileUtils.createReaders(outputPath, numberOfFiles);

                if (readers == null) {
                    return null;
                }

                List<Integer> elements = new ArrayList<>();
                int writeFilesCount = 0;
                int endedReadersCount = 0;

                while (endedReadersCount < readers.size()) {
                    endedReadersCount = 0;

                    Integer readerIndex = null;
                    int minElement = Integer.MAX_VALUE;

                    for (int i = 0; i < readers.size(); i++) {
                        BufferedReader reader = readers.get(i);
                        String line = FileUtils.readLine(reader);

                        if (line == null) {
                            endedReadersCount++;
                            continue;
                        }

                        int number = Integer.parseInt(line);

                        if ((elements.isEmpty() || number >= elements.get(elements.size() - 1)) && number < minElement) {
                            minElement = number;
                            readerIndex = i;
                        }
                    }

                    if (readerIndex == null) {
                        FileUtils.writeToFile(inputPath + writeFilesCount + ".lab", elements);
                        elements.clear();
                        writeFilesCount = (writeFilesCount + 1) % numberOfFiles;
                    } else {
                        elements.add(minElement);
                        readers.get(readerIndex).readLine();
                    }
                }

                FileUtils.writeToFile(inputPath + writeFilesCount + ".lab", elements);
                FileUtils.closeReaders(readers);

                flag = !flag;
                result = FileUtils.getIfEmptyFileIsSingle(inputPath, numberOfFiles);
            } while (result == null);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
