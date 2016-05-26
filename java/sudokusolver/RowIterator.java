package sudokusolver;

/**
 * This class enables looping over rows while generating an exact cover matrix.
 * Looping schema:
 * for outer = 0 to sudokuSize - 1:
 *    for inner = 0 to sudokuSize - 1:
 *       for iterNum = 0 to 1:
 *           for i = 0 to sudokuSize - 1:
 *               return next row, for which column X[outer]X[inner] in the
 *               exact cover matrix should contain one.
 *
 * where X#X# is one of columns (R#C#, R#N#, C#N#, B#N#) described here:
 * http://en.wikipedia.org/wiki/Exact_cover#Sudoku
 */
class RowIterator {
    protected final int size;       // 9
    protected final int size_2;     // 81
    protected final int sizeSqrt;   // 3
    /* Indices: */
    protected int phase = 0;        // phase number (0-3)
    protected int outer = 0;        // outer loop
    protected int inner = 0;        // inner loop
    protected int iterNum = 0;      // first or second iteration
    protected int i = 0;            // the most inner loop

    protected int result;

    public RowIterator(int sudokuSize) throws InvalidBoardException {
        if (sudokuSize <= 0)
            throw new InvalidBoardException("Sudoku size must be a positive number.");
        double sqrt = Math.sqrt(sudokuSize);
        if (sqrt != (int) sqrt)
            throw new InvalidBoardException("Sudoku size must be a square number.");
        this.size = sudokuSize;
        this.size_2 = sudokuSize * sudokuSize;
        this.sizeSqrt = (int) sqrt;
    }

    public int next() {
        switch (phase) {
            case 0 : nextRC(); break;
            case 1 : nextRN(); break;
            case 2 : nextCN(); break;
            case 3 : nextBN(); break;
        }

        updateIndices();
        return result;
    }

    /** consecutively updates indices: i, iterNum, inner, outer */
     private void updateIndices() {
        if (++i == size) {
            i = 0;
            if (++iterNum == 2) {
                iterNum = 0;
                if (++inner == size) {
                    inner = 0;
                    if (++outer == size) {
                        outer = 0;
                        ++phase;
                    }
                }
            }
        }
    }

    /**
     * Generate next row number in Row-Column phase.
     * outer = Row, inner = Column, i = Number
     */
    private void nextRC() {
        result = (outer * size_2) + (inner * size) + i;
    }

    /**
     * Generate next row number in Row-Number phase.
     * outer = Row, inner = Number, i = Column
     */
    private void nextRN() {
        result = (outer * size_2) + (i * size) + inner;
    }

    /**
     * Generate next row number in Column-Number phase.
     * outer = Column, inner = Number, i = Row
     */
    private void nextCN() {
        result = (i * size_2) + (outer * size) + inner;
    }

    /**
     * Generate next row number in Box-Number phase.
     * Row = 3*(outer/3) + (i/3), Col = 3*(outer % 3) + (i % 3), Number = inner
     */
    private void nextBN() {
        result = (sizeSqrt * (outer / sizeSqrt) + (i / sizeSqrt)) * size_2 +
                 (sizeSqrt * (outer % sizeSqrt) + (i % sizeSqrt)) * size + inner;
    }

    @Override
    public String toString() {
        return '(' + String.valueOf(outer) + ',' + String.valueOf(inner) + ',' +
                String.valueOf(iterNum) + ',' + String.valueOf(i) + ")  res:[" +
                String.valueOf(result) + ']';
    }
}
