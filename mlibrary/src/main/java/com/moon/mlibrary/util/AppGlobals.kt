package com.moon.mlibrary.util

import android.annotation.SuppressLint
import android.app.Application

/**
 * Date: 9/16/21 2:29 PM
 * Author: Moon
 * Desc: 对于组件化项目,不可能把项目实际的Application下沉到Base,而且各个module也不需要知道Application真实名字
 *       这种一次反射就能获取全局Application对象的方式相比于在Application#OnCreate保存一份的方式显示更加通用了
 */
object AppGlobals {
    private var application: Application? = null

    @SuppressLint("PrivateApi")
    fun get(): Application? {
        if (application == null) {
            try {
                application = Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication")
                    .invoke(null) as Application
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return application
    }
}
