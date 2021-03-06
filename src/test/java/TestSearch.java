
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

public class TestSearch {

    private List<Double> testData = new ArrayList<Double>();


    /* HELPER METHODS */

    private void initList(double[] arr) {
        testData.clear();
        for (double x: arr) {
            testData.add(x);
        }
    }

    private List<Double> makeList(double[] arr) {
        List<Double> list = new ArrayList<Double>();
        for (double x: arr) {
            list.add(x);
        }
        return list;
    }

    private Pair<Integer, Integer>[] zipTogether(int[] a1, int[] a2) {
        Pair<Integer, Integer>[] res = new Pair[a1.length];
        for (int i = 0; i < a1.length; i++) {
            res[i] = new Pair<Integer, Integer>(a1[i], a2[i]);
        }
        return res;
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


    /* SEARCH CONTINUITY ABOVE VALUE */

    @Test
    public void aboveVal() {
        initList(new double[] {
            42.0, 2.23, 234.23, 23.32, 23.42, -63.2, 49.01, -57, 23
        });
        assertEquals(2, Search.searchContinuityAboveValue(
            testData, 0, 8, 20.0, 3
        ));
    }

    @Test
    public void aboveValIndicesSmallerThanWinLength() {
        assertEquals(-1, Search.searchContinuityAboveValue(
            null, 2, 3, 20.0, 3
        ));
    }

    @Test
    public void aboveValAtBeginIndex() {
        initList(new double[] {
            0, 20, 20, 20, 0, 0, 0, 0
        });
        assertEquals(1, Search.searchContinuityAboveValue(testData, 1, 5, 10, 3));
    }

    @Test
    public void aboveValToEndIndex() {
        initList(new double[] {
            0, 20, 20, 20, 0, 0, 0, 0
        });
        assertEquals(1, Search.searchContinuityAboveValue(testData, 0, 3, 10, 3));
    }

    @Test
    public void aboveValExactBounds() {
        initList(new double[] {
            0, 20, 20, 20, 0, 0, 0, 0
        });
        assertEquals(1, Search.searchContinuityAboveValue(testData, 1, 3, 10, 3));
    }

    @Test
    public void myLittleCase() {
        initList(new double[] {0,2,3,5,0});
        assertEquals(1, Search.searchContinuityAboveValue(testData, 1, 3, 1, 3));
    }

    @Test
    public void aboveValBeforeIndexBegin() {
        initList(new double[] {
            0, 20, 20, 20, 0, 0, 0, 0
        });
        assertEquals(-1, Search.searchContinuityAboveValue(testData, 2, 6, 10, 3));
    }

    @Test
    public void aboveValRegionAfterIndexEnd() {
        initList(new double[] {
            0, 20, 20, 20, 0, 0, 0, 0
        });
        assertEquals(-1, Search.searchContinuityAboveValue(testData, 0, 2, 10, 3));
    }

    @Test
    public void winLengthTooLong() {
        initList(new double[] {
            0, 20, 20, 20, 0, 0, 0, 0
        });
        assertEquals(-1, Search.searchContinuityAboveValue(testData, 0, 7, 10, 4));
    }

    @Test
    public void winLengthShorterThanActualRegion() {
        initList(new double[] {
            1, 2, 3, 21, 22, 23, 4, 5, // difficult with non-distinct data.
        });
        assertEquals(3, Search.searchContinuityAboveValue(testData, 0, 7, 10, 2));
    }

    @Test
    public void windowAtEndOfRegion() {
        initList(new double[] {
            1, 2, 3, 4, 5, 21, 22, 23,
        });
        assertEquals(5, Search.searchContinuityAboveValue(testData, 0, 7, 10, 2));
    }

    @Test
    public void aboveValFindsFirst() {
        initList(new double[] {
            0.1, 0.2, 101, 102, 103, 0.3, 0.4, 0.5, 104, 105, 106, 107, 0.6, 0.7
        });
        assertEquals(2, Search.searchContinuityAboveValue(
            testData, 0, 14, 50, 3
        ));
    }


    /* BACK-SEARCH CONTINUITY WITHIN RANGE */

    @Test
    public void backWithinRangeSimple() {
        initList(new double[] {
            0, 100, 23, 21, 22, -22, 101
        });
        assertEquals(2, Search.backSearchContinuityWithinRange(
            testData, 6, 0, 20, 30, 3
        ));
    }

    @Test
    public void backWithinRangeWindowOnIndexBegin() {
        initList(new double[] {
            0, 100, 23, 21, 22, -22, 101
        });
        assertEquals(2, Search.backSearchContinuityWithinRange(
            testData, 6, 2, 20, 30, 3
        ));
    }

    @Test
    public void backWithinRangeWindowOnIndexEnd() {
        initList(new double[] {
            0, 100, 23, 21, 22, -22, 101
        });
        assertEquals(2, Search.backSearchContinuityWithinRange(
            testData, 4, 0, 20, 30, 3
        ));
    }

    @Test
    public void backWithinRangeWindowBeforeIndices() {
        initList(new double[] {
            0, 100, 23, 21, 22, -22, 101
        });
        assertEquals(-1, Search.backSearchContinuityWithinRange(
            testData, 6, 3, 20, 30, 3
        ));
    }

    @Test
    public void backWithinRangeWindowAfterIndices() {
        initList(new double[] {
            0, 100, 23, 21, 22, -22, 101
        });
        assertEquals(-1, Search.backSearchContinuityWithinRange(
            testData, 3, 0, 20, 30, 3
        ));
    }

    @Test
    public void backWithinRangeWindowSmallerThanExpected() {
        initList(new double[] {
            0, 100, 23, 21, 32, -22, 101
        });
        assertEquals(-1, Search.backSearchContinuityWithinRange(
            testData, 6, 0, 20, 30, 3
        ));
    }

    @Test
    public void backWithinRangeFindsLast() {
        initList(new double[] {
            0, 100, 23, 21, 22, -22, 101, 24, 25, 26, 0.2, 102
        });
        assertEquals(7, Search.backSearchContinuityWithinRange(
            testData, 11, 0, 20, 30, 3
        ));
    }


    /* SEARCH CONTINUITY ABOVE VALUE TWO SIGNALS */

    @Test
    public void twoSigSimple() {
        List<Double> one = makeList(new double[] {
            0.1, 0.2, 23, 21, 22, 0.3, 0.4
        });
        assertEquals(2, Search.searchContinuityAboveValueTwoSignals(
            one, one, 0, 6, 20, 20, 3
        ));
    }

    @Test
    public void twoSigSecondWindow() {
        assertEquals(6, Search.searchContinuityAboveValueTwoSignals(
            makeList(new double[] {1, 101, 102, 103,   2, 3, 104, 105, 106, 4, 5}),
            makeList(new double[] {1,   2, 101, 102, 103, 3, 104, 105, 106, 4, 5}),
            0, 10, 100, 100, 3
        ));
    }

    @Test
    public void twoSigFirstWindow() {
        assertEquals(1, Search.searchContinuityAboveValueTwoSignals(
            makeList(new double[] {1, 101, 102, 103, 2, 3, 104, 105, 106, 4, 5}),
            makeList(new double[] {1, 101, 102, 103, 2, 3, 104, 105, 106, 4, 5}),
            0, 10, 100, 100, 3
        ));
    }

    @Test
    public void twoSigNeverLineUp() {
        assertEquals(-1, Search.searchContinuityAboveValueTwoSignals(
            makeList(new double[] {1, 101, 102, 103,   2, 3, 104, 105, 106,   4, 5}),
            makeList(new double[] {1,   2, 101, 102, 103, 3,   4, 104, 105, 106, 4}),
            0, 10, 100, 100, 3
        ));
    }

    @Test
    public void twoSigNoWindowInOne() {
        assertEquals(-1, Search.searchContinuityAboveValueTwoSignals(
            makeList(new double[] {1, 101, 102, 103,   2, 3, 104, 105, 106, 4, 5}),
            makeList(new double[] {1,   2, 101,   6, 103, 3, 104, 105,   7, 4, 5}),
            0, 10, 100, 100, 3
        ));
    }

    @Test
    public void twoSigNoWindowInEither() {
        assertEquals(-1, Search.searchContinuityAboveValueTwoSignals(
            makeList(new double[] {1, 101,   6, 103,   2, 3, 104, 105, 7, 4, 5}),
            makeList(new double[] {1,   2, 101,   6, 103, 3, 104, 105, 7, 4, 5}),
            0, 10, 100, 100, 3
        ));
    }

    @Test
    public void twoSigDifferentTheshold() {
        assertEquals(6, Search.searchContinuityAboveValueTwoSignals(
            makeList(new double[] {1, 101, 102, 103,  2, 3, 104, 105, 106, 4, 5}),
            makeList(new double[] {1,   2,  21,  22, 23, 3,  24,  25,  26, 4, 5}),
            0, 10, 100, 20, 3
        ));
    }


    /* SEARCH MULTI-CONTINUITY WITHIN RANGE */

    @Test
    public void multiSimple() {
        assertArrayEquals(
            zipTogether(new int[] {1}, new int[] {4}),
            Search.searchMultiContinuityWithinRange(
                makeList(new double[] {0, 21, 22, 23, 24, 100, 1}),
                0, 6, 20.0, 30.0, 3
            )
        );
    }

    @Test
    public void multiTwoEasy() {
        assertArrayEquals(
            new Pair[] {
                new Pair(1, 4),
                new Pair(6, 10)
            },
            Search.searchMultiContinuityWithinRange(
                makeList(new double[] {1, 21, 22, 23, 24, 2, 25, 26, 27, 28, 29, 3}),
                0, 11, 20, 30, 3
            )
        );
    }

    @Test
    public void multiNoWinners() {
        assertArrayEquals(
            new Pair[] {},
            Search.searchMultiContinuityWithinRange(
                makeList(new double[] {0,1,2,3,4,5,6,7,8,9}),
                0, 9, 20, 30, 3
            )
        );
    }

    @Test
    public void multiTooShortWindow() {
        assertArrayEquals(
            new Pair[] {new Pair(5, 8)},
            Search.searchMultiContinuityWithinRange(
                makeList(new double[] {0, 21, 22, 3, 4, 25, 26, 27, 28, 9}),
                0, 9, 20, 30, 3
            )
        );
    }
}