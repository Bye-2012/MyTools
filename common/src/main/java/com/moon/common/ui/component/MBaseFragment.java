package com.moon.common.ui.component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Date: 9/29/21 4:34 PM
 * Author: Moon
 * Desc: BaseFragment类
 */
public abstract class MBaseFragment extends Fragment {

    protected View mLayoutView;

    @LayoutRes
    protected abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mLayoutView = inflater.inflate(getLayoutId(), container, false);
    }
}
