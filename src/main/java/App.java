
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
        Pair[] bounds = Search.searchMultiContinuityWithinRange(
            data.getAz(), 0, 1200, 10, 100, 10
        );

        for (Pair p: bounds) {
            int begin = (int) p.left;
            int end = (int) p.right;
            System.out.println("=== New Section (" + begin + ", " + end + ") ===");
            for (int i = begin; i <= end; i++) {
                System.out.println(data.getAz(i));
            }
        }
    }

    private static Pair<List, Pair> getBounds(SensorData data) {
        System.out.println("Please select an action:");
        System.out.println("\t0: Search continuity above value");
        System.out.println("\t1: Back search continuity within range");
        System.out.println("\t2: Search continuity above value two signals");
        System.out.println("\t3: Search multi-continuity within range");

        switch (in.next().trim()) {
            case "0":
                List<Double> col = getColumn(data);
                int indexBegin = getIndexBegin();
                int indexEnd = getIndexEnd();
                double threshold = getThreshold();
                int winLength = getWinLength();
                int index = Search.searchContinuityAboveValue(
                    col, indexBegin, indexEnd, threshold, winLength
                );
                return new Pair(col, new Pair[] {new Pair(index, index + winLength - 1)});
            case "1":
            case "2":
            case "3":
            case "exit":
                System.exit(0);
            default:
                error();
        }
        return null;
    }

    private static void error() {
        System.out.println("Invalid option; please choose one of the above.");
    }

    private static List getColumn(SensorData data) {
        while (true) {
            System.out.println("Please choose a column:");
            System.out.println("\t0: timestamp");
            System.out.println("\t1: gx");
            System.out.println("\t2: gy");
            System.out.println("\t3: gz");
            System.out.println("\t4: ax");
            System.out.println("\t5: ay");
            System.out.println("\t6: az");
            
            switch(in.next().trim()) {
                case "0": return data.getTimestamp();
                case "1": return data.getGx();
                case "2": return data.getGy();
                case "3": return data.getGz();
                case "4": return data.getAx();
                case "5": return data.getAy();
                case "6": return data.getAz();
                default:
                    error();
            }
        }
    }

    private static int getInt() {
        while (true) {
            try {
                int res = Integer.parseInt(in.next().trim());
                return res;
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid number; try again.");
            }
        }
    }

    private static double getDouble() {
        while (true) {
            try {
                double res = Double.parseDouble(in.next().trim());
                return res;
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid number; try again");
            }
        }
    }

    private static int getIndexBegin() {
        System.out.println("Please enter an indexBegin");
        return getInt();
    }

    private static int getIndexEnd() {
        System.out.println("Please enter an indexEnd");
        return getInt();
    }

    private static double getThreshold() {
        System.out.println("Please enter a threshold");
        return getDouble();
    }

    private static int getWinLength() {
        System.out.println("Please enter a winLength");
        return getInt();
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
