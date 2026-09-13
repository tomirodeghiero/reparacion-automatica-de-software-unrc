import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a sequence or vector indexed from right to left
 * (the left-most element will be at index 0)
 */
public final class Vector {

    private int size;
    private List<Integer> internaList;

    /**
     * Created a new empty Vector.
     */
    public Vector() {
        this(new LinkedList<Integer>(), false);
    }

    /**
     * Created a new Vector from an initial list adding every item in the
     * initial list resulting in a reversal of the original list.
     * @param from : the origininating list.
     */
    public Vector(List<Integer> from) {
        this(from, false);
    }

    /**
     * Created a new Vector from an initial list and it either uses the list as is,
     * or it adds every item in the initial list resulting in a reversal of the
     * original list.
     * @param from : the origininating list
     * @param as_is : if {@code true} then the elements of {@code from} will be added
     * in the exact same way as the originating list, else they will be added in reverse
     * order.
     */
    public Vector(List<Integer> from, boolean as_is) {
        size = 0;
        internaList = new LinkedList<>();
        if (as_is) {
            internaList.addAll(from);
            size = from.size();
        } else {
            for (Integer v : from) {
                add(v);
            }
        }
    }

    /**
     * Returns a list representation of this vector indexes on the
     * returned list will go from left to right.
     * @return a {@code List} representation of this Vector.
     */
    public List<Integer> toList() {
        return new LinkedList<>(internaList);
    }

    /**
     * Appends an element to this vector.
     * @param v : the element to append.
     */
    public void add(int v) {
        internaList.add(0, v);
        size++;
    }

    /**
     * Returns the element at a given index
     * @param index : the index of the element to return
     * @return the value at {@code index}
     * @throws IllegalArgumentException iff {@code index} is an invalid index, i.e.: not in [0, size)
     */
    public int at(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException(String.format("Invalid index %d [0 - %d]", index, this.size - 1));
        }
        return internaList.get(size - 1 - index);
    }

    /**
     * Modifies the element at a given index
     * @param index : the index of the element to modify
     * @param v : the new value for the position given by {@code index}
     * @return the value at {@code index}
     * @throws IllegalArgumentException iff {@code index} is an invalid index, i.e.: not in [0, size)
     */
    public void setAt(int index, int v) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException(String.format("Invalid index %d [0 - %d]", index, this.size - 1));
        }
        internaList.add(size - 1 - index, v);
    }

    /**
     * Returns the size of this vector
     * @return the size of this vector
     */
    public int size() {
        return size;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Vector)) {
            return false;
        }
        Vector otherAsVector = (Vector) other;
        if (otherAsVector.size() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (otherAsVector.at(i) != at(i)) {
                return false;
            }
        }
        return true;
    }

    
    /**
     * Returns the representation of this vector
     * @return a representation of this vector
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < size; i++) {
            sb.append(internaList.get(i));
            if (i + 1 < size) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }
    
}