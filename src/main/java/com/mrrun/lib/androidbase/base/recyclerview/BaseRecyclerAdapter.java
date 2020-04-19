package com.mrrun.lib.androidbase.base.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lipin on 2017/9/24.
 * RecyclerView通用Adapter，目前支持单一布局、多样布局
 * @param <T>
 *
 * @author lipin
 * @version 1.2
 */

public abstract class BaseRecyclerAdapter<T extends BaseRecyclerAdapter.IMultiItem> extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder> {

    /**
     * <b>属性功能描述:</b><br>
     * 上下文{@link Context}抑或Activity对象实例。<br>
     */
    @NonNull
    protected Context mContext;

    /**
     * <b>属性功能描述:</b><br>
     * 实体对象集合。<br>
     */
    protected List<T> mDatas;

    /**
     * <b>属性功能描述:</b><br>
     * 布局填充器{@link LayoutInflater}。<br>
     */
    @NonNull
    protected LayoutInflater mInflater;

    public BaseRecyclerAdapter(Context context, List<T> datas) {
        super();
        this.mContext = context;
        this.mDatas = datas;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutRes(position);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 得到实体对象集合中的对象数量
     *
     * @return <li>list size<li>0
     */
    @Override
    public final long getItemId(int position) {
        return position;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 返回布局layout,同时它在 BaseAdapter也作为ViewType来使用，所以如果是不同类型的item,不建议共用同个布局文件。 把
     * getLayoutRes跟 dataProcess交给
     * IMultiItem处理的好处就是实现多布局列表变得很简单，数据各自对应自己的布局文件，自己在 dataProcess方法中处理数据
     *
     * @param position
     * @return
     */
    public final int getLayoutRes(int position) {
        if (null == mDatas) {
            return -1;
        }
        T data = (T) mDatas.get(position);
        return data == null ? -1 : data.getLayoutRes();
    }

    @Override
    public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = null;
        // 这种加载方式Item宽高失效:View view = this.mInflater.inflate(viewType, null);
        // 这种方式加载布局item不会出现宽高失效
        View view = this.mInflater.inflate(viewType, parent, false);
        viewHolder = new BaseViewHolder(view);
        return viewHolder;
    }

    @Override
    public final void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mDatas != null && mDatas.size() > 0) {
            T data = (T) mDatas.get(position);
             if (null != data) {
                data.dataProcess(this, holder);
            }
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * 得到实体对象集合中的对象数量
     *
     * @return <li>list size<li>0
     */
    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
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
     * <b>类功能描述:</b><br>
     * 通用ViewHolder封装类
     *
     * @author lipin
     * @version 1.0
     */
    public static class BaseViewHolder extends RecyclerView.ViewHolder{

        /**
         * <b>属性功能描述:</b><br>
         * 复用的View
         */
        private View mConvertView;

        /**
         * <b>属性功能描述:</b><br>
         * 所有控件集合
         */
        private SparseArray<View> mViews;

        public BaseViewHolder(View itemView) {
            super(itemView);
            this.mViews = new SparseArray<View>();
            mConvertView = itemView;
        }

        /**
         * <b>方法功能描述:</b><br>
         * 获取 convertView
         *
         * @return mConvertView
         */
        public View getConvertView() {
            return mConvertView;
        }

        /**
         * <b>方法功能描述:</b><br>
         * 通过 viewId获取某个控件
         *
         * @param viewId
         *            控件id
         * @param <T>
         *            View子类
         * @return 返回 View
         */
        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (null == view) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

	/*
	 * 设置控件以及监听（采用链式编程方法）
	 */
        /**
         * <b>方法功能描述:</b><br>
         * 根据viewId设置某个{@link View}及子类控件tag
         *
         * @param viewId
         * @param tag
         * @return {@link BaseViewHolder}
         */
        public BaseViewHolder setTag(int viewId, Object tag) {
            View view = getView(viewId);
            if (null != view) {
                view.setTag(tag);
            }
            return this;
        }

        /**
         * <b>方法功能描述:</b><br>
         * 根据viewId设置某个{@link View}及子类控件tag
         *
         * @param viewId
         * @param key
         * @param tag
         * @return {@link BaseViewHolder}
         */
        public BaseViewHolder setTag(int viewId, int key, Object tag) {
            View view = getView(viewId);
            if (null != view) {
                view.setTag(key, tag);
            }
            return this;
        }

        /**
         * <b>方法功能描述:</b><br>
         * 根据viewId设置某个{@link View}及子类控件是否可见
         *
         * @param viewId
         * @param visible
         * @return {@link BaseViewHolder}
         */
        public BaseViewHolder setVisible(int viewId, boolean visible) {
            View view = getView(viewId);
            if (null != view) {
                view.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
            return this;
        }

        /**
         * <b>方法功能描述:</b><br>
         * 根据viewId设置某个{@link View}及子类控件是否可点击 Checkable
         *
         * @param viewId
         * @param checked
         * @return {@link BaseViewHolder}
         */
        public BaseViewHolder setChecked(int viewId, boolean checked) {
            Checkable view = (Checkable) getView(viewId);
            if (null != view) {
                view.setChecked(checked);
            }
            return this;
        }

        /**
         * <b>方法功能描述:</b><br>
         * 根据viewId设置某个{@link TextView}及子类控件的文本内容
         *
         * @param viewId
         * @param text
         * @return {@link BaseViewHolder}
         */
        public BaseViewHolder setText(int viewId, String text) {
            TextView view = getView(viewId);
            if (null != view) {
                view.setText(text);
            }
            return this;
        }

        /**
         * <b>方法功能描述:</b><br>
         * 根据viewId设置某个{@link Button}及子类控件的文本内容
         *
         * @param viewId
         * @param text
         * @return {@link BaseViewHolder}
         */
        public BaseViewHolder setButtonText(int viewId, String text) {
            Button view = getView(viewId);
            if (null != view) {
                view.setText(text);
            }
            return this;
        }

        /**
         * <b>方法功能描述:</b><br>
         * 根据viewId设置某个{@link ImageView}及子类控件的图片资源
         *
         * @param viewId
         * @param resId
         * @return {@link BaseViewHolder}
         */
        public BaseViewHolder setImageResource(int viewId, int resId) {
            ImageView view = getView(viewId);
            if (null != view) {
                view.setImageResource(resId);
            }
            return this;
        }

        /**
         * <b>方法功能描述:</b><br>
         * 根据viewId设置某个{@link View}及子类控件的点击事件.<br>
         * <b>注意：</b><br>
         * 由于加了点击事件，运行起来点击事件无效果，并不是因为代码有问题，而是焦点抢占原因，因此需要在布局文件 中设置对应控件是否可点击
         *
         * @param viewId
         * @param listener
         * @return {@link BaseViewHolder}
         */
        public BaseViewHolder setOnClickListener(int viewId,
                                                 View.OnClickListener listener) {
            View view = getView(viewId);
            if (null != view) {
                view.setOnClickListener(listener);
            }
            return this;
        }

        /**
         * <b>方法功能描述:</b><br>
         * 根据viewId设置某个{@link View}及子类控件的触摸事件
         *
         * @param viewId
         * @param listener
         * @return {@link BaseViewHolder}
         */
        public BaseViewHolder setOnTouchListener(int viewId,
                                                 View.OnTouchListener listener) {
            View view = getView(viewId);
            if (null != view) {
                view.setOnTouchListener(listener);
            }
            return this;
        }

        /**
         * <b>方法功能描述:</b><br>
         * 根据viewId设置某个{@link View}及子类控件的长按事件
         *
         * @param viewId
         * @param listener
         * @return {@link BaseViewHolder}
         */
        public BaseViewHolder setOnLongClickListener(int viewId,
                                                     View.OnLongClickListener listener) {
            View view = getView(viewId);
            if (null != view) {
                view.setOnLongClickListener(listener);
            }
            return this;
        }
    }

    /**
     * Item数据接口
     *
     * @author lipin
     * @version 1.0
     */
    public interface IMultiItem {
        /**
         * 不同类型的item请使用不同的布局文件， 即使它们的布局是一样的，也要copy多一份出来。
         *
         * @return 返回item对应的布局id
         */
        int getLayoutRes();

        /**
         * 数据处理，显示文本，图片等内容
         *
         * @param adapter
         * @param holder
         */
        void dataProcess(BaseRecyclerAdapter adapter, BaseRecyclerAdapter.BaseViewHolder holder);
    }
}
