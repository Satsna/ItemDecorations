package com.sheldonchen.itemdecorations.decorations;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by cxd on 2018/3/7
 */

public class GridLayoutDividerDecoration extends RecyclerView.ItemDecoration {

    /**
     * RecyclerView布局方向.
     */
    private int mOrientation = GridLayoutManager.VERTICAL;

    /**
     * 列表滚动方向上分割线的宽度.
     */
    private int mDividerWidth = 10;

    /**
     * 侧边分割线的宽度.
     */
    private int mSideDividerWidth = 10;

    /**
     * 是否画顶部侧边分割线.
     */
    private boolean mDrawTopSideDivider = false;

    /**
     * 是否画底部侧边分割线.
     */
    private boolean mDrawBottomSideDivider = false;

    /**
     * 是否画侧边（左侧、上侧）分割线.
     */
    private boolean mDrawStartSideDivider = false;

    /**
     * 是否画侧边（右侧、下侧）分割线.
     */
    private boolean mDrawEndSideDivider = false;

    /**
     * 分割线颜色.
     */
    @ColorInt private int mDividerColor = Color.parseColor("#FFCCCCCC");

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public GridLayoutDividerDecoration() {
    }

    public GridLayoutDividerDecoration(int mOrientation) {
        this.mOrientation = mOrientation;
    }

    // set methods.

    public GridLayoutDividerDecoration setOrientation(int orientation) {
        this.mOrientation = orientation;
        return this;
    }

    public GridLayoutDividerDecoration setDividerWidth(int dividerWidth) {
        this.mDividerWidth = dividerWidth;
        return this;
    }

    public GridLayoutDividerDecoration setSideDividerWidth(int sideDividerWidth) {
        this.mSideDividerWidth = sideDividerWidth;
        return this;
    }

    public GridLayoutDividerDecoration drawTopSideDivider(boolean drawTopSideDivider) {
        this.mDrawTopSideDivider = drawTopSideDivider;
        return this;
    }

    public GridLayoutDividerDecoration drawBottomSideDivider(boolean drawBottomSideDivider) {
        this.mDrawBottomSideDivider = drawBottomSideDivider;
        return this;
    }

    public GridLayoutDividerDecoration drawStartSideDivider(boolean drawStartSideDivider) {
        this.mDrawStartSideDivider = drawStartSideDivider;
        return this;
    }

    public GridLayoutDividerDecoration drawEndSideDivider(boolean drawEndSideDivider) {
        this.mDrawEndSideDivider = drawEndSideDivider;
        return this;
    }

    public GridLayoutDividerDecoration setDividerColor(@ColorInt int dividerColor) {
        this.mDividerColor = dividerColor;
        return this;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        mPaint.setColor(mDividerColor);

        draw(c, parent);
    }

    // draw.

    private void draw(Canvas canvas, RecyclerView parent) {
        int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();

            //画水平分隔线
            int left = child.getLeft();
            int right = child.getRight();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mDividerWidth;
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
            //画垂直分割线
            top = child.getTop();
            bottom = child.getBottom() + mDividerWidth;
            left = child.getRight() + layoutParams.rightMargin;
            right = left + mSideDividerWidth;
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // 在notifyDataSetChanged()之后并不能马上获取Adapter中的position, 要等布局结束之后才能获取到.
        // 而对于getChildLayoutPosition(), 在notifyItemInserted()之后, Layout不能马上获取到新的position
        // ,因为布局还没更新(需要<16ms的时间刷新视图), 所以只能获取到旧的, 但是Adapter中的position就可以马上获取到最新的position.
        final int itemPosition = parent.getChildLayoutPosition(view);

        final int spanCount = getSpanCount(parent);
        final int childCount = state.getItemCount();

        // 每个item分配到的offset总量.
        int dividerCount = spanCount - 1;
        if (mDrawStartSideDivider) dividerCount++;
        if (mDrawEndSideDivider) dividerCount++;

        int eachItemOffsetWidth = dividerCount * mSideDividerWidth / spanCount;

        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;

        if (mOrientation == GridLayoutManager.VERTICAL) {
            if (isFirstRaw(parent, itemPosition, spanCount)) {
                if(mDrawTopSideDivider) {
                    top = mDividerWidth;
                }
                if (isFirstColumn(parent, itemPosition, spanCount)) {
                    if(mDrawStartSideDivider) {
                        left = mSideDividerWidth;
                    }
                    right = eachItemOffsetWidth - left;
                    bottom = mDividerWidth;
                } else if(isLastColumn(parent, itemPosition, spanCount, childCount)) {

                } else {

                }
            }

            outRect.set(left, top, right, bottom);
        }

        int dl = mSideDividerWidth - eachItemOffsetWidth;

        left = itemPosition % spanCount * dl;
        right = eachItemOffsetWidth - left;
        // noinspection SuspiciousNameCombination
        bottom = mDividerWidth;
        if (isLastRaw(parent, itemPosition, spanCount, childCount)) {
            bottom = 0;
        }
        if(mOrientation == GridLayoutManager.VERTICAL) {
            outRect.set(left, 0, right, bottom);
        } else {
            outRect.set(left, 0, right, bottom);
        }
    }

    // utils.

    /**
     * 判断是否是第一行.
     */
    private boolean isFirstRaw(RecyclerView parent, int pos, int spanCount){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
            if (mOrientation == GridLayoutManager.VERTICAL) {
                if(pos < spanCount){
                    return true;
                }
            }else{
                if(pos % spanCount == 0){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断是否是第一列.
     */
    private boolean isFirstColumn(RecyclerView parent, int pos, int spanCount){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
            if (mOrientation == GridLayoutManager.VERTICAL) {
                if(pos % spanCount == 0){
                    return true;
                }
            }else{
                if(pos < spanCount){
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * 判断是否是最后一列.
     */
    private boolean isLastColumn(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 是否是最后一行.
     */
    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount) {
                    return true;
                }
            } else {
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    private int getSpanCount(RecyclerView parent) {
        int spanCount = 1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }

        return spanCount;
    }
}