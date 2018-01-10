package com.mlnx.device.ecg;

import java.io.Serializable;

/**
 * Created by amanda.shan on 2017/12/19.
 */
public class EcgDeviceInfo implements Serializable {

    private static final long serialVersionUID = 7247714666080613254L;

    private Integer patientId;
    private ECGDeviceRunMode ecgDeviceRunMode;
    private ECGChannelType ecgChannelType;

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public ECGDeviceRunMode getEcgDeviceRunMode() {
        return ecgDeviceRunMode;
    }

    public void setEcgDeviceRunMode(ECGDeviceRunMode ecgDeviceRunMode) {
        this.ecgDeviceRunMode = ecgDeviceRunMode;
    }

    public ECGChannelType getEcgChannelType() {
        return ecgChannelType;
    }

    public void setEcgChannelType(ECGChannelType ecgChannelType) {
        this.ecgChannelType = ecgChannelType;
    }

    @Override
    public String toString() {
        return "EcgDeviceInfo{" +
                "patientId=" + patientId +
                ", ecgDeviceRunMode=" + ecgDeviceRunMode +
                ", ecgChannelType=" + ecgChannelType +
                '}';
    }
}
