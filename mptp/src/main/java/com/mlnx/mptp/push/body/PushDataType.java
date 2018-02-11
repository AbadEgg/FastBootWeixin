package com.mlnx.mptp.push.body;

/**
 * Created by amanda.shan on 2018/2/8.
 */
public enum  PushDataType {

    ECG_INFO("心电数据"),
    ;

    private String title;

    PushDataType(String title) {
        this.title = title;
    }
}
