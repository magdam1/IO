package sudoku;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sudokusolver.InvalidBoardException;
import sudokusolver.SudokuChoice;
import sudokusolver.SudokuSolution;
import sudokusolver.SudokuSolver;


public class Game extends ActionBarActivity {
    private int[][] fields;
    private GameView gameView;
    private boolean edited;
    SudokuSolution solution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        edited = true;
        fields = new int[9][9];

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int [] tmp = extras.getIntArray("Board");
            if (tmp != null) {
                for (int i=0; i<9; i++) {
                    for (int j=0; j<9; j++) {
                        fields[j][i] = tmp[9*i+j];
                    }
                }
            }
        }

        setContentView(R.layout.activity_game);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_game);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1);

        /* Adds custom View class to ViewGroup */
        gameView = new GameView(this);
        gameView.requestFocus();
        gameView.setLayoutParams(params);
        layout.addView(gameView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Shows the next step in computed solution. Computes a new solution only
       if the board has changed */
    public void showHint(View view) {
        if(edited && !isCompleted()) {
            SudokuSolver solver = new SudokuSolver(fields);
            try {
                List<SudokuSolution> solutions = solver.solve(1);
                if(!solutions.isEmpty()) {
                    solution = solutions.remove(0);
                    showNext(solution);
                }
                else
                    showAlert("No solution exists"); //hardcoded bo nie dziala inaczej
            } catch (InvalidBoardException e) {
                showAlert(e.getMessage());
            }
        } else if(!isCompleted())
            showNext(solution);
    }

    private void showNext(SudokuSolution solution) {
        SudokuChoice choice = solution.getNext();
        int row = choice.getRow();
        int col = choice.getCol();
        int value = choice.getNumber();

        if(setField(row, col, value))
            setEdit(false);
    }

    /* Shows keyboard for input. */
    public void showKeyboard(int x, int y) {
        Dialog dialog = new KeyboardSudoku(this, gameView, usedFields(x, y));
        dialog.show();
    }

    public String getField(int i, int j) {
        if(fields[i][j] == 0)
            return "";
        else
            return String.valueOf(fields[i][j]);
    }

    /* Sets chosen field to value. Checks if data is correct. Returns true on success. */
    public boolean setField(int chosenX, int chosenY, int value) {
        Set<Integer> usedFields = usedFields(chosenX, chosenY);
        if(value != 0 && usedFields.contains(Integer.valueOf(value)))
            return false;

        fields[chosenX][chosenY] = value;
        invalidate();
        return true;
    }

    /* Shows alert message */
    private void showAlert(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage(message)
                .setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /* Returns a set of Integers used in the same row, column or 9x9 box. */
    private Set<Integer> usedFields(int x, int y) {
        Set<Integer> set = new HashSet<Integer>();

        //horizontally
        for(int i = 0; i < 9; i++) {
            if((i != x) && (fields[i][y] != 0))
                set.add(Integer.valueOf(fields[i][y]));
        }

        //vertically
        for(int j = 0; j < 9; j++) {
            if((j != y) && (fields[x][j] != 0))
                set.add(Integer.valueOf(fields[x][j]));
        }

        //9x9 box
        int startX = (x / 3) * 3;
        int startY = (y / 3) * 3;
        for(int i = startX; i < startX + 3; i++) {
            for(int j = startY; j < startY + 3; j++) {
                if((i != x) && (j != y) && (fields[i][j] != 0))
                    set.add(Integer.valueOf(fields[i][j]));
            }
        }

        return set;
    }

    /* Refreshes the layout. */
    private void invalidate() {
        gameView.invalidate();
    }


    /* Returns true if all fields are filled */
    private boolean isCompleted() {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(fields[i][j] == 0)
                    return false;
            }
        }

        return true;
    }

    public void setEdit(boolean edit) {
        this.edited = edit;
    }
}
