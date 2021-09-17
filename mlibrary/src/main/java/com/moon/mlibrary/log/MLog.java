package com.moon.mlibrary.log;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Author: Moon
 * Date: 9/9/21
 * Desc: 日志输出类
 */
public class MLog {

    private static final String M_LOG_PACKAGE;

    static {
        String className = MLog.class.getName();
        M_LOG_PACKAGE = className.substring(0, className.lastIndexOf('.') + 1);
    }

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
        log(type, MLogManager.getInstance().getConfig().getGlobalTag(), contents);
    }

    public static void log(@MLogType.TYPE int type, @NotNull String tag, Object... contents) {
        log(MLogManager.getInstance().getConfig(), type, tag, contents);
    }

    public static void log(@NotNull MLogConfig config, @MLogType.TYPE int type, String tag, Object... contents) {
        if (!config.enable()) return;
        StringBuilder sb = new StringBuilder();
        if (config.includeThread()) {
            String threadInfo = MLogConfig.M_THREAD_FORMATTER.format(Thread.currentThread());
            sb.append(threadInfo).append("\n");
        }
        if (config.stackTraceDepth() > 0) {
            String stackTraceInfo = MLogConfig.M_STACK_TRACE_FORMATTER.format(
                    MStackTraceUtil.getCropRealStackTrace(new Throwable().getStackTrace(), config.stackTraceDepth(), M_LOG_PACKAGE));
            sb.append(stackTraceInfo).append("\n");
        }
        String body = parseBody(contents, config);
        if (body != null) {
            body = body.replace("\\\"", "\""); // 替换转义字符
        }
        sb.append(body);
        List<MLogPrinter> printers = MLogManager.getInstance().getPrinters();
        if (printers == null || printers.size() == 0) {
            Log.println(Log.ERROR, config.getGlobalTag(), "No printer has been added ！！！！");
            return;
        }
        // 打印log
        for (MLogPrinter printer : printers) {
            printer.print(config, type, tag, sb.toString());
        }
    }

    /**
     * 解析组合日志信息
     *
     * @param contents 日志数组
     * @param config   配置信息
     */
    private static String parseBody(@NotNull Object[] contents, @NotNull MLogConfig config) {
        if (config.injectJsonParser() != null) {
            // 只有一个数据且为String时，不使用序列化
            if (contents.length == 1 && contents[0] instanceof String) {
                return ((String) contents[0]);
            }
            return config.injectJsonParser().toJson(contents);
        }
        StringBuilder sb = new StringBuilder();
        for (Object content : contents) {
            sb.append(content.toString()).append(";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\n");
        }
        return sb.toString();
    }
}
