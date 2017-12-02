
package capture;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class TestSensorData {

    private final double epsilon = 0.1;
    private final double[] testData = new double[] {
        42.0, 2.23, 234.23, -23.32, 23.42, -63.2, 49.01, -57, 23
    };

    SensorData sd = new SensorData();

    @Test
    public void gx() {
        for (double x: testData) {
            sd.insert(0,x,0,0,0,0,0);
        }
        assertEquals(9, sd.size());
        assertArrayEquals(testData, Arrays.copyOf(sd.getGx(), sd.size()), epsilon);
    }

    @Test
    public void gy() {
        for (double x: testData) {
            sd.insert(0,0,x,0,0,0,0);
        }
        assertEquals(9, sd.size());
        assertArrayEquals(testData, Arrays.copyOf(sd.getGy(), sd.size()), epsilon);
    }

}