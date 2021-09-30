package com.moon.mytools.logic;

import android.content.res.Resources;
import android.view.View;
import android.view.Window;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

import com.moon.common.tab.MFragmentTabView;
import com.moon.common.tab.MTabViewAdapter;
import com.moon.mui.tab.bottom.MTabBottomInfo;
import com.moon.mui.tab.bottom.MTabBottomLayout;
import com.moon.mytools.R;
import com.moon.mytools.fragment.CategoryFragment;
import com.moon.mytools.fragment.FavoriteFragment;
import com.moon.mytools.fragment.HomePageFragment;
import com.moon.mytools.fragment.ProfileFragment;
import com.moon.mytools.fragment.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 9/30/21 2:21 PM
 * Author: Moon
 * Desc: 主页逻辑操作类
 */
public class MainActivityLogic {

    private final ActivityProvider mActivityProvider;
    private MTabBottomLayout mTabBottomLayout;
    private MFragmentTabView mFragmentTabView;
    private ArrayList<MTabBottomInfo<?>> mInfoList;
    private int currentItemIndex;

    public MainActivityLogic(ActivityProvider activityProvider) {
        this.mActivityProvider = activityProvider;

        initTabBottom();
    }

    public MFragmentTabView getFragmentTabView() {
        return mFragmentTabView;
    }

    public List<MTabBottomInfo<?>> getInfoList() {
        return mInfoList;
    }

    public MTabBottomLayout getHiTabBottomLayout() {
        return mTabBottomLayout;
    }

    private void initTabBottom() {
        mTabBottomLayout = ((MTabBottomLayout) mActivityProvider.findViewById(R.id.tab_bottom_layout));

        mTabBottomLayout.setAlpha(0.8f);

        mInfoList = new ArrayList<>();
        int defaultColor = mActivityProvider.getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = mActivityProvider.getResources().getColor(R.color.tabBottomTintColor);
        MTabBottomInfo<Integer> homeInfo = new MTabBottomInfo<>(
                "首页",
                "fonts/iconfont.ttf",
                mActivityProvider.getString(R.string.if_home),
                null,
                defaultColor,
                tintColor
        );
        homeInfo.fragment = HomePageFragment.class;
        MTabBottomInfo<Integer> infoFavorite = new MTabBottomInfo<>(
                "收藏",
                "fonts/iconfont.ttf",
                mActivityProvider.getString(R.string.if_favorite),
                null,
                defaultColor,
                tintColor
        );
        infoFavorite.fragment = FavoriteFragment.class;
        MTabBottomInfo<Integer> infoCategory = new MTabBottomInfo<>(
                "分类",
                "fonts/iconfont.ttf",
                mActivityProvider.getString(R.string.if_category),
                null,
                defaultColor,
                tintColor
        );
        infoCategory.fragment = CategoryFragment.class;
        MTabBottomInfo<Integer> infoRecommend = new MTabBottomInfo<>(
                "推荐",
                "fonts/iconfont.ttf",
                mActivityProvider.getString(R.string.if_recommend),
                null,
                defaultColor,
                tintColor
        );
        infoRecommend.fragment = RecommendFragment.class;
        MTabBottomInfo<Integer> infoProfile = new MTabBottomInfo<>(
                "我的",
                "fonts/iconfont.ttf",
                mActivityProvider.getString(R.string.if_profile),
                null,
                defaultColor,
                tintColor
        );
        infoProfile.fragment = ProfileFragment.class;
        mInfoList.add(homeInfo);
        mInfoList.add(infoFavorite);
        mInfoList.add(infoCategory);
        mInfoList.add(infoRecommend);
        mInfoList.add(infoProfile);
        mTabBottomLayout.inflateInfo(mInfoList);

        initFragmentTabView();

        mTabBottomLayout.addTabSelectedChangeListener((index, prevInfo, nextInfo) -> {
            mFragmentTabView.setCurrentItem(index);
            MainActivityLogic.this.currentItemIndex = index;
        });
        mTabBottomLayout.defaultSelected(mInfoList.get(currentItemIndex));
    }

    private void initFragmentTabView() {
        MTabViewAdapter tabViewAdapter = new MTabViewAdapter(mActivityProvider.getSupportFragmentManager(), mInfoList);
        mFragmentTabView = ((MFragmentTabView) mActivityProvider.findViewById(R.id.fragment_tab_view));
        mFragmentTabView.setAdapter(tabViewAdapter);
    }

    public interface ActivityProvider {

        <T extends View> T findViewById(@IdRes int id);

        Resources getResources();

        FragmentManager getSupportFragmentManager();

        String getString(@StringRes int resId);

        Window getWindow();
    }
}
