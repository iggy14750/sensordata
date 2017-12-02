
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
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
    public void checkTreeSet() {
        TreeSet<Integer> ts = new TreeSet<Integer>();
        ts.add(5);
        ts.add(2313);
        ts.add(19);
        ts.add(22);
        assertEquals(5, (int) ts.first());
        assertEquals(2313, (int) ts.last());
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

    @Test
    public void aboveValAtBeginIndex() {
        initList(new double[] {
            0, 20, 20, 20, 0, 0, 0, 0
        });
        assertEquals(1, Search.continuityAboveValue(testData, 1, 5, 10, 3));
    }

    @Test
    public void aboveValToEndIndex() {
        initList(new double[] {
            0, 20, 20, 20, 0, 0, 0, 0
        });
        assertEquals(1, Search.continuityAboveValue(testData, 0, 3, 10, 3));
    }

    @Test
    public void aboveValExactBounds() {
        initList(new double[] {
            0, 20, 20, 20, 0, 0, 0, 0
        });
        assertEquals(1, Search.continuityAboveValue(testData, 1, 3, 10, 3));
    }

    @Test
    public void myLittleCase() {
        initList(new double[] {0,2,3,5,0});
        assertEquals(1, Search.continuityAboveValue(testData, 1, 3, 1, 3));
    }

    @Test
    public void aboveValBeforeIndexBegin() {
        initList(new double[] {
            0, 20, 20, 20, 0, 0, 0, 0
        });
        assertEquals(-1, Search.continuityAboveValue(testData, 2, 6, 10, 3));
    }

    @Test
    public void aboveValRegionAfterIndexEnd() {
        initList(new double[] {
            0, 20, 20, 20, 0, 0, 0, 0
        });
        assertEquals(-1, Search.continuityAboveValue(testData, 0, 2, 10, 3));
    }
}