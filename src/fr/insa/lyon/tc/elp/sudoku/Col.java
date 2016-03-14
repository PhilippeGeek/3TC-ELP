package fr.insa.lyon.tc.elp.sudoku;

import java.util.ArrayList;
import java.util.List;

public class Col extends Unit {

    private final int colIndex;

    public Col(int colIndex) {

        this.colIndex = colIndex;
    }

    public int getIndex() {
        return colIndex;
    }

    @Override
    public List<Square> squares() {
        ArrayList<Square> squares = new ArrayList<>();
        for (int row = 0; row < Square.UPPER_BOUND; row++)
            squares.add(new Square(row, getIndex()));
        return squares;
    }
}
