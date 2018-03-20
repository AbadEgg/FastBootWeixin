package com.mlnx.mptp.model.analysis;

import java.util.Arrays;

/**
 * Created by amanda.shan on 2018/2/6.
 */
public class RealEcgAnalysResult {

    private long time;

    private byte[] ecgData; // 解析出来的心电数据
    private byte[] filterEcgData; // 滤波后的的心电数据

    private Integer heart;
    private Integer pbNumb; // 早搏个数
    private int[] sts = new int[8];

    private HeartResult heartResult;    // 心率失常疾病

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public byte[] getEcgData() {
        return ecgData;
    }

    public void setEcgData(byte[] ecgData) {
        this.ecgData = ecgData;
    }

    public byte[] getFilterEcgData() {
        return filterEcgData;
    }

    public void setFilterEcgData(byte[] filterEcgData) {
        this.filterEcgData = filterEcgData;
    }

    public Integer getHeart() {
        return heart;
    }

    public void setHeart(Integer heart) {
        this.heart = heart;
    }

    public Integer getPbNumb() {
        return pbNumb;
    }

    public void setPbNumb(Integer pbNumb) {
        this.pbNumb = pbNumb;
    }

    public HeartResult getHeartResult() {
        return heartResult;
    }

    public void setHeartResult(HeartResult heartResult) {
        this.heartResult = heartResult;
    }

    public int[] getSts() {
        return sts;
    }

    public void setSts(int[] sts) {
        this.sts = sts;
    }

    @Override
    public String toString() {
        return "RealEcgAnalysResult{" +
                "time=" + time +
                ", ecgData=" + Arrays.toString(ecgData) +
                ", heart=" + heart +
                ", pbNumb=" + pbNumb +
                ", heartResult=" + heartResult +
                '}';
    }
}
