package com.sheldonchen.itemdecorations.painter;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.sheldonchen.itemdecorations.painter.base.IDividerPainter;

/**
 * Created by cxd on 2018/4/17
 */

public class DrawablePainter implements IDividerPainter {
    private Drawable mDividerDrawable;

    public DrawablePainter(Drawable drawable) {
        mDividerDrawable = drawable;
    }

    @Override
    public void drawDivider(Canvas canvas, int left, int top, int right, int bottom) {
        if(mDividerDrawable == null) return;

        mDividerDrawable.setBounds(left, top, right, bottom);
        mDividerDrawable.draw(canvas);
    }

}
