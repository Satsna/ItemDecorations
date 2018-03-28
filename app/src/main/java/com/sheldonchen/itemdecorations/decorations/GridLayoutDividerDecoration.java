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
 * 适用于RecyclerView网格布局以及瀑布流布局下的ItemDecoration
 * 完美支持网格布局设置了SpanSizeLookup的情况，目前不支持瀑布流布局设置了FullSpan的情况
 * Created by cxd on 2018/3/7
 */

public class GridLayoutDividerDecoration extends RecyclerView.ItemDecoration {

    /**
     * RecyclerView布局方向.
     */
    @DecorationOrientType private int mOrientation = GridLayoutManager.VERTICAL;

    /**
     * 列表滚动方向上分割线的宽度.
     */
    private int mDividerWidth = 10;

    /**
     * 侧边分割线的宽度.
     */
    private int mSideDividerWidth = 10;

    /**
     * 是否画顶部分割线.
     */
    private boolean mDrawTopSideDivider = false;

    /**
     * 是否画底部分割线.
     */
    private boolean mDrawBottomSideDivider = false;

    /**
     * 是否画侧边分割线.
     */
    private boolean mDrawSideDivider = false;

    /**
     * 分割线颜色.
     */
    @ColorInt private int mDividerColor = Color.TRANSPARENT;

    /**
     * 设置是否画分割线的颜色.
     */
    private boolean mDrawDividerColor = false;

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public GridLayoutDividerDecoration() {
    }

    public GridLayoutDividerDecoration(@DecorationOrientType int mOrientation) {
        this.mOrientation = mOrientation;
    }

    // set methods.

