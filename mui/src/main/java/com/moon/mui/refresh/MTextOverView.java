package com.moon.mui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.moon.mui.R;

/**
 * Date: 2021/10/20 10:11 上午
 * Author: Moon
 * Desc: 文本刷新头
 */
public class MTextOverView extends MOverView {

    private TextView mText;
    private View mRotateView;

    public MTextOverView(@NonNull Context context) {
        super(context);
    }

    public MTextOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MTextOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.m_refresh_overview, this, true);
        mText = findViewById(R.id.text);
        mRotateView = findViewById(R.id.iv_rotate);
    }

    @Override
    protected void onScroll(int scrollY, int pullRefreshHeight) {

    }

    @Override
    protected void onVisible() {
        mText.setText("下拉刷新");
    }

    @Override
    protected void onOver() {
        mText.setText("松开刷新");
    }

    @Override
    protected void onRefresh() {
        mText.setText("正在刷新...");
        Animation operatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        mRotateView.startAnimation(operatingAnim);
    }

    @Override
    protected void onFinish() {
        mRotateView.clearAnimation();
    }
}
