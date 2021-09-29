package com.moon.mui.tab.common;

import androidx.annotation.NonNull;

/**
 * Date: 9/18/21 11:45 AM
 * Author: Moon
 * Desc: 通用Tab接口
 */
public interface IMTab<D> extends IMTabLayout.OnTabSelectedListener<D> {

    void setMTabInfo(@NonNull D data);

    /**
     * 动态修改某个Item的高都
     */
    void resetMTabHeight(int height);
}
