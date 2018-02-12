package com.mlnx.qcms.protocol.head;

import com.mlnx.qcms.utils.QcmsLogUtils;

/**
 * 设备类型
 */
public enum DeviceType {

    PATIENT_MONITOR(0x00,"病人监控设备"),
    NARCOSIS_MONITOR(0x01,"麻醉监控设备"),
    CENTER_MONITOR_SERVER(0x02,"中心监控服务器设备"),
    UNKNOW_DEVICE(0x03,"未知设备"),
    QDEVICE_MAX(0x04,"最大设备");

    private int code;

    private String description;

    private static final int BITMASK = 0xFF;

    DeviceType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static DeviceType decode(int deviceCode) {

        int code = deviceCode & BITMASK;
        for (DeviceType deviceType : DeviceType.values()) {
            if (deviceType.code == code) {
                QcmsLogUtils.mpDecode("receive deviceType:" + deviceType);
                return deviceType;
            }
        }
        return null;
    }
}
