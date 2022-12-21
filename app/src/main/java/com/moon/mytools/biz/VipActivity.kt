package com.moon.mytools.biz

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.moon.common.ui.component.MBaseActivity
import com.moon.mytools.arouter.RouteFlag

/**
 * Date: 2022/12/21 13:58
 * Author: Moon
 * Desc:
 */
@Route(path = "/profile/vip", extras = RouteFlag.FLAG_VIP)
class VipActivity : MBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}