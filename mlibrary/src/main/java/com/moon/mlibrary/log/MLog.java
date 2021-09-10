package com.moon.mlibrary.log;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

/**
 * Author: LuXin
 * Date: 9/9/21
 * Desc: 日志输出类
 */
public class MLog {

    public static void v(Object... contents) {
        log(MLogType.V, contents);
    }

    public static void vt(String tag, Object... contents) {
        log(MLogType.V, tag, contents);
    }

    public static void d(Object... contents) {
        log(MLogType.D, contents);
    }

    public static void dt(String tag, Object... contents) {
        log(MLogType.D, tag, contents);
    }

    public static void e(Object... contents) {
        log(MLogType.E, contents);
    }

    public static void et(String tag, Object... contents) {
        log(MLogType.E, tag, contents);
    }

    public static void w(Object... contents) {
        log(MLogType.W, contents);
    }

    public static void wt(String tag, Object... contents) {
        log(MLogType.W, tag, contents);
    }

    public static void a(Object... contents) {
        log(MLogType.A, contents);
    }

    public static void at(String tag, Object... contents) {
        log(MLogType.A, tag, contents);
    }

    public static void i(Object... contents) {
        log(MLogType.I, contents);
    }

    public static void it(String tag, Object... contents) {
        log(MLogType.I, tag, contents);
    }

    public static void log(@MLogType.TYPE int type, Object... contents) {
        log(type, MLogManager.getInstance().getConfig().getDefaultTag(), contents);
    }

    public static void log(@MLogType.TYPE int type, @NotNull String tag, Object... contents) {
        log(MLogManager.getInstance().getConfig(), type, tag, contents);
    }

    public static void log(@NotNull MLogConfig config, @MLogType.TYPE int type, String tag, Object... contents) {
        if (!config.enable()) return;
        String body = parseBody(contents);
        Log.println(type, tag, body);
    }

    private static String parseBody(@NotNull Object[] contents) {
        StringBuilder sb = new StringBuilder();
        for (Object content : contents) {
            sb.append(content.toString()).append(";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
