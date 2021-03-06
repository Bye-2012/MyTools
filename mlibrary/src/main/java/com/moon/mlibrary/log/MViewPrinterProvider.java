package com.moon.mlibrary.log;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.moon.mlibrary.util.MDisplayUtil;

/**
 * Date: 9/16/21 11:24 AM
 * Author: Moon
 * Desc: 界面日志打印器Provider
 */
public class MViewPrinterProvider {

    private static final String TAG_FLOATING_VIEW = "TAG_FLOATING_VIEW";
    private static final String TAG_LOG_VIEW = "TAG_LOG_VIEW";

    private final FrameLayout rootView;
    private View floatingView;
    private boolean isOpen;
    private FrameLayout logView;
    private final RecyclerView recyclerView;

    public MViewPrinterProvider(FrameLayout rootView, RecyclerView recyclerView) {
        this.rootView = rootView;
        this.recyclerView = recyclerView;
    }

    public void showFloatingView() {
        if (rootView.findViewWithTag(TAG_FLOATING_VIEW) != null) {
            return;
        }
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.END;
        View floatingView = genFloatingView();
        floatingView.setTag(TAG_FLOATING_VIEW);
        floatingView.setBackgroundColor(Color.RED);
        floatingView.setAlpha(0.8f);
        params.bottomMargin = MDisplayUtil.dp2px(300, rootView.getResources());
        rootView.addView(genFloatingView(), params);
    }

    public void closeFloatingView() {
        rootView.removeView(genFloatingView());
    }

    public void showLogView() {
        if (rootView.findViewWithTag(TAG_LOG_VIEW) != null) {
            return;
        }
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MDisplayUtil.dp2px(250, rootView.getResources()));
        params.gravity = Gravity.BOTTOM;
        View logView = genLogView();
        logView.setTag(TAG_LOG_VIEW);
        rootView.addView(genLogView(), params);
        isOpen = true;
    }

    public void closeLogView() {
        isOpen = false;
        rootView.removeView(genLogView());
    }

    @SuppressLint("SetTextI18n")
    public View genFloatingView() {
        if (floatingView != null) return floatingView;
        TextView textView = new TextView(rootView.getContext());
        textView.setOnClickListener(v -> {
            if (!isOpen) showLogView();
        });
        textView.setText("MLog");
        return floatingView = textView;
    }

    @SuppressLint("SetTextI18n")
    public View genLogView() {
        if (logView != null) {
            return logView;
        }
        FrameLayout logView = new FrameLayout(rootView.getContext());
        logView.setBackgroundColor(Color.BLACK);
        logView.addView(recyclerView);

        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.END;
        TextView closeView = new TextView(rootView.getContext());
        closeView.setOnClickListener(v -> closeLogView());
        closeView.setBackgroundColor(Color.RED);
        closeView.setText("Close");
        logView.addView(closeView, params);
        return this.logView = logView;
    }
}
