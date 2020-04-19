package com.mrrun.lib.androidbase.base.adapter.simpleadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by lipin on 2018/1/12.
 * FragmentPagerAdapter适用于Fragment比较少的情况，因为我们会把每一个Fragment保存在内存中，不用每次切换的时候，去保存现场，切换回来在重新创建，所以用户体验比较好。
 * 区别：Fragment在切换的时候，不会销毁，而只是调用事务中的detach方法，这种方法我们只会把我们的Fragment的view销毁，
 * 而保留了以前的Fragment对象。所以通过这种方式创建的Fragment一直不会被销毁。
 */

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter{

    protected ArrayList<Fragment> mFragments;

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragments.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return (mFragments == null || mFragments.size() == 0) ? 0 : mFragments.size();
    }

    public void addFragment(Fragment fragment){
        if (mFragments != null) {
            mFragments.add(fragment);
        }
        notifyDataSetChanged();
    }
}
