package com.moon.mlibrary.log;

/**
 * Author: Moon
 * Date: 9/10/21
 * Desc: 堆栈日志信息格式化
 */
public class MStackTraceFormatter implements MLogFormatter<StackTraceElement[]> {

    @Override
    public String format(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder(128);
        if (stackTrace == null || stackTrace.length == 0) {
            return null;
        } else if (stackTrace.length == 1) {
            return "\t- " + stackTrace[0].toString();
        } else {
            for (int i = 0, len = stackTrace.length; i < len; i++) {
                if (i == 0) {
                    sb.append("StackTrace: \n");
                }
                if (i != len - 1) {
                    sb.append("\t├ ");
                    sb.append(stackTrace[i].toString());
                    sb.append("\n");
                } else {
                    sb.append("\t└ ");
                    sb.append(stackTrace[i].toString());
                }
            }
            return sb.toString();
        }
    }
}
