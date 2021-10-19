package com.moon.mytools

import com.google.gson.Gson
import com.moon.common.ui.component.MBaseApplication
import com.moon.mlibrary.log.MConsolePrinter
import com.moon.mlibrary.log.MLogConfig
import com.moon.mlibrary.log.MLogConfig.JsonParser
import com.moon.mlibrary.log.MLogManager
import com.umeng.commonsdk.UMConfigure
import com.ycbjie.webviewlib.utils.X5WebUtils

/**
 * Author: Moon
 * Date: 9/7/21
 * Desc: Application类
 */
class MyApplication : MBaseApplication() {

    override fun onCreate() {
        super.onCreate()

        UMConfigure.setLogEnabled(false)
        UMConfigure.preInit(this, "61380bb03776cb0e717b0db0", "Umeng")
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null)

        MLogManager.init(object : MLogConfig() {

            override fun injectJsonParser(): JsonParser {
                return JsonParser { src -> Gson().toJson(src) }
            }

            override fun getGlobalTag(): String {
                return "MyTools"
            }

            override fun enable(): Boolean {
                return true
            }

            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 3
            }

        }, MConsolePrinter())

        X5WebUtils.init(this)
    }
}
