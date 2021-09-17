package com.moon.mlibrary.log;

import org.jetbrains.annotations.NotNull;

/**
 * Author: Moon
 * Date: 9/10/21
 * Desc: 日志打印器接口
 */
public interface MLogPrinter {

    /**
     * 打印操作
     *
     * @param config      配置信息
     * @param level        日志级别
     * @param tag         TAG
     * @param printString 日志内容
     */
    void print(@NotNull MLogConfig config, @MLogType.TYPE int level, String tag, @NotNull String printString);
}
