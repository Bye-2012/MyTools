package com.moon.mui.banner.core;

import android.content.Context;
import android.widget.Scroller;

/**
 * Date: 2021/10/21 2:07 下午
 * Author: Moon
 * Desc: Banner 滚动器
 */
public class MBannerScroller extends Scroller {

    /**
     * 值越大，滑动越慢
     */
    private int mDuration = 1000;

    MBannerScroller(Context context, int duration) {
        super(context);
        mDuration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
