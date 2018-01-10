package com.mlnx.mptp.utils;

/**
 * Created by amanda.shan on 2017/3/23.
 */
public class MpLogLevelInfo {

    private boolean openFrameLog = false;
    private boolean openDecodeLog = false;

    private static MpLogLevelInfo instance;

    public static MpLogLevelInfo getInstance() {
        if (instance == null)
            synchronized (MpLogLevelInfo.class) {
                if (instance == null)
                    instance = new MpLogLevelInfo();
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
