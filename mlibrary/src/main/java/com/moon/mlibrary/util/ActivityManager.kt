package com.moon.mlibrary.util

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference

/**
 * Date: 2022/6/9 16:06
 * Author: Moon
 * Desc: Activity任务栈管理类
 */
class ActivityManager private constructor() {

    private val activityRefs = ArrayList<WeakReference<Activity>>()
    private val frontBackCallbacks = ArrayList<FrontBackCallback>()
    private var front = true
    private var activityStartCount = 0;

    fun init(app: Application) {
        app.registerActivityLifecycleCallbacks(InnerActivityLifecycleCallback())
    }

    fun registerFrontBackCallback(callback: FrontBackCallback) {
        frontBackCallbacks.add(callback)
    }

    fun removeFrontBackCallback(callback: FrontBackCallback) {
        frontBackCallbacks.remove(callback)
    }

    /**
     * 找出栈顶不为空，且没有被销毁的activity
     */
    fun getTopActivity(onlyAlive: Boolean): Activity? {
        if (activityRefs.size <= 0) {
            return null
        } else {
            val activityRef = activityRefs[activityRefs.size - 1]
            val activity: Activity? = activityRef.get()
            if (onlyAlive) {
                if ((activity == null) || activity.isFinishing || activity.isDestroyed) {
                    activityRefs.remove(activityRef)
                    return getTopActivity(onlyAlive)
                }
            }
            return activity
        }
    }

    /**
     * 前后台回调
     */
    fun onFrontBackChanged(front: Boolean) {
        for (callback in frontBackCallbacks) {
            callback.onChanged(front)
        }
    }

    inner class InnerActivityLifecycleCallback : Application.ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activityRefs.add(WeakReference(activity))
        }

        override fun onActivityStarted(activity: Activity) {
            activityStartCount++
            if (!front && activityStartCount > 0) {
                front = true
                onFrontBackChanged(front)
            }
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {
            activityStartCount--
            if (front && activityStartCount <= 0) {
                front = false
                onFrontBackChanged(front)
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            for (activityRef in activityRefs) {
                if (activityRef.get() == activity) {
                    activityRefs.remove(activityRef)
                    break
                }
            }
        }
    }

    //前后台回调接口
    interface FrontBackCallback {
        fun onChanged(front: Boolean)
    }

    //单例
    companion object {
        @JvmStatic
        val instance: ActivityManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityManager()
        }
    }
}
