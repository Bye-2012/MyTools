package com.moon.mytools.test

/**
 * Date: 2023/6/29 15:54
 * Author: Moon
 * Desc:
 */

class A {
    fun login() {
        val name = "123"
        val pwd = "456"

        B().loginNet(name, pwd, object : NetCallBack {
            override fun finished(isSuccess: Boolean) {
                println("luncheoning")
            }
        })

        B().loginNet(name, pwd) {
            if (it) {
                println("哈哈哈哈")
            }
        }
    }
}

class B {

    fun loginNet(name: String, pwd: String, callBack: NetCallBack) {
        println("网络请求")
        callBack.finished(true)
    }

    fun loginNet(name: String, pwd: String, action: (Boolean) -> Unit) {
        println("网络请求")
        action(true)
    }
}

interface NetCallBack {
    fun finished(isSuccess: Boolean);
}

