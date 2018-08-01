package hbmsu.com.myweather.custom;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import hbmsu.com.myweather.R;

/**
 * Created by asalah on 14/11/2017.
 */

public class MyButton extends android.support.v7.widget.AppCompatButton {

    Context context;
    int radius, strokeColor, strokeWidth, solidColor;
    DisplayMetrics metrics;
    private boolean half_circle = false, with_gradient = false;
    GradientDrawable shape;

    public MyButton(Context context) {
        super(context);
        this.context = context;
        inti(null);
    }

    public MyButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inti(attrs);
    }

    public MyButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        inti(attrs);
    }


    private void inti(AttributeSet attrs) {
        if (!isInEditMode()) {
//            setTypeface(Localization.getBold());
            if (attrs != null) {


                TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyButton);
                TypedArray originalTypedArray = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.textColor, android.R.attr.textAllCaps});
                half_circle = typedArray.getBoolean(R.styleable.MyButton_half_circle, false);
                setTextColor(originalTypedArray.getColor(0, ContextCompat.getColor(context, R.color.colorPrimary)));

                setAllCaps(originalTypedArray.getBoolean(1, false));

                originalTypedArray.recycle();

                Resources resources = context.getResources();
                metrics = resources.getDisplayMetrics();

                radius = typedArray.getDimensionPixelSize(R.styleable.MyButton_view_radius, (int) (3 * (metrics.densityDpi / 160f)));

                strokeColor = typedArray.getColor(R.styleable.MyButton_stroke_color, Color.TRANSPARENT);

                strokeWidth = typedArray.getDimensionPixelSize(R.styleable.MyButton_stroke_width, (int) (0 * (metrics.densityDpi / 160f)));

                solidColor = typedArray.getColor(R.styleable.MyButton_solid_color, Color.WHITE);

                with_gradient = typedArray.getBoolean(R.styleable.MyButton_with_gradient, false);
                if (with_gradient) {
//                    shape = new GradientDrawable(GradientDrawable.Orientation.TL_BR,
//                            new int[]{0xFFd4b061, 0xFFd4b061});
                    shape = new GradientDrawable();

                    shape.setCornerRadius(0);
                    shape.setStroke((int) (1 * (metrics.densityDpi / 160f)), Color.WHITE);
                    shape.setColor(Color.TRANSPARENT);
                    shape.setShape(GradientDrawable.RECTANGLE);
                    shape.invalidateSelf();
                    setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

                    this.setBackground(shape);
                } else {
                    shape = new GradientDrawable();
//                gradientDrawableNormal.setState(this.getDrawableState());
                    shape.setCornerRadius(radius);
                    shape.setStroke(strokeWidth, strokeColor);
                    shape.setShape(GradientDrawable.RECTANGLE);
                    shape.setColor(solidColor);
                    shape.invalidateSelf();

                    GradientDrawable gradientDrawablePressed = new GradientDrawable();
//                gradientDrawablePressed.setState(this.getDrawableState());
                    gradientDrawablePressed.setCornerRadius(radius);
                    gradientDrawablePressed.setStroke(strokeWidth, strokeColor);
                    gradientDrawablePressed.setShape(GradientDrawable.RECTANGLE);
                    gradientDrawablePressed.setColor(solidColor);

                    gradientDrawablePressed.invalidateSelf();


                    GradientDrawable gradientDrawableDisabled = new GradientDrawable();
//                gradientDrawableDisabled.setState(this.getDrawableState());
                    gradientDrawableDisabled.setCornerRadius(radius);
                    gradientDrawableDisabled.setStroke((int) (1 * (metrics.densityDpi / 160f)), Color.parseColor("#BAB5AF"));
                    gradientDrawableDisabled.setShape(GradientDrawable.RECTANGLE);
                    gradientDrawableDisabled.setColor(Color.parseColor("#B6B6B6"));

                    gradientDrawableDisabled.invalidateSelf();


                    StateListDrawable stateListDrawable = new StateListDrawable();
                    stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, gradientDrawableDisabled);
                    stateListDrawable.addState(new int[]{-android.R.attr.state_pressed}, shape);
                    stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, gradientDrawablePressed);

                    this.setBackground(stateListDrawable);
                }

                boolean flag = false;
                Drawable[] drawables = this.getCompoundDrawables();
                Drawable[] scaled = new Drawable[4];
                int i = 0;
                for (Drawable drawable : drawables) {
                    if (drawable != null) {
                        flag = true;
                        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * 0.3),
                                (int) (drawable.getIntrinsicHeight() * 0.3));
                        scaled[i++] = drawable;
                    }
                }
                if (flag)
                    setCompoundDrawables(scaled[0], scaled[1], scaled[2], scaled[3]);
                typedArray.recycle();
            } else {
                setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            }

        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (half_circle && !with_gradient) {
            shape.setShape(GradientDrawable.RECTANGLE);
            shape.setColor(solidColor);
//            shape.setCornerRadius(getHeight() / 2);
            shape.setCornerRadius(0);
            setBackground(shape);
        } else if (half_circle && with_gradient) {
            shape.setCornerRadius(0);
            shape.setStroke((int) (1 * (metrics.densityDpi / 160f)), Color.WHITE);
            shape.setColor(Color.TRANSPARENT);
            shape.setShape(GradientDrawable.RECTANGLE);
            shape.invalidateSelf();
            setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setCompoundDrawableTintList(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));
            }
        }


    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (half_circle && !with_gradient) {
            shape.setShape(GradientDrawable.RECTANGLE);
            shape.setColor(solidColor);
//            shape.setCornerRadius(getHeight() / 2);
            shape.setCornerRadius(0);
            setBackground(shape);
        } else if (half_circle && with_gradient) {
            shape.setCornerRadius(0);
            shape.setStroke((int) (1 * (metrics.densityDpi / 160f)), Color.WHITE);
            shape.setColor(Color.TRANSPARENT);
            shape.setShape(GradientDrawable.RECTANGLE);
            shape.invalidateSelf();
            setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setCompoundDrawableTintList(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));
            }
        }


    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        PushDownAnim.setOnTouchPushDownAnim(this);
    }
}
