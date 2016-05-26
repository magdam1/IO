package dancinglinks;

/** Represents a header of each column in matrix. */
class Header extends Cell {
    Header left;            // package visibility, NOT inherited
    Header right;           // package visibility, NOT inherited
    protected int count = 0;
    protected int colNum;

    public Header(int colNum) {
        this.rowNum = -1;
        this.head = this;
        this.colNum = colNum;
    }

    public void hideOneCell() { count -= 1; }
    public void showOneCell() { count += 1; }

    /** Removes whole column along with rows having one in that column. */
    public void remove() {
        hide();
        for (Cell row = down; row != this; row = row.down) {
            for (Cell col = row.right; col != row; col = col.right) {
                col.hide();
            }
        }
    }

    /** Restores whole column along with rows having one in that column. */
    public void restore() {
        for (Cell row = up; row != this; row = row.up) {
            for (Cell col = row.left; col != row; col = col.left) {
                col.show();
            }
        }
        show();
    }

    /** Removes column from the list of columns. */
    @Override
    public void hide() {
        left.right = right;
        right.left = left;
    }

    /** Adds column back to the list of columns. */
    @Override
    public void show() {
        left.right = this;
        right.left = this;
    }
}
