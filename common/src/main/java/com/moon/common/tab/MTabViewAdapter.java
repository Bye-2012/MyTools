package com.moon.common.tab;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.moon.mui.tab.bottom.MTabBottomInfo;

import java.util.List;

/**
 * Date: 9/29/21 4:52 PM
 * Author: Moon
 * Desc: Tab适配器
 */
public class MTabViewAdapter {

    private final List<MTabBottomInfo<?>> mInfoList;
    private final FragmentManager mFragmentManager;
    private Fragment mCurFragment;

    public MTabViewAdapter(FragmentManager fragmentManager, List<MTabBottomInfo<?>> infoList) {
        this.mFragmentManager = fragmentManager;
        this.mInfoList = infoList;
    }

    public void instantiateItem(View container, int position) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mCurFragment != null) {
            transaction.hide(mCurFragment);
        }
        String name = container.getId() + ":" + position;
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            transaction.show(fragment);
        } else {
            fragment = getItem(position);
            if (!fragment.isAdded()) {
                transaction.add(container.getId(), fragment, name);
            }
        }
        mCurFragment = fragment;
        transaction.commitNowAllowingStateLoss();
    }

    private Fragment getItem(int position) {
        try {
            return (Fragment) mInfoList.get(position).fragment.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Fragment getCurrentFragment() {
        return mCurFragment;
    }

    public int getCount() {
        return mInfoList == null ? 0 : mInfoList.size();
    }
}
