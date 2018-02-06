package com.mlnx.mptp.mptp.body;

/**
 * Created by amanda.shan on 2018/2/6.
 */
public enum TopicType {

    U_ECG_TOPIC("u/ecg/data/", "没有加密心电数据"), U_ECG_HEART_TOPIC("u/ecg/heart/", "心率"),
    U_ECG_ENCRYPTION_TOPIC("u/ecg/encryption/data/", "加密心电数据"),
    U_ECG_DEVICE_TOPIC("u/ecg/device/", "心电设备信息"), U_ECG_REAL_ANALY_TOPIC("u/ecg/analy_result/", "实时分析结果"),

    D_ECG_TOPIC("d/ecg/");

    private String prefix;
    private String title;

    private TopicType(String type) {
        this.prefix = type;
    }

    TopicType(String prefix, String title) {
        this.prefix = prefix;
        this.title = title;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
