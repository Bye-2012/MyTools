package com.moon.mytools.biz

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.moon.common.ui.component.MBaseActivity
import com.moon.mytools.arouter.RouteFlag

/**
 * Date: 2022/12/21 13:48
 * Author: Moon
 * Desc: 个人中心页
 */
@Route(path = "/profile/detail", extras = RouteFlag.FLAG_LOGIN)
class ProfileDetailActivity : MBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}