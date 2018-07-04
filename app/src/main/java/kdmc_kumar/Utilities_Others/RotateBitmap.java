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

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.annotation.Nullable;

public class RotateBitmap {

    public static final String TAG = "RotateBitmap";
    @Nullable
    private Bitmap mBitmap;
    private int mRotation;

    public RotateBitmap(Bitmap bitmap) {

        mBitmap = bitmap;
        mRotation = 0;
    }

    public RotateBitmap(Bitmap bitmap, int rotation) {

        mBitmap = bitmap;
        mRotation = rotation % 360;
    }

    public final int getRotation() {

        return mRotation;
    }

    public final void setRotation(int rotation) {

        mRotation = rotation;
    }

    public final Bitmap getBitmap() {

        return mBitmap;
    }

    public final void setBitmap(Bitmap bitmap) {

        mBitmap = bitmap;
    }

    public final Matrix getRotateMatrix() {
        // By default this is an identity matrix.
        Matrix matrix = new Matrix();
        if (mRotation != 0) {
            // We want to do the rotation at origin, but since the bounding
            // rectangle will be changed after rotation, so the delta values
            // are based on old & new width/height respectively.
            int cx = mBitmap.getWidth() / 2;
            int cy = mBitmap.getHeight() / 2;
            matrix.preTranslate((float) -cx, (float) -cy);
            matrix.postRotate((float) mRotation);
            matrix.postTranslate((float) (getWidth() / 2), (float) (getHeight() / 2));
        }
        return matrix;
    }

    private final boolean isOrientationChanged() {

        return (mRotation / 90) % 2 != 0;
    }

    public final int getHeight() {

        return isOrientationChanged() ? mBitmap.getWidth() : mBitmap.getHeight();
    }

    public final int getWidth() {

        return isOrientationChanged() ? mBitmap.getHeight() : mBitmap.getWidth();
    }

    public final void recycle() {

        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }
}

