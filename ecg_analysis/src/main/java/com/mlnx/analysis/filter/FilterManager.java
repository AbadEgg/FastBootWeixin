package com.mlnx.analysis.filter;

/**
 * Created by amanda.shan on 2018/3/23.
 */
public class FilterManager {


    public static EcgFilter getSsportEcgFilter() {
        return new EcgFilter(ECG_HIGHPASS_FILTER_LIST.ECG_LOWPASS_FILTER_25HZ, ECG_LOWPASS_FILTER_LIST
                .ECG_HIGHPASS_FILTER_05HZ, ECG_STOP_FILTER_LIST.ECG_STOP_FILTER_50);
    }

    public static EcgFilter getSportEcgFilter() {
        return new EcgFilter(ECG_HIGHPASS_FILTER_LIST.ECG_LOWPASS_FILTER_45HZ, ECG_LOWPASS_FILTER_LIST
                .ECG_HIGHPASS_FILTER_05HZ, ECG_STOP_FILTER_LIST.ECG_STOP_FILTER_50);
    }

    public static EcgFilter getEcgFilter() {
        return new EcgFilter(ECG_HIGHPASS_FILTER_LIST.ECG_LOWPASS_FILTER_45HZ, ECG_LOWPASS_FILTER_LIST
                .ECG_HIGHPASS_FILTER_05HZ, ECG_STOP_FILTER_LIST.ECG_STOP_FILTER_50);
    }
}
