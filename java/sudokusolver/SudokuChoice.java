package sudokusolver;

/**
 * Class representing one choice: row, column, number
 * CAUTION! unlike in DancingLinksLabels, number is between 1 and 9 !
 */
public class SudokuChoice {
    protected int row;           // 1-9 !
    protected int col;           // 1-9 !
    protected int number;        // 1-9 !

    SudokuChoice(DancingLinksLabels dancingLinksLabels) {
        this.row = dancingLinksLabels.row + 1;    // different numeration!
        this.col = dancingLinksLabels.col + 1;
        this.number = dancingLinksLabels.number + 1;
    }

    @Override
    public String toString() {
        return "R" + String.valueOf(row) + "C" + String.valueOf(col) + "#" + String.valueOf(number);
    }


    public int getNumber() {
        return number;
    }

    public int getCol() {
        return col - 1;
    }

    public int getRow() {
        return row - 1;
    }
}