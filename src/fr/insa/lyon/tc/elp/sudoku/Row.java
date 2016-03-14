package fr.insa.lyon.tc.elp.sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a sudoku grid row
 */
class Row extends Unit {

    /// index of the row
    private int myIndex;

    /**
     * Constructor
     *
     * @param index of the row
     */
    public Row(int index) {
        myIndex = index;
    }

    /**
     * @return list of the 9 squares of the row
     */
    public List<Square> squares() {
        ArrayList<Square> squares = new ArrayList<>();
        for (int col = 0; col < Square.UPPER_BOUND; col++)
            squares.add(new Square(myIndex, col));
        return squares;
    }
}
