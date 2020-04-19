package com.mrrun.lib.androidbase.base.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lipin on 2017/8/30.
 *
 */

/**
 * 当我们调用mRecyclerView.addItemDecoration()方法添加decoration的时候，RecyclerView在绘制的时候，
 * 去会绘制decorator，即调用该类的onDraw和onDrawOver方法。
 * onDraw方法先于drawChildren;
 * onDrawOver在drawChildren之后，一般我们选择复写其中一个即可;
 * getItemOffsets 可以通过outRect.set()为每个Item设置一定的偏移量，主要用于绘制Decorator。
 */
public class LinearDividerItemDecoration extends RecyclerView.ItemDecoration {

    private Context mContext;
    // 布局方向,默认垂直布局
    private int mOrientation = OrientationHelper.VERTICAL;
    // 自定义分割线颜色
    private int mDividerColor = Color.BLACK;
    // 自定义分割线Drawable
    private Drawable mDividerDrawable;
    // 分割线高或宽大小
    private float mDividerSize = 0f;
    // 分割线画笔
    private Paint mPaint;

    public LinearDividerItemDecoration(Context context, int mOrientation, float dividerSize, int divicerColor, Drawable dividerDrawable) {
        this.mContext = context;
        this.mOrientation = mOrientation;
        this.mDividerSize = dividerSize;
        this.mDividerColor = divicerColor;
        this.mDividerDrawable = dividerDrawable;
        init();
    }

    private void init() {
        initPaint();
    }

    private void initPaint() {
        // 如果没有设置Drawable则绘制纯色分割线
        if (this.mDividerDrawable == null) {
            // 初始化画笔(抗锯齿)并设置画笔颜色和画笔样式为填充
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mDividerColor);
        } else {// 绘制Drawable分割线
            if (mDividerSize == 0f) {// 如果没有指定分割线的size，则默认是Drawable的厚度
                if (mOrientation == OrientationHelper.VERTICAL) {
                    mDividerSize = mDividerDrawable.getIntrinsicHeight();
                } else if (mOrientation == OrientationHelper.VERTICAL){
                    mDividerSize = mDividerDrawable.getIntrinsicWidth();
                }
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == OrientationHelper.VERTICAL) {
            // 画横线
            drawHorientationDivider(c, parent, state);
        } else if (mOrientation == OrientationHelper.HORIZONTAL){
            // 画竖线
            drawVerticalDivider(c, parent, state);
        }
    }

    /**
     * 画竖线
     *
     * @param c
     * @param parent
     * @param state
     */
    private void drawVerticalDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        // c.drawRect(left, top, right, bottom, mPaint);X轴为手机顶部，y轴为手机左边，各个参数的意思就是到这两边的距离。
        // 得到分割线的四个点：左、上、右、下的距离
        // 画竖线时上下可以根据parent得到上、下的距离
        int top = parent.getPaddingTop();
        int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        // 左右需要根据每个孩子控件计算
        for (int i = 0; i <= parent.getChildCount() - 1; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int right = (int) (left + mDividerSize);
            // 得到四个点后开始画
            if (mDividerDrawable == null) {
                c.drawRect(left, top, right, bottom, mPaint);
            } else {
                mDividerDrawable.setBounds(left, top, right, bottom);
                mDividerDrawable.draw(c);
            }
        }
    }

    /**
     * 画横线
     *
     * @param c
     * @param parent
     * @param state
     */
    private void drawHorientationDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        // c.drawRect(left, top, right, bottom, mPaint);X轴为手机顶部，y轴为手机左边，各个参数的意思就是到这两边的距离。
        // 得到分割线的四个点：左、上、右、下的距离
        // 画横线时左右可以根据parent得到左、右的距离
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        // 上下需要根据每个孩子控件计算
        for (int i = 0; i <= parent.getChildCount() - 1; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = (int) (top + mDividerSize);
            // 得到四个点后开始画
            if (mDividerDrawable == null) {
                c.drawRect(left, top, right, bottom, mPaint);
            } else {
                mDividerDrawable.setBounds(left, top, right, bottom);
                mDividerDrawable.draw(c);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (isLastItem(view, parent)){// 最后一个Item不要分割线
            return;
        }
        if (mOrientation == OrientationHelper.VERTICAL) {
            outRect.set(0, 0, 0, (int) mDividerSize);
        } else {
            outRect.set(0, 0, (int) mDividerSize, 0);
        }
    }

    /**
     * 判断是否是RecyclerView中的最后一个Item
     *
     * @param view
     * @param parent
     * @return boolean
     */
    private boolean isLastItem(View view, RecyclerView parent) {
        int itemPosition = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();
        if (itemPosition >= itemCount - 1) {
            return true;
        }
        return false;
    }

    /**
     * 设置分割线方向
     */
    public void setOrientation(int orientation){
        if (orientation != OrientationHelper.VERTICAL && orientation != OrientationHelper.HORIZONTAL){
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }
}
