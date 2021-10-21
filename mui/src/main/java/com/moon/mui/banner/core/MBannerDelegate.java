package com.moon.mui.banner.core;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.moon.mui.R;
import com.moon.mui.banner.MBanner;
import com.moon.mui.banner.indicator.MCircleIndicator;
import com.moon.mui.banner.indicator.MIndicator;

import java.util.List;

/**
 * Date: 2021/10/21 3:00 下午
 * Author: Moon
 * Desc: Banner代理类
 */
public class MBannerDelegate implements IMBanner, ViewPager.OnPageChangeListener {

    private final Context mContext;
    private final MBanner mBanner;
    private MBannerAdapter mAdapter;
    private MIndicator<?> mMIndicator;
    private boolean mAutoPlay;
    private boolean mLoop;
    private List<? extends MBannerMo> mMBannerMos;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private int mIntervalTime = 5000;
    private MBanner.OnBannerClickListener mOnBannerClickListener;
    private MViewPager mMViewPager;
    private int mScrollDuration = -1;

    public MBannerDelegate(Context context, MBanner mbanner) {
        this.mContext = context;
        this.mBanner = mbanner;
    }

    @Override
    public void setBannerData(int layoutResId, @NonNull List<? extends MBannerMo> models) {
        this.mMBannerMos = models;
        init(layoutResId);
    }

    @Override
    public void setBannerData(@NonNull List<? extends MBannerMo> models) {
        setBannerData(R.layout.banner_default_layout, models);
    }

    private void init(@LayoutRes int layoutResId) {
        if (mAdapter == null) {
            mAdapter = new MBannerAdapter(mContext);
        }
        if (mMIndicator == null) {
            mMIndicator = new MCircleIndicator(mContext);
        }
        mMIndicator.onInflate(mMBannerMos.size());

        mAdapter.setLayoutResId(layoutResId);
        mAdapter.setBannerData(mMBannerMos);
        mAdapter.setAutoPlay(mAutoPlay);
        mAdapter.setLoop(mLoop);
        mAdapter.setBannerClickListener(mOnBannerClickListener);

        mMViewPager = new MViewPager(mContext);
        mMViewPager.setIntervalTime(mIntervalTime);
        mMViewPager.addOnPageChangeListener(this);
        mMViewPager.setAutoPlay(mAutoPlay);
        if (mScrollDuration > 0) mMViewPager.setScrollDuration(mScrollDuration);
        mMViewPager.setAdapter(mAdapter);

        if ((mLoop || mAutoPlay) && mAdapter.getRealCount() != 0) {
            //无限轮播关键点：使第一张能反向滑动到最后一张，已达到无限滚动的效果
            int firstItem = mAdapter.getFirstItem();
            mMViewPager.setCurrentItem(firstItem, false);
        }

        //清除缓存view
        mBanner.removeAllViews();
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mBanner.addView(mMViewPager, layoutParams);
        mBanner.addView(mMIndicator.get(), layoutParams);
    }

    @Override
    public void setIndicator(MIndicator<?> indicator) {
        this.mMIndicator = indicator;
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
        if (mAdapter != null) mAdapter.setAutoPlay(autoPlay);
        if (mMViewPager != null) mMViewPager.setAutoPlay(autoPlay);
    }

    @Override
    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    @Override
    public void setIntervalTime(int intervalTime) {
        if (intervalTime > 0) {
            this.mIntervalTime = intervalTime;
        }
    }

    @Override
    public void setBindAdapter(IBindAdapter bindAdapter) {
        mAdapter.setBindAdapter(bindAdapter);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.mOnBannerClickListener = onBannerClickListener;
    }

    @Override
    public void setScrollDuration(int duration) {
        this.mScrollDuration = duration;
        if (mMViewPager != null && duration > 0) {
            mMViewPager.setScrollDuration(duration);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (null != mOnPageChangeListener && mAdapter.getRealCount() != 0) {
            mOnPageChangeListener.onPageScrolled(position % mAdapter.getRealCount(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mAdapter.getRealCount() == 0) {
            return;
        }
        position = position % mAdapter.getRealCount();
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
        if (mMIndicator != null) {
            mMIndicator.onPointChange(position, mAdapter.getRealCount());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }
}
