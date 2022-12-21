package com.moon.mytools.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Date: 2022/12/21 14:00
 * Author: Moon
 * Desc: 全局降级服务
 */
@Route(path = "/degrade/global/service")
class DegradeServiceImpl : DegradeService {

    private var context: Context? = null

    override fun init(context: Context?) {
        this.context = context
    }

    override fun onLost(context: Context?, postcard: Postcard?) {
        ARouter.getInstance().build("/degrade/global/activity").navigation(context)
    }
}