    public GridLayoutDividerDecoration setOrientation(@DecorationOrientType int orientation) {
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

    public GridLayoutDividerDecoration drawSideDivider(boolean drawSideDivider) {
        this.mDrawSideDivider = drawSideDivider;
        return this;
    }

    public GridLayoutDividerDecoration setDividerColor(@ColorInt int dividerColor) {
        this.mDrawDividerColor = true;
        this.mDividerColor = dividerColor;
        return this;
    }

    public GridLayoutDividerDecoration setDrawDividerColor(boolean drawDividerColor) {
        this.mDrawDividerColor = drawDividerColor;
        return this;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        if(!mDrawDividerColor) return;

        mPaint.setColor(mDividerColor);

        if (mOrientation == GridLayoutManager.VERTICAL) {
            drawVertical(canvas, parent);
        } else {
            drawHorizontal(canvas, parent);
        }
    }

    // draw.

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();

            final boolean isFirstColumn = isFirstColumn(parent, i, spanCount);

            // 画水平分隔线.
            int sideOffset = 0;
            if(mDrawSideDivider) sideOffset = mSideDividerWidth;
            int left = child.getLeft();
            if(isFirstColumn) {
                left = left - sideOffset;
            }
            int right = child.getRight() + sideOffset;
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mDividerWidth;
            if(!isLastRaw(parent, i , spanCount, childSize) || mDrawBottomSideDivider) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
            if(isFirstRaw(parent, i, spanCount) && mDrawTopSideDivider) {
                bottom = child.getTop() - layoutParams.topMargin;
                top = bottom - mDividerWidth;
                canvas.drawRect(left, top, right, bottom, mPaint);
            }

            // 画竖直分隔线.
            top = child.getTop();
            bottom = child.getBottom();
            left = child.getRight() + layoutParams.rightMargin;
            right = left + mSideDividerWidth;
            if(!isLastColumn(parent, i, spanCount, childSize) || mDrawSideDivider) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
            if(isFirstColumn && mDrawSideDivider) {
                right = child.getLeft() - layoutParams.leftMargin;
                left = right - mSideDividerWidth;
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();

            final boolean isFirstRaw = isFirstRaw(parent, i, spanCount);
            // 画竖直分隔线.
            int sideOffset = 0;
            if(mDrawSideDivider) sideOffset = mSideDividerWidth;
            int top = child.getTop();
            if(isFirstRaw) {
                top = top - sideOffset;
            }
            int bottom = child.getBottom() + sideOffset;
            int left = child.getRight() + layoutParams.rightMargin;
            int right = left + mDividerWidth;
            if(!isLastColumn(parent, i, spanCount, childSize) || mDrawBottomSideDivider) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
            if(isFirstColumn(parent, i, spanCount) && mDrawTopSideDivider) {
                right = child.getLeft() - layoutParams.leftMargin;
                left = right - mDividerWidth;
                canvas.drawRect(left, top, right, bottom, mPaint);
            }

            // 画水平分隔线.
            left = child.getLeft();
            right = child.getRight();
            top = child.getBottom() + layoutParams.bottomMargin;
            bottom = top + mSideDividerWidth;
            if(!isLastRaw(parent, i, spanCount, childSize) || mDrawSideDivider) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
            if(isFirstRaw && mDrawSideDivider) {
                bottom = child.getTop() - layoutParams.topMargin;
                top = bottom - mSideDividerWidth;
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    @Override @SuppressWarnings("SuspiciousNameCombination")
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // 在notifyDataSetChanged()之后并不能马上获取Adapter中的position, 要等布局结束之后才能获取到.
        // 而对于getChildLayoutPosition(), 在notifyItemInserted()之后, Layout不能马上获取到新的position
        // ,因为布局还没更新(需要<16ms的时间刷新视图), 所以只能获取到旧的, 但是Adapter中的position就可以马上获取到最新的position.
        final int itemPosition = parent.getChildLayoutPosition(view);

        final int spanCount = getSpanCount(parent);
        final int childCount = state.getItemCount();

        // 每个item分配到的offset总量.
        int dividerCount = spanCount - 1;
        if (mDrawSideDivider) dividerCount = dividerCount + 2;

        // 确保每个item分配到的offset总量相等，原理是一个等差数列.
        int eachItemOffsetWidth = dividerCount * mSideDividerWidth / spanCount;
        int dc = eachItemOffsetWidth - mSideDividerWidth;

        int left = 0;
        int top = 0;
        int right;
        int bottom;

        if (mOrientation == GridLayoutManager.VERTICAL) {
            if (isFirstRaw(parent, itemPosition, spanCount) && mDrawTopSideDivider) {
                top = mDividerWidth;
            }
            bottom = mDividerWidth;
            if (isLastRaw(parent, itemPosition, spanCount, childCount) && !mDrawBottomSideDivider) {
                bottom = 0;
            }

            int a1 = 0;
            if(mDrawSideDivider) a1 = mSideDividerWidth;

            int spanIndex = getSpanIndex(parent, itemPosition, spanCount);
            int spanLastIndex = spanIndex + getSpanSize(parent, itemPosition) - 1;
            left = a1 - dc * spanIndex;
            right = eachItemOffsetWidth - a1 + dc * spanLastIndex;
        } else {
            if(isFirstColumn(parent, itemPosition, spanCount) && mDrawTopSideDivider) {
                left = mDividerWidth;
            }
            right = mDividerWidth;
            if(isLastColumn(parent, itemPosition, spanCount, childCount) && !mDrawBottomSideDivider) {
                right = 0;
            }

            int a1 = 0;
            if(mDrawSideDivider) a1 = mSideDividerWidth;

            int spanIndex = getSpanIndex(parent, itemPosition, spanCount);
            int spanLastIndex = spanIndex + getSpanSize(parent, itemPosition) - 1;
            top = a1 - dc * spanIndex;
            bottom = eachItemOffsetWidth - a1 + dc * spanLastIndex;
        }

        outRect.set(left, top, right, bottom);
    }

    private int getSpanIndex(RecyclerView parent, int pos, int spanCount) {
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            GridLayoutManager.SpanSizeLookup spanSizeLookup =
                    ((GridLayoutManager) manager).getSpanSizeLookup();
            return spanSizeLookup.getSpanIndex(pos, spanCount);
        } else {
            return pos % spanCount;
        }
    }

    private int getSpanSize(RecyclerView parent, int pos) {
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            GridLayoutManager.SpanSizeLookup spanSizeLookup =
                    ((GridLayoutManager) manager).getSpanSizeLookup();
            return spanSizeLookup.getSpanSize(pos);
        }
        return 1;
    }

    // utils.

