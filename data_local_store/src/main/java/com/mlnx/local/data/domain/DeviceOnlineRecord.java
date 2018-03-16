package com.mlnx.local.data.domain;

import java.util.Date;

/**
 * 记录设备上下线时间(存入mongodb)
 *
 * @author fzh
 * @create 2018/3/13 15:43
 */
public class DeviceOnlineRecord {

    private Integer patientId;

    private String deviceId;

    private Date date;

    private String deviceState;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(String deviceState) {
        this.deviceState = deviceState;
    }
}
