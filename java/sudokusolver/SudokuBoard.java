package sudokusolver;

/**
 * Class representing sudoku board.
 *
 * CAUTION! Rows and columns are indexed from 0 to 'size-1'.
 * Cells are filled with numbers from 1 to 'size'.
 */
public class SudokuBoard {
    protected final int size;       // usually 9
    protected int[][] board;

    /**
     * @param board must be a square array filled with numbers from 0 to size.
     *              0 means an empty cell.
     */
    public SudokuBoard(int[][] board) {
        this.board = board;
        this.size = board.length;
    }

    public int getSize() {
        return size;
    }

    public int[][] getBoard() {
        return board;
    }
}

