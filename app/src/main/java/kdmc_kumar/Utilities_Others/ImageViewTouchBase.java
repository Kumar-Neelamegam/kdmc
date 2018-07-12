/*
 * Copyright (C) 2009 The Android Open Source Project
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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;

abstract class ImageViewTouchBase extends android.support.v7.widget.AppCompatImageView {

    private static final float SCALE_RATE = 1.25F;
    @SuppressWarnings("unused")
    private static final String TAG = "ImageViewTouchBase";
    // The current bitmap being displayed.
    final RotateBitmap mBitmapDisplayed = new RotateBitmap(null);
    // This is the final matrix which is computed as the concatentation
    // of the base matrix and the supplementary matrix.
    private final Matrix mDisplayMatrix = new Matrix();

    // Temporary buffer used for getting the values out of a matrix.
    private final float[] mMatrixValues = new float[9];
    // This is the base transformation which is used to show the image
    // initially. The current computation for this shows the image in
    // it's entirety, letterboxing as needed. One could choose to
    // show the image as cropped instead.
    //
    // This matrix is recomputed when we go from the thumbnail image to
    // the full size image.
    private final Matrix mBaseMatrix = new Matrix();
    // This is the supplementary transformation which reflects what
    // the user has done in terms of zooming and panning.
    //
    // This matrix remains the same when we go from the thumbnail image
    // to the full size image.
    private final Matrix mSuppMatrix = new Matrix();
    private final Handler mHandler = new Handler();
    private int mThisWidth = -1;
    private int mThisHeight = -1;
    private float mMaxZoom = 0.0F;
    int mLeft = 0;
    int mRight = 0;
    int mTop = 0;
    int mBottom = 0;
    private Recycler mRecycler = null;
    @Nullable
    private Runnable mOnLayoutRunnable = null;

    protected ImageViewTouchBase(Context context) {

        super(context);
        init();
    }

    protected ImageViewTouchBase(Context context, AttributeSet attrs) {

        super(context, attrs);
        init();
    }

    public final void setRecycler(Recycler r) {

        mRecycler = r;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {

        super.onLayout(changed, left, top, right, bottom);
        mLeft = left;
        mRight = right;
        mTop = top;
        mBottom = bottom;
        mThisWidth = right - left;
        mThisHeight = bottom - top;
        Runnable r = mOnLayoutRunnable;
        if (r != null) {
            mOnLayoutRunnable = null;
            r.run();
        }
        if (mBitmapDisplayed.getBitmap() != null) {
            getProperBaseMatrix(mBitmapDisplayed, mBaseMatrix);
            setImageMatrix(getImageViewMatrix());
        }
    }

    @Override
    public final boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && getScale() > 1.0f) {
            // If we're zoomed in, pressing Back jumps out to show the entire
            // image, otherwise Back returns the user to the gallery.
            zoomTo();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public final void setImageBitmap(Bitmap bm) {

        setImageBitmap(bm, 0);
    }

    private void setImageBitmap(Bitmap bitmap, int rotation) {

        super.setImageBitmap(bitmap);
        Drawable d = getDrawable();
        if (d != null) {
            d.setDither(true);
        }

        Bitmap old = mBitmapDisplayed.getBitmap();
        mBitmapDisplayed.setBitmap(bitmap);
        mBitmapDisplayed.setRotation(rotation);

        if (old != null && old != bitmap && mRecycler != null) {
            mRecycler.recycle(old);
        }
    }

    public final void clear() {

        setImageBitmapResetBase(null, true);
    }

    // This function changes bitmap, reset base matrix according to the size
    // of the bitmap, and optionally reset the supplementary matrix.
    public final void setImageBitmapResetBase(final Bitmap bitmap,
                                              final boolean resetSupp) {

        setImageRotateBitmapResetBase(new RotateBitmap(bitmap), resetSupp);
    }

    public final void setImageRotateBitmapResetBase(final RotateBitmap bitmap,
                                                    final boolean resetSupp) {

        final int viewWidth = getWidth();

        if (viewWidth <= 0) {
            mOnLayoutRunnable = () -> setImageRotateBitmapResetBase(bitmap, resetSupp);
            return;
        }

        if (bitmap.getBitmap() != null) {
            getProperBaseMatrix(bitmap, mBaseMatrix);
            setImageBitmap(bitmap.getBitmap(), bitmap.getRotation());
        } else {
            mBaseMatrix.reset();
            setImageBitmap(null);
        }

        if (resetSupp) {
            mSuppMatrix.reset();
        }
        setImageMatrix(getImageViewMatrix());
        mMaxZoom = maxZoom();
    }

    // Center as much as possible in one or both axis. Centering is
    // defined as follows: if the image is scaled down below the
    // view's dimensions then center it (literally). If the image
    // is scaled larger than the view and is translated out of view
    // then translate it back into view (i.e. eliminate black bars).
    final void center() {

        if (mBitmapDisplayed.getBitmap() == null) {
            return;
        }

        Matrix m = getImageViewMatrix();

        RectF rect = new RectF((float) 0, (float) 0, (float) mBitmapDisplayed.getBitmap().getWidth(),
                (float) mBitmapDisplayed.getBitmap().getHeight());

        m.mapRect(rect);

        float height = rect.height();
        float width = rect.width();

        float deltaX = (float) 0, deltaY = (float) 0;

        if (true) {
            int viewHeight = getHeight();
            if (height < (float) viewHeight) {
                deltaY = ((float) viewHeight - height) / 2.0F - rect.top;
            } else if (rect.top > (float) 0) {
                deltaY = -rect.top;
            } else if (rect.bottom < (float) viewHeight) {
                deltaY = (float) getHeight() - rect.bottom;
            }
        }

        if (true) {
            int viewWidth = getWidth();
            if (width < (float) viewWidth) {
                deltaX = ((float) viewWidth - width) / 2.0F - rect.left;
            } else if (rect.left > (float) 0) {
                deltaX = -rect.left;
            } else if (rect.right < (float) viewWidth) {
                deltaX = (float) viewWidth - rect.right;
            }
        }

        postTranslate(deltaX, deltaY);
        setImageMatrix(getImageViewMatrix());
    }

    private void init() {

        setScaleType(ScaleType.MATRIX);
    }

    private final float getValue(Matrix matrix) {

        matrix.getValues(mMatrixValues);
        return mMatrixValues[Matrix.MSCALE_X];
    }

    // Get the scale factor out of the matrix.
    private final float getScale(Matrix matrix) {

        return getValue(matrix);
    }

    final float getScale() {

        return getScale(mSuppMatrix);
    }

    // Setup the base matrix so that the image is centered and scaled properly.
    private void getProperBaseMatrix(RotateBitmap bitmap, Matrix matrix) {

        float viewWidth = (float) getWidth();
        float viewHeight = (float) getHeight();

        float w = (float) bitmap.getWidth();
        float h = (float) bitmap.getHeight();
        @SuppressWarnings("unused")
        int rotation = bitmap.getRotation();
        matrix.reset();

        // We limit up-scaling to 2x otherwise the result may look bad if it's
        // a small icon.
        float widthScale = Math.min(viewWidth / w, 2.0f);
        float heightScale = Math.min(viewHeight / h, 2.0f);
        float scale = Math.min(widthScale, heightScale);

        matrix.postConcat(bitmap.getRotateMatrix());
        matrix.postScale(scale, scale);

        matrix.postTranslate((viewWidth - w * scale) / 2.0F, (viewHeight - h
                * scale) / 2.0F);
    }

    // Combine the base matrix and the supp matrix to make the final matrix.
    private final Matrix getImageViewMatrix() {
        // The final matrix is computed as the concatentation of the base matrix
        // and the supplementary matrix.
        mDisplayMatrix.set(mBaseMatrix);
        mDisplayMatrix.postConcat(mSuppMatrix);
        return mDisplayMatrix;
    }

    // Sets the maximum zoom, which is a scale relative to the base matrix. It
    // is calculated to show the image at 400% zoom regardless of screen or
    // image orientation. If in the future we decode the full 3 megapixel image,
    // rather than the current 1024x768, this should be changed down to 200%.
    private final float maxZoom() {

        if (mBitmapDisplayed.getBitmap() == null) {
            return 1.0F;
        }

        float fw = (float) mBitmapDisplayed.getWidth() / mThisWidth;
        float fh = (float) mBitmapDisplayed.getHeight() / mThisHeight;
        return Math.max(fw, fh) * 4;
    }

    public void zoomTo1(float scale, float centerX, float centerY) {

        float scale1 = scale;
        if (scale1 > mMaxZoom) {
            scale1 = mMaxZoom;
        }

        float oldScale = getScale();
        float deltaScale = scale1 / oldScale;

        mSuppMatrix.postScale(deltaScale, deltaScale, centerX, centerY);
        setImageMatrix(getImageViewMatrix());
        center();
    }

    void zoomTo(final float scale, final float centerX,
                final float centerY) {

        final float incrementPerMs = (scale - getScale()) / 300F;
        final float oldScale = getScale();
        final long startTime = System.currentTimeMillis();

        mHandler.post(new Runnable() {
            public void run() {

                long now = System.currentTimeMillis();
                float currentMs = Math.min(300F, (float) (now - startTime));
                float target = oldScale + (incrementPerMs * currentMs);
                zoomTo1(target, centerX, centerY);

                if (currentMs < 300F) {
                    mHandler.post(this);
                }
            }
        });
    }

    private final void zoomTo() {

        float cx = (float) getWidth() / 2.0F;
        float cy = (float) getHeight() / 2.0F;

        zoomTo(1.0f, cx, cy);
    }

    private final void zoomIn() {

        if (getScale() >= mMaxZoom) {
            return; // Don't let the user zoom into the molecular level.
        }
        if (mBitmapDisplayed.getBitmap() == null) {
            return;
        }

        float cx = (float) getWidth() / 2.0F;
        float cy = (float) getHeight() / 2.0F;

        mSuppMatrix.postScale(ImageViewTouchBase.SCALE_RATE, ImageViewTouchBase.SCALE_RATE, cx, cy);
        setImageMatrix(getImageViewMatrix());
    }

    private final void zoomOut() {

        if (mBitmapDisplayed.getBitmap() == null) {
            return;
        }

        float cx = (float) getWidth() / 2.0F;
        float cy = (float) getHeight() / 2.0F;

        // Zoom out to at most 1x.
        Matrix tmp = new Matrix(mSuppMatrix);
        tmp.postScale(1.0F / ImageViewTouchBase.SCALE_RATE, 1.0F / ImageViewTouchBase.SCALE_RATE, cx, cy);

        if (getScale(tmp) < 1.0F) {
            mSuppMatrix.setScale(1.0F, 1.0F, cx, cy);
        } else {
            mSuppMatrix.postScale(1.0F / ImageViewTouchBase.SCALE_RATE, 1.0F / ImageViewTouchBase.SCALE_RATE, cx, cy);
        }
        setImageMatrix(getImageViewMatrix());
        center();
    }

    void postTranslate(float dx, float dy) {

        mSuppMatrix.postTranslate(dx, dy);
    }

    final void panBy(float dx, float dy) {

        postTranslate(dx, dy);
        setImageMatrix(getImageViewMatrix());
    }

    // ImageViewTouchBase will pass a Bitmap to the Recycler if it has finished
    // its use of that Bitmap.
    interface Recycler {

        void recycle(Bitmap b);
    }
}
