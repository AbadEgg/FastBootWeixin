package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.QcmsLogUtils;

public enum ParamName {

    PARAM_DEFINE(0x00,""),
    PARAM_HR(0x00,""),
    PARAM_PVCS(0x01,""),
    PARAM_ST1(0x02,""),
    PARAM_ST2(0x03,""),
    PARAM_ST3(0x04,""),
    PARAM_ST4(0x05,""),
    PARAM_ST5(0x06,""),
    PARAM_ST6(0x07,""),
    PARAM_ST7(0x08,""),
    PARAM_ST8(0x09,""),
    PARAM_ST9(0x0a,""),
    PARAM_ST10(0x0b,""),
    PARAM_ST11(0x0c,""),
    PARAM_ST12(0x0d,""),
    PARAM_SPO2(0x0e,""),
    PARAM_PR(0x0f,""),
    PARAM_NIBP_SYS(0x10,""),
    PARAM_NIBP_MEAN(0x11,""),
    PARAM_NIBP_DIA(0x12,""),
    PARAM_RESP(0x13,""),
    PARAM_T1(0x14,""),
    PARAM_T2(0x15,""),
    PARAM_TD(0x16,""),
    PARAM_ETCO2(0x17,""),
    PARAM_FICO2(0x18,""),
    PARAM_AWRR(0x19,""),
    PARAM_IBP1_SYS(0x1a,""),
    PARAM_IBP1_MEAN(0x1b,""),
    PARAM_IBP1_DIA(0x1c,""),
    PARAM_IBP2_SYS(0x1d,""),
    PARAM_IBP2_MEAN(0x1e,""),
    PARAM_IBP2_DIA(0x1f,""),
    PARAM_IBP3_SYS(0x20,""),
    PARAM_IBP3_MEAN(0x21,""),
    PARAM_IBP3_DIA(0x22,""),
    PARAM_IBP4_SYS(0x23,""),
    PARAM_IBP4_MEAN(0x24,""),
    PARAM_IBP4_DIA(0x25,""),
    PARAM_CO_BT(0x26,""),
    PARAM_AG_ETCO2(0x27,""),
    PARAM_AG_FICO2(0x28,""),
    PARAM_AG_AWRR(0x29,""),
    PARAM_AG_ETO2(0x2a,""),
    PARAM_AG_FIO2(0x2b,""),
    PARAM_AG_ETN2O(0x2c,""),
    PARAM_AG_FIN2O(0x2d,""),
    PARAM_AG_ETAA(0x2e,""),
    PARAM_AG_FIAA(0x2f,""),
    PARAM_BIS(0x30,""),

    PARAM_MAX(0x31,"");

    private int code;

    private String description;

    private static final int BITMASK = 0xFF;

    ParamName(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ParamName decode(int paramNameCode) {

        int code = paramNameCode & BITMASK;
        for (ParamName paramName : ParamName.values()) {
            if (paramName.code == code) {
                QcmsLogUtils.mpDecode("receive paramName:" + paramName);
                return paramName;
            }
        }
        return null;
    }
}
