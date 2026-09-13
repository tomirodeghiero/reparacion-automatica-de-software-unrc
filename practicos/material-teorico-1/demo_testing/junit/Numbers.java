import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * This class defines several functions to deal with transformations between decimal
 * and other bases values.
 */
public final class Numbers {

    /**
     * Given a base function from int to int, this function will return a weight function which
     * will provide the weight for the position corresponding to a specific digit.
     * @param base : the base function, given the position of a digit, it will return the corresponding base
     * @return a weight function using the provided {@code base} function.
     */
    public static UnaryOperator<Integer> makeWeightFunction(UnaryOperator<Integer> base) {
        return new UnaryOperator<Integer>() {
            @Override
            public Integer apply(Integer i) {
                if (i == 0) {
                    return 1;
                } else {
                    int w = base.apply(0);
                    for (int j = 1; j < i; j++) {
                        w = w * base.apply(j);
                    }
                    return w;
                }
            }
        };
    }

    /**
     * Given a vector depicting a number in a base x and a weight function for that base
     * this function will return the corresponding decimal representation of that value
     * @param bx : the vector representing the digits of a value in some base X
     * @param weight : the weight function for base X
     * @return a decimal representation of {@code bx}
     */
    public int fromBaseXToDecdef(Vector bx, UnaryOperator<Integer> weight) {
        int res = 0;
        for (int i = 0; i < bx.size(); i++) {
            res += bx.at(i) * weight.apply(i);
        }
        return res;
    }

    /**
     * Given a decimal value and a weight function for base X this function
     * will transform the decimal value into a vector depicting that value in base X
     * @param d : the decimal value to transform
     * @param weight : the weight function for base X
     * @return a vector depicting {@code d} in base X
     */
    public Vector fromDecToBaseX(int d, UnaryOperator<Integer> weight) {
        return fromDecToBaseX(d, weight, 0);
    }
        
    /**
     * Given a decimal value and a weight function for base X this function
     * will transform the decimal value into a vector depicting that value in base X
     * @param d : the decimal value to transform
     * @param weight : the weight function for base X
     * @param elems : how many digits will the returning vector will have, this number is
     * only used to add non-significant zeros to complete the number of digits required.
     * @return a vector depicting {@code d} in base X
     */
    public Vector fromDecToBaseX(int d, UnaryOperator<Integer> weight, int elems) {
        Vector lc = new Vector();
        int i = 0;
        boolean finished = false;
        int quotient = d;
        while (!finished) {
            int w_i = weight.apply(i);
            int w_next = weight.apply(i + 1);
            int radix = Math.floorDiv(w_next, w_i);  // the base for this digit
            int lc_i = quotient % radix;
            quotient = Math.floorDiv(quotient, radix);
            lc.add(lc_i);
            i++;
            finished = quotient == 0;
        }
        while (lc.size() < elems) {
            lc.add(0);
        }
        return lc;
    }

    /**
     * Given a value in base X represented in a vector of size n return the next
     * value if the original value was not the maximum value of n digits, otherwise
     * return nothing
     * @return either a vector representing the value that follows {@code value} in base X
     * and with n digits, or nothing.
     */
    public Optional<Vector> next(Vector value, UnaryOperator<Integer> base) {
        Vector next_v = new Vector(value.toList(), true);
        int i = 0;
        boolean hasCarry = true;
        while (i < value.size() && hasCarry) {
            if (next_v.at(i) == base.apply(i) - 1) {
                next_v.setAt(i, 0);
            } else {
                next_v.setAt(i, next_v.at(i) + 1);
                hasCarry = false;
            }
            i++;
        }
        if (hasCarry) {
            return Optional.empty();
        } else {
            return Optional.of(next_v);
        }
    }

}