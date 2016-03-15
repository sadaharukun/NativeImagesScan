package com.example.yaoxinxin.imagescan.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yaoxinxin on 16/3/15.
 */
public class defaultItemDecoratoin extends RecyclerView.ItemDecoration {

    private static final String TAG = defaultItemDecoratoin.class.getSimpleName();

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;

    private static final int VERTICAL = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private RecyclerView mRecyclerView;

    private int mOrientation;


    public defaultItemDecoratoin(Context c, int orientation) {
        super();
        TypedArray a = c.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOritention(orientation);
    }

    private void setOritention(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException();
        } else {
            mOrientation = orientation;
        }
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDraw(c, parent, state);
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHoritation(c, parent);
        }
    }

    private void drawHoritation(Canvas c, RecyclerView parent) {

    }

    private void drawVertical(Canvas c, RecyclerView parent) {

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }
}
