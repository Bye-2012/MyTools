package com.moon.mui.refresh;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moon.mlibrary.log.MLog;

/**
 * Date: 2021/10/15 5:36 下午
 * Author: Moon
 * Desc: 滑动工具类
 */
public class MScrollUtil {

    /**
     * 查找可以滚动的child
     *
     * @return 可以滚动的child
     */
    public static View findScrollableChild(@NonNull ViewGroup viewGroup) {
        View child = viewGroup.getChildAt(1);
        if (child instanceof RecyclerView || child instanceof AdapterView) {
            return child;
        }
        if (child instanceof ViewGroup) {//往下多找一层
            View tempChild = ((ViewGroup) child).getChildAt(0);
            if (tempChild instanceof RecyclerView || tempChild instanceof AdapterView) {
                child = tempChild;
            }
        }
        return child;
    }

    /**
     * 判断child是否发生了滚动
     *
     * @param child
     * @return true 发生了滚动
     */
    public static boolean childScrolled(@NonNull View child) {
        if (child instanceof AdapterView) {
            AdapterView adapterView = (AdapterView) child;
            if (adapterView.getFirstVisiblePosition() != 0
                    || adapterView.getFirstVisiblePosition() == 0 && adapterView.getChildAt(0) != null
                    && adapterView.getChildAt(0).getTop() < 0) {
                return true;
            }
        } else if (child.getScrollY() > 0) {
            return true;
        }
        if (child instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) child;
            View view = recyclerView.getChildAt(0);
            int firstPosition = recyclerView.getChildAdapterPosition(view);
            MLog.d("----:top", view.getTop() + "");
            return firstPosition != 0 || view.getTop() != 0;
        }
        return false;
    }
}