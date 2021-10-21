package com.moon.mui.banner.core;

/**
 * Date: 2021/10/21 2:58 下午
 * Author: Moon
 * Desc: MBanner数据绑定接口，基于该接口可以实现数据的绑定和框架层解耦
 */
public interface IBindAdapter {
    void onBind(MBannerAdapter.MBannerViewHolder viewHolder, MBannerMo mo, int position);
}
