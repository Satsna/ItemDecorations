package com.sheldonchen.itemdecorations.provider;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;

import com.sheldonchen.itemdecorations.provider.base.BaseProvider;

/**
 * Created by cxd on 2018/4/17
 */

public class ColorIntProvider extends BaseProvider<Integer> {
    private final Paint mPaint;

    public ColorIntProvider(@ColorInt Integer colorInt) {
        super(colorInt);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(colorInt);
    }

    @Override
    public void drawDivider(Canvas canvas, int left, int top, int right, int bottom) {
        canvas.drawRect(left, top, right, bottom, mPaint);
    }

}
