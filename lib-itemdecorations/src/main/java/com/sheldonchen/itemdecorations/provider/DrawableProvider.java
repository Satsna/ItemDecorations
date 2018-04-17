package com.sheldonchen.itemdecorations.provider;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.sheldonchen.itemdecorations.provider.base.BaseProvider;

/**
 * Created by cxd on 2018/4/17
 */

public class DrawableProvider extends BaseProvider<Drawable> {

    public DrawableProvider(Drawable entity) {
        super(entity);
    }

    @Override
    public void drawDivider(Canvas canvas, int left, int top, int right, int bottom) {
        entity.setBounds(left, top, right, bottom);
        entity.draw(canvas);
    }

}
