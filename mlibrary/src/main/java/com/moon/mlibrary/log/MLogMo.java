package com.moon.mlibrary.log;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Date: 9/16/21 11:50 AM
 * Author: Moon
 * Desc: 日志列表 item model
 */
public class MLogMo {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.CHINA);

    public long timeMillis;
    public int level;
    public String tag;
    public String log;

    public MLogMo(long timeMillis, int level, String tag, String log) {
        this.timeMillis = timeMillis;
        this.level = level;
        this.tag = tag;
        this.log = log;
    }

    private String format() {
        return sdf.format(timeMillis);
    }

    public String getFlattened() {
        return format() + '|' + level + '|' + tag + "|:";
    }

    public String flattenedLog() {
        return getFlattened() + "\n" + log;
    }
}
