package com.moon.mui.banner.core;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.moon.mui.banner.indicator.MIndicator;

import java.util.List;

/**
 * Date: 2021/10/21 2:54 下午
 * Author: Moon
 * Desc: MBanner 接口
 */
public interface IMBanner {

    void setBannerData(@LayoutRes int layoutResId, @NonNull List<? extends MBannerMo> models);

    void setBannerData(@NonNull List<? extends MBannerMo> models);

    void setIndicator(MIndicator<?> indicator);

    void setAutoPlay(boolean autoPlay);

    void setLoop(boolean loop);

    void setIntervalTime(int intervalTime);

    void setBindAdapter(IBindAdapter bindAdapter);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    void setOnBannerClickListener(OnBannerClickListener onBannerClickListener);

    void setScrollDuration(int duration);

    interface OnBannerClickListener {
        void onBannerClick(@NonNull MBannerAdapter.MBannerViewHolder viewHolder, @NonNull MBannerMo bannerMo, int position);
    }
}
