package com.moon.mytools

import android.app.Application
import com.moon.mlibrary.log.MLog
import com.moon.mlibrary.log.MLogConfig
import com.moon.mlibrary.log.MLogManager
import com.umeng.commonsdk.UMConfigure
import com.ycbjie.webviewlib.utils.X5WebUtils

/**
 * Author: LuXin
 * Date: 9/7/21
 * Desc: Application类
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        X5WebUtils.init(this)

        UMConfigure.setLogEnabled(false)
        UMConfigure.preInit(this, "61380bb03776cb0e717b0db0", "Umeng")
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null)

        MLogManager.init(MLogConfig())

        MLog.d("hhhhhh")
    }
}
