package com.moon.mytools.arouter;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Date: 2022/7/22 16:06
 * Author: Moon
 * Desc:
 */
public interface HelloService extends IProvider {
    void sayHello(String content);
}
