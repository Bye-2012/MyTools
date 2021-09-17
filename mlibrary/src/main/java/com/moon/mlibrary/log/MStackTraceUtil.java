package com.moon.mlibrary.log;

/**
 * Date: 9/10/21 4:16 PM
 * Author: Moon
 * Desc: 堆栈信息处理工具类
 */
public class MStackTraceUtil {

    /**
     * 获取裁切过滤后的堆栈信息
     *
     * @param stackTrace    原始堆栈信息
     * @param maxDepth      最大深度
     * @param ignorePackage 工具类包名
     */
    public static StackTraceElement[] getCropRealStackTrace(StackTraceElement[] stackTrace, int maxDepth, String ignorePackage) {
        return cropStackTrace(getRealStackTrace(stackTrace, ignorePackage), maxDepth);
    }

    /**
     * 裁切 stack trace
     *
     * @param stackTrace 原始 stack trace
     * @param maxDepth   最大深度
     */
    private static StackTraceElement[] cropStackTrace(StackTraceElement[] stackTrace, int maxDepth) {
        int realDepth = stackTrace.length;
        if (maxDepth > 0) {
            realDepth = Math.min(realDepth, maxDepth);
        }
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(stackTrace, 0, realStack, 0, realDepth);
        return realStack;
    }

    /**
     * 过滤 MLog工具类的 stack trace信息
     *
     * @param stackTrace    原始 stack trace
     * @param ignorePackage 工具类包名
     */
    private static StackTraceElement[] getRealStackTrace(StackTraceElement[] stackTrace, String ignorePackage) {
        int ignoreDepth = 0;
        int allDepth = stackTrace.length;
        String className;
        for (int i = allDepth - 1; i > 0; i--) {
            className = stackTrace[i].getClassName();
            if (ignorePackage != null && className.startsWith(ignorePackage)) {
                ignoreDepth = i + 1;
                break;
            }
        }
        int realDepth = allDepth - ignoreDepth;
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(stackTrace, ignoreDepth, realStack, 0, realDepth);
        return realStack;
    }
}
