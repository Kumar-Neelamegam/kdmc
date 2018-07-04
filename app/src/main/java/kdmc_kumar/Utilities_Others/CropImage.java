/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kdmc_kumar.Utilities_Others;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

import displ.mobydocmarathi.com.R;

public class CropImage extends MonitoredActivity {

    public static final String IMAGE_PATH = "image-path";
    public static final String SCALE = "scale";
    private static final String ORIENTATION_IN_DEGREES = "orientation_in_degrees";
    public static final String ASPECT_X = "aspectX";
    public static final String ASPECT_Y = "aspectY";
    public static final String OUTPUT_X = "outputX";
    public static final String OUTPUT_Y = "outputY";
    private static final String SCALE_UP_IF_NEEDED = "scaleUpIfNeeded";
    private static final String CIRCLE_CROP = "circleCrop";
    private static final String RETURN_DATA = "return-data";
    private static final String RETURN_DATA_AS_BITMAP = "data";
    private static final String ACTION_INLINE_DATA = "inline-data";
    private static final int NO_STORAGE_ERROR = -1;
    private static final int CANNOT_STAT_ERROR = -2;
    private static final String TAG = "CropImage";
    private static Bitmap mBitmap = null;
    private static final int IMAGE_MAX_SIZE = 1024;
    private final Handler mHandler = new Handler();
    private final BitmapManager.ThreadSet mDecodingThreads = new BitmapManager.ThreadSet();
    boolean mWaitingToPick = false; // Whether we are wait the user to pick a face.
    boolean mSaving = false; // Whether the "save" button is already clicked.
    HighlightView mCrop = null;
    // These are various options can be specified in the intent.
    private final Bitmap.CompressFormat mOutputFormat = Bitmap.CompressFormat.JPEG;
    private Uri mSaveUri = null;
    private final boolean mDoFaceDetection = true;
    private boolean mCircleCrop = false;
    private int mAspectX = 0;
    private int mAspectY = 0;
    private int mOutputX = 0;
    private int mOutputY = 0;
    private boolean mScale = false;
    private CropImageView mImageView = null;
    private final Runnable mRunFaceDetection = new Runnable() {
        float mScale = 1.0F;
        Matrix mImageMatrix = null;
        final FaceDetector.Face[] mFaces = new FaceDetector.Face[3];
        int mNumFaces = 0;

        // For each face, we create a HightlightView for it.
        private void handleFace(FaceDetector.Face f) {

            PointF midPoint = new PointF();

            int r = ((int) (f.eyesDistance() * mScale)) * 2;
            f.getMidPoint(midPoint);
            midPoint.x *= mScale;
            midPoint.y *= mScale;

            int midX = (int) midPoint.x;
            int midY = (int) midPoint.y;

            HighlightView hv = new HighlightView(mImageView);

            int width = mBitmap.getWidth();
            int height = mBitmap.getHeight();

            Rect imageRect = new Rect(0, 0, width, height);

            RectF faceRect = new RectF((float) midX, (float) midY, (float) midX, (float) midY);
            faceRect.inset((float) -r, (float) -r);
            if (faceRect.left < (float) 0) {
                faceRect.inset(-faceRect.left, -faceRect.left);
            }

            if (faceRect.top < (float) 0) {
                faceRect.inset(-faceRect.top, -faceRect.top);
            }

            if (faceRect.right > (float) imageRect.right) {
                faceRect.inset(faceRect.right - (float) imageRect.right, faceRect.right
                        - (float) imageRect.right);
            }

            if (faceRect.bottom > (float) imageRect.bottom) {
                faceRect.inset(faceRect.bottom - (float) imageRect.bottom,
                        faceRect.bottom - (float) imageRect.bottom);
            }

            hv.setup(mImageMatrix, imageRect, faceRect, mCircleCrop,
                    mAspectX != 0 && mAspectY != 0);

            mImageView.add(hv);
        }

        // Create a default HightlightView if we found no face in the picture.
        private void makeDefault() {

            HighlightView hv = new HighlightView(mImageView);

            int width = mBitmap.getWidth();
            int height = mBitmap.getHeight();

            Rect imageRect = new Rect(0, 0, width, height);

            // make the default size about 4/5 of the width or height
            int cropWidth = Math.min(width, height) * 4 / 5;
            int cropHeight = cropWidth;

            if (mAspectX != 0 && mAspectY != 0) {

                if (mAspectX > mAspectY) {

                    cropHeight = cropWidth * mAspectY / mAspectX;
                } else {

                    cropWidth = cropHeight * mAspectX / mAspectY;
                }
            }

            int x = (width - cropWidth) / 2;
            int y = (height - cropHeight) / 2;

            RectF cropRect = new RectF((float) x, (float) y, (float) (x + cropWidth), (float) (y + cropHeight));
            hv.setup(mImageMatrix, imageRect, cropRect, mCircleCrop,
                    mAspectX != 0 && mAspectY != 0);

            mImageView.mHighlightViews.clear(); // Thong added for rotate

            mImageView.add(hv);
        }

        // Scale the image down for faster face detection.
        private Bitmap prepareBitmap() {


            if (mBitmap == null) {
                mBitmap.recycle();
                return null;
            }

            // 256 pixels wide is enough.
            if (mBitmap.getWidth() > 256) {

                mScale = 256.0F / (float) mBitmap.getWidth();
            }
            Matrix matrix = new Matrix();
            matrix.setScale(mScale, mScale);


            return Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
                    mBitmap.getHeight(), matrix, true);
        }


