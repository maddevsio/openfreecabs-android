package io.maddevs.openfreecabs.utils.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import io.maddevs.openfreecabs.R;

/**
 * Created by man on 01.10.16.
 */
public class CircleView extends View {
    RectF rectF;
    Paint backgroundPaint;
    float aspectRatio;
    int color = Color.WHITE;

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        rectF = new RectF();

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircleView,
                0, 0);

        try {
            aspectRatio = typedArray.getFloat(R.styleable.CircleView_aspectRatio, 1);
            color = typedArray.getInt(R.styleable.CircleView_fillColor, color);
        } finally {
            typedArray.recycle();
        }

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(color);
        backgroundPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        float corner = Math.min(width, height) * aspectRatio;
        setMeasuredDimension((int) corner, (int) corner);
        rectF.set(0, 0, corner, corner);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(rectF, backgroundPaint);
    }
}
