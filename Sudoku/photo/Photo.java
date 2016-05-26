package photo;
import sudoku.*;
import digitrecognizer.*;

import android.app.Activity;
import android.content.*;
import android.graphics.*;
import android.os.Bundle;
import android.view.*;
import android.hardware.Camera;
import android.hardware.Camera.*;
import android.util.Log;

import java.io.InputStream;

/*
    This is the main Photo activity.
    It manages the camera behavior, enables taking a picture,
    initiates processing the picture
    and sends the results to the Game activity.
*/
public class Photo extends Activity {

    private Camera mCamera;
    private CameraPreview mPreview;
    private PhotoView mPhotoView;

    private byte[] JPEGData; // A temporary picture captured with the camera.
    private Activity activity = this;

    // Needed after capturing a picture, by a Camera.takePicture() function
    private PictureCallback mPicture = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            mCamera.stopPreview();
            JPEGData = data;
            mPhotoView.afterCapture();
        }
    };

    // Resets camera's autofocus to a default position.
    private void resetAutofocus() {
        mCamera.cancelAutoFocus();
        mCamera.autoFocus(null);
    }

    private void setCameraParameters() {
        mCamera.setDisplayOrientation(90);
        Camera.Parameters params = mCamera.getParameters();
        params.setColorEffect(Parameters.EFFECT_MONO);
        params.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        params.setFlashMode(Parameters.FLASH_MODE_AUTO);
        params.setPictureFormat(ImageFormat.JPEG);
        mCamera.setParameters(params);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photo);

        // Getting a Camera instance.
        try {
            mCamera = Camera.open();
        }
        catch (Exception e) {
            mCamera = null;
            Log.e("Exception", e.getMessage());
        }

        mPhotoView = new PhotoView(this);
        mPreview = new CameraPreview(this, mCamera, mPhotoView.cameraPreview());

        // If the operation above is succesful, mCamera parameters can be set.
        if (mCamera != null) {
            resetAutofocus();
            setCameraParameters();
            mPreview.setPreviewParameters();
        }

        // Event taking place after a "Capture" button has been clicked.
        mPhotoView.captureButton().setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mPhotoView.captureWasClicked()) {
                            mPhotoView.duringCapture();
                            mCamera.takePicture(null, null, null, mPicture);
                        }
                    }
                }
        );

        // Event taking place after a "Cancel" button has ben clicked.
        mPhotoView.cancelButton().setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPhotoView.cancel();
                        mCamera.startPreview();
                    }
                }
        );

        // Event taking place after an "Accept" button has been clicked.
        mPhotoView.acceptButton().setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            // processing the photo
                            InputStream is = getResources().getAssets().open("network.txt");
                            PhotoProcess pc = new PhotoProcess(JPEGData, new Core(is));
                            int[] resultBoard = pc.getNumbers();

                            // starting the game with numbers extracted from the photo
                            Intent intent = new Intent(activity, Game.class);
                            intent.putExtra("Board", resultBoard);
                            startActivity(intent);
                        }
                        catch (Exception e) {
                            Log.e("Exception", e.getMessage());
                        }
                    }
                }
        );
    }
}
