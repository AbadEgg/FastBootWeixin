package com.mlnx.analysis.filter;

/**
 * Created by amanda.shan on 2018/3/23.
 */
public enum ECG_HIGHPASS_FILTER_LIST {

    ECG_LOWPASS_FILTER_70HZ((byte) 0),
    ECG_LOWPASS_FILTER_100HZ((byte) 1),
    ECG_LOWPASS_FILTER_150HZ((byte) 2),
    ECG_LOWPASS_FILTER_25HZ((byte) 3),
    ECG_LOWPASS_FILTER_35HZ((byte) 4),
    ECG_LOWPASS_FILTER_45HZ((byte) 5),
    ECG_LOWPASS_FILTER_MAX((byte) 6);

    private byte code;

    ECG_HIGHPASS_FILTER_LIST(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }
}
