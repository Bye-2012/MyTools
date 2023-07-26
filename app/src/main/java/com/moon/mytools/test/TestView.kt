package com.moon.mytools.test

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * Date: 2023/7/21 15:00
 * Author: Moon
 * Desc: 测试View
 */
class TestView(context: Context?, attr: AttributeSet?) : View(context, attr) {

    private val path: Path = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas.drawPath()
    }
}