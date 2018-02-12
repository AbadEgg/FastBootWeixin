package com.mlnx.qcms.utils;

/**
 * Created by amanda.shan on 2017/3/23.
 */
public class LogLevelInfo {

    private boolean openFrameLog = false;
    private boolean openDecodeLog = false;

    private static LogLevelInfo instance;

    public static LogLevelInfo getInstance() {
        if (instance == null) {
            synchronized (LogLevelInfo.class) {
                if (instance == null) {
                    instance = new LogLevelInfo();
                }
            }
        }
        return instance;
    }

    public boolean isOpenFrameLog() {
        return openFrameLog;
    }

    public void setOpenFrameLog(boolean openFrameLog) {
        this.openFrameLog = openFrameLog;
    }

    public boolean isOpenDecodeLog() {
        return openDecodeLog;
    }

    public void setOpenDecodeLog(boolean openDecodeLog) {
        this.openDecodeLog = openDecodeLog;
    }
}
