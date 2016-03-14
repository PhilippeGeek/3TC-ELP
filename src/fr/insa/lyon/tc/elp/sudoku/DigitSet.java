package fr.insa.lyon.tc.elp.sudoku;

import java.util.HashSet;
import java.util.Set;

/**
 * Class that represents the set of possible digits (in 1..9)
 * at a sudoku position
 */
class DigitSet {

    /// set of digits
    private Set<Integer> mySet = new HashSet<Integer>();

    /**
     * Constructor: add all digits from 1 to 9
     */
    public DigitSet() {
        this(0);
    }

    /**
     * Constructor: add a given digit to the set
     *
     * @param aDigit digit to add
     * @warning if aDigit is not valid (not in 1..9),
     * all digits from 1 to 9 are added
     */
    public DigitSet(int aDigit) {
        if ((aDigit < 1) || (aDigit > 9))
            for (int i = 1; i <= 9; i++)
                mySet.add(i);
        else
            mySet.add(aDigit);
    }

    /**
     * Copy constructor: add to this set
     * all digits of the other set
     *
     * @param other another digit set
     */
    public DigitSet(DigitSet other) {
        mySet = new HashSet<>(other.mySet);
    }

    /**
     * Remove a given digit from this set
     *
     * @param aDigit any valid digit
     * @warning nothing is done if the given digit
     * is not in the set
     */
    public void remove(int aDigit) {
        mySet.remove(aDigit);
    }

    /**
     * Remove all digits of another set from this set
     *
     * @param other another digit set
     */
    public void remove(DigitSet other) {
        for (Object i : other.mySet.toArray())
            remove((Integer)i);
    }

    /**
     * Give the set size (number of digits)
     *
     * @return set size
     */
    public int size() {
        return mySet.size();
    }

    /**
     * Give the first digit of the set
     *
     * @return first digit
     */
    public int firstDigit() {
        if (size() > 0)
            return mySet.toArray(new Integer[size()])[0];
        else
            return 0;
    }

    /**
     * Give a string representation of the digit set
     * where all digits are concatenated without space.
     *
     * @return the string
     * @warning the string length must be equal to the set size
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Integer i : mySet) {
            builder.append(i);
        }
        return builder.toString();
    }
}