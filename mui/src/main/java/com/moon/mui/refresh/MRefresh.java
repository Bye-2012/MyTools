package com.moon.mui.refresh;

/**
 * Date: 10/13/21 2:37 PM
 * Author: Moon
 * Desc: 下拉刷新接口
 */
public interface MRefresh {

    /**
     * 刷新完成
     */
    void refreshFinished();

    /**
     * 设置下拉刷新的视图
     *
     * @param view 刷新的视图
     */
    void setRefreshOverView(MOverView view);

    /**
     * 是否禁止滚动
     *
     * @param disableRefreshScroll 是否禁止滚动
     */
    void setDisableRefreshScroll(boolean disableRefreshScroll);

    /**
     * 设置刷新监听
     *
     * @param listener 刷新监听
     */
    void setRefreshListener(MRefreshListener listener);

    interface MRefreshListener {

        void onRefresh();

        boolean enableRefresh();
    }
}
