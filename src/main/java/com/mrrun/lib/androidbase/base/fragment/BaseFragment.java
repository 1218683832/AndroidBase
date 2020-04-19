package com.mrrun.lib.androidbase.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lipin on 2017/8/28.
 * <b>类功能描述:</b><br>
 * 继承自{@link Fragment}<br>
 * 你的Fragment需继承本类<br>
 * 该类中定义一些常用的方法你可以很轻易的使用它.<br>
 * <li>{@link #init()}</li>
 * <li>{@link #setupViewAndData()}</li>
 * <li>{@link #setupViewOnClick(View)}</li>
 * <li>{@link #setupViewOnFocusChange(View, boolean)}</li>
 * <li>{@link #setupViewOnTouch(View, MotionEvent)}</li>
 * <li>{@link #jumpToActivity(Class)}</li>
 * <li>{@link #jumpToActivity(Intent)}</li>
 * <li>{@link #layoutId()}</li>
 *
 * @author lipin
 * @version 1.1
 */

public abstract class BaseFragment extends Fragment {

    /**
     * <b>属性功能描述:</b><br>
     * 上下文{@link Context}抑或Activity对象实例。<br>
     * 直接使用即可
     */
    @NonNull
    protected Context mContext;

    /**
     * b>属性功能描述:</b><br>
     * {@link Unbinder}对象实例<br>
     * 用于解绑ButterKnife
     */
    protected Unbinder  mUnbinder;
    /**
     * Fragment根View
     */
    protected View mRootView;

    /**
     * <b>方法功能描述:</b><br>
     *  子类不再需要设置布局ID，也不再需要使用ButterKnife.bind();
     * <ol>
     * <li>加载布局视图{@link #init()} 在setContentView()调用之前调用，用来设置
     * (如：状态栏、ActionBar、导航栏样式、动态权限等);</li>
     * <li>绑定控件{@link #bindButterKnife()}</li>
     * <li>初始化数据{@link #setupViewAndData()}</li>
     * </ol>
     * <b>以上方法不需要手动加入</b><br>
     * <br>
     * <b>特别的:</b><br>
     * 请实现{@link #layoutId()}以便{@link Fragment}加载布局视图.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     *
     * @see ButterKnife
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = layoutId();
        if (layoutId == 0) {
            throw new IllegalArgumentException(
                    "layoutId() returned 0, which is not allowed. "
                            + "If you don't want to use layoutId() but implement your own view for this "
                            + "fragment manually, then you have to override onCreateView();");
        } else {
            init();
            mRootView = inflater.inflate(layoutId, container, false);
            this.mContext = this.getContext();
            bindButterKnife();
            setupViewAndData();
            return mRootView;
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * <ol>
     * <li>解绑控件{@link #unbindButterKnife()}</li>
     * </ol>
     * <b>以上方法不需要手动加入</b><br>
     * <br>
     * <b>特别说明：</b><br>
     * 建议super.onDestroy();放在最后调用
     *
     * @see ButterKnife
     */
    @Override
    public void onDestroy() {
        unbindButterKnife();
        super.onDestroy();
    }

    /**
     * <b>方法功能描述:</b><br>
     * 初始化数据和View，具体可以根据项目需求进行定制， 你需要实现该方法
     *
     * @return 无返回值
     */
    protected abstract void setupViewAndData();

    /**
     * <b>方法功能描述:</b><br>
     * 在setContentView()调用之前调用，可以设置沉浸式状态栏、ActionBar、动态权限申请等
     *
     * @return 无返回值
     */
    protected void init() {}

    /**
     * <b>方法功能描述:</b><br>
     * final方法<br>
     * 使用ButterKnife插件,View以及事件绑定
     *
     * @return 无返回值
     *
     * @see ButterKnife
     */
    private void bindButterKnife() {
        mUnbinder = ButterKnife.bind(this, mRootView);
    }

    /**
     * <b>方法功能描述:</b><br>
     * final方法<br>
     * 使用ButterKnife插件, View以及事件解绑
     *
     * @return 无返回值
     *
     * @see ButterKnife
     */
    private void unbindButterKnife() {
        if (null != mUnbinder) {
            mUnbinder.unbind();
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * 当前界面的布局文件id(由子类实现)
     *
     * @return int
     */
    @LayoutRes
    protected abstract int layoutId();

    /**
     * <b>方法功能描述:</b><br>
     *  跳转到Activity
     *
     * @param intent
     */
    public final void jumpToActivity(Intent intent) {
        startActivity(intent);
    }

    /**
     * <b>方法功能描述:</b><br>
     *  跳转到Activity
     *
     * @param intent
     * @param options
     */
    public final void jumpToActivity(Intent intent, @Nullable Bundle options) {
        startActivity(intent, options);
    }

    /**
     * <b>方法功能描述:</b><br>
     *  跳转到Activity
     *
     * @param cls
     */
    public final void jumpToActivity(Class cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }

    /**
     * <b>方法功能描述:</b><br>
     *  跳转到Activity
     *
     * @param cls
     * @param options
     */
    public final void jumpToActivity(Class cls, @Nullable Bundle options) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent, options);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 在此方法中写入View焦点变化事件<br>
     * 在使用此方法之前需加入@{@link butterknife.OnClick}
     *
     * <pre>
     * &#064;OnFocusChange({R.id.tv_top_bg})
     * public void setupViewOnFocusChange(View view, boolean hasFocus){
     * switch (view.getId()) {
     * case R.id.tv_top_bg:
     * closeMusicList();// 关闭列表
     * break;
     * default:
     * break;
     * }
     * </pre>
     *
     * @param view
     * @param hasFocus
     */
    public void setupViewOnFocusChange(View view, boolean hasFocus){}

    /**
     * <b>方法功能描述:</b><br>
     * 在此方法中写入View点击事件<br>
     * 在使用此方法之前需加入@{@link butterknife.OnClick}
     *
     * <pre>
     * &#064;OnTouch({R.id.tv_top_bg})
     * public void setupViewOnTouch(View view, MotionEvent event){
     * switch (view.getId()) {
     * case R.id.tv_top_bg:
     * closeMusicList();// 关闭列表
     * break;
     * default:
     * break;
     * }
     * </pre>
     *
     * @param view
     * @param event
     * @return 无返回值
     * @see ButterKnife
     */
    public boolean setupViewOnTouch(View view, MotionEvent event){ return false;}

    /**
     * <b>方法功能描述:</b><br>
     * 在此方法中写入View点击事件<br>
     * 在使用此方法之前需加入@{@link butterknife.OnClick}
     *
     * <pre>
     * 一个例子:
     * &#064;OnClick({ R.id.bt_toastutils, R.id.bt_colorutils })
     * public void setupViewOnClick(View v) {
     * switch (v.getId()) {
     * case R.id.bt_toastutils:
     * ToastUtils.showToast(this, &quot;Toast工具类&quot;);
     * break;
     * }
     * }
     * </pre>
     *
     * @param view
     *            视图对象
     * @return 无返回值
     *
     * @see ButterKnife
     */
    public void setupViewOnClick(View view){}
}
