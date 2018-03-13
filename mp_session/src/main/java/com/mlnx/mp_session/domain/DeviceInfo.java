package com.mlnx.mp_session.domain;

import com.mlnx.mptp.DeviceType;

/**
 * Created by amanda.shan on 2018/2/11.
 */
public class DeviceInfo {

    private Integer patientId;

    private String deivceId;

    private DeviceType deviceType;

    private Long packetTime;

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getDeivceId() {
        return deivceId;
    }

    public void setDeivceId(String deivceId) {
        this.deivceId = deivceId;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public Long getPacketTime() {
        return packetTime;
    }

    public void setPacketTime(Long packetTime) {
        this.packetTime = packetTime;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "patientId=" + patientId +
                ", deivceId='" + deivceId + '\'' +
                ", packetTime=" + packetTime +
                '}';
    }
}
