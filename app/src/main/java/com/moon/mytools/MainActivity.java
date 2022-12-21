package com.moon.mytools;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.moon.common.ui.component.MBaseActivity;
import com.moon.mlibrary.log.MLog;
import com.moon.mlibrary.util.ActivityManager;
import com.moon.mytools.logic.MainActivityLogic;

/**
 * Date: 9/30/21 2:29 PM
 * Author: Moon
 * Desc: 主页
 */
@Route(path = "/index/main")
public class MainActivity extends MBaseActivity implements MainActivityLogic.ActivityProvider, ActivityManager.FrontBackCallback {

    @Autowired
    public int age;

    private MainActivityLogic mMainActivityLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ARouter.getInstance().inject(this);

        mMainActivityLogic = new MainActivityLogic(this, savedInstanceState);

        ActivityManager.getInstance().registerFrontBackCallback(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.commit();
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
