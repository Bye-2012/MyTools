package com.moon.mytools

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moon.mlibrary.log.*
import com.moon.mytools.demo.VideoListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mViewPrinter: MViewPrinter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btMovie.setOnClickListener {
            startActivity(Intent(this, VideoListActivity::class.java))
        }

        MLog.d(Person("lx", 30))

        mViewPrinter = MViewPrinter(this)
        mViewPrinter!!.viewProvider.showFloatingView()

        showLog.setOnClickListener {
            MLogManager.getInstance().addPrinter(mViewPrinter)
            MLog.log(
                object : MLogConfig() {
                    override fun includeThread(): Boolean {
                        return true
                    }
                },
                MLogType.E,
                "---",
                "55555"
            )
        }
    }

    class Person(var name: String, var age: Int)
}
