
package processing;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

public class TestSearch {

    private List<Double> testData = new ArrayList<Double>();

    private void initList(double[] arr) {
        testData.clear();
        for (double x: arr) {
            testData.add(x);
        }
    }

    @Test
    public void aboveVal() {
        initList(new double[] {
            42.0, 2.23, 234.23, 23.32, 23.42, -63.2, 49.01, -57, 23
        });
        assertEquals(2, Search.continuityAboveValue(
            testData, 0, 8, 20.0, 3
        ));
    }
}