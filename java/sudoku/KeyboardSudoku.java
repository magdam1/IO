package sudoku;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import java.util.Set;

/* Represents a keyboard for a given field */
public class KeyboardSudoku extends Dialog {
    private final GameView gameView;
    private final View buttons[] = new View[9];
    private final Set<Integer> usedFields;
    private View keyboard;

    public KeyboardSudoku(Game game, GameView gameView, Set<Integer> used) {
        super(game);

        this.usedFields = used;
        this.gameView = gameView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.keyboard_sudoku_title);
        setContentView(R.layout.keyboard_sudoku);
        findViews();
        for(int value : usedFields)
            buttons[value - 1].setVisibility(View.INVISIBLE);
        setListeners();
    }

    private void findViews() {
        keyboard = findViewById(R.id.keyboard_sudoku);
        buttons[0] = findViewById(R.id.keyboard_sudoku_1);
        buttons[1] = findViewById(R.id.keyboard_sudoku_2);
        buttons[2] = findViewById(R.id.keyboard_sudoku_3);
        buttons[3] = findViewById(R.id.keyboard_sudoku_4);
        buttons[4] = findViewById(R.id.keyboard_sudoku_5);
        buttons[5] = findViewById(R.id.keyboard_sudoku_6);
        buttons[6] = findViewById(R.id.keyboard_sudoku_7);
        buttons[7] = findViewById(R.id.keyboard_sudoku_8);
        buttons[8] = findViewById(R.id.keyboard_sudoku_9);
    }

    private void setListeners() {
        for(int i = 0; i < buttons.length; i++) {
            final int t = i + 1;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    returnResult(t);
                }
            });
        }

        keyboard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                returnResult(0);
            }
        });
    }

    /* Returns value corresponding to each button */
    private void returnResult(int value) {
        gameView.setChosenField(value);
        dismiss();
    }
}
