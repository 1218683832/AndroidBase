package com.mrrun.lib.androidbase.widget.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by lipin on 2017/9/6.
 * <b>类功能描述:</b><br>
 * 1、ViewPager可设置滚动速度
 *
 * @author lipin
 * @version 1.0
 */

public class CustomViewPager extends android.support.v4.view.ViewPager {

    private Context mContext;

    private ViewPagerScroller mViewPagerScroller;

    public CustomViewPager(Context context) {
        super(context);
        this.mContext = context;
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }


    public void setScrollSpeed(int duration) {
        if (mViewPagerScroller == null) {
            mViewPagerScroller = new ViewPagerScroller(this.mContext);
            mViewPagerScroller.initViewPagerScroll(this);
            mViewPagerScroller.setScrollDuration(duration);
        } else {
            mViewPagerScroller.setScrollDuration(duration);
        }
    }

    /**
     * Created by lipin on 2017/9/6.
     * <b>类功能描述:</b><br>
     * ViewPager滚动速度设置
     *
     * @author lipin
     * @version 1.0
     */
    private static class ViewPagerScroller extends Scroller {

        private int mScrollDuration = 2000; // 滑动速度

        public ViewPagerScroller(Context context) {
            super(context);
        }

        public void initViewPagerScroll(CustomViewPager viewPager) {
            try {
                Field mScroller = CustomViewPager.class.getDeclaredField("mScroller");
                mScroller.setAccessible(true);
                mScroller.set(viewPager, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }

        /**
         * 设置滚动速度
         *
         * @param duration
         */
        public void setScrollDuration(int duration) {
            this.mScrollDuration = duration;
        }
    }
}
