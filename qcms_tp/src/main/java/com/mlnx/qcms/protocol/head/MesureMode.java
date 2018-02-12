package com.mlnx.qcms.protocol.head;

import com.mlnx.qcms.utils.QcmsLogUtils;

public enum MesureMode {

    DATA(0X00,""),WAVE(0X01,"");

    private int code;

    private String description;

    private static final int BITMASK = 0xFF;

    MesureMode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static MesureMode decode(int mesureCode) {

        int code = mesureCode & BITMASK;
        for (MesureMode mesureMode : MesureMode.values()) {
            if (mesureMode.code == code) {
                QcmsLogUtils.mpDecode("receive mesureMode:" + mesureMode);
                return mesureMode;
            }
        }
        return null;
    }
}
