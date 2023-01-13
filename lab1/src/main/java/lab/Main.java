package lab;

import lab.files.FileUtils;
import lab.sorts.OptimizedKWaysSort;
import lab.sorts.SimpleKWaysSort;
import lab.utils.Timer;

import java.io.IOException;

public class Main {

    private static final String FILE_PATH =
            "A/10MB.lab";

    private static final long MEGABYTE = (long) Math.pow(2, 20);
    private static final long GIGABYTE = (long) Math.pow(2, 30);

    public static void main(String[] args) {
        try {
            //System.out.print("Generating 10 MB file... ");
            //generateDataFile(2 * GIGABYTE);

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
        FileUtils.generateDataFile(FILE_PATH, 0, 1_000_000_000, size);
        timer.stop();
        System.out.println("Done");
        System.out.println("Time passed (HH:mm:ss.SSSS): " + timer.getTime());
    }

    /**
     * Sorting by normal algorithm with 10 MB data
     *
     * @throws IOException File exception
     */
    private static void normalSort() throws IOException {
        Timer timer = new Timer();

        System.out.print("Sorting normal...");
        timer.start();
        String resultFilePath = SimpleKWaysSort.sort(FILE_PATH, 5);
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
        String resultFilePath = OptimizedKWaysSort.sort(FILE_PATH, 5);
        timer.stop();
        System.out.println(" Done");
        System.out.println("Time passed (HH:mm:ss.SSSS): " + timer.getTime());
        System.out.println("Path to result file: " + resultFilePath);
    }
}
