package com.mlnx.mptp.model.analysis;

import java.util.Arrays;

/**
 * Created by amanda.shan on 2018/2/6.
 */
public class RealEcgAnalysResult {

    private long time;

    private byte[] ecgData; // 解析出来的心电数据

    private Integer heart;
    private Integer pbNumb; // 早搏个数

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
