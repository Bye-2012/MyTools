package com.moon.mytools;

import android.os.Bundle;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.moon.common.ui.component.MBaseActivity;
import com.moon.mlibrary.log.MLog;
import com.moon.mlibrary.util.ActivityManager;
import com.moon.mytools.logic.MainActivityLogic;

/**
 * Date: 9/30/21 2:29 PM
 * Author: Moon
 * Desc: 主页
 */
public class MainActivity extends MBaseActivity implements MainActivityLogic.ActivityProvider, ActivityManager.FrontBackCallback {

    private MainActivityLogic mMainActivityLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainActivityLogic = new MainActivityLogic(this, savedInstanceState);

        ActivityManager.getInstance().registerFrontBackCallback(this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMainActivityLogic.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().removeFrontBackCallback(this);
    }

    @Override
    public void onChanged(boolean front) {
        MLog.d("app处于：" + front);
    }
}
