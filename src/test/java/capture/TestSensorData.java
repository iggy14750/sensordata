
package capture;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSensorData {

    private final double[] testData = new double[] {
        42.0, 2.23, 234.23, -23.32, 23.42, -63.2, 49.01, -57
    };

    private final double epsilon = 0.1;

    @Test
    public void gx() {
        SensorData sd = new SensorData();
        for (double x: testData) {
            sd.insert(0,x,0,0,0,0,0);
        }
        assertArrayEquals(testData, sd.getGx(), epsilon);
    }

}