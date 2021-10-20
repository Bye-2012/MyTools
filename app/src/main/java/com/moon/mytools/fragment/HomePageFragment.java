package com.moon.mytools.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.moon.common.ui.component.MBaseFragment;
import com.moon.mytools.R;
import com.moon.mytools.demo.RefreshDemoActivity;
import com.moon.mytools.demo.TopTabDemoActivity;
import com.moon.mytools.web.WebListActivity;

/**
 * Date: 9/29/21 5:27 PM
 * Author: Moon
 * Desc: 首页
 */
public class HomePageFragment extends MBaseFragment implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLayoutView.findViewById(R.id.btn_top_tab).setOnClickListener(this);
        mLayoutView.findViewById(R.id.btn_video).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_top_tab:
                startActivity(new Intent(getContext(), RefreshDemoActivity.class));
                break;
            case R.id.btn_video:
                startActivity(new Intent(getContext(), WebListActivity.class));
                break;
        }
    }
}
