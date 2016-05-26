package dancinglinks;

/**
 * Represents a cell contaning 1 in a sparse matrix representation.
 * Each cell has links to 4 neighbouring cells containing ones.
 * Cells are arranged in doubly-linked lists.
 */
class Cell {
    Cell left;            // package visibility, NOT inherited
    Cell right;           // package visibility, NOT inherited
    protected Cell up;
    protected Cell down;
    protected Header head;
    protected int rowNum;

    public Cell() {}
    public Cell(int rowNum, Header head) {
        this.rowNum = rowNum;
        this.head = head;
        head.count += 1;
    }

    /** Detaches cell from a column list. */
    public void hide() {
        up.down = down;
        down.up = up;
        head.hideOneCell();
    }

    /** Attaches cell back to a column list. */
    public void show() {
        head.showOneCell();
        up.down = this;
        down.up = this;
    }

    @Override
    public String toString() {
        return "(" + String.valueOf(rowNum) + "," + String.valueOf(head.colNum) + ")";
    }
}
