package dancinglinks;

import java.util.Stack;

/** Class storing temporary solution to
 * an exact cover problem as a stack of rows. */
class RowStack {
    Stack<Cell> stack = new Stack<>();

    public RowStack() {}

    public void push(Cell row) {
        stack.push(row);
    }

    public Cell pop() {
        return stack.pop();
    }

    public Solution toSolution() {
        return new Solution(stack);
    }
}
