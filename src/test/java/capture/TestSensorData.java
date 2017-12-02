
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
        assertEquals(testData[3], sd.getGx(3), epsilon);
        assertEquals(testData[8], sd.getGx(8), epsilon);
    }

    @Test
    public void gy() {
        for (double x: testData) {
            sd.insert(0,0,x,0,0,0,0);
        }
        assertEquals(9, sd.size());
        assertEquals(testData[2], sd.getGy(2), epsilon);
        assertEquals(testData[8], sd.getGy(8), epsilon);
    }

    @Test
    public void gz() {
        for (double x: testData) {
            sd.insert(0,0,0,x,0,0,0);
        }
        assertEquals(9, sd.size());
        assertNotEquals(testData[0], sd.getGz(1), epsilon);
        assertEquals(testData[6], sd.getGz(6), epsilon);
    }

    @Test
    public void timestamp() {
        int len = 16;
        for (int i = 0; i < len; i++) {
            sd.insert(2*i, 0,0,0,0,0,0);
        }
        assertEquals(len, sd.size());
        assertEquals(4, sd.getTimestamp(2));
        assertEquals(12, sd.getTimestamp(6));
    }

}