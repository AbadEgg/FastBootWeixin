package com.mlnx.domain;

/**
 * 事件统计
 *
 * @author fzh
 * @create 2018/4/3 11:22
 */
public class EventStatistic {

    //停搏超过2s
    private int stopHeartCount;
    //心动过缓
    private int slowHeartCount;
    //心动过速
    private int fastHeartCount;

    public int getStopHeartCount() {
        return stopHeartCount;
    }

    public void setStopHeartCount(int stopHeartCount) {
        this.stopHeartCount = stopHeartCount;
    }

    public int getSlowHeartCount() {
        return slowHeartCount;
    }

    public void setSlowHeartCount(int slowHeartCount) {
        this.slowHeartCount = slowHeartCount;
    }

    public int getFastHeartCount() {
        return fastHeartCount;
    }

    public void setFastHeartCount(int fastHeartCount) {
        this.fastHeartCount = fastHeartCount;
    }
}
