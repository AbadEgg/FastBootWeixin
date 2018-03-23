package com.mlnx.analysis.filter;

/**
 * Created by amanda.shan on 2018/3/23.
 */
public class EcgFilter {

    private ECG_HIGHPASS_FILTER_LIST highpassFilterList;

    private ECG_LOWPASS_FILTER_LIST lowpassFilterList;

    private ECG_STOP_FILTER_LIST ecgStopFilterList;

    public EcgFilter(ECG_HIGHPASS_FILTER_LIST highpassFilterList, ECG_LOWPASS_FILTER_LIST lowpassFilterList,
                     ECG_STOP_FILTER_LIST ecgStopFilterList) {
        this.highpassFilterList = highpassFilterList;
        this.lowpassFilterList = lowpassFilterList;
        this.ecgStopFilterList = ecgStopFilterList;
    }

    public ECG_HIGHPASS_FILTER_LIST getHighpassFilterList() {
        return highpassFilterList;
    }

    public void setHighpassFilterList(ECG_HIGHPASS_FILTER_LIST highpassFilterList) {
        this.highpassFilterList = highpassFilterList;
    }

    public ECG_LOWPASS_FILTER_LIST getLowpassFilterList() {
        return lowpassFilterList;
    }

    public void setLowpassFilterList(ECG_LOWPASS_FILTER_LIST lowpassFilterList) {
        this.lowpassFilterList = lowpassFilterList;
    }

    public ECG_STOP_FILTER_LIST getEcgStopFilterList() {
        return ecgStopFilterList;
    }

    public void setEcgStopFilterList(ECG_STOP_FILTER_LIST ecgStopFilterList) {
        this.ecgStopFilterList = ecgStopFilterList;
    }
}
