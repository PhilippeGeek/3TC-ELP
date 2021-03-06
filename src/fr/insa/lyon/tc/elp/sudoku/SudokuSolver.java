package fr.insa.lyon.tc.elp.sudoku;

import java.io.*;
import java.util.*;

/**
 * Class that solves a sudoku grid
 * given at construction as a stream
 */
class SudokuSolver {

    /// size of the sudolu grid
    private static final int size = 9;

    /// map of pairs (square, int), ie original grid to complete
    private Map<Square, Integer> originalGrid = new HashMap<Square, Integer>();
    /// map of pairs (square, set of possible digits)
    private Map<Square, DigitSet> workingGrid = new HashMap<Square, DigitSet>();
    /// map of pairs (square, list of friends)
    private Map<Square, List<Square>> friends = new HashMap<Square, List<Square>>();
    /// assignation marks as a set of squares 
    private Set<Square> marks = new HashSet<Square>();

    /**
     * Construction from the standard input stream
     */
    public SudokuSolver() throws IOException {
        this(System.in);
    }

    /**
     * Constructor
     *
     * @param stream any input stream
     */
    public SudokuSolver(InputStream stream) throws IOException {
        fillGrid(stream);
        fillExtraDataSructures();
    }

    /**
     * Helper for construction that parses the sudoku grid from an input stream
     *
     * @param stream any input stream
     */
    private void fillGrid(InputStream stream) throws IOException {

        Reader reader = new InputStreamReader(stream);
        char c; //read character from reader
        int d; //read digit from c

        for (int i = 0; i < size; i++) {
            int j = 0;
            while (j < size) {
                c = (char) reader.read();
                if (Character.isDigit(c)) {
                    d = Integer.valueOf(String.valueOf(c));
                    if ((d >= 1) && (d <= 9))
                        originalGrid.put(new Square(i, j), d);
                    j++;
                }
            }
        }
    }

    /**
     * Helper for construction that initializes:
     * - the working grid as map of pairs (square, digit set)
     * - the map of pairs (square, list of friends)
     */
    private void fillExtraDataSructures() {

        for(int col = 0; col<Square.UPPER_BOUND; col++) {
            for (int row = 0; row < Square.UPPER_BOUND; row++) {
                Square position = new Square(row, col);

                /// fill friends
                HashSet<Square> friends_of = new HashSet<>(21);
                friends_of.addAll(position.getRow().squares());
                friends_of.addAll(position.getCol().squares());
                friends_of.addAll(position.getBox().squares());
                friends.put(position, new ArrayList<>(friends_of));

                /// fill working grid
                recomputeWorkingGridFor(position);
            }
        }

    }

    private void recomputeWorkingGridFor(Square position){
        DigitSet possibilities = workingGrid.get(position);
        if(possibilities == null) {
            possibilities = new DigitSet();
            workingGrid.put(position, possibilities);
        }
        if(originalGrid.get(position)!=null){
            possibilities.remove(possibilities);
        }
        for (Square friend : friends.get(position))
            if (originalGrid.containsKey(friend))
                possibilities.remove(originalGrid.get(friend));
    }

    /**
     * Assign a digit to a square and propagate this new constraint
     *
     * @param aMap     map of pairs (Square, digit set) to compute the solution
     * @param aMarkSet set of assigned squares
     * @param aSquare  square at which we assign a digit
     * @param aDigit   digit to assign
     * @return 'false' if the solution is not valid, 'true' otherwise
     */
    private boolean assign(Map<Square, DigitSet> aMap, Set<Square> aMarkSet, Square aSquare, int aDigit) {
        //assign,
        aMap.put(aSquare, new DigitSet(aDigit));
        //mark,
        aMarkSet.add(aSquare);
        //and propagate
        return propagate(aMap, aMarkSet, aSquare, aDigit);
    }

    /**
     * Propagate a constraint from a given square where there is one possible digit
     * to all friend squares
     *
     * @param aMap     map of pairs (Square, digit set) to compute the solution
     * @param aMarkSet set of assigned squares
     * @param aSquare  square with a known digit
     * @param aDigit   digit of the square
     * @return 'false' if the solution is not valid, 'true' otherwise
     */
    private boolean propagate(Map<Square, DigitSet> aMap, Set<Square> aMarkSet, Square aSquare, int aDigit) {
        boolean isOK = true;
        Iterator<Square> it = friends.get(aSquare).iterator();
        while ((it.hasNext()) && (isOK)) {
            isOK = isOK && remove(aMap, aMarkSet, it.next(), aDigit);
        }
        return isOK;
    }

