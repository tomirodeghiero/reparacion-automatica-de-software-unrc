import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class VectorTest {

    private final List<Integer> input;
    private final boolean asIs;
    private final List<Integer> expected;

    public VectorTest(List<Integer> input, boolean asIs, List<Integer> expected) {
        this.input = input;
        this.asIs = asIs;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{index}: input={0}, asIs={1}, expected={2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            { Arrays.asList(), false, Arrays.asList() },
            { Arrays.asList(1), false, Arrays.asList(1) },
            { Arrays.asList(1, 2, 3), false, Arrays.asList(1, 2, 3) },
            { Arrays.asList(1, 2, 3), true, Arrays.asList(3, 2, 1) }
        });
    }

    @Test
    public void testConstructor() {
        Vector v = new Vector(input, asIs);
        assertEquals(
            String.format(
                "Expecting %d elements, got %d instead",
                expected.size(),
                v.size()
            ),
            expected.size(),
            v.size()
        );
        assertTrue(
            String.format(
                "Elements in vector (%s) are different than expected (%s)",
                v.toString(),
                listToString(expected)
            ),
            same(v, expected)
        );
    }

    @Test
    public void testAdd() {
        Vector v = new Vector(input, asIs);
        v.add(5);
        List<Integer> expectedAfterAdd = new ArrayList<>(expected);
        expectedAfterAdd.add(5);
        assertEquals(
            String.format(
                "Expecting %d elements, got %d instead",
                expectedAfterAdd.size(),
                v.size()
            ),
            expectedAfterAdd.size(),
            v.size()
        );
        assertTrue(
            String.format(
                "Elements in vector (%s) are different than expected (%s)",
                v.toString(),
                listToString(expectedAfterAdd)
            ),
            same(v, expectedAfterAdd)
        );
        assertEquals(
            String.format(
                "Expected v[%d] == %d, got %d instead",
                v.size() - 1,
                5,
                v.at(v.size() - 1)
            ),
            5,
            v.at(v.size() - 1)
        );
    }

    private static final boolean same(Vector v, List<Integer> expected) {
        if (v.size() != expected.size()) {
            return false;
        }
        for (int i = 0; i < v.size(); i++) {
            if (v.at(i) != expected.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static final String listToString(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i + 1 < list.size()) {
                sb.append(", ");
            }
        }

        sb.append(']');
        return sb.toString();
    }

}