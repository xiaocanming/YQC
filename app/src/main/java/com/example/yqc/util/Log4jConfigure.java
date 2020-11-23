package com.example.yqc.util;

import android.os.Environment;

import org.apache.log4j.Level;

import java.io.File;

import de.mindpipe.android.logging.log4j.LogConfigurator;

public class Log4jConfigure {
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 10;
    public static final String
            DEFAULT_LOG_DIR = "//YQC//Log//";
    public static final String DEFAULT_LOG_FILE_NAME = "YQC.log";
    public static final String TAG = "Log4jConfigure";
    // 对应AndroidManifest文件中的package
    public static final String PACKAGE_NAME = "com.example.yqc";

    public static void configure(String fileName) {
        final LogConfigurator logConfigurator = new LogConfigurator();
        try {
            if (isSdcardMounted()) {
                logConfigurator.setFileName(Environment.getExternalStorageDirectory()
                        + DEFAULT_LOG_DIR + fileName);
            } else {
                logConfigurator.setFileName("//data//data//" + PACKAGE_NAME + "//files"
                        + File.separator + fileName);
            }
            //以下设置是按指定大小来生成新的文件
            /*
             * logConfigurator.setMaxBackupSize(4);
             * logConfigurator.setMaxFileSize(MAX_FILE_SIZE);
             */
            logConfigurator.setMaxBackupSize(10);
            logConfigurator.setMaxFileSize(MAX_FILE_SIZE);

            //以下为通用配置
            logConfigurator.setImmediateFlush(true);
            logConfigurator.setRootLevel(Level.DEBUG);
            logConfigurator.setFilePattern("%d\t%p/%c:\t%m%n");
            logConfigurator.configure();
            android.util.Log.e(TAG, "Log4j config finish");
        } catch (Throwable throwable) {
            logConfigurator.setResetConfiguration(true);
            android.util.Log.e(TAG, "Log4j config error, use default config. Error:" + throwable);
        }
    }

    public static void configure() {
        configure(DEFAULT_LOG_FILE_NAME);
    }

    private static boolean isSdcardMounted() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

}
