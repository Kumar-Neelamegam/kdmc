/*
 * Copyright (C) 2018 Angad Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kdmc_kumar.Utilities_Others.toggle;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import displ.mobydocmarathi.com.R;

/**
 * <p>
 * Created by Angad Singh on 27/1/18.
 * </p>
 */

public class LabeledSwitch extends View {
    private int width = 0;
    private int height = 0;
    private int padding = 0;

    private int colorOn;
    private int colorOff;
    private int colorBorder;
    private int colorDisabled;

    private int textSize;

    private int outerRadii = 0;
    private int thumbRadii = 0;

    private boolean isOn;
    private boolean enabled;

    private Paint paint;

    private long startTime = 0L;

    private String labelOn;
    private String labelOff;

    private RectF thumbBounds;

    private RectF leftBgArc;
    private RectF rightBgArc;

    private RectF leftFgArc;
    private RectF rightFgArc;

    private Typeface typeface = null;

    private float thumbOnCenterX = 0.0F;
    private float thumbOffCenterX = 0.0F;

    private OnToggledListener onToggledListener = null;

    public LabeledSwitch(Context context) {
        super(context);
        initView();
    }

    public LabeledSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initProperties(attrs);
    }

    public LabeledSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initProperties(attrs);
    }

    private void initView() {
        this.isOn = false;
        this.labelOn = "YES";
        this.labelOff = "NO";

        this.enabled = true;
        this.textSize = (int)(12.0f * getResources().getDisplayMetrics().scaledDensity);

        colorBorder = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M ? (colorOn = getResources().getColor(R.color.colorAccent, getContext().getTheme())) : (colorOn = getResources().getColor(R.color.colorAccent));

        paint = new Paint();
        paint.setAntiAlias(true);

        leftBgArc = new RectF();
        rightBgArc = new RectF();

        leftFgArc = new RectF();
        rightFgArc = new RectF();
        thumbBounds = new RectF();

        this.colorOff = Color.parseColor("#FFFFFF");
        this.colorDisabled = Color.parseColor("#D3D3D3");
    }

    private void initProperties(AttributeSet attrs) {
        TypedArray tarr = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.Toggle,0,0);
        final int N = tarr.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = tarr.getIndex(i);
            if (attr == R.styleable.Toggle_on) {
                isOn = tarr.getBoolean(R.styleable.Toggle_on, false);
            } else if (attr == R.styleable.Toggle_colorOff) {
                colorOff = tarr.getColor(R.styleable.Toggle_colorOff, Color.parseColor("#FFFFFF"));
            } else if (attr == R.styleable.Toggle_colorBorder) {
                int accentColor;
                accentColor = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M ? getResources().getColor(R.color.colorAccent, getContext().getTheme()) : getResources().getColor(R.color.colorAccent);
                colorBorder = tarr.getColor(R.styleable.Toggle_colorBorder, accentColor);
            } else if (attr == R.styleable.Toggle_colorOn) {
                int accentColor;
                accentColor = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M ? getResources().getColor(R.color.colorAccent, getContext().getTheme()) : getResources().getColor(R.color.colorAccent);
                colorOn = tarr.getColor(R.styleable.Toggle_colorOn, accentColor);
            } else if (attr == R.styleable.Toggle_colorDisabled) {
                colorDisabled = tarr.getColor(R.styleable.Toggle_colorOff, Color.parseColor("#D3D3D3"));
            } else if (attr == R.styleable.Toggle_textOff) {
                labelOff = tarr.getString(R.styleable.Toggle_textOff);
            } else if (attr == R.styleable.Toggle_textOn) {
                labelOn = tarr.getString(R.styleable.Toggle_textOn);
            } else if (attr == R.styleable.Toggle_android_textSize) {
                int defaultTextSize = (int)(12.0f * getResources().getDisplayMetrics().scaledDensity);
                textSize = tarr.getDimensionPixelSize(R.styleable.Toggle_android_textSize, defaultTextSize);
            } else if(attr == R.styleable.Toggle_android_enabled) {
                enabled = tarr.getBoolean(R.styleable.Toggle_android_enabled, false);
            }
        }
    }

    @Override
    protected final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        float scaledSizeInPixels = textSize * getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize((float) textSize);

