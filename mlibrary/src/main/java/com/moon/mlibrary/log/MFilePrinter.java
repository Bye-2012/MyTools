package com.moon.mlibrary.log;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Date: 9/17/21 11:42 AM
 * Author: Moon
 * Desc: 日志文件打印器
 * <p>
 * tips：
 * 1、BlockingQueue的使用，防止频繁的创建线程；
 * 2、线程同步；
 * 2、文件操作，BufferedWriter的应用；
 */
public class MFilePrinter implements MLogPrinter {

    private static MFilePrinter instance;
    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

    private final String logPath;
    private final long retentionTime;
    private volatile PrintWorker worker;
    private LogWriter writer;

    private MFilePrinter(String logPath, long retentionTime) {
        this.logPath = logPath;
        this.retentionTime = retentionTime;
        this.worker = new PrintWorker();
        this.writer = new LogWriter();
        cleanExpiredLog();
    }

    /**
     * 获取MFilePrinter实例
     *
     * @param logPath       log保存路径，如果是外部路径需要确保已经有外部存储的读写权限
     * @param retentionTime log文件的有效时长，单位毫秒，<=0表示一直有效
     */
    public static synchronized MFilePrinter getInstance(String logPath, long retentionTime) {
        if (instance == null) {
            instance = new MFilePrinter(logPath, retentionTime);
        }
        return instance;
    }

    @Override
    public void print(@NotNull MLogConfig config, int level, String tag, @NotNull String printString) {
        long timeMillis = System.currentTimeMillis();
        if (!worker.isRunning()) {
            worker.start();
        }
        worker.put(new MLogMo(timeMillis, level, tag, printString));
    }

    /**
     * 打印操作
     *
     * @param logMo 日志
     */
    private void doPrint(MLogMo logMo) {
        String lastFileName = writer.getPreFileName();
        if (lastFileName == null) {
            String newFileName = genFileName();
            if (writer.isReady()) {
                writer.close();
            }
            if (!writer.ready(newFileName)) {
                return;
            }
        }

        writer.append(logMo.flattenedLog());
    }

    /**
     * 清除过期log
     */
    private void cleanExpiredLog() {
        if (retentionTime <= 0) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        File logDir = new File(logPath);
        File[] files = logDir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (currentTimeMillis - file.lastModified() > retentionTime) {
                file.delete();
            }
        }
    }

    private String genFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    private class PrintWorker implements Runnable {

        private final BlockingQueue<MLogMo> logs = new LinkedBlockingQueue<>();
        private volatile boolean running;

        /**
         * 将log放入打印队列
         *
         * @param log 要被打印的log
         */
        void put(MLogMo log) {
            try {
                logs.put(log);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * 判断工作线程是否还在运行中
         *
         * @return true 在运行
         */
        boolean isRunning() {
            synchronized (this) {
                return running;
            }
        }

        /**
         * 启动工作线程
         */
        void start() {
            synchronized (this) {
                EXECUTOR.execute(this);
                running = true;
            }
        }

        @Override
        public void run() {
            MLogMo log;
            try {
                while (true) {
                    log = logs.take();
                    doPrint(log);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                synchronized (this) {
                    running = false;
                }
            }
        }
    }

    /**
     * 写日志到文件
     */
    private class LogWriter {

        private String preFileName;
        private File logFile;
        private BufferedWriter bufferedWriter;

        boolean isReady() {
            return bufferedWriter != null;
        }

        /**
         * 日志写入前的准备工作
         *
         * @param newFileName log的文件名
         * @return 是否准备好
         */
        boolean ready(String newFileName) {
            preFileName = newFileName;
            logFile = new File(logPath, newFileName);
            // 当log文件不存在时创建log文件
            if (!logFile.exists()) {
                try {
                    File parent = logFile.getParentFile();
                    assert parent != null;
                    if (!parent.exists()) parent.mkdirs();
                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    preFileName = null;
                    logFile = null;
                    return false;
                }
            }
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(logFile, true));
            } catch (Exception e) {
                e.printStackTrace();
                preFileName = null;
                logFile = null;
                return false;
            }
            return true;
        }

        /**
         * 将log写入文件
         *
         * @param flattenedLog 格式化后的log
         */
        void append(String flattenedLog) {
            try {
                bufferedWriter.write(flattenedLog);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 关闭bufferedWriter
         */
        boolean close() {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    bufferedWriter = null;
                    preFileName = null;
                    logFile = null;
                }
            }
            return true;
        }

        public String getPreFileName() {
            return preFileName;
        }
    }
}
