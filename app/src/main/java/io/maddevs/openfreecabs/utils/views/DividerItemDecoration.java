package io.maddevs.openfreecabs.utils.views;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    Drawable divider;
    int orientation;

    public DividerItemDecoration(Drawable divider) {
        this.divider = divider;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) > 0 || parent.getChildCount() == 1) {
            orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.top = divider.getIntrinsicHeight();
            } else if (orientation == LinearLayoutManager.HORIZONTAL) {
                outRect.left = divider.getIntrinsicWidth();
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (orientation == LinearLayoutManager.VERTICAL) {
            drawHorizontalDividers(c, parent);
        } else if (orientation == LinearLayoutManager.HORIZONTAL) {
            drawVerticalDividers(c, parent);
        }
    }

    private void drawHorizontalDividers(Canvas canvas, RecyclerView parent) {
        int parentLeft = parent.getPaddingLeft();
        int parentRight = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int parentTop = child.getBottom() + params.bottomMargin;
            int parentBottom = parentTop + divider.getIntrinsicHeight();

            divider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
            divider.draw(canvas);
        }
    }

    private void drawVerticalDividers(Canvas canvas, RecyclerView parent) {
        int parentTop = parent.getPaddingTop();
        int parentBottom = parent.getHeight() - parent.getPaddingBottom();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int parentLeft = child.getRight() + params.rightMargin;
            int parentRight = parentLeft + divider.getIntrinsicWidth();

            divider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
            divider.draw(canvas);
        }
    }
}
