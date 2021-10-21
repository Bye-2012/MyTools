package com.moon.mui.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.moon.mui.R;
import com.moon.mui.banner.core.IBindAdapter;
import com.moon.mui.banner.core.IMBanner;
import com.moon.mui.banner.core.MBannerDelegate;
import com.moon.mui.banner.core.MBannerMo;
import com.moon.mui.banner.indicator.MIndicator;

import java.util.List;

/**
 * Date: 2021/10/21 2:14 下午
 * Author: Moon
 * <p>
 * 核心问题：
 * 1. 如何实现UI的高度定制？
 * 2. 作为有限的item如何实现无线轮播呢？
 * 3. Banner需要展示网络图片，如何将网络图片库和Banner组件进行解耦？
 * 4. 指示器样式各异，如何实现指示器的高度定制？
 * 5. 如何设置ViewPager的滚动速度？
 */
public class MBanner extends FrameLayout implements IMBanner {

    private final MBannerDelegate mBannerDelegate;

    public MBanner(@NonNull Context context) {
        this(context, null);
    }

    public MBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBannerDelegate = new MBannerDelegate(context, this);
        initCustomAttr(context, attrs);
    }

    private void initCustomAttr(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MBanner);
            boolean autoPlay = typedArray.getBoolean(R.styleable.MBanner_autoPlay, true);
            boolean loop = typedArray.getBoolean(R.styleable.MBanner_loop, true);
            int intervalTime = typedArray.getInteger(R.styleable.MBanner_intervalTime, -1);
            setAutoPlay(autoPlay);
            setLoop(loop);
            setIntervalTime(intervalTime);
            typedArray.recycle();
        }
    }

    @Override
    public void setBannerData(int layoutResId, @NonNull List<? extends MBannerMo> models) {
        mBannerDelegate.setBannerData(layoutResId, models);
    }

    @Override
    public void setBannerData(@NonNull List<? extends MBannerMo> models) {
        mBannerDelegate.setBannerData(models);
    }

    @Override
    public void setIndicator(MIndicator<?> indicator) {
        mBannerDelegate.setIndicator(indicator);
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        mBannerDelegate.setAutoPlay(autoPlay);
    }

    @Override
    public void setLoop(boolean loop) {
        mBannerDelegate.setLoop(loop);
    }

    @Override
    public void setIntervalTime(int intervalTime) {
        mBannerDelegate.setIntervalTime(intervalTime);
    }

    @Override
    public void setBindAdapter(IBindAdapter bindAdapter) {
        mBannerDelegate.setBindAdapter(bindAdapter);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mBannerDelegate.setOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        mBannerDelegate.setOnBannerClickListener(onBannerClickListener);
    }

    @Override
    public void setScrollDuration(int duration) {
        mBannerDelegate.setScrollDuration(duration);
    }
}
