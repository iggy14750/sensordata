
package capture;

import java.util.ArrayList;
import java.util.List;

public class SensorData {

    private List<Integer> timestamp;
    private List<Double> gx;
    private List<Double> gy;
    private List<Double> gz;
    private List<Double> ax;
    private List<Double> ay;
    private List<Double> az;

    public SensorData() {
        timestamp = new ArrayList<Integer>();
        gx = new ArrayList<Double>();
        gy = new ArrayList<Double>();
        gz = new ArrayList<Double>();
        ax = new ArrayList<Double>();
        ay = new ArrayList<Double>();
        az = new ArrayList<Double>();
    }

    public void insert(int timestamp, 
        double gx, double gy, double gz,
        double ax, double ay, double az
    ) {
        this.timestamp.add(timestamp);
        this.gx.add(gx);
        this.gy.add(gy);
        this.gz.add(gz);
        this.ax.add(ax);
        this.ay.add(ay);
        this.az.add(az);
    }

    public int size() {
        return gx.size();
    }

    public int getTimestamp(int index) {
        return timestamp.get(index);
    }

    public double getGx(int index) {
        return gx.get(index);
     }

    public double getGy(int index) {
        return gy.get(index);
    }

    public double getGz(int index) {
        return gz.get(index);
    }

    public double getAx(int index) {
        return ax.get(index);
    }

    public double getAy(int index) {
        return ay.get(index);
    }
}