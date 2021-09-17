package com.moon.mlibrary.log;

/**
 * Author: Moon
 * Date: 9/10/21
 * Desc: 日志格式化接口
 */
public interface MLogFormatter<T> {
    String format(T data);
}
