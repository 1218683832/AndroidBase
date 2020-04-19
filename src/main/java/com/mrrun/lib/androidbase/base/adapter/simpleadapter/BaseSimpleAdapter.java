package com.mrrun.lib.androidbase.base.adapter.simpleadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrrun.lib.androidbase.base.adapter.AdapterInterface;
import com.mrrun.lib.androidbase.base.adapter.ViewBindEventImp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lipin on 2017/7/24.
 * <b>类功能描述:</b><br>
 * ListView、GridView等通用Adapter，目前支持单一布局
 *
 * @author lipin
 * @version 1.4
 */

public abstract class BaseSimpleAdapter<T, VH extends BaseSimpleAdapter.ViewHolder>
        extends android.widget.BaseAdapter {

    protected Context mContext;

    protected LayoutInflater mLayoutInflater;

    protected List<T> mDatas = new ArrayList<>();

    protected int mLayoutId;

    ViewBindEventImp mViewBindEventImp;

    public BaseSimpleAdapter(Context context, int layoutId) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mLayoutId = layoutId;
        this.mViewBindEventImp = new ViewBindEventImp();
    }

    /**
     * 向Adapter中添加一条数据
     *
     * @param data
     */
    public final void addItem(T data) {
        if (mDatas != null) {
            mDatas.add(data);
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * Adapter中Item的点击事件回调接口
     */
    public void setOnClickListener(AdapterInterface.OnClickListener onClickListener) {
        this.mViewBindEventImp.setOnClickListener(onClickListener);
    }

    /**
     * <b>方法功能描述:</b><br>
     * Adapter中Item的触摸事件回调接口
     */
    public void setOnTouchListener(AdapterInterface.OnTouchListener onTouchListener) {
        this.mViewBindEventImp.setOnTouchListener(onTouchListener);
    }

    /**
     * <b>方法功能描述:</b><br>
     * Adapter中Item的长按事件回调接口
     */
    public void setOnLongClickListener(AdapterInterface.OnLongClickListener onLongClickListener) {
        this.mViewBindEventImp.setOnLongClickListener(onLongClickListener);
    }

    /**
     * <b>方法功能描述:</b><br>
     * Adapter中Item的拖拽事件回调接口
     */
    public void setOnDragListener(AdapterInterface.OnDragListener onDragListener) {
        this.mViewBindEventImp.setOnDragListener(onDragListener);
    }

    @Override
    public final int getCount() {
        return (null != mDatas && mDatas.size() > 0) ? mDatas.size() : 0;
    }

    @Override
    public final T getItem(int position) {
        return (null != mDatas && mDatas.size() > 0 && position > -1) ? mDatas.get(position) : null;
    }

    @Override
    public final long getItemId(int position) {
        return position;
    }

    @Override
    public final int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public final int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        VH viewHolder = onCreateViewHolder(position, convertView, parent);
        onBindViewData(viewHolder, position);
        return viewHolder.getView();
    }

    /**
     * <b>方法功能描述:</b><br>
     * 对item中的view进行数据绑定操作,由使用者实现
     *
     * @param viewHolder
     * @param position
     */
    protected abstract void onBindViewData(VH viewHolder, int position);

    /**
     * <b>方法功能描述:</b><br>
     * 创建ViewHolder或获得可复用的ViewHolder
     *
     * @param position
     * @param convertView
     * @param parent
     *
     * @return VH
     */
    protected final VH onCreateViewHolder(int position, View convertView, ViewGroup parent){
        VH viewHolder = null;
        if (convertView == null) {
            convertView = this.mLayoutInflater.inflate(mLayoutId, parent, false);
            viewHolder = buildViewHolder(convertView, position);// 建造具体的ViewHolder
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (VH) convertView.getTag();
            viewHolder.position = position;// View复用的原因需要重新设置当前View的位置信息
        }
        return  viewHolder;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 建造具体的ViewHolder,使用者实现
     *
     * @param convertView
     * @param position
     *
     * @return VH
     */
    protected abstract VH buildViewHolder(View convertView, int position);

    /**
     * <b>方法功能描述:</b><br>
     * 为某个控件绑定点击事件
     *
     * @param view
     * @param position
     */
    public final void onBindClickListener(View view, int position) {
        mViewBindEventImp.onBindClickListener(view, position);
    }

    /**
     *  <b>方法功能描述:</b><br>
     * 为某个控件绑定触摸事件
     *
     * @param view
     * @param position
     */
    public final void onBindTouchListener(View view, final int position) {
        mViewBindEventImp.onBindTouchListener(view, position);
    }

    /**
     *  <b>方法功能描述:</b><br>
     * 为某个控件绑定长按事件
     *
     * @param view
     * @param position
     */
    public final void onBindLongClickListener(View view, final int position) {
        mViewBindEventImp.onBindLongClickListener(view, position);
    }

    /**
     *  <b>方法功能描述:</b><br>
     * 为某个控件绑定拖拽事件
     *
     * @param view
     * @param position
     */
    public final void onBindDragListener(View view, final int position) {
        mViewBindEventImp.onBindDragListener(view, position);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 获得Adapter里的数据源
     *
     * @return List<T>
     */
    public List<T> getDatas() {
        return this.mDatas;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 设置Adapter里的数据源
     *
     * @param datas
     */
    public void setDatas(List<T> datas) {
        this.mDatas = datas;
    }

    /**
     * b>类功能描述:</b><br>
     * ViewHolder
     */
    public class ViewHolder {

        protected View view;

        public int position;

        public ViewHolder(View view, int index) {
            this.view = view;
            this.position = index;
        }

        public View getView() {
            return view;
        }
    }
}
