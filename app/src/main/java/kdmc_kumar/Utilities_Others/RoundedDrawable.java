package kdmc_kumar.Utilities_Others;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView.ScaleType;

public class RoundedDrawable extends Drawable {

    private static final String TAG = "RoundedDrawable";
    public static final int DEFAULT_BORDER_COLOR = Color.BLACK;

    private final RectF mBounds = new RectF();
    private final RectF mDrawableRect = new RectF();
    private final RectF mBitmapRect = new RectF();
    private final BitmapShader mBitmapShader;
    private final Paint mBitmapPaint;
    private final int mBitmapWidth;
    private final int mBitmapHeight;
    private final RectF mBorderRect = new RectF();
    private final Paint mBorderPaint;
    private final Matrix mShaderMatrix = new Matrix();

    private float mCornerRadius = (float) 0;
    private boolean mOval = false;
    private float mBorderWidth = (float) 0;
    private ColorStateList mBorderColor = ColorStateList.valueOf(DEFAULT_BORDER_COLOR);
    private ScaleType mScaleType = ScaleType.FIT_CENTER;

    private RoundedDrawable(Bitmap bitmap) {

        mBitmapWidth = bitmap.getWidth();
        mBitmapHeight = bitmap.getHeight();
        mBitmapRect.set((float) 0, (float) 0, (float) mBitmapWidth, (float) mBitmapHeight);

        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapShader.setLocalMatrix(mShaderMatrix);

        mBitmapPaint = new Paint();
        mBitmapPaint.setStyle(Paint.Style.FILL);
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setShader(mBitmapShader);

        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor.getColorForState(getState(), DEFAULT_BORDER_COLOR));
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }

    public static RoundedDrawable fromBitmap(Bitmap bitmap) {
        return bitmap != null ? new RoundedDrawable(bitmap) : null;
    }

    public static Drawable fromDrawable(Drawable drawable) {
        if (drawable != null) {
            if (drawable instanceof RoundedDrawable) {
                // just return if it's already a RoundedDrawable
                return drawable;
            } else if (drawable instanceof LayerDrawable) {
                LayerDrawable ld = (LayerDrawable) drawable;
                int num = ld.getNumberOfLayers();

                // loop through layers to and change to RoundedDrawables if possible
                for (int i = 0; i < num; i++) {
                    Drawable d = ld.getDrawable(i);
                    ld.setDrawableByLayerId(ld.getId(i), fromDrawable(d));
                }
                return ld;
            }

            // try to get a bitmap from the drawable and
            Bitmap bm = drawableToBitmap(drawable);
            if (bm != null) {
                return new RoundedDrawable(bm);
            } else {
                Log.w(TAG, "Failed to create bitmap from drawable!");
            }
        }
        return drawable;
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        @Nullable Bitmap bitmap;
        int width = Math.max(drawable.getIntrinsicWidth(), 1);
        int height = Math.max(drawable.getIntrinsicHeight(), 1);
        try {
            bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
            bitmap = null;
        }

        return bitmap;
    }

    @Override
    public final boolean isStateful() {
        return mBorderColor.isStateful();
    }

    @Override
    protected final boolean onStateChange(int[] state) {
        int newColor = mBorderColor.getColorForState(state, 0);
        if (mBorderPaint.getColor() == newColor) {
            return super.onStateChange(state);
        } else {
            mBorderPaint.setColor(newColor);
            return true;
        }
    }

    private void updateShaderMatrix() {
        float scale;
        float dx;
        float dy;

        switch (mScaleType) {
            case CENTER:
                mBorderRect.set(mBounds);
                mBorderRect.inset((mBorderWidth) / 2.0F, (mBorderWidth) / 2.0F);

                mShaderMatrix.set(null);
                mShaderMatrix.setTranslate((float) (int) ((mBorderRect.width() - (float) mBitmapWidth) * 0.5f + 0.5f),
                        (float) (int) ((mBorderRect.height() - (float) mBitmapHeight) * 0.5f + 0.5f));
                break;

            case CENTER_CROP:
                mBorderRect.set(mBounds);
                mBorderRect.inset((mBorderWidth) / 2.0F, (mBorderWidth) / 2.0F);

                mShaderMatrix.set(null);

                dx = (float) 0;
                dy = (float) 0;

                if ((float) mBitmapWidth * mBorderRect.height() > mBorderRect.width() * (float) mBitmapHeight) {
                    scale = mBorderRect.height() / mBitmapHeight;
                    dx = (mBorderRect.width() - (float) mBitmapWidth * scale) * 0.5f;
                } else {
                    scale = mBorderRect.width() / mBitmapWidth;
                    dy = (mBorderRect.height() - (float) mBitmapHeight * scale) * 0.5f;
                }

                mShaderMatrix.setScale(scale, scale);
                mShaderMatrix.postTranslate((float) (int) (dx + 0.5f) + mBorderWidth,
                        (float) (int) (dy + 0.5f) + mBorderWidth);
                break;

            case CENTER_INSIDE:
                mShaderMatrix.set(null);

                scale = mBitmapWidth <= mBounds.width() && mBitmapHeight <= mBounds.height() ? 1.0f : Math.min(mBounds.width() / (float) mBitmapWidth,
                        mBounds.height() / (float) mBitmapHeight);

                dx = (float) (int) ((mBounds.width() - (float) mBitmapWidth * scale) * 0.5f + 0.5f);
                dy = (float) (int) ((mBounds.height() - (float) mBitmapHeight * scale) * 0.5f + 0.5f);

                mShaderMatrix.setScale(scale, scale);
                mShaderMatrix.postTranslate(dx, dy);

                mBorderRect.set(mBitmapRect);
                mShaderMatrix.mapRect(mBorderRect);
                mBorderRect.inset((mBorderWidth) / 2.0F, (mBorderWidth) / 2.0F);
                mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                break;

            default:
            case FIT_CENTER:
                mBorderRect.set(mBitmapRect);
                mShaderMatrix.setRectToRect(mBitmapRect, mBounds, Matrix.ScaleToFit.CENTER);
                mShaderMatrix.mapRect(mBorderRect);
                mBorderRect.inset((mBorderWidth) / 2.0F, (mBorderWidth) / 2.0F);
                mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                break;

            case FIT_END:
                mBorderRect.set(mBitmapRect);
                mShaderMatrix.setRectToRect(mBitmapRect, mBounds, Matrix.ScaleToFit.END);
                mShaderMatrix.mapRect(mBorderRect);
                mBorderRect.inset((mBorderWidth) / 2.0F, (mBorderWidth) / 2.0F);
                mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                break;

            case FIT_START:
                mBorderRect.set(mBitmapRect);
                mShaderMatrix.setRectToRect(mBitmapRect, mBounds, Matrix.ScaleToFit.START);
                mShaderMatrix.mapRect(mBorderRect);
                mBorderRect.inset((mBorderWidth) / 2.0F, (mBorderWidth) / 2.0F);
                mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                break;

            case FIT_XY:
                mBorderRect.set(mBounds);
                mBorderRect.inset((mBorderWidth) / 2.0F, (mBorderWidth) / 2.0F);
                mShaderMatrix.set(null);
                mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                break;
        }

        mDrawableRect.set(mBorderRect);
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    @Override
    protected final void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        mBounds.set(bounds);

        updateShaderMatrix();
    }

    @Override
    public final void draw(@NonNull Canvas canvas) {

        if (mOval) {
            if (mBorderWidth > (float) 0) {
                canvas.drawOval(mDrawableRect, mBitmapPaint);
                canvas.drawOval(mBorderRect, mBorderPaint);
            } else {
                canvas.drawOval(mDrawableRect, mBitmapPaint);
            }
        } else {
            if (mBorderWidth > (float) 0) {
                canvas.drawRoundRect(mDrawableRect, Math.max(mCornerRadius, (float) 0),
                        Math.max(mCornerRadius, (float) 0), mBitmapPaint);
                canvas.drawRoundRect(mBorderRect, mCornerRadius, mCornerRadius, mBorderPaint);
            } else {
                canvas.drawRoundRect(mDrawableRect, mCornerRadius, mCornerRadius, mBitmapPaint);
            }
        }
    }

    @Override
    public final int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public final void setAlpha(int i) {
        mBitmapPaint.setAlpha(i);
        invalidateSelf();
    }

    @Override
    public final void setColorFilter(ColorFilter colorFilter) {
        mBitmapPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public final void setDither(boolean dither) {
        mBitmapPaint.setDither(dither);
        invalidateSelf();
    }

    @Override
    public final void setFilterBitmap(boolean filter) {
        mBitmapPaint.setFilterBitmap(filter);
        invalidateSelf();
    }

    @Override
    public final int getIntrinsicWidth() {
        return mBitmapWidth;
    }

    @Override
    public final int getIntrinsicHeight() {
        return mBitmapHeight;
    }

    public final float getCornerRadius() {
        return mCornerRadius;
    }

    public final RoundedDrawable setCornerRadius(float radius) {
        mCornerRadius = radius;
        return this;
    }

    public final float getBorderWidth() {
        return mBorderWidth;
    }

    public final RoundedDrawable setBorderWidth(int width) {
        mBorderWidth = (float) width;
        mBorderPaint.setStrokeWidth(mBorderWidth);
        return this;
    }

    public final int getBorderColor() {
        return mBorderColor.getDefaultColor();
    }

    public final RoundedDrawable setBorderColor(int color) {
        return setBorderColors(ColorStateList.valueOf(color));
    }

    public final ColorStateList getBorderColors() {
        return mBorderColor;
    }

    public final RoundedDrawable setBorderColors(ColorStateList colors) {
        mBorderColor = colors != null ? colors : ColorStateList.valueOf(0);
        mBorderPaint.setColor(mBorderColor.getColorForState(getState(), DEFAULT_BORDER_COLOR));
        return this;
    }

    public final boolean isOval() {
        return mOval;
    }

    public final RoundedDrawable setOval(boolean oval) {
        mOval = oval;
        return this;
    }

    public final ScaleType getScaleType() {
        return mScaleType;
    }

    public final RoundedDrawable setScaleType(ScaleType scaleType) {
        ScaleType scaleType1 = scaleType;
        if (scaleType1 == null) {
            scaleType1 = ScaleType.FIT_CENTER;
        }
        if (mScaleType != scaleType1) {
            mScaleType = scaleType1;
            updateShaderMatrix();
        }
        return this;
    }

    public final Bitmap toBitmap() {
        return drawableToBitmap(this);
    }
}
