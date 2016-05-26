package photo;

import android.content.Context;
import android.hardware.Camera;
import android.view.*;
import android.util.Log;
import android.widget.FrameLayout;

/*
    This class manages the camera preview
    and its behavior regarding what happens with the layout.
*/
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private Camera mCamera;
    private SurfaceHolder mHolder;
    FrameLayout mLayout;

    public CameraPreview(Context context, Camera camera, FrameLayout layout) {
        super(context);
        mCamera = camera;
        mLayout = layout;
        /* Installs a SurfaceHolder.Callback, so we get notified when the
           underlying surface is created and destroyed. */
        mHolder = getHolder();
    }

    // Starts preview when the surface is created.
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        }
        catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
    }

    // Releases the camera after the surface is destroyed.
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.release();
    }

    // (To be developed)
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        }
        catch (Exception e){
            Log.e("Exception", e.getMessage());
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        }
        catch (Exception e){
            Log.e("Exception", e.getMessage());
        }
    }

    private void setLayoutSize() {
        Camera.Size mCameraSize = mCamera.getParameters().getPreviewSize();
        ViewGroup.LayoutParams layoutParams = mLayout.getLayoutParams();
        layoutParams.height = mCameraSize.width;
        layoutParams.width = mCameraSize.height;
    }

    public void setPreviewParameters() {
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mLayout.addView(this);
        setLayoutSize();
    }
}
