package lab;

import lab.utils.Timer;

import java.io.IOException;

public class Main {

    private static final String FILE_PATH =
            "C:/Users/maxim/Documents/Universities/KPI/Algorithms Projecting/Lab 1/Data Files/A2GB.lab";
    private static final String SORTED_PARTS_FILE_PATH =
            "C:/Users/maxim/Documents/Universities/KPI/Algorithms Projecting/Lab 1/ASorted.lab";

    private static final long MEGABYTE = (long) Math.pow(2, 20);
    private static final long GIGABYTE = (long) Math.pow(2, 30);

    public static void main(String[] args) {
        try {
            // System.out.print("Generating 10 MB file... ");
            // generateDataFile(10 * MEGABYTE);

            // normalSort();
            modifiedSort();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in a file");
        }
    }

    /**
     * Generates file with data
     *
     * @param size Size of file
     * @throws IOException File exception
     */
    private static void generateDataFile(long size) throws IOException {
        Timer timer = new Timer();

        timer.start();
        FileGenerator.generateFileBySize(FILE_PATH, 0, 1_000_000_000, size);
        timer.stop();
        System.out.println("Done");
        System.out.println("Time passed (HH:mm:ss.SSSS): " + timer.getTime());
    }

    /**
     * Sorting by normal algorithm with 100 MB data
     *
     * @throws IOException File exception
     */
    private static void normalSort() throws IOException {
        Timer timer = new Timer();

        System.out.print("Sorting normal...");
        timer.start();
        String resultFilePath = Sorts.kWayMergeSort(FILE_PATH, 5);
        timer.stop();
        System.out.println(" Done");
        System.out.println("Time passed (HH:mm:ss.SSSS): " + timer.getTime());
        System.out.println("Path to result file: " + resultFilePath);
    }

    /**
     * Sorting by modified algorithm with 2 GB data
     *
     * @throws IOException File exception
     */
    private static void modifiedSort() throws IOException {
        Timer timer = new Timer();

        System.out.print("Sorting modified...");
        timer.start();
        Sorts.sortParts(FILE_PATH, SORTED_PARTS_FILE_PATH, 2 * GIGABYTE, 2 * MEGABYTE);
        Sorts.kWayMergeSort(SORTED_PARTS_FILE_PATH, 5);
        timer.stop();
        System.out.println(" Done");
        System.out.println("Time passed (HH:mm:ss.SSSS): " + timer.getTime());
    }
}
