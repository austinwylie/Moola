package com.hci.moola.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

/**
 * Created by byoung2 on 11/10/14.
 */
public class ExpandableLayout extends LinearLayout {
    private Animation openAnim, closeAnim;
    private int duration;
    private boolean isExpanded;

    public ExpandableLayout(Context context) {
        this(context, null, 0);
    }

    public ExpandableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        duration = 200;
        if (!isInEditMode())
            setVisibility(GONE);

        isExpanded = false;
    }

    public void setAnimationDuration(int duration) {
        this.duration = duration;
    }

    public void expand() {
        if (!isExpanded) {
            int minHeight = getMinimumHeight();
            measure(LayoutParams.MATCH_PARENT, minHeight);
            getLayoutParams().height = 0;

            openAnim = buildExpandAnimation(getMeasuredHeight());
            openAnim.setDuration(duration);

            setVisibility(View.VISIBLE);
            isExpanded = true;
            startAnimation(openAnim);
        }
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
//    }

    private Animation buildExpandAnimation(final int targetHeight) {
        return new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                getLayoutParams().height = interpolatedTime == 1 ? LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
    }

    public void collapse() {
        if (isExpanded) {
            closeAnim = buildCollapseAnimation(getMeasuredHeight());
            closeAnim.setDuration(duration);

            isExpanded = false;
            startAnimation(closeAnim);
        }
    }

    private Animation buildCollapseAnimation(final int initialHeight) {
        return new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    setVisibility(View.GONE);
                } else {
                    getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
    }
}
