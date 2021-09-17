package com.moon.mlibrary.log;

/**
 * Author: Moon
 * Date: 9/10/21
 * Desc: 线程日志信息格式化
 */
public class MThreadFormatter implements MLogFormatter<Thread> {

    @Override
    public String format(Thread data) {
        return "Thread: " + data.getName();
    }
}
