package dancinglinks;

import java.util.LinkedList;
import java.util.List;

/** This class implements Donald Knuth's Dancing Links algorithm. */
public class DancingLinks {
    /* Those are attributes due to efficiency: */
    @SuppressWarnings("FieldCanBeLocal")
    private Header column;
    @SuppressWarnings("FieldCanBeLocal")
    private Cell row;
    @SuppressWarnings("FieldCanBeLocal")
    private Cell col;

    private Header superHead;               // head of the linked matrix
    private MatrixZeroOne matrixZeroOne;    // matrix of zeros and ones
    /** Stack of rows temporarily included to solution: */
    private RowStack tempSolution = new RowStack();
    /** All solutions of the exact cover problem: */
    private List<Solution> solutions = new LinkedList<>();
    /** Maximum number of solutions to give while finding an exact cover. */
    int maxSolutions = 0;       // 0 means all solutions
    /** Whether to continue an exact cover finding algorithm. */
    boolean continueSearch = false;

    /** Feeds DancingLinks with an array of zeros and ones */
    public DancingLinks(boolean[][] array) {
        matrixZeroOne = new MatrixZeroOne(array);
    }
    /** Feeds DancingLinks with an array of integers (converted to 0 and 1) */
    public DancingLinks(int[][] array) {
        matrixZeroOne = new MatrixZeroOne(array);
    }

    /**
     * Finds an exact cover for the given matrix. Firstly converts the
     * 'matrixZeroOne' to quaterly-linked list representation and then performs
     * "Dancing Links" algorithm by recursively calling 'step()'.
     * Returns list of solutions to the exact cover problem containing at most
     * 'maxSolutions' solutions or all of them if maxSolutions equals to 0.
     */
    public List<Solution> exactCover(int maxSolutions) {
        this.maxSolutions = maxSolutions;
        continueSearch = true;
        superHead = matrixZeroOne.toSparse();
        step();
        return solutions;
    }

    public List<Solution> getSolutions() {
        return solutions;
    }

    /** Recursively add one row to solution at each step. */
    public void step() {
        column = choose_min_column();
        if (column == null) {               // success
            solutions.add(tempSolution.toSolution());
            if (solutions.size() >= maxSolutions)
                continueSearch = false;     // stop if too many solutions
        } else if (column.count > 0) {      // else solution doesn't exist

            column.remove();

            for (row = column.down; row != column; row = row.down) {
                tempSolution.push(row);
                for (col = row.right; col != row; col = col.right) {
                    col.head.remove();
                }

                if (continueSearch)
                    step();

                row = tempSolution.pop();
                column = row.head;
                for (col = row.left; col != row; col = col.left) {
                    col.head.restore();
                }
            }

            column.restore();
        }

    }

    /** Chooses a column with smallest number of ones. (Knuth's heuristic) */
    private Header choose_min_column() {
        if (superHead == superHead.right) {     // empty matrix
            return null;
        } else {                                // choose min column
            int min = superHead.right.count;
            Header minH = superHead.right;
            for (Header h = superHead.right; h != superHead; h = h.right) {
                if (h.count < min) {
                    min = h.count;
                    minH = h;
                }
            }
            return minH;
        }
    }
}
