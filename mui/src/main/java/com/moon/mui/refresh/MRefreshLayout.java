package com.moon.mui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Date: 2021/10/15 5:35 下午
 * Author: Moon
 * Desc: 下拉刷新控件
 */
public class MRefreshLayout extends FrameLayout implements MRefresh {

    public MRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public MRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void refreshFinished() {

    }

    @Override
    public void setRefreshOverView(MOverView view) {

    }

    @Override
    public void setDisableRefreshScroll(boolean disableRefreshScroll) {

    }

    @Override
    public void setRefreshListener(MRefreshListener listener) {

    }
}
