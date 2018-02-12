package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.QcmsLogUtils;

public enum AlarmLevel {

    ALARM_LEVEL_HIGH(0x00,""),
    ALARM_LEVEL_MID(0x01,""),
    ALARM_LEVEL_LOW(0x02,""),
    ALARM_LEVEL_TXT(0x03,"");

    private int code;

    private String description;

    private static final int BITMASK = 0xFF;

    AlarmLevel(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static AlarmLevel decode(int alarmLevelCode) {

        int code = alarmLevelCode & BITMASK;
        for (AlarmLevel alarmLevel : AlarmLevel.values()) {
            if (alarmLevel.code == code) {
                QcmsLogUtils.mpDecode("receive alarmLevel:" + alarmLevel);
                return alarmLevel;
            }
        }
        return null;
    }
}
