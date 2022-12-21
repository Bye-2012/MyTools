package com.moon.mytools.arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * Date: 2022/12/21 09:46
 * Author: Moon
 * Desc:
 */
@Route(path = "/provide2/test")
public class TestServiceImpl implements TestService {
    @Override
    public void testIt() {

    }

    @Override
    public void init(Context context) {

    }
}
