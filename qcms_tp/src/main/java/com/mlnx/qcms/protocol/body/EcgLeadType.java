package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.QcmsLogUtils;

public enum EcgLeadType {

    ECG_LEAD_TYPE_3(0x00,"3leads"),
    ECG_LEAD_TYPE_5(0x01,"5leads"),
    ECG_LEAD_TYPE_12(0x02,"12leads"),
    ECG_LEAD_TYPE_MAX(0x03,"");

    private int code;

    private String description;

    private static final int BITMASK = 0xFF;

    EcgLeadType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static EcgLeadType decode(int ecgCode) {

        int code = ecgCode & BITMASK;
        for (EcgLeadType ecgLeadType : EcgLeadType.values()) {
            if (ecgLeadType.code == code) {
                QcmsLogUtils.mpDecode("receive ecgLeadType:" + ecgLeadType);
                return ecgLeadType;
            }
        }
        return null;
    }
}
