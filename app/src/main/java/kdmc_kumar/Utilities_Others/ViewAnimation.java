package kdmc_kumar.Utilities_Others;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ViewAnimation {

    public ViewAnimation() {
    }

    public static void expand(final View v, final AnimListener animListener) {
        Animation a = expandAction(v);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animListener.onFinish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(a);
    }

    public static void expand(final View v) {
       Animation a = expandAction(v);
       v.startAnimation(a);
    }

    private static Animation expandAction(final View v) {

        v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1.0F
                        ? LayoutParams.WRAP_CONTENT
                        : (int) ((float) targtetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((long) (int) ((float) targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
        return a;
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1.0F) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) ((float) initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((long) (int) ((float) initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void flyInDown(final View v, final AnimListener animListener) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(0.0f);
        v.setTranslationY((float) 0);
        v.setTranslationY((float) -v.getHeight());
        // Prepare the View for the animation
        v.animate()
                .setDuration(200L)
                .translationY((float) 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animListener != null) animListener.onFinish();
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(1.0f)
                .start();
    }

    public static void flyOutDown(final View v, final AnimListener animListener) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(1.0f);
        v.setTranslationY((float) 0);
        // Prepare the View for the animation
        v.animate()
                .setDuration(200L)
                .translationY((float) v.getHeight())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animListener != null) animListener.onFinish();
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(0.0f)
                .start();
    }

    public static void fadeIn(final View v) {
        ViewAnimation.fadeIn(v, null);
    }

    private static void fadeIn(final View v, final AnimListener animListener) {
        v.setVisibility(View.GONE);
        v.setAlpha(0.0f);
        // Prepare the View for the animation
        v.animate()
                .setDuration(200L)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.VISIBLE);
                        if (animListener != null) animListener.onFinish();
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(1.0f);
    }

    public static void fadeOut(final View v) {
        ViewAnimation.fadeOut(v, null);
    }

    private static void fadeOut(final View v, final AnimListener animListener) {
        v.setAlpha(1.0f);
        // Prepare the View for the animation
        v.animate()
                .setDuration(500L)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animListener != null) animListener.onFinish();
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(0.0f);
    }

    public static void showIn(final View v) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(0.0f);
        v.setTranslationY((float) v.getHeight());
        v.animate()
                .setDuration(200L)
                .translationY((float) 0)
                .setListener(new AnimatorListenerAdapter() {
                })
                .alpha(1.0f)
                .start();
    }

    public static void initShowOut(final View v) {
        v.setVisibility(View.GONE);
        v.setTranslationY((float) v.getHeight());
        v.setAlpha(0.0f);
    }

    public static void showOut(final View v) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(1.0f);
        v.setTranslationY((float) 0);
        v.animate()
                .setDuration(200L)
                .translationY((float) v.getHeight())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.GONE);
                        super.onAnimationEnd(animation);
                    }
                }).alpha(0.0f)
                .start();
    }

    public static boolean rotateFab(final View v, boolean rotate) {
        v.animate().setDuration(200L)
                .setListener(new AnimatorListenerAdapter() {
                })
                .rotation(rotate ? 135.0f : 0.0f);
        return rotate;
    }


    public interface AnimListener {
        void onFinish();
    }

    public static void fadeOutIn(View view) {
        view.setAlpha(0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 0.5f, 1.0f);
        ObjectAnimator.ofFloat(view, "alpha", 0.0f).start();
        animatorAlpha.setDuration(500L);
        animatorSet.play(animatorAlpha);
        animatorSet.start();
    }


    public static void showScale(final View v) {
        ViewAnimation.showScale(v, null);
    }

    private static void showScale(final View v, final AnimListener animListener) {
        v.animate()
                .scaleY(1.0F)
                .scaleX(1.0F)
                .setDuration(200L)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animListener != null) animListener.onFinish();
                        super.onAnimationEnd(animation);
                    }
                })
                .start();
    }

    public static void hideScale(final View v) {
        ViewAnimation.fadeOut(v, null);
    }

    public static void hideScale(final View v, final AnimListener animListener) {
        v.animate()
                .scaleY((float) 0)
                .scaleX((float) 0)
                .setDuration(200L)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animListener != null) animListener.onFinish();
                        super.onAnimationEnd(animation);
                    }
                })
                .start();
    }
}
