package com.mrrun.lib.androidbase.base.manager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by lipin on 2017/8/25.
 * b>类功能描述:</b><br>
 * Fragment管理使用类
 * 1、可以用于管理Fragment界面切换的多种组合操作，每种操作实现一次界面的操作;
 * 2、可以仅用于创建、管理Fragment.
 *
 * @author lipin
 * @version 1.2
 */
public abstract class BaseFragmentManager {

    private static final String TAG = BaseFragmentManager.class.getSimpleName();

    protected FragmentActivity mActivity;

    protected FragmentManager mFragmentManager;

    protected Fragment[] mFragments;

    /**
     * <b>方法功能描述:</b><br>
     * 用于创建、管理Fragment.
     *
     * @param fragmentActivity
     */
    public BaseFragmentManager(FragmentActivity fragmentActivity) {
        if (fragmentActivity == null) {
            throw new NullPointerException("fragmentActivity arg is null!");
        }
        mActivity = fragmentActivity;
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    /**
     * <b>方法功能描述:</b><br>
     * 用于管理Fragment界面切换的多种组合操作，每种操作实现一次界面的操作
     *
     * @param fragmentActivity
     * @param fragmentCount
     */
    public BaseFragmentManager(FragmentActivity fragmentActivity, int fragmentCount) {
        if (fragmentActivity == null) {
            throw new NullPointerException("fragmentActivity arg is null!");
        }
        if (fragmentCount <= 0) {
            throw new IllegalArgumentException("fragmentCount arg can't less than or equal to 0!");
        }
        mActivity = fragmentActivity;
        mFragmentManager = mActivity.getSupportFragmentManager();
        mFragments = new Fragment[fragmentCount];
    }

    /**
     * <b>方法功能描述:</b><br>
     * 根据id返回对应的Fragment
     *
     * @param id
     * @return fragment
     */
    public final Fragment findFragmentById(int id) {
        Fragment fragment =  mFragmentManager.findFragmentById(id);
        return fragment;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 根据tag返回对应的Fragment
     *
     * @param tag
     * @return fragment
     */
    public final Fragment findFragmentByTag(String tag) {
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        return fragment;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 获得Fragment回退栈顶部的Fragment
     *
     * @return fragment
     */
    public final Fragment getTopFragment() {
        Fragment fragment = null;
        if (isHasFragment()) {
            int topIndex = mFragmentManager.getBackStackEntryCount() - 1;
            String tag = mFragmentManager.getBackStackEntryAt(topIndex).getName();
            fragment = findFragmentByTag(tag);
        }
        return fragment;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 判断Fragment回退栈里是否有Fragment
     *
     * @return boolean
     */
    public final boolean isHasFragment() {
        boolean hasFragment = false;
        if (mFragmentManager.getBackStackEntryCount() != 0) {
            hasFragment = true;
        }
        return hasFragment;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 获得相关联的FragmentManager
     *
     * @return fragmentManager
     */
    public android.support.v4.app.FragmentManager getFragmentManager() {
        return mFragmentManager;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 将Fragment实例放入Fragment集合指定位置
     *
     * @param index
     * @param fragment
     */
    public final void put(int index, Fragment fragment) {
        if (mFragments == null) {
            throw new NullPointerException("You didn't initialize Fragment[], so mFragments is null!" +
                    "If you don't want to use put(int, Fragment) then you have to use setFragments(Fragment[]) first!");
        }
        if (index < 0 || index > mFragments.length - 1) {
            throw new ArrayIndexOutOfBoundsException("index arg is out of bound of array!");
        }
        mFragments[index] = fragment;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 取出指定位置的Fragment实例
     *
     * @param index
     * @return fragment
     */
    public final Fragment get(int index) {
        if (mFragments == null) {
            throw new NullPointerException("You didn't initialize Fragment[], so mFragments is null!" +
                    "If you don't want to use get(int) then you have to use setFragments(Fragment[]) first!");
        }
        if (index < 0 || index > mFragments.length - 1) {
            throw new ArrayIndexOutOfBoundsException("index arg is out of bound of array!");
        }
        return mFragments[index];
    }

    public Fragment[] getFragments() {
        return mFragments;
    }

    public void setFragments(Fragment[] fragments) {
        this.mFragments = fragments;
    }

    // 界面切换的多种组合操作，每种操作实现一次界面的操作
    // add和replace操作不要同时使用
    //==========================不使用回退栈的操作==========================
    public final void addFragmentNoBackStack(Fragment fragment, int layoutId, String fgTag) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(layoutId, fragment, fgTag);
        transaction.commit();
    }
    public final void removeFragmentNoBackStack(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }
    public final void replaceFragmentNoBackStack(Fragment fragment, int layoutId, String fgTag) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(layoutId, fragment, fgTag);
        transaction.commit();
    }
    public final void hide(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.hide(fragment);
            transaction.commit();
        }
    }
    public final void hide(int index) {
        if (mFragments == null) {
            throw new NullPointerException("You didn't initialize Fragment[], so mFragments is null!" +
                    "If you don't want to use hide(int) then you have to use setFragments(Fragment[]) first!");
        }
        if (index < 0 || index > mFragments.length - 1) {
            throw new ArrayIndexOutOfBoundsException("index arg is out of bound of array!");
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.hide(mFragments[index]);
        transaction.commit();
    }
    public final void show(Fragment fragment) {
        if (fragment != null && fragment.isHidden()) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.show(fragment);
            transaction.commit();
        }
    }
    public final void show(int index) {
        if (mFragments == null) {
            throw new NullPointerException("You didn't initialize Fragment[], so mFragments is null!" +
                    "If you don't want to use show(int) then you have to use setFragments(Fragment[]) first!");
        }
        if (index < 0 || index > mFragments.length - 1) {
            throw new ArrayIndexOutOfBoundsException("index arg is out of bound of array!");
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.show(mFragments[index]);
        transaction.commit();
    }
    public final void detach(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.detach(fragment);
        transaction.commit();
    }
    public final void attach(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.attach(fragment);
        transaction.commit();
    }
}
