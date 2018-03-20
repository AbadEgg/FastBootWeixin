package com.mlnx.mptp.mptp.body;

/**
 * Created by amanda.shan on 2018/2/6.
 */
public enum TopicType {

    U_ECG_TOPIC("没有加密心电数据", 1),
    U_ECG_ENCRYPTION_TOPIC("加密心电数据", 1),
    U_ECG_REAL_ANALY_TOPIC("实时分析结果", 1),
    U_ECG_HEART_TOPIC("心率", 1),
    U_ECG_DEVICE_TOPIC("心电设备信息", 1),

    U_BP_TOPIC("血压数据", 2),
    U_SPO_TOPIC("血氧数据", 3),
    U_BT_TOPIC("呼吸数据", 4),
    U_TEMP_TOPIC("体温数据",7),
    U_CO2_TOPIC("CO2数据",8),

    D_ECG_TOPIC("用户发送信息给设备", 5),

    U_DEVICE_ONLINE_TOPIC("设备是否在线",6);

    private String title;
    private int type;

    TopicType(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }
}
