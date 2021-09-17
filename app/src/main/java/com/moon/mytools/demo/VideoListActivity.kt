package com.moon.mytools.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moon.mytools.R
import kotlinx.android.synthetic.main.activity_video.*

/**
 * Author: Moon
 * Date: 9/7/21
 * Desc: 看视频页面
 */
class VideoListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val intent = Intent(this, WebViewActivity::class.java)

        btNunu.setOnClickListener {
            intent.putExtra("url", "https://www.nunuyy.cc/")
            startActivity(intent)
        }

        btQindou.setOnClickListener {
//            intent.putExtra("url", "https://www.qd2019.net/")
            intent.putExtra("url", "http://m.ttkmj.org/")
            startActivity(intent)
        }

        btpianku.setOnClickListener {
            intent.putExtra("url", "https://m.pianku.li")
            startActivity(intent)
        }

        btyinghua.setOnClickListener {
            intent.putExtra("url", "http://m.yhdm.so/")
            startActivity(intent)
        }
    }
}
