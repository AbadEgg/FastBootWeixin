package com.mlnx.mptp.push.body;

/**
 * Created by amanda.shan on 2018/2/8.
 */
public enum  PushDataType {

    ECG_INFO("心电数据"),
    SPO_INFO("血氧数据"),
    BP_INFO("血压数据"),
    TEMP_INFO("体温数据"),

    ASK_DEVICE_INFO("询问设备信息"),
    ;

    private String title;

    PushDataType(String title) {
        this.title = title;
    }
}
