package com.moon.mui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.moon.mlibrary.util.MDisplayUtil;

/**
 * Date: 2021/10/15 3:08 下午
 * Author: Moon
 * Desc: 下拉刷新的Overlay视图,可以重载这个类来定义自己的Overlay
 */
public abstract class MOverView extends FrameLayout {

    public enum MRefreshState {
        /**
         * 初始态
         */
        STATE_INIT,
        /**
         * Header展示的状态
         */
        STATE_VISIBLE,
        /**
         * 超出可刷新距离的状态
         */
        STATE_OVER,
        /**
         * 超出刷新位置松开手后的状态
         */
        STATE_OVER_RELEASE,
        /**
         * 刷新中的状态
         */
        STATE_REFRESH
    }

    protected MRefreshState mState = MRefreshState.STATE_INIT;

    /**
     * 触发下拉刷新 需要的最小高度
     */
    public int mPullRefreshHeight;
    /**
     * 最小阻尼
     */
    public float minDamp = 1.6f;
    /**
     * 最大阻尼
     */
    public float maxDamp = 2.2f;

    public MOverView(@NonNull Context context) {
        super(context);
        preInit();
    }

    public MOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        preInit();
    }

    public MOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        preInit();
    }

    private void preInit() {
        mPullRefreshHeight = MDisplayUtil.dp2px(66, getResources());
        init();
    }

    /**
     * 初始化
     */
    protected abstract void init();

    /**
     * 滑动中
     *
     * @param scrollY           滑动Y值
     * @param pullRefreshHeight 触发刷新高度
     */
    protected abstract void onScroll(int scrollY, int pullRefreshHeight);

    /**
     * 显示出来
     */
    protected abstract void onVisible();

    /**
     * 达到触发刷新状态
     */
    protected abstract void onOver();

    /**
     * 松手，开始刷新
     */
    protected abstract void onRefresh();

    /**
     * 刷新结束
     */
    protected abstract void onFinish();

    public MRefreshState getState() {
        return mState;
    }

    public void setState(MRefreshState state) {
        this.mState = state;
    }
}
