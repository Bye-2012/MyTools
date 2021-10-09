package com.moon.mui.tab.top;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

/**
 * Date: 9/18/21 11:48 AM
 * Author: Moon
 * Desc: 顶部Tab Info
 */
public class MTabTopInfo<Color> {

    public enum TabType {
        BITMAP, TEXT
    }

    public Class<? extends Fragment> fragment;
    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectedBitmap;
    public Color defaultColor;
    public Color tintColor;
    public TabType tabType;

    public MTabTopInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.tabType = TabType.BITMAP;
    }

    public MTabTopInfo(String name, Color defaultColor, Color tintColor) {
        this.name = name;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.TEXT;
    }
}
