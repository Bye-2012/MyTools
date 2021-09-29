package com.moon.mui.tab.bottom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.moon.mlibrary.util.MDisplayUtil;
import com.moon.mlibrary.util.MViewUtil;
import com.moon.mui.R;
import com.moon.mui.tab.common.IMTabLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Date: 9/28/21 5:50 PM
 * Author: Moon
 * Desc: 底部Tab容器类
 * <p>
 * Tips:
 * 1. 透明度和底部透出，列表可渲染高度问题
 * 2. 中间高度超过，凸起布局
 */
public class MTabBottomLayout extends FrameLayout implements IMTabLayout<MTabBottom, MTabBottomInfo<?>> {

    private static final String TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM";

    private float bottomAlpha = 1f;
    //TabBottom高度
    private float tabBottomHeight = 50;
    //TabBottom的头部线条高度
    private float bottomLineHeight = 0.5f;
    //TabBottom的头部线条颜色
    private String bottomLineColor = "#dfe0e1";

    private final List<OnTabSelectedListener<MTabBottomInfo<?>>> tabSelectedChangeListeners = new ArrayList<>();
    private MTabBottomInfo<?> selectedInfo;
    private List<MTabBottomInfo<?>> infoList;

    public MTabBottomLayout(@NonNull Context context) {
        this(context, null);
    }

    public MTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTabAlpha(float alpha) {
        this.bottomAlpha = alpha;
    }

    public void setTabHeight(float tabHeight) {
        this.tabBottomHeight = tabHeight;
    }

    public void setBottomLineHeight(float bottomLineHeight) {
        this.bottomLineHeight = bottomLineHeight;
    }

    public void setBottomLineColor(String bottomLineColor) {
        this.bottomLineColor = bottomLineColor;
    }

    @Override
    public MTabBottom findTab(@NonNull MTabBottomInfo<?> data) {
        ViewGroup ll = findViewWithTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof MTabBottom) {
                MTabBottom tab = (MTabBottom) child;
                if (tab.getHiTabInfo() == data) {
                    return tab;
                }
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<MTabBottomInfo<?>> listener) {
        tabSelectedChangeListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NonNull MTabBottomInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    @Override
    public void inflateInfo(@NonNull List<MTabBottomInfo<?>> infoList) {
        if (infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;
        // 移除所有控件，防止多次inflate
        for (int i = getChildCount() - 1; i > 0; i--) {
            removeViewAt(i);
        }
        // 移除之前添加的TabBottom Listener Tips：Java foreach remove问题
        Iterator<OnTabSelectedListener<MTabBottomInfo<?>>> iterator = tabSelectedChangeListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof MTabBottom) {
                iterator.remove();
            }
        }
        selectedInfo = null;
        addBackground();
        int height = MDisplayUtil.dp2px(tabBottomHeight, getResources());
        FrameLayout ll = new FrameLayout(getContext()); //Tips：为何不用LinearLayout：当动态改变child大小后Gravity.BOTTOM会失效
        int width = MDisplayUtil.getDisplayWidthInPx(getContext()) / infoList.size();
        ll.setTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < infoList.size(); i++) {
            MTabBottomInfo<?> info = infoList.get(i);
            MTabBottom tabBottom = new MTabBottom(getContext());
            LayoutParams params = new LayoutParams(width, height);
            params.gravity = Gravity.BOTTOM;
            params.leftMargin = i * width;
            tabBottom.setLayoutParams(params);
            tabBottom.setMTabInfo(info);
            tabBottom.setOnClickListener(v -> onSelected(info));
            tabSelectedChangeListeners.add(tabBottom);
            ll.addView(tabBottom, params);
        }
        addBottomLine();
        LayoutParams flPrams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        flPrams.gravity = Gravity.BOTTOM;
        addView(ll, flPrams);
        // 修复底部Padding
        fixContentView();
    }

    private void onSelected(MTabBottomInfo<?> nextInfo) {
        for (OnTabSelectedListener<MTabBottomInfo<?>> listener : tabSelectedChangeListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        selectedInfo = nextInfo;
    }

    private void addBackground() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.m_bottom_layout_bg, null);
        LayoutParams params =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MDisplayUtil.dp2px(tabBottomHeight, getResources()));
        params.gravity = Gravity.BOTTOM;
        addView(view, params);
        view.setAlpha(bottomAlpha);
    }

    private void addBottomLine() {
        View bottomLine = new View(getContext());
        bottomLine.setBackgroundColor(Color.parseColor(bottomLineColor));
        LayoutParams bottomLineParams =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MDisplayUtil.dp2px(bottomLineHeight, getResources()));
        bottomLineParams.gravity = Gravity.BOTTOM;
        bottomLineParams.bottomMargin = MDisplayUtil.dp2px(tabBottomHeight - bottomLineHeight, getResources());
        addView(bottomLine, bottomLineParams);
        bottomLine.setAlpha(bottomAlpha);
    }

    private void fixContentView() {
        if (!(getChildAt(0) instanceof ViewGroup)) {
            return;
        }
        ViewGroup rootView = (ViewGroup) getChildAt(0);
        ViewGroup targetView = MViewUtil.findTypeView(rootView, RecyclerView.class);
        if (targetView == null) {
            targetView = MViewUtil.findTypeView(rootView, ScrollView.class);
        }
        if (targetView == null) {
            targetView = MViewUtil.findTypeView(rootView, AbsListView.class);
        }
        if (targetView == null){
            targetView = MViewUtil.findTypeView(rootView, NestedScrollView.class);
        }
        if (targetView != null) {
            targetView.setPadding(0, 0, 0, MDisplayUtil.dp2px(tabBottomHeight, getResources()));
            targetView.setClipToPadding(false);
        }
    }
}
