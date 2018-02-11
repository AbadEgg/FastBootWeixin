package com.mlnx.mptp.model;


import com.mlnx.mptp.model.analysis.HeartResult;

/**
 * Created by amanda.shan on 2018/2/6.
 */
public class EcgAnalysisResult {

    private Integer heart;
    private Integer pbNumb; // 早搏个数

    private HeartResult heartResult;    // 心率失常疾病

    public Integer getHeart() {
        return heart;
    }

    public EcgAnalysisResult setHeart(Integer heart) {
        this.heart = heart;
        return this;
    }

    public Integer getPbNumb() {
        return pbNumb;
    }

    public EcgAnalysisResult setPbNumb(Integer pbNumb) {
        this.pbNumb = pbNumb;
        return this;
    }

    public HeartResult getHeartResult() {
        return heartResult;
    }

    public EcgAnalysisResult setHeartResult(HeartResult heartResult) {
        this.heartResult = heartResult;
        return this;
    }

    @Override
    public String toString() {
        return "EcgAnalysisResult{" +
                "heart=" + heart +
                ", pbNumb=" + pbNumb +
                ", heartResult=" + heartResult +
                '}';
    }
}
