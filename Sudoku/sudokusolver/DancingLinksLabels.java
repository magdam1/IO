package sudokusolver;

/**
 * Class representing one row in a coincidence matrix corresponding to an
 * exact cover of a sudoku problem. Each row in this matrix has format: R_C_#_,
 * where _ is a number 0-8 (!!!) and letters are: R - row, C - column, # - number
 * and corresponds to this specific row and column being filled with that number
 *
 * CAUTION! unlike in SudokuBoard, number is between 0 and 8 !
 */
class DancingLinksLabels {
    protected int row;           // 0-8 !
    protected int col;           // 0-8 !
    protected int number;        // 0-8 !

    protected boolean selected;  // whether the cell is already filled
    protected boolean deleted;   // whether the row is conflicting with filled numbers

    DancingLinksLabels(int row, int col, int number, boolean selected) {
        this.row = row;
        this.col = col;
        this.number = number;
        this.selected = selected;
        this.deleted = selected;        // initially selected == deleted
    }

    void select() {
        selected = true;
        deleted = true;
    }

    void exclude() {
        deleted = true;
    }

    @Override
    public String toString() {
        return "R" + String.valueOf(row) + "C" + String.valueOf(col) + "#" + String.valueOf(number) +
                "   sel: [" + selected + "], del: " + deleted + "]";
    }

    public String toShortString() {
        return "R" + String.valueOf(row) + "C" + String.valueOf(col) + "#" + String.valueOf(number);
    }
}
