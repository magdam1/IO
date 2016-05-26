package sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
    private final Game game;
    private float width;
    private float height;
    private int chosenX;
    private int chosenY;

    public GameView(Context context) {
        super(context);

        setFocusable(true);
        setFocusableInTouchMode(true);
        this.game = (Game) context;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        width = w / 9f;
        height = h / 9f;
        super.onSizeChanged(w, h, oldW, oldH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint back = new Paint();
        back.setColor(getResources().getColor(R.color.game_back));
        canvas.drawRect(0, 0, getWidth(), getHeight(), back);

        Paint dark = new Paint();
        dark.setColor(getResources().getColor(R.color.game_dark));
        dark.setStrokeWidth(6);

        Paint highlight = new Paint();
        highlight.setColor(getResources().getColor(R.color.game_highlight));
        highlight.setStrokeWidth(3);

        Paint light = new Paint();
        light.setColor(getResources().getColor(R.color.game_light));
        light.setStrokeWidth(3);

        //draws small lines
        for (int i = 0; i < 9; i++) {
            canvas.drawLine(0, i * height, getWidth(), i * height, light);
            canvas.drawLine(i * width, 0, i * width, getHeight(), light);
        }

        //draws major lines
        for (int i = 0; i < 9; i++) {
            if (i % 3 != 0)
                continue;

            canvas.drawLine(0, i * height, getWidth(), i * height, dark);
            canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
        }

        //draws digits
        Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
        foreground.setColor(getResources().getColor(R.color.game_fore));
        foreground.setStyle(Paint.Style.FILL);
        foreground.setTextSize(height * 0.6f);
        foreground.setTextScaleX(width / height);
        foreground.setTextAlign(Paint.Align.CENTER);

        //draws digit in the middle of the field
        Paint.FontMetrics fm = foreground.getFontMetrics();
        float x = width / 2;
        float y = height / 2 - (fm.ascent + fm.descent) / 2;
        for(int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++) {
                canvas.drawText(this.game.getField(i, j), i * width + x, j * height + y, foreground);
            }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() != MotionEvent.ACTION_DOWN)
            return super.onTouchEvent(event);

        int x = (int) (event.getX() / width);
        int y = (int) ((event.getY()) / height);
        chosenX = Math.min(Math.max(x, 0), 8);
        chosenY = Math.min(Math.max(y, 0), 8);
        game.showKeyboard(chosenX, chosenY);

        return true;
    }

    public void setChosenField(int value) {
        if(game.setField(chosenX, chosenY, value)) {
            game.setEdit(true);
            invalidate();
        }
    }
}
