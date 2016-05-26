package photo;

import android.app.Activity;
import android.view.View;
import android.widget.*;

import sudoku.R;

/*
    This class keeps some of the layout components
    and manages their visibility during different events.
*/
public class PhotoView {
    private FrameLayout cameraPrv;
    private ImageButton captureBttn;
    private Button cancelBttn;
    private Button acceptBttn;
    private TextView wait;
    private boolean captureClicked;

    public PhotoView(Activity activity) {
        cancelBttn = (Button) activity.findViewById(R.id.cancel);
        acceptBttn = (Button) activity.findViewById(R.id.accept);
        captureBttn = (ImageButton) activity.findViewById(R.id.capture);
        cameraPrv = (FrameLayout) activity.findViewById(R.id.camera);
        wait = (TextView) activity.findViewById(R.id.wait);
        captureClicked = false;
    }

    public void duringCapture() {
        captureBttn.setVisibility(View.GONE);
        wait.setVisibility(View.VISIBLE);
        captureClicked = true;
    }

    public void afterCapture() {
        wait.setVisibility(View.GONE);
        cancelBttn.setVisibility(View.VISIBLE);
        acceptBttn.setVisibility(View.VISIBLE);
    }

    public void cancel() {
        captureBttn.setVisibility(View.VISIBLE);
        wait.setVisibility(View.GONE);
        cancelBttn.setVisibility(View.GONE);
        acceptBttn.setVisibility(View.GONE);
        captureClicked = false;
    }

    // Getters.
    public ImageButton captureButton() {
        return captureBttn;
    }

    public Button cancelButton() {
        return cancelBttn;
    }

    public Button acceptButton() {
        return acceptBttn;
    }

    public FrameLayout cameraPreview() {
        return cameraPrv;
    }

    public boolean captureWasClicked() {
        return captureClicked;
    }
}
