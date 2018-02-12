package com.mlnx.mp_server.protocol;

import com.mlnx.mptp.model.ECGData;
import com.mlnx.mptp.model.ECGDeviceInfo;

/**
 * Created by amanda.shan on 2018/2/12.
 */
public class EcgMessage extends AbstractMessage {

    private String deviceId;

    private ECGData ecgData;
    private Long packetTime;
    private ECGDeviceInfo ecgDeviceInfo;

    private boolean deviceInfoUpdate = false;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public ECGData getEcgData() {
        return ecgData;
    }

    public void setEcgData(ECGData ecgData) {
        this.ecgData = ecgData;
    }

    public Long getPacketTime() {
        return packetTime;
    }

    public void setPacketTime(Long packetTime) {
        this.packetTime = packetTime;
    }

    public ECGDeviceInfo getEcgDeviceInfo() {
        return ecgDeviceInfo;
    }

    public void setEcgDeviceInfo(ECGDeviceInfo ecgDeviceInfo) {
        this.ecgDeviceInfo = ecgDeviceInfo;
    }

    public boolean isDeviceInfoUpdate() {
        return deviceInfoUpdate;
    }

    public void setDeviceInfoUpdate(boolean deviceInfoUpdate) {
        this.deviceInfoUpdate = deviceInfoUpdate;
    }
}