        public void run() {

            mImageMatrix = mImageView.getImageMatrix();
            Bitmap faceBitmap = prepareBitmap();

            mScale = 1.0F / mScale;
            if (faceBitmap != null && mDoFaceDetection) {
                FaceDetector detector = new FaceDetector(faceBitmap.getWidth(),
                        faceBitmap.getHeight(), mFaces.length);
                mNumFaces = detector.findFaces(faceBitmap, mFaces);
            }

            if (faceBitmap != null && faceBitmap != mBitmap) {
                faceBitmap.recycle();
            }

            mHandler.post(() -> {

                mWaitingToPick = mNumFaces > 1;
                if (mNumFaces > 0) {
                    for (int i = 0; i < mNumFaces; i++) {
                        handleFace(mFaces[i]);
                    }
                } else {
                    makeDefault();
                }
                mImageView.invalidate();
                if (mImageView.mHighlightViews.size() == 1) {
                    mCrop = mImageView.mHighlightViews.get(0);
                    mCrop.setFocus(true);
                }

                if (mNumFaces > 1) {
                    Toast.makeText(CropImage.this, "Multi face crop help",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
    private ContentResolver mContentResolver = null;
    private String mImagePath = null;
    // These options specifiy the output image size and whether we should
    // scale the output to fit it (or just crop it).
    private boolean mScaleUp = true;

    public CropImage() {
    }

    private static void showStorageToast(Activity activity) {

        showStorageToast(activity, calculatePicturesRemaining(activity));
    }

    @SuppressLint("ShowToast")
    private static void showStorageToast(Activity activity, int remaining) {

        String noStorageText = null;

        if (remaining == NO_STORAGE_ERROR) {

            String state = Environment.getExternalStorageState();
            noStorageText = state.equals(Environment.MEDIA_CHECKING) ? activity.getString(R.string.preparing_card) : activity.getString(R.string.no_storage_card);
        } else if (remaining < 1) {

            noStorageText = activity.getString(R.string.not_enough_space);
        }

        if (noStorageText != null) {

            Toast.makeText(activity, noStorageText, Toast.LENGTH_LONG).show();
        }
    }

    private static int calculatePicturesRemaining(Activity activity) {

        try {
			/*
			 * if (!ImageManager.hasStorage()) { return NO_STORAGE_ERROR; } else
			 * {
			 */
            String storageDirectory = "";
            String state = Environment.getExternalStorageState();
            storageDirectory = Environment.MEDIA_MOUNTED.equals(state) ? Environment.getExternalStorageDirectory()
                    .toString() : activity.getFilesDir().toString();
            StatFs stat = new StatFs(storageDirectory);
            @SuppressWarnings("deprecation")
            float remaining = ((float) stat.getAvailableBlocks() * stat
                    .getBlockSize()) / 400000.0F;
            return (int) remaining;
            // }
        } catch (Exception ignored) {
            // if we can't stat the filesystem then we don't know how many
            // pictures are remaining. it might be zero but just leave it
            // blank since we really don't know.
            return CANNOT_STAT_ERROR;
        }
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mContentResolver = getContentResolver();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cropimage);

        mImageView = findViewById(R.id.image);

        showStorageToast(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {

            if (extras.getString(CIRCLE_CROP) != null) {

                // if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                // mImageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                // }

                mCircleCrop = true;
                mAspectX = 1;
                mAspectY = 1;
            }

            mImagePath = extras.getString(IMAGE_PATH);

            mSaveUri = getImageUri(mImagePath);
            mBitmap = getBitmap(mImagePath);

            if (extras.containsKey(ASPECT_X)
                    && extras.get(ASPECT_X) instanceof Integer) {

                mAspectX = extras.getInt(ASPECT_X);
            } else {

                throw new IllegalArgumentException("aspect_x must be integer");
            }
            if (extras.containsKey(ASPECT_Y)
                    && extras.get(ASPECT_Y) instanceof Integer) {

                mAspectY = extras.getInt(ASPECT_Y);
            } else {

                throw new IllegalArgumentException("aspect_y must be integer");
            }
            mOutputX = extras.getInt(OUTPUT_X);
            mOutputY = extras.getInt(OUTPUT_Y);
            mScale = extras.getBoolean(SCALE, true);
            mScaleUp = extras.getBoolean(SCALE_UP_IF_NEEDED, true);
        }

        if (mBitmap == null) {

            Log.d(TAG, "finish!!!");
            finish();
            return;
        }

        // Make UI fullscreen.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.discard).setOnClickListener(
                v -> {

                    setResult(RESULT_CANCELED);
                    finish();
                });

        findViewById(R.id.save).setOnClickListener(v -> {

            try {
                onSaveClicked();
            } catch (Exception ignored) {
                finish();
            }
        });
        findViewById(R.id.rotateLeft).setOnClickListener(
                v -> {

                    mBitmap = Util.rotateImage(mBitmap, -90.0F);
                    RotateBitmap rotateBitmap = new RotateBitmap(mBitmap);
                    mImageView.setImageRotateBitmapResetBase(rotateBitmap,
                            true);
                    mRunFaceDetection.run();
                });

        findViewById(R.id.rotateRight).setOnClickListener(
                v -> {

                    mBitmap = Util.rotateImage(mBitmap, 90.0F);
                    RotateBitmap rotateBitmap = new RotateBitmap(mBitmap);
                    mImageView.setImageRotateBitmapResetBase(rotateBitmap,
                            true);
                    mRunFaceDetection.run();
                });
        startFaceDetection();
    }

    private static Uri getImageUri(String path) {

        return Uri.fromFile(new File(path));
    }

    private Bitmap getBitmap(String path) {

        Uri uri = getImageUri(path);
        InputStream in = null;
        try {
            in = mContentResolver.openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(in, null, o);
            in.close();

            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) StrictMath.pow(
                        2.0,
                        (double) (int) Math.round(StrictMath.log((double) IMAGE_MAX_SIZE
                                / (double) Math.max(o.outHeight, o.outWidth))
                                / StrictMath.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = mContentResolver.openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
            in.close();

            return b;
        } catch (FileNotFoundException ignored) {
            //Log.e(TAG, "file " + path + " not found");
        } catch (IOException ignored) {
            //Log.e(TAG, "file " + path + " not found");
        }
        return null;
    }

    private void startFaceDetection() {

        if (isFinishing()) {
            return;
        }

        mImageView.setImageBitmapResetBase(mBitmap, true);

        Util.startBackgroundJob(this, null, "Please wait\u2026",
                () -> {

                    final CountDownLatch latch = new CountDownLatch(1);
                    final Bitmap b = mBitmap;
                    mHandler.post(() -> {

                        if (b != mBitmap && b != null) {
                            mImageView.setImageBitmapResetBase(b, true);
                            mBitmap.recycle();
                            mBitmap = b;
                        }
                        if (mImageView.getScale() == 1.0F) {
                            mImageView.center();
                        }
                        latch.countDown();
                    });
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    mRunFaceDetection.run();
                }, mHandler);
    }

    private void onSaveClicked() throws Exception {
        // TODO this code needs to change to use the decode/crop/encode single
        // step api so that we don't require that the whole (possibly large)
        // bitmap doesn't have to be read into memory
        if (mSaving)
            return;

        if (mCrop == null) {

            return;
        }

        mSaving = true;

        Rect r = mCrop.getCropRect();

        int width = r.width();
        int height = r.height();

        // If we are circle cropping, we want alpha channel, which is the
        // third param here.
        Bitmap croppedImage;

        croppedImage = Bitmap.createBitmap(width, height,
                mCircleCrop ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        if (croppedImage == null) {

            return;
        }

        {
            Canvas canvas = new Canvas(croppedImage);
            Rect dstRect = new Rect(0, 0, width, height);
            canvas.drawBitmap(mBitmap, r, dstRect, null);
        }

        if (mCircleCrop) {

            // OK, so what's all this about?
            // Bitmaps are inherently rectangular but we want to return
            // something that's basically a circle. So we fill in the
            // area around the circle with alpha. Note the all important
            // PortDuff.Mode.CLEAR.
            Canvas c = new Canvas(croppedImage);
            Path p = new Path();
            p.addCircle((float) width / 2.0F, (float) height / 2.0F, (float) width / 2.0F, Path.Direction.CW);
            c.clipPath(p, Region.Op.DIFFERENCE);
            c.drawColor(0x00000000, PorterDuff.Mode.CLEAR);
        }

		/* If the output is required to a specific size then scale or fill */
        if (mOutputX != 0 && mOutputY != 0) {

            if (mScale) {

				/* Scale the image to the required dimensions */
                Bitmap old = croppedImage;
                croppedImage = Util.transform(new Matrix(), croppedImage,
                        mOutputX, mOutputY, mScaleUp);
                if (old != croppedImage) {

                    old.recycle();
                }
            } else {

				/*
                 * Don't scale the image crop it to the size requested. Create
				 * an new image with the cropped image in the center and the
				 * extra space filled.
				 */

                // Don't scale the image but instead fill it so it's the
                // required dimension
                Bitmap b = Bitmap.createBitmap(mOutputX, mOutputY,
                        Bitmap.Config.RGB_565);
                Canvas canvas = new Canvas(b);

                Rect srcRect = mCrop.getCropRect();
                Rect dstRect = new Rect(0, 0, mOutputX, mOutputY);

                int dx = (srcRect.width() - dstRect.width()) / 2;
                int dy = (srcRect.height() - dstRect.height()) / 2;

				/* If the srcRect is too big, use the center part of it. */
                srcRect.inset(Math.max(0, dx), Math.max(0, dy));

				/* If the dstRect is too big, use the center part of it. */
                dstRect.inset(Math.max(0, -dx), Math.max(0, -dy));

				/* Draw the cropped bitmap in the center */
                canvas.drawBitmap(mBitmap, srcRect, dstRect, null);

				/* Set the cropped bitmap as the new bitmap */
                croppedImage.recycle();
                croppedImage = b;
            }
        }

        // Return the cropped image directly or save it to the specified URI.
        Bundle myExtras = getIntent().getExtras();
        if (myExtras != null
                && (myExtras.getParcelable("data") != null || myExtras
                .getBoolean(RETURN_DATA))) {

            Bundle extras = new Bundle();
            extras.putParcelable(RETURN_DATA_AS_BITMAP, croppedImage);
            setResult(RESULT_OK, (new Intent()).setAction(ACTION_INLINE_DATA)
                    .putExtras(extras));
            finish();
        } else {
            final Bitmap b = croppedImage;
            Util.startBackgroundJob(this, null,
                    getString(R.string.saving_image), () -> saveOutput(b), mHandler);
        }
    }

    private void saveOutput(Bitmap croppedImage) {

        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = mContentResolver.openOutputStream(mSaveUri);
                if (outputStream != null) {
                    croppedImage.compress(mOutputFormat, 90, outputStream);
                }
            } catch (IOException ignored) {

                //Log.e(TAG, "Cannot open file: " + mSaveUri, ex);
                setResult(RESULT_CANCELED);
                finish();
                return;
            } finally {

                Util.closeSilently(outputStream);
            }

            Bundle extras = new Bundle();
            Intent intent = new Intent(mSaveUri.toString());
            intent.putExtras(extras);
            intent.putExtra(IMAGE_PATH, mImagePath);
            intent.putExtra(ORIENTATION_IN_DEGREES,
                    Util.getOrientationInDegree(this));
            setResult(RESULT_OK, intent);
        } else {

            //Log.e(TAG, "not defined image url");
        }
        croppedImage.recycle();
        finish();
    }

    @Override
    protected final void onPause() {

        super.onPause();
        BitmapManager.instance().cancelThreadDecoding(mDecodingThreads);
    }

    @Override
    protected final void onDestroy() {

        super.onDestroy();

        if (mBitmap != null) {

            mBitmap.recycle();
        }
    }

}
