package com.moon.mui.item

import androidx.recyclerview.widget.RecyclerView

/**
 * Date: 2022/7/11 09:49
 * Author: Moon
 * Desc: RecyclerView数据类基类
 */
abstract class MDataItem<DATA, VH : RecyclerView.ViewHolder>(data: DATA) {

    var mData: DATA? = null

    init {
        mData = data
    }

}