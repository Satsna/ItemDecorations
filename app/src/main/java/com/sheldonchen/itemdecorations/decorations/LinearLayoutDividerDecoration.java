package com.sheldonchen.itemdecorations.decorations;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cxd on 2017/05/24
 */

public class LinearLayoutDividerDecoration extends RecyclerView.ItemDecoration {

    @DecorationOrientType private int mOrientation = LinearLayoutManager.VERTICAL;

    /**
     * 分割线宽度.
     */
    private int mDividerWidth = 1;

    /**
     * 竖向列表：左边距   横向列表：上边距.
     */
    private int mStartPadding = 0;

    /**
     * 竖向列表：右边距   横向列表：下边距.
     */
    private int mEndPadding = 0;

    /**
     * 是否画第一个item之前的分割线.
     */
    private boolean mDrawFirstDivider = true;

    /**
     * 是否画最后一个item之后的分割线.
     */
    private boolean mDrawLastDivider = true;

    @ColorInt private int mDividerColor = Color.TRANSPARENT;

    private boolean mDrawDividerColor = false;

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Set<Integer> mNonDrawPositions = new HashSet<>();

    public LinearLayoutDividerDecoration() {
    }

    public LinearLayoutDividerDecoration(@DecorationOrientType int orientation) {
        this.mOrientation = orientation;
    }

    // set methods.

    public LinearLayoutDividerDecoration setOrientation(@DecorationOrientType int orientation) {
        this.mOrientation = orientation;
        return this;
    }

    public LinearLayoutDividerDecoration setStartPadding(int startPadding) {
        setPadding(startPadding, mEndPadding);
        return this;
    }

    public LinearLayoutDividerDecoration setEndPadding(int endPadding) {
        setPadding(mStartPadding, endPadding);
        return this;
    }

    public LinearLayoutDividerDecoration setPadding(int startPadding, int endPadding) {
        this.mStartPadding = checkValid(startPadding);
        this.mEndPadding = checkValid(endPadding);
        return this;
    }

    public LinearLayoutDividerDecoration setDividerColor(@ColorInt int color) {
        mDrawDividerColor = true;
        this.mDividerColor = color;
        return this;
    }

    public LinearLayoutDividerDecoration setDrawFirstDivider(boolean isDraw) {
        this.mDrawFirstDivider = isDraw;
        return this;
    }

    public LinearLayoutDividerDecoration setDrawLastDivider(boolean isDraw) {
        this.mDrawLastDivider = isDraw;
        return this;
    }

    public LinearLayoutDividerDecoration setDividerWidth(int dividerWidth) {
        this.mDividerWidth = checkValid(dividerWidth);
        return this;
    }

    public LinearLayoutDividerDecoration setDrawDividerColor(boolean drawDividerColor) {
        this.mDrawDividerColor = drawDividerColor;
        return this;
    }

    /**
     * 指定位置的divider不画，可指定多个位置.
     */
    public LinearLayoutDividerDecoration notDrawSpecificDivider(int position) {
        mNonDrawPositions.add(position);
        return this;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if(!mDrawDividerColor) return;

        mPaint.setColor(mDividerColor);

        if(mOrientation == LinearLayoutManager.VERTICAL) {
            drawVerticalDivider(c, parent, state);
        } else {
            drawHorizontalDivider(c, parent, state);
        }
    }

    @SuppressWarnings("UnusedParameters")
    private void drawVerticalDivider(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft() + mStartPadding;
        final int right = parent.getWidth() - parent.getPaddingRight() - mEndPadding;
        for (int i = 0, childCount = parent.getChildCount(); i < childCount; i++) {
            View childView = parent.getChildAt(i);
            final int layoutPos = parent.getChildLayoutPosition(childView);
            RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) childView.getLayoutParams();
            int top = childView.getBottom() + params.bottomMargin;
            int bottom = top + mDividerWidth;
            if(i < childCount - 1 || mDrawLastDivider) {
                if(!mNonDrawPositions.contains(layoutPos)) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
            if(i == 0 && mDrawFirstDivider) {
                bottom = childView.getTop() - params.topMargin;
                top = bottom - mDividerWidth;
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    @SuppressWarnings("UnusedParameters")
    private void drawHorizontalDivider(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        final int top = parent.getPaddingTop() + mStartPadding;
        final int bottom = parent.getHeight() - parent.getPaddingBottom() - mEndPadding;
        for(int i = 0, childCount = parent.getChildCount(); i < childCount; i++) {
            View childView = parent.getChildAt(i);
            final int layoutPos = parent.getChildLayoutPosition(childView);
            RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getRight() + params.rightMargin;
            int right = left + mDividerWidth;
            if(i < childCount - 1 || mDrawLastDivider) {
                if(!mNonDrawPositions.contains(layoutPos)) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
            if(i == 0 && mDrawFirstDivider) {
                right = childView.getLeft() - params.leftMargin;
                left = right - mDividerWidth;
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        final int currentPos = parent.getChildLayoutPosition(view);
        final int lastPos = state.getItemCount() - 1;
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            if(mDrawFirstDivider && currentPos == 0) {
                outRect.top = mDividerWidth;
            }
            if(!mDrawLastDivider && currentPos == lastPos) {
                outRect.bottom = 0;
            } else {
                outRect.bottom = mDividerWidth;
            }
            if(mNonDrawPositions.contains(currentPos)) {
                outRect.bottom = 0;
            }
        } else {
            if(mDrawFirstDivider && currentPos == 0) {
                outRect.left = mDividerWidth;
            }
            if(!mDrawLastDivider && currentPos == lastPos) {
                outRect.right = 0;
            } else {
                outRect.right = mDividerWidth;
            }
            if(mNonDrawPositions.contains(currentPos)) {
                outRect.right = 0;
            }
        }
    }

    // utils.

    private static int checkValid(int value) {
        return value < 0 ? 0 : value;
    }
}
