
package capture;


public class SensorData {

    private static final int DIMENSIONS = 3;
    private static final int DEFAULT_SIZE = 8;

    private int size;
    private double[][] gyro;

    public SensorData() {
        size = 0;
        gyro = new double[DIMENSIONS][DEFAULT_SIZE];
    }

    public void insert(int timestamp, 
        double gx, double gy, double gz,
        double ax, double ay, double az
    ) {
        ensureCapacity();
        gyro[0][size] = gx;
        gyro[1][size] = gy;
        gyro[2][size] = gz;
        size++;
    }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (gyro[0].length == size) {
            for (int i = 0; i < DIMENSIONS; i++) {
                int n = gyro[i].length;
                double[] temp = new double[2*n];
                for (int j = 0; j < n; j++) {
                    temp[j] = gyro[i][j];
                }
                gyro[i] = temp;
            }
        }
    }

    public double[] getGx() {
        return gyro[0];
    }

    public double[] getGy() {
        return gyro[1];
    }
}