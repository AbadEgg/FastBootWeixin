package com.mlnx.mp_server.protocol;

/**
 * @author fzh
 * @create 2018/3/20 10:34
 */
public class CO2Message extends AbstractMessage {

    private float co2Value;

    private Integer patientId;
    private String deviceId;

    private Long packetTime;

    public float getCo2Value() {
        return co2Value;
    }

    public void setCo2Value(float co2Value) {
        this.co2Value = co2Value;
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

    public Long getPacketTime() {
        return packetTime;
    }

    public void setPacketTime(Long packetTime) {
        this.packetTime = packetTime;
    }
}