    /**
     * 判断是否是第一行.
     */
    private boolean isFirstRaw(RecyclerView parent, int pos, int spanCount){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            if(mOrientation == GridLayoutManager.VERTICAL
                    && pos < getGridFirstDividerOffset(parent, gridLayoutManager)) {
                return true;
            } else if(mOrientation == GridLayoutManager.HORIZONTAL
                    && isFirstRowInHorizontalGridLayout(gridLayoutManager, pos)) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            if (mOrientation == GridLayoutManager.VERTICAL
                    && pos < spanCount) {
                return true;
            }else if(mOrientation == GridLayoutManager.HORIZONTAL
                    && pos % spanCount == 0){
                return true;
            }
        }

        return false;
    }

    private static int getGridFirstDividerOffset(RecyclerView parent, GridLayoutManager manager) {
        GridLayoutManager.SpanSizeLookup spanSizeLookup = manager.getSpanSizeLookup();
        spanSizeLookup.setSpanIndexCacheEnabled(true);

        int spanCount = manager.getSpanCount();
        int itemCount = parent.getAdapter().getItemCount();
        for (int i = 1; i < itemCount; i++) {
            if (spanSizeLookup.getSpanIndex(i, spanCount) == 0) {
                return i;
            }
        }

        return spanCount;
    }

    private static boolean isFirstRowInHorizontalGridLayout(GridLayoutManager manager, int pos) {
        GridLayoutManager.SpanSizeLookup spanSizeLookup = manager.getSpanSizeLookup();
        spanSizeLookup.setSpanIndexCacheEnabled(true);

        int spanCount = manager.getSpanCount();
        if(spanSizeLookup.getSpanIndex(pos, spanCount) == 0) {
            return true;
        }
        return false;
    }


    /**
     * 判断是否是第一列.
     */
    private boolean isFirstColumn(RecyclerView parent, int pos, int spanCount){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            if(mOrientation == GridLayoutManager.VERTICAL
                    && isFirstRowInHorizontalGridLayout(gridLayoutManager, pos)) {
                return true;
            } else if(mOrientation == GridLayoutManager.HORIZONTAL
                    && pos < getGridFirstDividerOffset(parent, gridLayoutManager)) {
                return true;
            }

        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            if (mOrientation == GridLayoutManager.VERTICAL
                    && pos % spanCount == 0) {
                return true;
            }else if(mOrientation == GridLayoutManager.HORIZONTAL
                    && pos < spanCount){
                return true;
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
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

            if(mOrientation == GridLayoutManager.VERTICAL) {
                if(pos == childCount - 1) {
                    return spanSizeLookup.getSpanSize(pos) == spanCount;
                } else if(spanSizeLookup.getSpanIndex(pos + 1, spanCount) == 0){
                    return true;
                }
            } else if(mOrientation == GridLayoutManager.HORIZONTAL
                    && pos >= getGridLastDividerOffset(parent, gridLayoutManager)){
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            if (mOrientation == StaggeredGridLayoutManager.VERTICAL
                    && (pos + 1) % spanCount == 0) {
                return true;
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount) {
                    return true;
                }
            }
        }

        return false;
    }

    private int getGridLastDividerOffset(RecyclerView parent, GridLayoutManager manager) {
        GridLayoutManager.SpanSizeLookup spanSizeLookup = manager.getSpanSizeLookup();

        int spanCount = manager.getSpanCount();
        int itemCount = parent.getAdapter().getItemCount();
        for (int i = itemCount - 1; i >= 0; i--) {
            if (spanSizeLookup.getSpanIndex(i, spanCount) == 0) {
                return i;
            }
        }

        return itemCount - 1;
    }

    /**
     * 是否是最后一行.
     */
    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

            if(mOrientation == GridLayoutManager.VERTICAL
                    && pos >= getGridLastDividerOffset(parent, gridLayoutManager)) {
                return true;
            } else if(mOrientation == GridLayoutManager.HORIZONTAL){
                if(pos == childCount - 1) {
                    return spanSizeLookup.getSpanSize(pos) == spanCount;
                } else if(spanSizeLookup.getSpanIndex(pos + 1, spanCount) == 0){
                    return true;
                }
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            if (mOrientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount) {
                    return true;
                }
            } else if(mOrientation == StaggeredGridLayoutManager.HORIZONTAL
                    && (pos + 1) % spanCount == 0){
                return true;
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