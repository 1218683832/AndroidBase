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

public class ViewBindEventImp implements AdapterInterface.ViewBindEvent{

    /**
     * <b>方法功能描述:</b><br>
     * Adapter中Item的点击事件回调接口
     */
    protected AdapterInterface.OnClickListener mOnClickListener;
    public void setOnClickListener(AdapterInterface.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    /**
     * <b>方法功能描述:</b><br>
     * Adapter中Item的触摸事件回调接口
     */
    protected AdapterInterface.OnTouchListener mOnTouchListener;
    public void setOnTouchListener(AdapterInterface.OnTouchListener onTouchListener) {
        this.mOnTouchListener = onTouchListener;
    }

    /**
     * <b>方法功能描述:</b><br>
     * Adapter中Item的长按事件回调接口
     */
    protected AdapterInterface.OnLongClickListener mOnLongClickListener;
    public void setOnLongClickListener(AdapterInterface.OnLongClickListener onLongClickListener) {
        this.mOnLongClickListener = onLongClickListener;
    }

    /**
     * <b>方法功能描述:</b><br>
     * Adapter中Item的拖拽事件回调接口
     */
    protected AdapterInterface.OnDragListener mOnDragListener;
    public void setOnDragListener(AdapterInterface.OnDragListener onDragListener) {
        this.mOnDragListener = onDragListener;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 为某个控件绑定点击事件
     *
     * @param view
     * @param position
     */
    @Override
    public void onBindClickListener(View view, final int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null){
                    mOnClickListener.onClick(v, position);
                }
            }
        });
    }

    /**
     *  <b>方法功能描述:</b><br>
     * 为某个控件绑定触摸事件
     *
     * @param view
     * @param position
     */
    @Override
    public void onBindTouchListener(View view, final int position) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (mOnTouchListener != null){
                    return mOnTouchListener.onTouch(view, event, position);
                }
                return false;
            }
        });
    }

    /**
     *  <b>方法功能描述:</b><br>
     * 为某个控件绑定长按事件
     *
     * @param view
     * @param position
     */
    @Override
    public void onBindLongClickListener(View view, final int position) {
        view.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                if (mOnLongClickListener != null){
                    return mOnLongClickListener.onLongClick(view, position);
                }
                return false;
            }
        });
    }

    /**
     *  <b>方法功能描述:</b><br>
     * 为某个控件绑定拖拽事件
     *
     * @param view
     * @param position
     */
    @Override
    public void onBindDragListener(View view, final int position) {
        view.setOnDragListener(new View.OnDragListener() {

            @Override
            public boolean onDrag(View view, DragEvent event) {
                if (mOnDragListener != null){
                    return mOnDragListener.onDrag(view, event, position);
                }
                return false;
            }
        });
    }
}
