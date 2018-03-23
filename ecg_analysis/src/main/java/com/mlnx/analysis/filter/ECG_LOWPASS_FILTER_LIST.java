package com.mlnx.analysis.filter;

/**
 * Created by amanda.shan on 2018/3/23.
 */
public enum ECG_LOWPASS_FILTER_LIST {

    ECG_HIGHPASS_FILTER_005HZ((byte) 0),

    ECG_HIGHPASS_FILTER_01HZ((byte) 1),

    ECG_HIGHPASS_FILTER_02HZ((byte) 2),

    ECG_HIGHPASS_FILTER_05HZ((byte) 3),

    ECG_HIGHPASS_FILTER_002HZ((byte) 4),

    ECG_HIGHPASS_FILTER_MAX((byte) 5);

    private byte code;

    ECG_LOWPASS_FILTER_LIST(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }
}
