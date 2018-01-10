package com.mlnx.device_server.domain;

import com.mlnx.mp_server.core.EcgDeviceSession;
import com.mlnx.mptp.mptp.head.DeviceType;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by amanda.shan on 2017/4/1.
 */
public class EcgOnlineDevice {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Integer patientId;
    private String deviceId;
    protected String lastTime;
    protected DeviceType deviceType;

    public EcgOnlineDevice(EcgDeviceSession ecgDeviceSession) {
        deviceId = ecgDeviceSession.getDeviceId();
        if (ecgDeviceSession.getLastEcgDataTime() != null)
            lastTime = format.format(new Date(ecgDeviceSession.getLastEcgDataTime()));
        else lastTime = "未收到心电数据包";
        deviceType = ecgDeviceSession.getDeviceType();
        patientId = ecgDeviceSession.getPatientId();
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
}
