package com.moon.mytools.test

data class HotkeyData(
    val data: List<Data>,
    val errorCode: Int,
    val errorMsg: String
) {
    data class Data(
        val id: Int,
        val link: String,
        val name: String,
        val order: Int,
        val visible: Int
    )
}