//      Drawing Switch background here
        {
            if(enabled) {
                paint.setColor(colorBorder);
            } else {
                paint.setColor(colorDisabled);
            }
            canvas.drawArc(leftBgArc, 90.0F, 180.0F, false, paint);
            canvas.drawArc(rightBgArc, 90.0F, -180.0F, false, paint);
            canvas.drawRect((float) outerRadii, (float) 0, (float) (width - outerRadii), (float) height, paint);

            paint.setColor(colorOff);

            canvas.drawArc(leftFgArc, 90.0F, 180.0F, false, paint);
            canvas.drawArc(rightFgArc, 90.0F, -180.0F, false, paint);
            canvas.drawRect((float) outerRadii, (float) (padding / 10), (float) (width - outerRadii), (float) (height - (padding / 10)), paint);

            int alpha = (int) (((thumbBounds.centerX() - thumbOffCenterX) / (thumbOnCenterX - thumbOffCenterX)) * 255.0F);
            int onColor;
            onColor = isEnabled() ? Color.argb(alpha, Color.red(colorOn), Color.green(colorOn), Color.blue(colorOn)) : Color.argb(alpha, Color.red(colorDisabled), Color.green(colorDisabled), Color.blue(colorDisabled));
            paint.setColor(onColor);

            canvas.drawArc(leftBgArc, 90.0F, 180.0F, false, paint);
            canvas.drawArc(rightBgArc, 90.0F, -180.0F, false, paint);
            canvas.drawRect((float) outerRadii, (float) 0, (float) (width - outerRadii), (float) height, paint);

            int alpha1 = (int) (((thumbOnCenterX - thumbBounds.centerX()) / (thumbOnCenterX - thumbOffCenterX)) * 255.0F);
            int offColor = Color.argb(alpha1, Color.red(colorOff), Color.green(colorOff), Color.blue(colorOff));
            paint.setColor(offColor);

            canvas.drawArc(leftFgArc, 90.0F, 180.0F, false, paint);
            canvas.drawArc(rightFgArc, 90.0F, -180.0F, false, paint);
            canvas.drawRect((float) outerRadii, (float) (padding / 10), (float) (width - outerRadii), (float) (height - (padding / 10)), paint);
        }

//      Drawing Switch Labels here
        String MAX_CHAR = "N";
        float textCenter = paint.measureText(MAX_CHAR) / 2.0F;
        if(isOn) {
            int alpha = (int)(((thumbBounds.centerX() - (float) (width / 2)) / (thumbOnCenterX - (float) (width / 2))) * 255.0F);
            int offColor = Color.argb(alpha < 0 ? 0 : alpha, Color.red(colorOff), Color.green(colorOff), Color.blue(colorOff));
            paint.setColor(offColor);

            int maxSize = width - (2 * padding) - (2 * thumbRadii);

//            float extraSpace = (maxSize - paint.measureText(labelOn)) / 2;

//            Effective label text area
//            canvas.drawRect(padding, padding, (padding / 2) + maxSize, height - padding, paint);
//            int onColor = Color.argb(alpha < 0 ? 0 : alpha, Color.red(colorOn), Color.green(colorOn), Color.blue(colorOn));
//            paint.setColor(onColor);

            float centerX = (float) ((((padding / 2) + maxSize) - padding) / 2);
            canvas.drawText(labelOn, (float) padding + centerX - (paint.measureText(labelOn) / 2.0F), (float) (height / 2) + textCenter, paint);
        } else {
            int alpha = (int)((((float) (width / 2) - thumbBounds.centerX()) / ((float) (width / 2) - thumbOffCenterX)) * 255.0F);
            int onColor;
            onColor = isEnabled() ? Color.argb(alpha < 0 ? 0 : alpha, Color.red(colorOn), Color.green(colorOn), Color.blue(colorOn)) : Color.argb(alpha < 0 ? 0 : alpha, Color.red(colorDisabled), Color.green(colorDisabled), Color.blue(colorDisabled));
            paint.setColor(onColor);

//            int maxSize = width - (2 * thumbRadii);
//            float extraSpace = (maxSize - paint.measureText(labelOff)) / 2;

//            Effective label text area
//            canvas.drawRect((padding + (padding / 2)) + (2 * thumbRadii), padding, width - padding, height - padding, paint);
//            int offColor = Color.argb(alpha < 0 ? 0 : alpha, Color.red(colorOff), Color.green(colorOff), Color.blue(colorOff));
//            paint.setColor(offColor);

            float centerX = (float) ((width - padding - ((padding + (padding / 2)) + (2 * thumbRadii))) / 2);
            canvas.drawText(labelOff, (float) (padding + (padding / 2)) + (float) (2 * thumbRadii) + centerX - (paint.measureText(labelOff) / 2.0F), (float) (height / 2) + textCenter, paint);
        }

