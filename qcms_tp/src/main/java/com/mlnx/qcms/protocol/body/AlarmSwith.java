package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.QcmsLogUtils;

public enum AlarmSwith {

    ALARM_SWITCH_CLOSE(0x00,""),
    ALARM_SWITCH_OPEN(0x01,"");

    private int code;

    private String description;

    private static final int BITMASK = 0xFF;

    AlarmSwith(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static AlarmSwith decode(int alarmSwithCode) {

        int code = alarmSwithCode & BITMASK;
        for (AlarmSwith alarmSwith : AlarmSwith.values()) {
            if (alarmSwith.code == code) {
                QcmsLogUtils.mpDecode("receive alarmSwith:" + alarmSwith);
                return alarmSwith;
            }
        }
        return null;
    }
}
