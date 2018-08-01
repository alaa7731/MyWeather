package hbmsu.com.myweather.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import hbmsu.com.myweather.R;

/**
 * Created by asalah on 14/11/2017.
 */

public class MyLinearLayout extends LinearLayout {

    private int backgroundColor;
    private boolean half_circle = false, with_gradient = false;
    GradientDrawable gradientDrawableNormal;
    int radius, strokeColor, strokeWidth, solidColor;
    int[] gradientColors = new int[]{0xFFd4b061, 0xFFd4b061};

    public MyLinearLayout(Context context) {
        super(context);
        init(context, null);

    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (!isInEditMode()) {
            if (attrs != null) {
                TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyLinearLayout);
                Boolean enabled = typedArray.getBoolean(R.styleable.MyLinearLayout_layout_enabled, true);
                with_gradient = typedArray.getBoolean(R.styleable.MyLinearLayout_with_gradient, false);

                this.setEnabled(enabled);


                Resources resources = context.getResources();
                DisplayMetrics metrics = resources.getDisplayMetrics();


                radius = typedArray.getDimensionPixelSize(R.styleable.MyLinearLayout_view_radius, (int) (3 * (metrics.densityDpi / 160f)));

                strokeColor = typedArray.getColor(R.styleable.MyLinearLayout_stroke_color, Color.TRANSPARENT);

                strokeWidth = typedArray.getDimensionPixelSize(R.styleable.MyLinearLayout_stroke_width, (int) (0 * (metrics.densityDpi / 160f)));

                solidColor = typedArray.getColor(R.styleable.MyLinearLayout_solid_color, Color.WHITE);

                half_circle = typedArray.getBoolean(R.styleable.MyLinearLayout_half_circle, false);


                if (with_gradient) {
                    gradientDrawableNormal = new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                            gradientColors);
                    gradientDrawableNormal.setCornerRadius(radius);
                    gradientDrawableNormal.setStroke(0, Color.TRANSPARENT);
                    gradientDrawableNormal.setShape(GradientDrawable.RECTANGLE);
                    gradientDrawableNormal.invalidateSelf();

                    this.setBackground(gradientDrawableNormal);
                } else {
                    gradientDrawableNormal = new GradientDrawable();
                    gradientDrawableNormal.setCornerRadius(radius);
                    gradientDrawableNormal.setStroke(typedArray.getDimensionPixelSize(R.styleable.MyLinearLayout_stroke_width,
                            (int) (0 * (metrics.densityDpi / 160f))),
                            typedArray.getColor(R.styleable.MyLinearLayout_stroke_color, Color.TRANSPARENT));
                    gradientDrawableNormal.setShape(GradientDrawable.RECTANGLE);
                    backgroundColor = typedArray.getColor(R.styleable.MyLinearLayout_solid_color, Color.TRANSPARENT);
                    gradientDrawableNormal.setColor(backgroundColor);
                    this.setBackground(gradientDrawableNormal);

                    typedArray.recycle();
                }
            }
        }
    }

    public void setWith_gradient(boolean with_gradient) {
        this.with_gradient = with_gradient;

        requestLayout();
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (half_circle && !with_gradient) {
            gradientDrawableNormal.setShape(GradientDrawable.RECTANGLE);
            gradientDrawableNormal.setColor(solidColor);
            gradientDrawableNormal.setCornerRadius(getHeight() / 2);
            setBackground(gradientDrawableNormal);
        } else if (half_circle && with_gradient) {
            gradientDrawableNormal.setOrientation(GradientDrawable.Orientation.TL_BR);
            gradientDrawableNormal.setColors(gradientColors);
            gradientDrawableNormal.setCornerRadius(getHeight() / 2);
            gradientDrawableNormal.setStroke(0, Color.TRANSPARENT);
            gradientDrawableNormal.setShape(GradientDrawable.RECTANGLE);
            gradientDrawableNormal.invalidateSelf();
            setBackground(gradientDrawableNormal);
        } else if (with_gradient) {
            gradientDrawableNormal.setOrientation(GradientDrawable.Orientation.TL_BR);
            gradientDrawableNormal.setColors(gradientColors);
            gradientDrawableNormal.setCornerRadius(radius);
            gradientDrawableNormal.setStroke(0, Color.TRANSPARENT);
            gradientDrawableNormal.setShape(GradientDrawable.RECTANGLE);
            gradientDrawableNormal.invalidateSelf();
            setBackground(gradientDrawableNormal);
        } else {
            gradientDrawableNormal.setShape(GradientDrawable.RECTANGLE);
            gradientDrawableNormal.setColor(solidColor);
            gradientDrawableNormal.setCornerRadius(radius);
            setBackground(gradientDrawableNormal);

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (half_circle && !with_gradient) {
            gradientDrawableNormal.setShape(GradientDrawable.RECTANGLE);
            gradientDrawableNormal.setColor(solidColor);
            gradientDrawableNormal.setCornerRadius(getHeight() / 2);
            setBackground(gradientDrawableNormal);
        } else if (half_circle && with_gradient) {
            gradientDrawableNormal.setOrientation(GradientDrawable.Orientation.TL_BR);
            gradientDrawableNormal.setColors(gradientColors);
            gradientDrawableNormal.setCornerRadius(getHeight() / 2);
            gradientDrawableNormal.setStroke(0, Color.TRANSPARENT);
            gradientDrawableNormal.setShape(GradientDrawable.RECTANGLE);
            gradientDrawableNormal.invalidateSelf();
            setBackground(gradientDrawableNormal);
        } else if (with_gradient) {
            gradientDrawableNormal.setOrientation(GradientDrawable.Orientation.TL_BR);
            gradientDrawableNormal.setColors(gradientColors);
            gradientDrawableNormal.setCornerRadius(radius);
            gradientDrawableNormal.setStroke(0, Color.TRANSPARENT);
            gradientDrawableNormal.setShape(GradientDrawable.RECTANGLE);
            gradientDrawableNormal.invalidateSelf();
            setBackground(gradientDrawableNormal);
        }
    }


    public void setShape(int color, int radius) {
        setShape(color, Color.TRANSPARENT, -1, radius, null);
    }

    public void setShape(int color, int topLeftCorner, int topRightCorner, int bottomRightCorner, int bottomLeftCorner) {
        setShape(color, Color.TRANSPARENT, 0, 0,
                new float[]{topLeftCorner, topLeftCorner, topRightCorner, topRightCorner,
                        bottomLeftCorner, bottomLeftCorner, bottomRightCorner, bottomRightCorner});
    }

    public void setShape(int color) {
        setShape(color, Color.TRANSPARENT, 0, 0, null);
    }

    public void setShape(int color, int strokeColor, int strokeWidth, int radius, float[] radii) {
        if (radius == -1)
            radius = this.radius;
        if (strokeWidth == -1)
            strokeWidth = this.strokeWidth;
        if (strokeColor == -1)
            strokeColor = this.strokeColor;
        gradientDrawableNormal = new GradientDrawable();
        if (radii == null)
            gradientDrawableNormal.setCornerRadius(radius);
        else gradientDrawableNormal.setCornerRadii(radii);
        gradientDrawableNormal.setStroke(strokeWidth,
                strokeColor);
        gradientDrawableNormal.setShape(GradientDrawable.RECTANGLE);
        backgroundColor = color;
        gradientDrawableNormal.setColor(backgroundColor);
        this.setBackground(gradientDrawableNormal);
    }
}
