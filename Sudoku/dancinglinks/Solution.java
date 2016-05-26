package dancinglinks;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/** One solution to an exact cover problem as a list of rows in matrix. */
public class Solution {
    private List<Integer> rows = new LinkedList<>();

    Solution(Stack<Cell> rowStack) {        // package visibility!
        for (Cell c : rowStack) {
            rows.add(c.rowNum);
        }
    }

    public List<Integer> getRows() {
        return rows;
    }

    public String toString() {
        return rows.toString();
    }
}