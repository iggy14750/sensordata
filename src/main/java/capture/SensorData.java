
package capture;

import java.util.ArrayList;
import java.util.List;

public class SensorData {

    private List<Double> gx;

    public SensorData() {
        gx = new ArrayList();
    }

    public void insert(int timestamp, 
        double gx, double gy, double gz,
        double ax, double ay, double az
    ) {
        this.gx.add(gx);
    }

    public double[] getGx() {
        double[] res = new double[gx.size()];
        for (int i = 0; i < gx.size(); i++) {
            res[i] = (double) gx.get(i);
        }
        return res;
    }
}