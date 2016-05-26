package sudokusolver;

/** Exception thrown in case of inconsistent sudoku board state. */
public class InvalidBoardException extends Exception {
    public InvalidBoardException(String s) {
        super(s);
    }
}
