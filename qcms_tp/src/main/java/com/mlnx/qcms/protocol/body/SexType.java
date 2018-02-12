package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.QcmsLogUtils;

public enum SexType {

    PATIENT_SEX_MALE(0x00,"男"),
    PATIENT_SEX_FAMALE(0x01,"女"),
    PATIENT_SEX_OTHERS(0x02,"其他");

    private int code;

    private String description;

    private static final int BITMASK = 0xFF;

    SexType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static SexType decode(int sexTypeCode) {

        int code = sexTypeCode & BITMASK;
        for (SexType sexType : SexType.values()) {
            if (sexType.code == code) {
                QcmsLogUtils.mpDecode("receive sexType:" + sexType);
                return sexType;
            }
        }
        return null;
    }
}
