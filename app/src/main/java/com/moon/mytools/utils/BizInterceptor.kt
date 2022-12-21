package com.moon.mytools.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.moon.mytools.arouter.RouteFlag

/**
 * Date: 2022/7/15 14:06
 * Author: Moon
 * Desc: 拦截器
 */
@Interceptor(name = "biz_interceptor", priority = 9)
class BizInterceptor : IInterceptor {

    private var context: Context? = null

    override fun init(context: Context?) {
        this.context = context
    }

    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        val flag = postcard!!.extra
        if (flag and RouteFlag.FLAG_LOGIN != 0) {
            //login
            callback!!.onInterrupt(RuntimeException("need login"))
            showToast("need login")
        } else if (flag and RouteFlag.FLAG_AUTHENTICATION != 0) {
            //auth
            callback!!.onInterrupt(RuntimeException("need authentication"))
            showToast("need authentication")
        } else if (flag and RouteFlag.FLAG_VIP != 0) {
            //vip
            callback!!.onInterrupt(RuntimeException("need vip"))
            showToast("need vip")
        } else {
            callback!!.onContinue(postcard)
        }
    }

    private fun showToast(s: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
        }
    }
}
