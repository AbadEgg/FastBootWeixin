package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.QcmsLogUtils;

public enum WaveEnum {

    WAVE_BEGIN(0x00,""),
    WAVE_ECG(0x00,""),
    WAVE_SPO2(0x01,""),
    WAVE_RESP(0x02,""),
    WAVE_CO2(0x03,""),
    WAVE_AG_CO2(0x04,""),
    WAVE_AG_N2O(0x05,""),
    WAVE_AG_AA(0x06,""),
    WAVE_AG_O2(0x07,""),
    WAVE_IBP1(0x08,""),
    WAVE_IBP2(0x09,""),
    WAVE_IBP3(0x0a,""),
    WAVE_IBP4(0x0b,""),
    WAVE_BIS(0x0c,""),

    WAVE_MAX(0x0d,"");

    private int code;

    private String description;

    private static final int BITMASK = 0xFF;

    WaveEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static WaveEnum decode(int waveEnumCode) {

        int code = waveEnumCode & BITMASK;
        for (WaveEnum waveEnum : WaveEnum.values()) {
            if (waveEnum.code == code) {
                QcmsLogUtils.mpDecode("receive waveEnum:" + waveEnum);
                return waveEnum;
            }
        }
        return null;
    }
}
