package com.moon.mlibrary.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Moon
 * Date: 9/9/21
 * Desc: 日志管理类
 */
public class MLogManager {

    private static MLogManager instance;
    private final MLogConfig config;
    private final List<MLogPrinter> printers = new ArrayList<>();

    private MLogManager(MLogConfig config, MLogPrinter[] printers) {
        this.config = config;
        this.printers.addAll(Arrays.asList(printers));
    }

    public static void init(MLogConfig config, MLogPrinter... printers) {
        instance = new MLogManager(config, printers);
    }

    public static MLogManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("MLogManager should init first");
        }
        return instance;
    }

    /**
     * 获取配置信息
     */
    public MLogConfig getConfig() {
        return config;
    }

    /**
     * 获取打印器
     */
    public List<MLogPrinter> getPrinters() {
        return printers;
    }

    /**
     * 添加打印器
     */
    public void addPrinter(MLogPrinter printer) {
        printers.add(printer);
    }

    /**
     * 移除打印器
     */
    public void removePrinter(MLogPrinter printer) {
        if (printer != null) {
            printers.remove(printer);
        }
    }
}
