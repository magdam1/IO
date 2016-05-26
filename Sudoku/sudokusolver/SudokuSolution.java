package sudokusolver;

import dancinglinks.Solution;

import java.util.LinkedList;
import java.util.List;

/** Represents sudoku solution. */
public class SudokuSolution {
    private List<SudokuChoice> choices = new LinkedList<>();

    public SudokuSolution(Solution raw_solution, DancingLinksLabels[] labels) {
        for (Integer i : raw_solution.getRows()) {
            choices.add(new SudokuChoice(labels[i]));
        }
    }

    public List<SudokuChoice> getChoices() {
        return choices;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (SudokuChoice choice : choices) {
            result.append(choice.toString()).append(", ");
        }
        result.replace(result.length() - 2, result.length(), "]");
        return result.toString();
    }

    public SudokuChoice getNext() {
        if(!choices.isEmpty())
            return choices.remove(0);

        return null;
    }
}
