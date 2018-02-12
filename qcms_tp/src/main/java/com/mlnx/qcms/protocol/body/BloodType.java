package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.QcmsLogUtils;

public enum BloodType {

    BLOOD_A(0x00,""),
    BLOOD_B(0x01,""),
    BLOOD_AB(0x02,""),
    BLOOD_O(0x03,""),

    BLOOD_MAX(0x04,"");

    private int code;

    private String description;

    private static final int BITMASK = 0xFF;

    BloodType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static BloodType decode(int bloodTypeCode) {

        int code = bloodTypeCode & BITMASK;
        for (BloodType bloodType : BloodType.values()) {
            if (bloodType.code == code) {
                QcmsLogUtils.mpDecode("receive bloodType:" + bloodType);
                return bloodType;
            }
        }
        return null;
    }
}
