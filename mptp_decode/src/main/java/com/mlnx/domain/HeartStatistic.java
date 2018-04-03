package com.mlnx.domain;

/**
 * 心率统计
 *
 * @author fzh
 * @create 2018/4/3 10:56
 */
public class HeartStatistic {
    //平均心率
    private int avgHeart;
    //白天平均心率
    private int avgDayHeart;
    //夜晚平均心率
    private int avgNightHeart;
    //最大心率
    private int maxHeart;
    //最大心率发生时间
    private long maxOccurTime;
    //最慢心率
    private int minHeart;
    //最慢心率发生时间
    private long minOccurTime;

    public int getAvgHeart() {
        return avgHeart;
    }

    public void setAvgHeart(int avgHeart) {
        this.avgHeart = avgHeart;
    }

    public int getAvgDayHeart() {
        return avgDayHeart;
    }

    public void setAvgDayHeart(int avgDayHeart) {
        this.avgDayHeart = avgDayHeart;
    }

    public int getAvgNightHeart() {
        return avgNightHeart;
    }

    public void setAvgNightHeart(int avgNightHeart) {
        this.avgNightHeart = avgNightHeart;
    }

    public int getMaxHeart() {
        return maxHeart;
    }

    public void setMaxHeart(int maxHeart) {
        this.maxHeart = maxHeart;
    }

    public long getMaxOccurTime() {
        return maxOccurTime;
    }

    public void setMaxOccurTime(long maxOccurTime) {
        this.maxOccurTime = maxOccurTime;
    }

    public int getMinHeart() {
        return minHeart;
    }

    public void setMinHeart(int minHeart) {
        this.minHeart = minHeart;
    }

    public long getMinOccurTime() {
        return minOccurTime;
    }

    public void setMinOccurTime(long minOccurTime) {
        this.minOccurTime = minOccurTime;
    }
}
