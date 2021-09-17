package com.moon.mlibrary.log;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import static com.moon.mlibrary.log.MLogConfig.MAX_LEN;

/**
 * Author: Moon
 * Date: 9/10/21
 * Desc: 控制台打印器
 */
public class MConsolePrinter implements MLogPrinter {

    @Override
    public void print(@NotNull MLogConfig config, int level, String tag, @NotNull String printString) {
        int len = printString.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            StringBuilder log = new StringBuilder();
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                log.append(printString.substring(index, MAX_LEN));
                index += MAX_LEN;
            }
            if (index != len) {
                log.append(printString.substring(index, len));
            }
            Log.println(level, tag, log.toString());
        } else {
            Log.println(level, tag, printString);
        }
    }
}
