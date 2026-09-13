import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Collection of functions to count values in an array
 */
public final class CountingThings {

    /**
     * Count zeros in an int array
     * 
     * @param values the array of {@code int} values
     * @return sum of {@code i} such that {@code values[i] == 0}
     * @throws NullPointerException if {@code values} is {@code Null}
     * <hr>
     * <i>Taken from Introduction to Software Testing 2nd edition</i>
     */
    public static int countZeros(int[] values) {
        int count = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[i] == 0) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        List<Integer> values = new ArrayList<>();
        for (String arg : args) {
            try {
                int v = Integer.parseInt(arg);
                values.add(v);
            } catch (NumberFormatException nfe) {
                System.err.printf("%s is not a number, skipping it\n", arg);
            }
        }
        int[] valuesArray = new int[values.size()];
        for (int i = 0; i < valuesArray.length; i++) {
            valuesArray[i] = values.get(i);
        }
        int zeros = countZeros(valuesArray);
        System.out.printf("%s has %d zeros\n", Arrays.toString(valuesArray), zeros);
    }

}