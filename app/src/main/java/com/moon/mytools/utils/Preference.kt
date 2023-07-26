package com.moon.mytools.utils

import android.content.Context
import android.content.SharedPreferences
import com.moon.mytools.MyApplication
import kotlin.reflect.KProperty

/**
 * Date: 2023/7/5 14:28
 * Author: Moon
 * Desc: SP工具类
 */
class Preference<T>(val name: String, private val default: T) {

    companion object {
        private val prefs: SharedPreferences by lazy {
            MyApplication.instance.applicationContext.getSharedPreferences(
                "MyTools",
                Context.MODE_PRIVATE
            )
        }

        fun clear() {
            prefs.edit().clear().apply()
        }

        fun remove(name: String) {
            prefs.edit().remove(name).apply()
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
        getSharedPreference(name, default)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
        setSharedPreference(name, value)

    @Suppress("UNCHECKED_CAST")
    private fun getSharedPreference(name: String, default: T): T {
        val ret = when (default) {
            is Int -> prefs.getInt(name, default)
            is Float -> prefs.getFloat(name, default)
            is Boolean -> prefs.getBoolean(name, default)
            is Long -> prefs.getLong(name, default)
            is String -> prefs.getString(name, default)
            else -> throw RuntimeException("error type")
        }
        return ret as T
    }

    private fun setSharedPreference(name: String, value: T) {
        with(prefs.edit()) {
            when (value) {
                is Int -> putInt(name, value)
                is Float -> putFloat(name, value)
                is String -> putString(name, value)
                is Boolean -> putBoolean(name, value)
                is Long -> putLong(name, value)
                else -> throw RuntimeException("error type")
            }.apply()
        }
    }
}
