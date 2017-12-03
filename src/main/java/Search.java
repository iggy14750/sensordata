
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
        TreeSet<Double> rolling = new TreeSet<Double>();
        for (int i = 0; i < winLength; i++) {
            rolling.add(data.get(indexEnd - i));
            System.out.println("Adding element " + i + " to the tree set");
        }

        for (int i = (indexEnd - winLength + 1); i > indexBegin; i--) {
            if (rolling.first() > thresholdLo && rolling.last() < thresholdHi) {
                return i;
            }
            rolling.remove(data.get(i + winLength - 1));
            rolling.add(data.get(i-1));
        }

        if (rolling.first() > thresholdLo && rolling.last() < thresholdHi) {
            return indexBegin;
        }

        return -1;
    }
}