package com.moon.mytools.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.moon.common.ui.component.MBaseFragment;
import com.moon.mytools.R;
import com.ycbjie.webviewlib.utils.ToastUtils;

/**
 * Date: 9/29/21 5:27 PM
 * Author: Moon
 * Desc: 收藏页
 */
public class FavoriteFragment extends MBaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favorite;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLayoutView.findViewById(R.id.tttt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/demo/refresh")
                        .greenChannel()
                        .navigation();
            }
        });
    }
}
