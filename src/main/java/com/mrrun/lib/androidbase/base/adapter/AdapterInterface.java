package com.mrrun.lib.androidbase.base.adapter;

import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lipin on 2017/9/29.
 *
 * @author lipin
 * @version 1.2
 */

public interface AdapterInterface {

    /**
     * <b>方法功能描述:</b><br>
     *  Adapter中View绑定事件回调接口
     */
    public interface ViewBindEvent {

        void onBindClickListener(View view, int position);

        void onBindTouchListener(View view, int position);

        void onBindLongClickListener(View view, int position);

        void onBindDragListener(View view, int position);
    }

    /**
     * <b>方法功能描述:</b><br>
     * Adapter中Item的点击事件回调接口
     */
    public interface OnClickListener {
        void onClick(View view, int position);
    }

    /**
     * <b>方法功能描述:</b><br>
     * Adapter中Item的触摸事件回调接口
     */
    public interface OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent, int position);
    }

    /**
     * <b>方法功能描述:</b><br>
     * Adapter中Item的长按事件回调接口
     */
    public interface OnLongClickListener {
        public boolean onLongClick(View view, int position);
    }

    /**
     * <b>方法功能描述:</b><br>
     * Adapter中Item的拖拽事件回调接口
     */
    public interface OnDragListener {
        public boolean onDrag(View view, DragEvent dragEvent, int position);
    }
}
