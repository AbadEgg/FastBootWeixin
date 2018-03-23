package com.mlnx.analysis.filter;

/**
 * Created by amanda.shan on 2018/3/23.
 */
public enum  ECG_STOP_FILTER_LIST {

    ECG_STOP_FILTER_50((byte)0),
    ECG_STOP_FILTER_60((byte)1),		// 60Hz trap filter
    NO_ECG_STOP_FILTER((byte)2),		//add by Brook for correct
    ECG_STOP_FILTER_MAX((byte)3);

    private byte code;

    ECG_STOP_FILTER_LIST(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }
}
