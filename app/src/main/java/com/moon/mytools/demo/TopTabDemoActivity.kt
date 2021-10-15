package com.moon.mytools.demo

import android.os.Bundle
import android.widget.Toast
import com.moon.common.ui.component.MBaseActivity
import com.moon.mui.tab.top.MTabTopInfo
import com.moon.mytools.R
import kotlinx.android.synthetic.main.activity_top_tab_demo.*

class TopTabDemoActivity : MBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_tab_demo)
        initTabTop()
    }

    private fun initTabTop() {
        val infoList: MutableList<MTabTopInfo<*>> = mutableListOf()
        val defaultColor = resources.getColor(R.color.tabBottomDefaultColor)
        val tintColor = resources.getColor(R.color.tabBottomTintColor)
        val tabsStr = arrayOf(
            "热门",
            "服装",
            "数码",
            "鞋子",
            "零食",
            "家电",
            "汽车",
            "百货",
            "家居",
            "装修",
            "运动"
        )
        for (name in tabsStr) {
            val tabTopInfo = MTabTopInfo(name, defaultColor, tintColor)
            infoList.add(tabTopInfo)
        }

        tab_top_layout.inflateInfo(infoList)
        tab_top_layout.addTabSelectedChangeListener { _, _, nextInfo ->
            Toast.makeText(this@TopTabDemoActivity, nextInfo.name, Toast.LENGTH_SHORT).show()
        }
        tab_top_layout.defaultSelected(infoList[0])
    }
}
