package com.mrrun.lib.androidbase.base.adapter.simpleadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by lipin on 2018/1/12.
 * 对于我们的Fragment比较多的情况，我们需要切换的时候销毁以前的Fragment以释放内存，就可以使用FragmentStatePagerAdapter。
 * 区别：我们切换不同的Fragment的时候，我们会把前面的Fragment销毁，而我们系统在销毁前，会把我们的我们Fragment的Bundle在我们的onSaveInstanceState(Bundle)
 * 保存下来。等用户切换回来的时候，我们的Fragment就会根据我们的instance state恢复出来。
 */

public class BaseFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    protected ArrayList<Fragment> mFragments;

    public BaseFragmentStatePagerAdapter(FragmentManager fm) {
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