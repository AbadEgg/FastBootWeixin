package com.mlnx.analysis.domain;

/**
 * Created by amanda.shan on 2018/2/6.
 */
public enum HeartResult {

    /**
     * 0  心搏停止  1  纤维性颤动 VF
     * 2  室性心动过速  3  R ON T
     * 4  多连发室性早搏  5  未用
     * 6  二连室性早搏  7  偶发室性早搏
     * 8  二联律  9  三联律
     * 10  心动过速  11  心动过缓
     * 12  多形 PVC  13  起搏器未俘获
     * 14  起搏器未起搏  15  不规则节律
     * 16  漏博  17  未用
     * 18  未用  19  未用
     * 20  未用  21  未用
     * 22  正常窦性心律  23  未用
     * 24  噪声过大
     */

    HEART_0(0, "心搏停止"), HEART_1(1, "纤维性颤动 VF"), HEART_2(2, "室性心动过速"),
    HEART_3(3, "R ON T"), HEART_4(4, "多连发室性早搏"), HEART_5(5, "未用"),
    HEART_6(6, "二连室性早搏"), HEART_7(7, "偶发室性早搏"), HEART_8(8, "二联律"),
    HEART_9(9, "三联律"), HEART_10(10, "心动过速"), HEART_11(11, "心动过缓"),
    HEART_12(12, "多形 PVC"), HEART_13(13, "起搏器未俘获"), HEART_14(14, "起搏器未起搏"),
    HEART_15(15, "不规则节律"), HEART_16(16, "漏博"), HEART_20(20, "正常窦性心律"), HEART_24(24, "噪声过大");

    private int code;
    private String title;

    HeartResult(int code, String title) {
        this.code = code;
        this.title = title;
    }

    public static HeartResult decode(int code){
        for (HeartResult heartResult : HeartResult.values()){
            if (code == heartResult.getCode()){
                return heartResult;
            }
        }

        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
