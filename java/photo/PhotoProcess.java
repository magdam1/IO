package photo;

import android.graphics.*;
import android.util.Log;

import java.util.zip.DeflaterOutputStream;

import digitrecognizer.Core;

/*
    This class is responsible of processing the given photo
    and extracting the numbers (current state of the game) from it.
*/
public class PhotoProcess {

    private Bitmap bmp; // The photo intended to be processed.
    private Core core; // An instance of a class used to recognize digits.
    private int scaleSize;
    private double[][] colorSaturation;
    private int[] board = new int[81]; //All the extracted numbers are stored here.

    public PhotoProcess(byte[] JPEGData, Core core) {
        bmp = BitmapFactory.decodeByteArray(JPEGData, 0, JPEGData.length);
        this.core = core;
        this.scaleSize = this.core.getImageSize();
        colorSaturation = new double [scaleSize][scaleSize];
    }

    // Rotates the source photo to put it in the right position.
    private void rotatePhoto () {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        bmp = rotatedBitmap;
    }

    // Changes contrast and brightness of the photo.
    private void changeContrastBrightness(float contrast, float brightness)
    {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        contrast, 0, 0, 0, brightness,
                        0, contrast, 0, 0, brightness,
                        0, 0, contrast, 0, brightness,
                        0, 0, 0, 1, 0
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        bmp = ret;
    }

    // Extracts the color value from a single pixel.
    private double extractColorFromPixel(Bitmap piece, int p1, int p2) {
        int colorTmp = piece.getPixel(p1, p2);
        colorTmp = Color.green(colorTmp);
        double colorTmpD = colorTmp;
        colorTmpD /= 255;
        return Math.abs(colorTmpD - 1);
    }

    // Extracts a number from a single piece (1/81'th of the photo).
    private int extractNumberFromPiece(Bitmap piece) {
        try {
            for (int p1 = 0; p1 < scaleSize; p1++) {
                for (int p2 = 0; p2 < scaleSize; p2++) {
                    colorSaturation[p1][p2] = extractColorFromPixel(piece, p1, p2);
                }
            }

            int recognizedDigit = core.recognizeDigit(colorSaturation, 0.3);
            // If the result equals -1, core failed to recognize any digit.
            return (recognizedDigit != -1 ? recognizedDigit : 0);
        }
        catch (Exception e) {
            Log.e("Exception", e.getMessage());
            return 0;
        }
    }

    // Divides the photo in 81 pieces and extracts all the numbers.
    private int[] divideAndExtract() {
        // setting necessary sizes
        int height = bmp.getHeight(), width = bmp.getWidth(),
            longerSide = Math.max(height, width),
            shorterSide = Math.min(height, width),
            littleSide = shorterSide/9,
            y = (longerSide - shorterSide)/2,
            margin = 5; // Margin used to cut excessive "frame" around the digit

        try {
            for (int i=0; i<9; i++) {
                for (int j=0; j<9; j++) {

                    // cutting a 1/81'th of the picture, with margins
                    Bitmap piece = Bitmap.createBitmap(bmp, (littleSide * j) + margin, y + margin + (littleSide * i),
                                                        littleSide - margin, littleSide - margin);

                    // scaling the piece to an appropriate size for the core
                    Bitmap scaledPiece = Bitmap.createScaledBitmap(piece, scaleSize, scaleSize, false);

                    board[9 * i + j] = extractNumberFromPiece(scaledPiece);
                }
            }
            return board;
        }
        catch (Exception e) {
            Log.e("Exception", e.getMessage());
            return null;
        }
    }

    // Returns numbers extracted from the photo.
    public int[] getNumbers() {
        rotatePhoto();
        changeContrastBrightness(5, -20);
        divideAndExtract();
        return board;
    }
}
