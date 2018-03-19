package com.mlnx.mp_server.protocol;

/**
 * @author fzh
 * @create 2018/3/19 10:28
 */
public class TempMessage extends AbstractMessage {

    private float temp;
    private Integer patientId;
    private String deviceId;

    private Long packetTime;

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
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
