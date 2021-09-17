package com.moon.mlibrary.util;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Date: 9/16/21 2:07 PM
 * Author: Moon
 * Desc: 显示工具列
 */
public class MDisplayUtil {

    public static int dp2px(float dp, Resources resources) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    public static int dp2px(float dp) {
        Resources resources = AppGlobals.INSTANCE.get().getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    public static int sp2px(float dp) {
        Resources resources = AppGlobals.INSTANCE.get().getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, resources.getDisplayMetrics());
    }
}
