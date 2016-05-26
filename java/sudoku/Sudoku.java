package sudoku;
import photo.*;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Sudoku extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sudoku, menu);
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

    public void showInfo(View view) {
        Intent intent = new Intent(this, Info.class);
        startActivity(intent);
    }

    /* Creates dialog window with input choices. */
    public void newGame(View view) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_inputs)
                .setItems(R.array.inputs,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                launch(which);
                            }
                        })
                .show();
    }

    /* Starts the activity with chosen input type */
    private void launch(int which) {
        Intent intent;
        if (which == 0) {
            intent = new Intent(this, Game.class);
        }
        else {
            intent = new Intent(this, Photo.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        }
        startActivity(intent);
    }
}