    /**
     * Remove a given digit from the digit set at a given square.
     * Then, propagate this new constraint if needed, and check units
     * the square belongs to.
     *
     * @param aMap     map of pairs (Square, digit set) to compute the solution
     * @param aMarkSet set of assigned squares
     * @param aSquare  square where a digit must be removed
     * @param aDigit   digit to remove
     * @return 'false' if the solution is not valid, 'true' otherwise
     */
    public boolean remove(Map<Square, DigitSet> aMap, Set<Square> aMarkSet, Square aSquare, int aDigit) {

        //we remove aDigit from the digit set s at square aSquare
        DigitSet s = aMap.get(aSquare);
        s.remove(aDigit);

        if (s.size() == 0)
            return false; //contradiction: only one possible digit at two squares in the same unit
            // (1) if s is reduced to one value, assign it if not already done
        else if ((s.size() == 1) && (!aMarkSet.contains(aSquare))) {
            if (!assign(aMap, aMarkSet, aSquare, s.firstDigit()))
                return false;
        }
        // (2) check all units aSquare belongs to.
        boolean isOK = true;
        isOK = isOK && checkUnit(aMap, aMarkSet, aSquare.getRow());
        isOK = isOK && checkUnit(aMap, aMarkSet, aSquare.getCol());
        isOK = isOK && checkUnit(aMap, aMarkSet, aSquare.getBox());
        return isOK;
    }

    /**
     * Check if there is one square in a unit
     * for which there is a possible digit, not possible
     * for other unit squares. If yes, assign this digit
     * to this square
     *
     * @param aMap     map of pairs (Square, digit set) to compute the solution
     * @param aMarkSet set of assigned squares
     * @param aUnit    any unit
     * @return 'false' if the sudoku is not valid, 'true' otherwise
     */
    private boolean checkUnit(Map<Square, DigitSet> aMap, Set<Square> aMarkSet, Unit aUnit) {
        boolean isOK = true;
        //for all unit square
        Iterator<Square> it = aUnit.squares().iterator();
        while ((it.hasNext()) && (isOK)) {
            Square square = it.next();
            DigitSet s = new DigitSet(aMap.get(square));
            if (s.size() > 1) {
                //only keep digits that are not possible for other squares
                for (Square other : aUnit.squares()) {
                    if (!square.equals(other))
                        s.remove(aMap.get(other));
                }
                if ((s.size() == 1) && (!aMarkSet.contains(square)))
                    //if one such digit, assign it if not already done
                    isOK = isOK && assign(aMap, aMarkSet, square, s.firstDigit());
            }
        }
        return isOK;
    }

    /**
     * Solve the sudoku grid
     *
     * @return 'false' if not valid, 'true' otherwise
     */
    public boolean solve() {
        // propagate constraint from every known digit:
        // while there are known digits in the original grid
        // and the 'assign' method returns true,
        // we take the next entry and call the 'assign'
        // method for it.
        boolean isOk = true;
        int previousMarksCount = 9*9+1;
        do {
            if(marks.size()!=0)
                previousMarksCount = marks.size();
            marks.clear();
            for (int col = 0; col < 9; col++) {
                for (int row = 0; row < 9; row++) {
                    Square position = new Square(row, col);
                    DigitSet set = workingGrid.get(position);
                    if (set.size() == 1) {
                        originalGrid.put(position, set.firstDigit());
                        set.remove(set);
                    }
                    else if (set.size() > 1)
                        marks.add(position);
                }
            }
            for (Square position : marks)
                recomputeWorkingGridFor(position);
        } while (marks.size() > 0 && marks.size() < previousMarksCount);
        return marks.size() == 0;
    }

    /**
     * Display the original grid
     *
     * @param stream any output stream
     */
    public void displayOriginalGrid(OutputStream stream) {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(stream), true);
        String line; //line to print
        Integer d; //digit to print

        out.println("Grid:");
        out.println(separator(1));
        for (int i = 0; i < size; i++) {
            line = "";
            for (int j = 0; j < size; j++) {
                if ((d = originalGrid.get(new Square(i, j))) != null)
                    line += " " + d + " ";
                else
                    line += " . ";
                if ((j % 3) == 2)
                    line += "|";
            }
            out.println(line);
            if ((i % 3) == 2)
                out.println(separator(1));
        }
    }

    /**
     * Display the working grid
     *
     * @param stream any output stream
     */
    public void displayWorkingGrid(OutputStream stream) {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(stream), true);
        String line; //line to print
        DigitSet s; //digit set to print

        out.println("Data structure:");
        out.println(separator(size));
        for (int i = 0; i < size; i++) {
            line = "";
            for (int j = 0; j < size; j++) {
                s = workingGrid.get(new Square(i, j));
                line += " " + s;
                line += repeatedString(" ", size + 1 - s.size());
                if ((j % 3) == 2)
                    line += "|";
            }
            out.println(line);
            if ((i % 3) == 2)
                out.println(separator(size));
        }
    }

    private String repeatedString(String substring, int nb) {
        String res = "";
        for (int k = 0; k < nb; k++)
            res += substring;
        return res;
    }

    private String separator(int length) {
        String line = "";
        for (int k = 0; k < 3; k++) {
            line += repeatedString("-", length);
            line += "-+";
            line += repeatedString("-", length);
            line += "-+";
            line += repeatedString("-", length);
            line += "--|";
        }
        return line;
    }

}
