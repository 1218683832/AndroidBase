package com.mrrun.lib.androidbase.base.adapter.simpleadapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrrun.lib.androidbase.base.adapter.AdapterInterface;
import com.mrrun.lib.androidbase.base.adapter.ViewBindEventImp;

import java.util.List;

/**
 * Created by lipin on 2017/9/22.
 * PagerAdapter不能直接使用像ListView那样的ViewHolder，来实现View的复用,
 * 可以根据PagerAdapter的特性进行改造。
 *
 * @author lipin
 * @version 1.2
 */

public abstract class BaseSimplePagerAdapter<T, VH extends BaseSimplePagerAdapter.ViewHolder>
        extends PagerAdapter
        implements AdapterInterface.ViewBindEvent{

    protected Context mContext;

    protected LayoutInflater mLayoutInflater;

    protected List<T> mDatas;

    protected int mLayoutId;

    ViewBindEventImp mViewBindEventImp;

    public BaseSimplePagerAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        this.mDatas = datas;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mLayoutId = layoutId;
        this.mViewBindEventImp = new ViewBindEventImp();
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
    public final Object instantiateItem(ViewGroup parent, int position) {
        VH viewHolder = onCreateViewHolder(parent, position);
        onBindViewData(viewHolder, position);
        return viewHolder.getView();
    }

    protected final VH onCreateViewHolder(ViewGroup parent, int position){
        VH viewHolder = null;
        View convertView = this.mLayoutInflater.inflate(mLayoutId, null);
        viewHolder = buildViewHolder(convertView, position);// 建造具体的ViewHolder
        convertView.setTag(viewHolder);
        viewHolder.position = position;// View复用的原因需要重新设置当前View的位置信息
        parent.addView(convertView);
        return  viewHolder;
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
     * 建造具体的ViewHolder,使用者实现
     *
     * @param convertView
     * @param position
     *
     * @return VH
     */
    protected abstract VH buildViewHolder(View convertView, int position);

    @Override
    public final int getCount() {
        return (null != mDatas && mDatas.size() > 0) ? mDatas.size() : 0;
    }

    @Override
    public final boolean isViewFromObject(View view, Object object) {
        return  view == object;
    }

    @Override
    public final void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

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
    public static abstract class ViewHolder {

        public View itemView;

        public int position;

        public ViewHolder(View itemView, int index) {
            this.itemView = itemView;
            this.position = index;
        }

        public View getView() {
            return itemView;
        }
    }
}
