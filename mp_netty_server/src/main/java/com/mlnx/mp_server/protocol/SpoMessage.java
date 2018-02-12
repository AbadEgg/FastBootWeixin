package com.mlnx.mp_server.protocol;

/**
 * Created by amanda.shan on 2018/2/12.
 */
public class SpoMessage extends AbstractMessage {

    private Long time;
    private Integer spo;
    private Integer heart;

    private Integer patientId;
    private String deviceId;

    private Long packetTime;

    public Long getPacketTime() {
        return packetTime;
    }

    public void setPacketTime(Long packetTime) {
        this.packetTime = packetTime;
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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getSpo() {
        return spo;
    }

    public void setSpo(Integer spo) {
        this.spo = spo;
    }

    public Integer getHeart() {
        return heart;
    }

    public void setHeart(Integer heart) {
        this.heart = heart;
    }
}
