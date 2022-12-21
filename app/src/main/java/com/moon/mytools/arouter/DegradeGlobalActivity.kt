package com.moon.mytools.arouter

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.moon.common.ui.component.MBaseActivity
import com.moon.mytools.R

/**
 * Date: 2022/12/21 14:03
 * Author: Moon
 * Desc:
 */
@Route(path = "/degrade/global/activity")
class DegradeGlobalActivity : MBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_degrade)
    }
}