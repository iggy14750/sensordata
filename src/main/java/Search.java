
import java.util.List;
import java.util.TreeSet;

public class Search {

    public static int searchContinuityAboveValue(
        List<Double> data,
        int indexBegin,
        int indexEnd,
        double threshold,
        int winLength
    ) {
        if (indexEnd - indexBegin < winLength - 1) return -1;

        TreeSet<Double> rollingMin = new TreeSet<Double>();
        for (int i = 0; i < winLength; i++) {
            rollingMin.add(data.get(indexBegin + i));
        }

        for (int i = indexBegin; i < (indexEnd - winLength + 1); i++) {
            if (rollingMin.first() > threshold) {
                return i;
            }
            rollingMin.remove(data.get(i));
            rollingMin.add(data.get(i + winLength));
        }
        // Special case for when the window is at the end of given indices
        if (rollingMin.first() > threshold) {
            return (indexEnd - winLength + 1);
        }

        return -1; // No such continiguous region exists
    }

    public static int backSearchContinuityWithinRange(
        List<Double> data,
        int indexBegin,
        int indexEnd,
        double thresholdLo,
        double thresholdHi,
        int winLength
    ) {
        if (indexBegin - indexEnd < winLength - 1) return -1;

        TreeSet<Double> rolling = new TreeSet<Double>();
        for (int i = 0; i < winLength; i++) {
            rolling.add(data.get(indexBegin - i));
        }

        for (int i = (indexBegin - winLength + 1); i > indexEnd; i--) {
            if (rolling.first() > thresholdLo && rolling.last() < thresholdHi) {
                return i;
            }
            rolling.remove(data.get(i + winLength - 1));
            rolling.add(data.get(i-1));
        }

        if (rolling.first() > thresholdLo && rolling.last() < thresholdHi) {
            return indexEnd;
        }

        return -1;
    }

    public static int searchContinuityAboveValueTwoSignals(
        List<Double> data1,
        List<Double> data2,
        int indexBegin,
        int indexEnd,
        double threshold1,
        double threshold2,
        int winLength
    ) {
        do {
            int res1 = searchContinuityAboveValue(
                data1, indexBegin, indexEnd, threshold1, winLength);
            int res2 = searchContinuityAboveValue(
                data2, indexBegin, indexEnd, threshold2, winLength);
            
            if (res1 == -1 || res2 == -1) return -1;
            if (res1 == res2) return res1;

            indexBegin = Math.max(res1, res2);

        } while (true);
    }
}