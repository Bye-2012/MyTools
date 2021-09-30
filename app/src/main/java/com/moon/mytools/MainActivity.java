package com.moon.mytools;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.moon.common.ui.component.MBaseActivity;
import com.moon.mytools.logic.MainActivityLogic;

/**
 * Date: 9/30/21 2:29 PM
 * Author: Moon
 * Desc: 主页
 */
public class MainActivity extends MBaseActivity implements MainActivityLogic.ActivityProvider {

    private MainActivityLogic mainActivityLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityLogic = new MainActivityLogic(this);
    }
}
