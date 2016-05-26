package sudokusolver;

/** Exception thrown when sudoku can't be solved */
public class NoSolutionException extends Exception {
    public NoSolutionException() {
        super("No solution exists");
    }
}
