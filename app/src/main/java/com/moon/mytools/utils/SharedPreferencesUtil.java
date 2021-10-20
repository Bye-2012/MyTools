package com.moon.mytools.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.moon.mlibrary.util.AppGlobals;

/**
 * Date: 2021/10/19 5:44 下午
 * Author: Moon
 * Desc:
 */
public class SharedPreferencesUtil {

    private static SharedPreferences sps = AppGlobals.INSTANCE.get().getSharedPreferences("application", Context.MODE_PRIVATE);

    public static void put(String key, Object value) {
        SharedPreferences.Editor edit = sps.edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            edit.putFloat(key, (Float) value);
        } else {
            throw new RuntimeException("SharedPreferences 不可保存当前类型");
        }
        edit.commit();
    }

    public static String getString(String key, String... defaultValue) {
        if (defaultValue == null || defaultValue.length != 1) {
            return sps.getString(key, "");
        } else {
            return sps.getString(key, defaultValue[0]);
        }
    }

    public static Boolean getBoolean(String key, boolean... defaultValue) {
        if (defaultValue == null || defaultValue.length != 1) {
            return sps.getBoolean(key, false);
        } else {
            return sps.getBoolean(key, defaultValue[0]);
        }
    }

    public static Integer getInteger(String key, int... defaultValue) {
        if (defaultValue == null || defaultValue.length != 1) {
            return sps.getInt(key, 0);
        } else {
            return sps.getInt(key, defaultValue[0]);
        }
    }

    public static Long getLong(String key, long... defaultValue) {
        if (defaultValue == null || defaultValue.length != 1) {
            return sps.getLong(key, 0);
        } else {
            return sps.getLong(key, defaultValue[0]);
        }
    }

    public static Float getFloat(String key, float... defaultValue) {
        if (defaultValue == null || defaultValue.length != 1) {
            return sps.getFloat(key, 0);
        } else {
            return sps.getFloat(key, defaultValue[0]);
        }
    }
}
