package com.moon.mui.tab.common;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Date: 9/18/21 11:28 AM
 * Author: Moon
 * Desc: 通用Tab容器接口
 */
public interface IMTabLayout<Tab extends ViewGroup, D> {

    Tab findTab(@NonNull D data);

    void addTabSelectedChangeListener(OnTabSelectedListener<D> listener);

    void defaultSelected(@NonNull D defaultInfo);

    void inflateInfo(@NonNull List<D> infoList);

    interface OnTabSelectedListener<D> {
        void onTabSelectedChange(int index, @Nullable D preInfo, @NonNull D nextInfo);
    }
}
