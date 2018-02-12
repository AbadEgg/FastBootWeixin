package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.QcmsLogUtils;

public enum NibpMeasureMode {
    NIBP_MODE_IDLE(0x00,""),
    NIBP_MODE_MANU(0x01,""),
    NIBP_MODE_AUTO(0x02,""),
    NIBP_MODE_CONTINUE(0x03,""),
    NIBP_MODE_MAX(0x04,"");

    private int code;

    private String description;

    private static final int BITMASK = 0xFF;

    NibpMeasureMode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static NibpMeasureMode decode(int nibpMeasureCode) {

        int code = nibpMeasureCode & BITMASK;
        for (NibpMeasureMode nibpMeasureMode : NibpMeasureMode.values()) {
            if (nibpMeasureMode.code == code) {
                QcmsLogUtils.mpDecode("receive nibpMeasureMode:" + nibpMeasureMode);
                return nibpMeasureMode;
            }
        }
        return null;
    }
}
