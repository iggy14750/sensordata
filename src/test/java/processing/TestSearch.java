
package processing;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

public class TestSearch {

    private static List<Double> testData = new ArrayList<Double>();

    @BeforeClass
    public static void setup() {
        double[] _td = new double[] {
            42.0, 2.23, 234.23, 23.32, 23.42, -63.2, 49.01, -57, 23
        };
        for (double x: _td) {
            testData.add(x);
        }
    }

    @Test
    public void aboveVal() {
        assertEquals(2, Search.continuityAboveValue(
            testData, 0, 8, 20.0, 3
        ));
    }
}