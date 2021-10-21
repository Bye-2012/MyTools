package com.moon.mytools.demo;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.moon.common.ui.component.MBaseActivity;
import com.moon.mui.banner.MBanner;
import com.moon.mui.banner.core.IBindAdapter;
import com.moon.mui.banner.core.MBannerAdapter;
import com.moon.mui.banner.core.MBannerMo;
import com.moon.mui.banner.indicator.MCircleIndicator;
import com.moon.mytools.R;

import java.util.ArrayList;

/**
 * Date: 2021/10/21 5:19 下午
 * Author: Moon
 * Desc:
 */
public class BannerDemoActivity extends MBaseActivity {

    private final String[] ss = new String[]{
            "https://www.devio.org/img/beauty_camera/beauty_camera1.jpg",
            "https://www.devio.org/img/beauty_camera/beauty_camera3.jpg",
            "https://www.devio.org/img/beauty_camera/beauty_camera4.jpg",
            "https://www.devio.org/img/beauty_camera/beauty_camera5.jpg",
            "https://www.devio.org/img/beauty_camera/beauty_camera2.jpg",
            "https://www.devio.org/img/beauty_camera/beauty_camera6.jpg",
            "https://www.devio.org/img/beauty_camera/beauty_camera7.jpg",
            "https://www.devio.org/img/beauty_camera/beauty_camera8.jpeg"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        MBanner banner = findViewById(R.id.banner);
        ArrayList<BannerMo> models = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            String s = ss[i % ss.length];
            BannerMo bannerMo = new BannerMo();
            bannerMo.url = s;
            models.add(bannerMo);
        }
        MCircleIndicator indicator = new MCircleIndicator(this);
        banner.setIndicator(indicator);
        banner.setAutoPlay(true);
        banner.setLoop(true);
        banner.setIntervalTime(3000);
        banner.setBannerData(R.layout.banner_item_layout, models);
        banner.setBindAdapter(new IBindAdapter() {
            @Override
            public void onBind(MBannerAdapter.MBannerViewHolder viewHolder, MBannerMo mo, int position) {
                ImageView iv = viewHolder.findViewById(R.id.iv_image);
                TextView tv = viewHolder.findViewById(R.id.tv_title);
                tv.setText(mo.url);
                Glide.with(BannerDemoActivity.this).load(mo.url).into(iv);
            }
        });
    }
}
