package sudokusolver;

import java.util.Arrays;

/** Class used to generate an exact cover matrix given sudoku board. */
class ExactCoverMatrixGenerator {
    private final int constraints = 4;  // number of constraints in exact cover
    private int[][] sudoku;
    private final int sudokuSize;       // typically 9
    private boolean[][] matrix;         // exact cover matrix
    private int width;                  // width of 'matrix'
    private int height;                 // height of 'matrix'
    private DancingLinksLabels[] DLXRows;     // row labels in exact cover matrix

    public ExactCoverMatrixGenerator(int[][] board)
            throws InvalidBoardException {
        this(new SudokuBoard(board));
    }

    public ExactCoverMatrixGenerator(SudokuBoard sudoku)
            throws InvalidBoardException {
        this.sudoku = sudoku.getBoard();
        this.sudokuSize = sudoku.getSize();
        this.height = sudokuSize * sudokuSize * sudokuSize;
        this.width = sudokuSize * sudokuSize * constraints;
        this.DLXRows = new DancingLinksLabels[height];
        this.matrix = new boolean[height][width];

        fillDLXRows();
        generateMatrix();
    }

    /** get the exact cover matrix */
    public boolean[][] getExactCoverMatrix() {
        return matrix;
    }
    /** get labels to the exact cover matrix */
    public DancingLinksLabels[] getDancingLinksLabels() {
        return DLXRows;
    }

    /** fills DLXRows array (labels for the matrix) */
    private void fillDLXRows() {
        int i = 0;
        for (int row = 0; row < sudokuSize; ++row)
            for (int col = 0; col < sudokuSize; ++col)
                for (int digit = 0; digit < sudokuSize; ++digit)
                    if (sudoku[row][col] == digit + 1)   // different numeration
                        DLXRows[i++] = new DancingLinksLabels(row, col, digit, true);
                    else
                        DLXRows[i++] = new DancingLinksLabels(row, col, digit, false);
    }

    /** generates an exact cover matrix */
    private void generateMatrix() throws InvalidBoardException {
        RowIterator rowIt = new RowIterator(sudokuSize);
        int nextCol = 0;
        for (int i = 0; i < width; ++i) {
            nextCol = generateColumn(nextCol, rowIt);
        }

        /* Now the matrix is ready, but it's too wide and has some rows that
         * should be deleted. 'nextCol' is a proper width of the new array. */

        int newRow = 0;   // points to current row in the updated matrix
        int oldRow = 0;   // loops over all rows in the old matrix
        int newWidth = nextCol;     // width of the updated matrix
        for (; oldRow < height; ++oldRow) {
            if (!DLXRows[oldRow].deleted) {      // else just ignore the row
                DLXRows[newRow] = DLXRows[oldRow];
                /* reassign and truncate whole row: */
                matrix[newRow] = Arrays.copyOf(matrix[oldRow], newWidth);
                ++newRow;
            }
        }
        /* also truncate columns: */
        matrix = Arrays.copyOf(matrix, newRow);
    }

    /** generates one column in an exact cover matrix */
    private int generateColumn(int col, RowIterator rowIt)
            throws InvalidBoardException {
        boolean alreadyHit = false;   // whether column 'col' was hit by any row
        int row;

        for (int i = 0; i < sudokuSize; ++i) {
            row = rowIt.next();
            if (DLXRows[row].selected) {
                if (alreadyHit)
                    throw new InvalidBoardException("Conflicting numbers");
                alreadyHit = true;
            }
        }
        /* now rowIt will be looping over the same rows as before: */
        if (alreadyHit) {   // exclude column and delete all rows with 1 in that column
            for (int i = 0; i < sudokuSize; ++i) {
                row = rowIt.next();
                DLXRows[row].exclude();
            }
        } else {            // include column to matrix
            for (int i = 0; i < sudokuSize; ++i) {
                row = rowIt.next();
                matrix[row][col] = true;
            }
            col += 1;       // column is included
        }

        return col;
    }
}
