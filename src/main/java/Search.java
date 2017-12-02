
import java.util.List;
import java.util.TreeSet;

public class Search {

    public static int continuityAboveValue(
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

        for (int i = indexBegin; i < (indexEnd - winLength + 2); i++) {
            if (rollingMin.first() > threshold) {
                return i;
            }
            rollingMin.remove(data.get(i));
            rollingMin.add(data.get(i + winLength));
        }

        return -1; // No such continiguous region exists
    }
}