package com.moon.mytools.utils

import android.view.View

/**
 * Date: 2023/7/6 15:20
 * Author: Moon
 * Desc: 防止双击
 */
abstract class DoubleClickListener : View.OnClickListener {

    companion object {
        private const val INTERVAL_TIME = 1000L //间隔时间
    }

    private var mLastClickTime: Long = 0 //最近一次点击时间

    override fun onClick(v: View) {
        val currentTimeMillis = System.currentTimeMillis()
        if (mLastClickTime != 0L) {
            if (currentTimeMillis - mLastClickTime > INTERVAL_TIME) {
                onClicked(v)
            }
        }
        mLastClickTime = currentTimeMillis
    }

    protected abstract fun onClicked(v: View?)
}
