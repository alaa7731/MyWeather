package hbmsu.com.myweather.custom;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import hbmsu.com.myweather.R;

/**
 * Created by asalah on 14/11/2017.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView {

    Context context;
    DisplayMetrics metrics;

    public MyTextView(Context context) {
        super(context);
        this.context = context;
        inti(null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inti(attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        inti(attrs);
    }


    private void inti(AttributeSet attrs) {
        if (!isInEditMode()) {
 //            setTypeface(Localization.getRegular());

            if (attrs != null) {

                TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);

                Resources resources = context.getResources();
                metrics = resources.getDisplayMetrics();

                int radius = typedArray.getDimensionPixelSize(R.styleable.MyTextView_view_radius, (int) (0 * (metrics.densityDpi / 160f)));

                GradientDrawable gradientDrawableNormal = new GradientDrawable();
                gradientDrawableNormal.setCornerRadius(radius);
                gradientDrawableNormal.setStroke(typedArray.getDimensionPixelSize(R.styleable.MyTextView_stroke_width,
                        (int) (0 * (metrics.densityDpi / 160f))),
                        typedArray.getColor(R.styleable.MyTextView_stroke_color, Color.TRANSPARENT));
                gradientDrawableNormal.setShape(GradientDrawable.RECTANGLE);
                gradientDrawableNormal.setColor(typedArray.getColor(R.styleable.MyTextView_solid_color, Color.TRANSPARENT));
                this.setBackground(gradientDrawableNormal);

                typedArray.recycle();
            }
        }
    }

    public void setShape(int color, int radius) {
        setShape(color, Color.TRANSPARENT, 0, radius);
    }

    public void setShape(int color) {
        setShape(color, Color.TRANSPARENT, 0, 0);
    }

    public void setShape(int color, int strokeColor, int strokeWidth, int radius) {
        GradientDrawable gradientDrawableNormal = new GradientDrawable();
        gradientDrawableNormal.setCornerRadius(radius);
        gradientDrawableNormal.setStroke(strokeWidth,
                strokeColor);
        gradientDrawableNormal.setShape(GradientDrawable.RECTANGLE);
        gradientDrawableNormal.setColor(color);
        this.setBackground(gradientDrawableNormal);
    }
}
