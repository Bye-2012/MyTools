package com.moon.mui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Date: 2021/10/15 5:35 下午
 * Author: Moon
 * Desc: 下拉刷新控件
 */
public class MRefreshLayout extends FrameLayout implements MRefresh {

    private GestureDetector mGestureDetector;
    private MOverView.MRefreshState mState;
    private MRefresh.MRefreshListener mRefreshListener;
    private MOverView mOverView;
    private AutoScroller mAutoScroller;
    private int mLastY;
    private boolean disableRefreshScroll; //刷新时是否禁止滚动

    public MRefreshLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public MRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mGestureDetector = new GestureDetector(getContext(), mMGestureDetector);
        mAutoScroller = new AutoScroller();
    }

    @Override
    public void refreshFinished() {
        final View head = getChildAt(0);
        mOverView.onFinish();
        mOverView.setState(MOverView.MRefreshState.STATE_INIT);
        final int bottom = head.getBottom();
        if (bottom > 0) {
            recover(bottom);
        }
        mState = MOverView.MRefreshState.STATE_INIT;
    }

    @Override
    public void setRefreshOverView(MOverView view) {
        if (this.mOverView != null) {
            removeView(mOverView);
        }
        this.mOverView = view;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mOverView, 0, params);
    }

    @Override
    public void setDisableRefreshScroll(boolean disableRefreshScroll) {
        this.disableRefreshScroll = disableRefreshScroll;
    }

    @Override
    public void setRefreshListener(MRefreshListener listener) {
        this.mRefreshListener = listener;
    }

    /**
     * 恢复原位
     *
     * @param dis 距离
     */
    private void recover(int dis) {
        if (mRefreshListener != null && dis > mOverView.mPullRefreshHeight) {
            mAutoScroller.recover(dis - mOverView.mPullRefreshHeight);
            mState = MOverView.MRefreshState.STATE_OVER_RELEASE;
        } else {
            mAutoScroller.recover(dis);
        }
    }

    /**
     * 手势监听
     */
    private final MGestureDetector mMGestureDetector = new MGestureDetector() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float disX, float disY) {
            if (Math.abs(disX) > Math.abs(disY) || mRefreshListener != null && !mRefreshListener.enableRefresh()) {
                //横向滑动或刷新被禁止则不处理
                return false;
            }
            if (disableRefreshScroll && mState == MOverView.MRefreshState.STATE_REFRESH) {//刷新时是否禁止滑动
                return true;
            }
            View head = getChildAt(0);
            View child = MScrollUtil.findScrollableChild(MRefreshLayout.this);
            if (MScrollUtil.childScrolled(child)) {
                //如果列表发生了滚动则不处理
                return false;
            }
            //没有刷新或没有达到可以刷新的距离，且头部已经划出或下拉
            if ((mState != MOverView.MRefreshState.STATE_REFRESH || head.getBottom() <= mOverView.mPullRefreshHeight)
                    && (head.getBottom() > 0 || disY <= 0.0F)) {
                //还在滑动中
                if (mState != MOverView.MRefreshState.STATE_OVER_RELEASE) {
                    int speed;
                    //阻尼计算
                    if (child.getTop() < mOverView.mPullRefreshHeight) {
                        speed = (int) (mLastY / mOverView.minDamp);
                    } else {
                        speed = (int) (mLastY / mOverView.maxDamp);
                    }
                    //如果是正在刷新状态，则不允许在滑动的时候改变状态
                    boolean bool = moveDown(speed, true);
                    mLastY = (int) (-disY);
                    return bool;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    };

    /**
     * 根据偏移量移动header与child
     *
     * @param offsetY 偏移量
     * @param nonAuto 是否非自动滚动触发
     * @return
     */
    private boolean moveDown(int offsetY, boolean nonAuto) {
        View head = getChildAt(0);
        View child = getChildAt(1);
        int childTop = child.getTop() + offsetY;

        if (childTop <= 0) {//异常情况的补充
            offsetY = -child.getTop();
            //移动head与child的位置，到原始位置
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
            if (mState != MOverView.MRefreshState.STATE_REFRESH) {
                mState = MOverView.MRefreshState.STATE_INIT;
            }
        } else if (mState == MOverView.MRefreshState.STATE_REFRESH && childTop > mOverView.mPullRefreshHeight) {
            //如果正在下拉刷新中，禁止继续下拉
            return false;
        } else if (childTop <= mOverView.mPullRefreshHeight) {//还没超出设定的刷新距离
            if (mOverView.getState() != MOverView.MRefreshState.STATE_VISIBLE && nonAuto) {//头部开始显示
                mOverView.onVisible();
                mOverView.setState(MOverView.MRefreshState.STATE_VISIBLE);
                mState = MOverView.MRefreshState.STATE_VISIBLE;
            }
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
            if (childTop == mOverView.mPullRefreshHeight && mState == MOverView.MRefreshState.STATE_OVER_RELEASE) {
                refresh();
            }
        } else {
            if (mOverView.getState() != MOverView.MRefreshState.STATE_OVER && nonAuto) {
                //超出刷新位置
                mOverView.onOver();
                mOverView.setState(MOverView.MRefreshState.STATE_OVER);
            }
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
        }
        if (mOverView != null) {
            mOverView.onScroll(head.getBottom(), mOverView.mPullRefreshHeight);
        }
        return true;
    }

    /**
     * 刷新
     */
    private void refresh() {
        if (mRefreshListener != null) {
            mState = MOverView.MRefreshState.STATE_REFRESH;
            mOverView.onRefresh();
            mOverView.setState(MOverView.MRefreshState.STATE_REFRESH);
            mRefreshListener.onRefresh();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //事件分发处理
        if (!mAutoScroller.isFinished()) {
            return false;
        }

        View head = getChildAt(0);
        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL
                || ev.getAction() == MotionEvent.ACTION_POINTER_INDEX_MASK) { //松开手
            if (head.getBottom() > 0) {
                if (mState != MOverView.MRefreshState.STATE_REFRESH) { //非正在刷新
                    recover(head.getBottom());
                    return false;
                }
            }
            mLastY = 0;
        }
        boolean consumed = mGestureDetector.onTouchEvent(ev);
        if ((consumed || (mState != MOverView.MRefreshState.STATE_INIT && mState != MOverView.MRefreshState.STATE_REFRESH))
                && head.getBottom() != 0) {
            ev.setAction(MotionEvent.ACTION_CANCEL); //让父类接受不到真实的事件
            return super.dispatchTouchEvent(ev);
        }

        if (consumed) {
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //定义head和child的排列位置
        View head = getChildAt(0);
        View child = getChildAt(1);
        if (head != null && child != null) {
            int childTop = child.getTop();
            if (mState == MOverView.MRefreshState.STATE_REFRESH) {
                head.layout(0, mOverView.mPullRefreshHeight - head.getMeasuredHeight(), right, mOverView.mPullRefreshHeight);
                child.layout(0, mOverView.mPullRefreshHeight, right, mOverView.mPullRefreshHeight + child.getMeasuredHeight());
            } else {
                //left,top,right,bottom
                head.layout(0, childTop - head.getMeasuredHeight(), right, childTop);
                child.layout(0, childTop, right, childTop + child.getMeasuredHeight());
            }

            View other;
            //让HiRefreshLayout节点下两个以上的child能够不跟随手势移动以实现一些特殊效果，如悬浮的效果
            for (int i = 2; i < getChildCount(); ++i) {
                other = getChildAt(i);
                other.layout(0, top, right, bottom);
            }
        }
    }

    /**
     * 借助Scroller实现视图的自动滚动
     * https://juejin.im/post/5c7f4f0351882562ed516ab6
     */
    private class AutoScroller implements Runnable {
        private final Scroller mScroller;
        private int mLastY;
        private boolean mIsFinished;

        AutoScroller() {
            mScroller = new Scroller(getContext(), new LinearInterpolator());
            mIsFinished = true;
        }

        @Override
        public void run() {
            if (mScroller.computeScrollOffset()) {//还未滚动完成
                moveDown(mLastY - mScroller.getCurrY(), false);
                mLastY = mScroller.getCurrY();
                post(this);
            } else {
                removeCallbacks(this);
                mIsFinished = true;
            }
        }

        void recover(int dis) {
            if (dis <= 0) {
                return;
            }
            removeCallbacks(this);
            mLastY = 0;
            mIsFinished = false;
            mScroller.startScroll(0, 0, 0, dis, 300);
            post(this);
        }

        boolean isFinished() {
            return mIsFinished;
        }
    }
}
