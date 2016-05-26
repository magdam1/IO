package dancinglinks;

/** Class generating sparse matrix representation given a matrix of 0's and 1's */
class MatrixZeroOne {
    private boolean[][] data;
    private int width;
    private int height;

    private Cell[][] sparseData;
    private Header[] headers;

    /** Feeds MatrixZeroOne with an array of zeros and ones */
    public MatrixZeroOne(boolean[][] array) throws NullPointerException {
        data = array;
        height = array.length;
        if (height == 0) throw new NullPointerException();
        width = array[0].length;
    }

    /** Feeds MatrixZeroOne with an array of integers (converted to 0 and 1) */
    public MatrixZeroOne(int[][] array) throws NullPointerException {
        height = array.length;
        if (height == 0) throw new NullPointerException();
        width = array[0].length;
        data = new boolean[height][width];
        for (int i = 0; i < height; ++i)
            for (int j = 0; j < width; ++j)
                data[i][j] = (array[i][j] != 0);
    }

    /** Generate sparse matrix representation of a matrix.
     *  'Array' is arranged rowwise. Returns reference to "superHead". */
    Header toSparse() {
        if (height == 0 || width == 0) {
            return null;
        } else {
            /* Create all objects: */
            headers = new Header[width + 1];
            for (int col = 0; col < width + 1; ++col)
                headers[col] = new Header(col);

            sparseData = new Cell[height][width];
            for (int row = 0; row < height; ++row)
                for (int col = 0; col < width; ++col)
                    if (data[row][col])
                        sparseData[row][col] = new Cell(row, headers[col]);

            Header superHead = headers[width];

            /* Link everything: */
            link_headers();
            for (int row = 0; row < height; ++row) {
                link_horizontally(row);
            }
            for (int col = 0; col < width; ++col) {
                link_vertically(col);
            }

            superHead.down = superHead.up = superHead.head = superHead;
            return superHead;
        }
    }

    /** Sets 'left' and 'right' attribute of each header. */
    private void link_headers() {
        print("@link_headers:");
        for (int col = 0; col < width; ++col) {
            headers[col].right = headers[col + 1];
            headers[col + 1].left = headers[col];
            print(-1, col, -1, col + 1);
        }
        headers[width].right = headers[0];
        headers[0].left = headers[width];
        print(-1, 0, -1, width);
    }

    /** Sets 'left' and 'right' attribute of each cell in a row 'row'. */
    private void link_horizontally(int row) {
        print("@link_horizontally(" + String.valueOf(row) + "):");
        int l, r;       // two neighbouring cells of 1 moving right
        int first = first_horizontal(row);

        if (first >= 0) {   // non-empty row
            r = first;

            do {
                l = r;
                r = next_horizontal(row, r);
                sparseData[row][l].right = sparseData[row][r];
                sparseData[row][r].left = sparseData[row][l];
                print(row, l, row, r);
            } while (r != first);
        }
    }

    /** Returns index of first non-empty column in row 'row' or -1 if it's empty. */
    private int first_horizontal(int row) {
        print("@@first_horizontal(" + String.valueOf(row) + "):");
        int j = 0;
        while (j < width && !data[row][j]) {
            ++j;
        }
        return j == width ? -1 : j;
    }
    /**
     * Returns index of first non-empty column in row 'row'
     * starting from column 'col' + 1. We assume that row 'row' is not empty.
     */
    private int next_horizontal(int row, int col) {
        print("@@next_horizontal(" + String.valueOf(row) + ", " + String.valueOf(col) + "):");
        do {
            col = (col + 1) % width;
        } while (!data[row][col]);
        return col;
    }



    /**
     * Sets 'up', 'down', 'head' attribute of each cell in a column 'col'
     * and a header of this column.
     */
    private void link_vertically(int col) {
        print("@link_vertically(" + String.valueOf(col) + "):");
        int u, d;       // two neighbouring cells of 1 moving down
        int first = first_vertical(col);

        Header colHead = headers[col];
        colHead.head = colHead;             // self-reference

        if (first == -1) {    // empty column
            colHead.down = colHead;
            colHead.up = colHead;
            print(-1, col, -1, col);

        } else {        // non-empty column
            u = first;
            d = next_vertical(u, col);

            while (d != first) {
                sparseData[u][col].down = sparseData[d][col];
                sparseData[d][col].up = sparseData[u][col];

                print(u, col, d, col);
                print(u, col);

                u = d;
                d = next_vertical(d, col);
            }
            // now 'd == first' and 'u' is the last non-empty cell in column 'col'
            sparseData[u][col].down = colHead;
            sparseData[first][col].up = colHead;
            colHead.down = sparseData[first][col];
            colHead.up = sparseData[u][col];

            print(u, col, -1, col);
            print(first, col, -1, col);
            print(u, col);
        }
    }

    /** Returns index of first non-empty row in column 'col' or -1 if it's empty. */
    private int first_vertical(int col) {
        print("@@first_vertical(" + String.valueOf(col) + "):");
        int i = 0;
        while (i < height && !data[i][col]) {
            ++i;
        }
        return i == height ? -1 : i;
    }
    /**
     * Returns index of first non-empty row in column 'col'
     * starting from row 'row' + 1. We assume that column 'col' is not empty.
     */
    private int next_vertical(int row, int col) {
        print("@@next_vertical(" + String.valueOf(row) + ", " + String.valueOf(col) + "):");
        do {
            row = (row + 1) % height;
        } while (!data[row][col]);
        return row;
    }

    private void print(String s) {
        //System.out.println(s);
    }

    private void print(int i1, int j1) {
        //System.out.println("(" + String.valueOf(i1) + "," + String.valueOf(j1) +
         //       ") -> head");
    }

    private void print(int i1, int j1, int i2, int j2) {
       // System.out.println("(" + String.valueOf(i1) + "," + String.valueOf(j1) +
        //        ") <-> (" + String.valueOf(i2) + "," + String.valueOf(j2) + ")");
    }

}
