package com.moon.mlibrary.log;

/**
 * Author: LuXin
 * Date: 9/9/21
 * Desc: 日志管理类
 */
public class MLogManager {

    private final MLogConfig config;
    private static MLogManager instance;

    private MLogManager(MLogConfig config) {
        this.config = config;
    }

    public static void init(MLogConfig config) {
        instance = new MLogManager(config);
    }

    public static MLogManager getInstance() {
        if (instance == null){
            throw new RuntimeException("MLogManager should init first");
        }
        return instance;
    }

    public MLogConfig getConfig() {
        return config;
    }
}
