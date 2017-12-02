
package capture;

import java.util.ArrayList;
import java.util.List;

public class SensorData {

    private List<Double> gx;
    private List<Double> gy;
    private List<Double> gz;

    public SensorData() {
        gx = new ArrayList<Double>();
        gy = new ArrayList<Double>();
        gz = new ArrayList<Double>();
    }

    public void insert(int timestamp, 
        double gx, double gy, double gz,
        double ax, double ay, double az
    ) {
        this.gx.add(gx);
        this.gy.add(gy);
        this.gz.add(gz);
    }

    public int size() {
        return gx.size();
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
}