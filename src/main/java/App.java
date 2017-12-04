
import java.io.*;
import java.util.*;

public class App {

    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Please include an input file you would like to process as an argument.");
            System.exit(1);
        }
        SensorData data = readIn(args[0]);

        /**
         * Here is an example of how to call multi-continuity within range.
         * Returns an array of a custom type.
         * Just edit the lines listed 'Edit Me'.
         * Check out the code below for an example of how to use other methods.
         */
        Pair[] bounds = Search.searchMultiContinuityWithinRange(
            data.getAz(), 0, 1200, 10, 100, 10 // Edit me to change arguments to method.
        );

        for (Pair p: bounds) {
            int begin = (int) p.left;
            int end = (int) p.right;
            System.out.println("=== New Section (" + begin + ", " + end + ") ===");
            for (int i = begin; i <= end; i++) {
                System.out.println(data.getAz(i)); // Edit me to change which column is printed.
            }
        }


        /**
         * Here is an example of how to call and use the other 3 methods.
         * (This is necessary because searchMultiContinuityWithinRange has a different return type.)
         */
        List<Double> column = data.getGx(); // Change which column or signal is used here.
        int winLength = 10; // change winlenth here, of course.
        int index = Search.backSearchContinuityWithinRange( // change which method is called here.
            column, 1000, 500, 5, 20, winLength
        );
        System.out.printf("=== New Section (%d, %d) ===\n", index, index + winLength - 1);
        for (int i = index; i < (index + winLength); i++) {
            System.out.println(column.get(i));
        }
    }

    private static SensorData readIn(String filename) throws IOException {
        SensorData data = new SensorData();
        Scanner sc = new Scanner(new File(filename));
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] row = line.split(",");
            int time = Integer.parseInt(row[0].trim());
            double gx = Double.parseDouble(row[1].trim());
            double gy = Double.parseDouble(row[2].trim());
            double gz = Double.parseDouble(row[3].trim());
            double ax = Double.parseDouble(row[4].trim());
            double ay = Double.parseDouble(row[5].trim());
            double az = Double.parseDouble(row[6].trim());
            data.insert(time, gx, gy, gz, ax, ay, az);
        }
        return data;
    }
}
