
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

    public List<Integer> getTimestamp() {
        return timestamp;
    }

    public double getGx(int index) {
        return gx.get(index);
    }

    public List<Double> getGx() {
        return gx;
    }

    public double getGy(int index) {
        return gy.get(index);
    }

    public List<Double> getGy() {
        return gy;
    }

    public double getGz(int index) {
        return gz.get(index);
    }

    public List<Double> getGz() {
        return gz;
    }

    public double getAx(int index) {
        return ax.get(index);
    }

    public List<Double> getAx() {
        return ax;
    }

    public double getAy(int index) {
        return ay.get(index);
    }

    public List<Double> getAy() {
        return ay;
    }

    public double getAz(int index) {
        return az.get(index);
    }

    public List<Double> getAz() {
        return az;
    }
}