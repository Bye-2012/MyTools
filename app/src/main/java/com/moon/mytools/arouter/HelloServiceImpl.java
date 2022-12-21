package com.moon.mytools.arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.moon.mlibrary.log.MLog;

/**
 * Date: 2022/7/22 16:07
 * Author: Moon
 * Desc:
 */
@Route(path = "/provide/hello")
public class HelloServiceImpl implements HelloService {

    @Override
    public void sayHello(String content) {
        MLog.d("content::::::" + content);
    }

    @Override
    public void init(Context context) {
        MLog.d("HelloServiceImpl::: init");
    }
}
