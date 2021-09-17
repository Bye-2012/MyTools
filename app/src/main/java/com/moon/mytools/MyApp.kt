package com.moon.mytools

import android.app.Application
import com.google.gson.Gson
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
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        X5WebUtils.init(this)

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

        }, MConsolePrinter())
    }
}
