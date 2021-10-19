package com.moon.mytools.web;

import android.content.Context;
import android.content.res.Resources;

import com.moon.mytools.R;

/**
 * Date: 2021/10/19 11:44 上午
 * Author: Moon
 * Desc: 广告过滤
 */
public class AdFilterTool {

    public static boolean hasAd(Context context, String url) {
        Resources res = context.getResources();
        String[] adUrls = res.getStringArray(R.array.ad_url);
        for (String adUrl : adUrls) {
            if (url.contains(adUrl)) {
                return true;
            }
        }
        return false;
    }
}
