package com.moon.mlibrary.log;

/**
 * Author: Moon
 * Date: 9/9/21
 * Desc: 日志配置类
 */
public abstract class MLogConfig {

    static int MAX_LEN = 512;
    static MStackTraceFormatter M_STACK_TRACE_FORMATTER = new MStackTraceFormatter();
    static MThreadFormatter M_THREAD_FORMATTER = new MThreadFormatter();

    /**
     * 数据序列化解析器
     */
    public JsonParser injectJsonParser() {
        return null;
    }

    /**
     * 是否包含线程信息
     */
    public boolean includeThread() {
        return false;
    }

    /**
     * 堆栈信息深度
     */
    public int stackTraceDepth() {
        return 5;
    }

    /**
     * 获取全局TAG
     */
    public String getGlobalTag() {
        return "MLog";
    }

    /**
     * 是否开启日志
     */
    public boolean enable() {
        return true;
    }

    /**
     * 数据序列化解析器
     */
    public interface JsonParser {
        String toJson(Object src);
    }
}
