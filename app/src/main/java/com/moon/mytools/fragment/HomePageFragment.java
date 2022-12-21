package com.moon.mytools.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.moon.common.ui.component.MBaseFragment;
import com.moon.mytools.R;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_top_tab:
//                ARouter.getInstance()
//                        .build("/demo/banner")
//                        .withInt("count", 2)
//                        .greenChannel()
//                        .navigation();
                ARouter.getInstance().build("/profile/detail").navigation(getContext());
                break;
        }
    }
}