//      Drawing Switch Thumb here
        {
            int alpha = (int) (((thumbBounds.centerX() - thumbOffCenterX) / (thumbOnCenterX - thumbOffCenterX)) * 255.0F);
            int offColor = Color.argb(alpha, Color.red(colorOff), Color.green(colorOff), Color.blue(colorOff));
            paint.setColor(offColor);

            canvas.drawCircle(thumbBounds.centerX(), thumbBounds.centerY(), (float) thumbRadii, paint);
            int alpha1 = (int) (((thumbOnCenterX - thumbBounds.centerX()) / (thumbOnCenterX - thumbOffCenterX)) * 255.0F);

            int onColor;
            onColor = isEnabled() ? Color.argb(alpha1 < 0 ? 0 : alpha1, Color.red(colorOn), Color.green(colorOn), Color.blue(colorOn)) : Color.argb(alpha1 < 0 ? 0 : alpha1, Color.red(colorDisabled), Color.green(colorDisabled), Color.blue(colorDisabled));
            paint.setColor(onColor);
            canvas.drawCircle(thumbBounds.centerX(), thumbBounds.centerY(), (float) thumbRadii, paint);
        }
    }

    @Override
    protected final void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = getResources().getDimensionPixelSize(R.dimen.default_width);
        int desiredHeight = getResources().getDimensionPixelSize(R.dimen.default_height);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
                width = Math.min(desiredWidth, widthSize);
                break;
            default:
                width = desiredWidth;
                break;
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                height = Math.min(desiredHeight, heightSize);
                break;
            default:
                height = desiredHeight;
                break;
        }

        setMeasuredDimension(width, height);

        outerRadii = Math.min(width, height) / 2;
        thumbRadii = (int) ((float) Math.min(width, height) / (2.88f));
        padding = (height - thumbRadii) / 2;

        thumbBounds.set((float) (width - padding - thumbRadii), (float) padding, (float) (width - padding), (float) (height - padding));
        thumbOnCenterX = thumbBounds.centerX();

        thumbBounds.set((float) padding, (float) padding, (float) (padding + thumbRadii), (float) (height - padding));
        thumbOffCenterX = thumbBounds.centerX();

        if(isOn) {
            thumbBounds.set((float) (width - padding - thumbRadii), (float) padding, (float) (width - padding), (float) (height - padding));
        } else {
            thumbBounds.set((float) padding, (float) padding, (float) (padding + thumbRadii), (float) (height - padding));
        }

        leftBgArc.set((float) 0, (float) 0, (float) (2 * outerRadii), (float) height);
        rightBgArc.set((float) (width - (2 * outerRadii)), (float) 0, (float) width, (float) height);

        leftFgArc.set((float) (padding / 10), (float) (padding / 10), (float) ((2 * outerRadii) - (padding / 10)), (float) (height - (padding / 10)));
        rightFgArc.set((float) (width - (2 * outerRadii) + (padding / 10)), (float) (padding / 10), (float) (width - (padding / 10)), (float) (height - (padding / 10)));
    }

    @Override
    public final boolean performClick() {
        super.performClick();
        if (isOn) {
            ValueAnimator switchColor = ValueAnimator.ofFloat((float) (width - padding - thumbRadii), (float) padding);
            switchColor.addUpdateListener(animation -> {
                float value = ((Float) animation.getAnimatedValue()).floatValue();
                thumbBounds.set(value, thumbBounds.top, value + (float) thumbRadii, thumbBounds.bottom);
                invalidate();
            });
            switchColor.setInterpolator(new AccelerateDecelerateInterpolator());
            switchColor.setDuration(250L);
            switchColor.start();
        } else {
            ValueAnimator switchColor = ValueAnimator.ofFloat((float) padding, (float) (width - padding - thumbRadii));
            switchColor.addUpdateListener(animation -> {
                float value = ((Float) animation.getAnimatedValue()).floatValue();
                thumbBounds.set(value, thumbBounds.top, value + (float) thumbRadii, thumbBounds.bottom);
                invalidate();
            });
            switchColor.setInterpolator(new AccelerateDecelerateInterpolator());
            switchColor.setDuration(250L);
            switchColor.start();
        }
        isOn =! isOn;
        return true;
    }

    @Override
    public final boolean onTouchEvent(MotionEvent event) {
        if(enabled) {
            float x = event.getX();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    startTime = System.currentTimeMillis();
                    return true;
                }

                case MotionEvent.ACTION_MOVE: {
                    // MOVE THUMB TO THIS POSITION
                    if (x - (float) (thumbRadii / 2) > (float) padding && x + (float) (thumbRadii / 2) < (float) (width - padding)) {
//                         Uncommenting this toggle switch back to last state on quick swipe actions.
//                        if (x >= width / 2) {
//                            isOn = true;
//                        } else {
//                            isOn = false;
//                        }
                        thumbBounds.set(x - (float) (thumbRadii / 2), thumbBounds.top, x + (float) (thumbRadii / 2), thumbBounds.bottom);
                        invalidate();
                    }
                    return true;
                }

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL: {
                    long endTime = System.currentTimeMillis();
                    long span = endTime - startTime;
                    if (span < 200L) {
                        // JUST TOGGLE THE CURRENT SELECTION
                        // USING PERFORM CLICK FOR ACCESSIBILITY SUPPORT
                        performClick();
                    } else {
                        if (x >= (float) (width / 2)) {
                            // MOVE SWITCH TO RIGHT
                            ValueAnimator switchColor = ValueAnimator.ofFloat((x > (float) (width - padding - thumbRadii) ? (float) (width - padding - thumbRadii) : x), (float) (width - padding - thumbRadii));
                            switchColor.addUpdateListener(animation -> {
                                float value = ((Float) animation.getAnimatedValue()).floatValue();
                                thumbBounds.set(value, thumbBounds.top, value + (float) thumbRadii, thumbBounds.bottom);
                                invalidate();
                            });
                            switchColor.setInterpolator(new AccelerateDecelerateInterpolator());
                            switchColor.setDuration(250L);
                            switchColor.start();
                            isOn = true;
                        } else {
                            // MOVE SWITCH TO LEFT
                            ValueAnimator switchColor = ValueAnimator.ofFloat((x < (float) padding ? (float) padding : x), (float) padding);
                            switchColor.addUpdateListener(animation -> {
                                float value = ((Float) animation.getAnimatedValue()).floatValue();
                                thumbBounds.set(value, thumbBounds.top, value + (float) thumbRadii, thumbBounds.bottom);
                                invalidate();
                            });
                            switchColor.setInterpolator(new AccelerateDecelerateInterpolator());
                            switchColor.setDuration(250L);
                            switchColor.start();
                            isOn = false;
                        }
                    }
                    invalidate();
                    if(onToggledListener != null) {
                        onToggledListener.onSwitched(this, isOn);
                    }
                    return true;
                }

                default: {
                    return super.onTouchEvent(event);
                }
            }
        } else {
            return false;
        }
    }

    public final void setOnToggledListener(OnToggledListener onToggledListener) {
        this.onToggledListener = onToggledListener;
    }



    public final int getColorOn() {
        return colorOn;
    }

    public final void setColorOn(int colorOn) {
        this.colorOn = colorOn;
        invalidate();
    }

    public final int getColorOff() {
        return colorOff;
    }

    public final void setColorOff(int colorOff) {
        this.colorOff = colorOff;
        invalidate();
    }

    public final String getLabelOn() {
        return labelOn;
    }

    public final void setLabelOn(String labelOn) {
        this.labelOn = labelOn;
        invalidate();
    }

    public final String getLabelOff() {
        return labelOff;
    }

    public final void setLabelOff(String labelOff) {
        this.labelOff = labelOff;
        invalidate();
    }

    public final Typeface getTypeface() {
        return typeface;
    }

    public final void setTypeface(Typeface typeface) {
        this.typeface = typeface;
        paint.setTypeface(typeface);
        invalidate();
    }

    public final boolean isOn() {
        return isOn;
    }

    public final void setOn(boolean on) {
        isOn = on;
        if(isOn) {
            thumbBounds.set((float) (width - padding - thumbRadii), (float) padding, (float) (width - padding), (float) (height - padding));
        } else {
            thumbBounds.set((float) padding, (float) padding, (float) (padding + thumbRadii), (float) (height - padding));
        }
        invalidate();
    }

    public final int getColorDisabled() {
        return colorDisabled;
    }

    public final void setColorDisabled(int colorDisabled) {
        this.colorDisabled = colorDisabled;
        invalidate();
    }

    @Override
    public final boolean isEnabled() {
        return enabled;
    }
}
