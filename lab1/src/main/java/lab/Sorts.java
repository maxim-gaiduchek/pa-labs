package lab;

import java.io.*;
import java.util.*;
import java.util.stream.LongStream;

public class Sorts {

    /**
     * K-way Merge Sort
     *
     * @param dataFileName  Given data file path
     * @param numberOfFiles Number of files to split the data file
     * @return Results file path
     * @throws IOException Some file error
     */
    public static String kWayMergeSort(String dataFileName, int numberOfFiles) throws IOException {
        if (numberOfFiles < 2) {
            throw new IllegalArgumentException("numberOfFiles is less then 2");
        }

        String[] bFileNames = new String[numberOfFiles];
        String[] cFileNames = new String[numberOfFiles];

        for (int i = 0; i < numberOfFiles; i++) {
            bFileNames[i] = "C:/Users/maxim/Documents/Universities/KPI/Algorithms Projecting/Lab 1/B" + i + ".lab";
            new File(bFileNames[i]).delete();
            cFileNames[i] = "C:/Users/maxim/Documents/Universities/KPI/Algorithms Projecting/Lab 1/C" + i + ".lab";
            new File(cFileNames[i]).delete();
        }

        splitFile(dataFileName, bFileNames);

        return merge(bFileNames, cFileNames);
    }

    /**
     * Split file into files with sets of growing sequences
     *
     * @param dataFileName Path to file with data
     * @param bFileNames   Array with B-files names
     * @throws IOException Some file error
     */
    private static void splitFile(String dataFileName, String[] bFileNames) throws IOException {
        int fileCounter = 0;
        int previous = Integer.MIN_VALUE;
        Writer currentWriter = new FileWriter(bFileNames[0]);
        Scanner scanner = new Scanner(new FileReader(dataFileName));

        while (scanner.hasNext()) {
            int number = scanner.nextInt();

            if (number < previous) {
                currentWriter.close();

                String fileName = bFileNames[++fileCounter % bFileNames.length];

                currentWriter = new FileWriter(fileName, true);
            }

            currentWriter.write(number + "\n");
            currentWriter.flush();

            previous = number;
        }

        currentWriter.close();
        scanner.close();
    }

    /**
     * Merging files with growing sequences
     *
     * @param bFileNames Array with B-files names
     * @param cFileNames Array with C-files names
     * @return File path with a sorted result
     * @throws IOException Some file error
     */
    private static String merge(String[] bFileNames, String[] cFileNames) throws IOException {
        List<Reader> readers = new ArrayList<>(
                Arrays.stream(bFileNames)
                        .map(File::new)
                        .filter(file -> file.length() > 0)
                        .map(file -> {
                            try {
                                return new Reader(file);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            return null;
                        })
                        .filter(Objects::nonNull)
                        .toList()
        );

        if (readers.size() == 1) {
            return readers.get(0).getFilePath();
        }

        List<Writer> writers = new ArrayList<>(cFileNames.length);

        for (String fileName : cFileNames) {
            writers.add(new FileWriter(fileName));
        }

        Reader currentReader = readers.get(0);
        Writer currentWriter = writers.get(0);
        long readersCounter = 0, closedReadersCounter = 0, writersCounter = 0;

        List<Integer> nums = new ArrayList<>();
        List<Integer> nextNums = new ArrayList<>();

        while (closedReadersCounter < readers.size()) {
            while (currentReader.isClosed() || !currentReader.hasNext()) {
                currentReader = readers.get((int) (++readersCounter % readers.size()));

                if (!currentReader.isClosed() && currentReader.hasNext() || closedReadersCounter == readers.size()) {
                    break;
                }
                if (currentReader.isClosed()) {
                    continue;
                }

                currentReader.close();
                closedReadersCounter++;
            }

            if (closedReadersCounter == readers.size()) {
                Collections.sort(nums);

                for (int n : nums) {
                    currentWriter.write(n + "\n");
                    currentWriter.flush();
                }

                currentWriter = writers.get((int) (++writersCounter % writers.size()));
                Collections.sort(nextNums);

                for (int n : nextNums) {
                    currentWriter.write(n + "\n");
                    currentWriter.flush();
                }

                break;
            }

            int num = currentReader.nextInt();

            if (num >= currentReader.getMaxValue()) {
                nums.add(num);
                currentReader.setMaxValue(num);
            } else {
                nextNums.add(num);
                currentReader.setMaxValue(num);
                currentReader = readers.get((int) (++readersCounter % readers.size()));
            }

            if (nextNums.size() >= readers.size()) {
                Collections.sort(nums);

                for (int n : nums) {
                    currentWriter.write(n + "\n");
                    currentWriter.flush();
                }

                currentWriter = writers.get((int) (++writersCounter % writers.size()));

                nums.clear();
                nums.addAll(nextNums);
                nextNums.clear();
            }
        }

        writers.forEach(writer -> {
            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return merge(cFileNames, bFileNames);
    }

    /**
     * lab.Sorts parts of the data file
     *
     * @param dataFileName       Given data file path
     * @param partedDataFileName Given data file path
     * @param size               Given data file size
     * @param partSize           Sizes of parts
     * @throws IOException Some file error
     */
    public static void sortParts(String dataFileName, String partedDataFileName, long size, long partSize) throws IOException {
        Scanner scanner = new Scanner(new FileReader(dataFileName));
        Writer writer = new FileWriter(partedDataFileName);

        for (int i = 0; i < size / partSize; i++) {
            LongStream.range(0, partSize)
                    .map(num -> {
                        if (scanner.hasNext()) {
                            return scanner.nextInt();
                        }
                        return -1;
                    })
                    .filter(num -> num > 0)
                    .sorted()
                    .forEach(num -> {
                        try {
                            writer.write(num + "\n");
                            writer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }

        scanner.close();
        writer.close();
    }
}
