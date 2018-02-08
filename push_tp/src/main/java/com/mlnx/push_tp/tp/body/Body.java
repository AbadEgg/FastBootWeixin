package com.mlnx.push_tp.tp.body;

/**
 * Created by amanda.shan on 2017/10/23.
 */
public class Body {

    private String topic;
    private Integer keepAliveTimer;
    private String pushMsg; // 推送数据
    private Long packetTime;

    private DeviceState deviceState;

    private String deviceId;
    private Integer messageId;
    private ResponseCode responseCode;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceState getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(DeviceState deviceState) {
        this.deviceState = deviceState;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getKeepAliveTimer() {
        return keepAliveTimer;
    }

    public void setKeepAliveTimer(Integer keepAliveTimer) {
        this.keepAliveTimer = keepAliveTimer;
    }

    public String getPushMsg() {
        return pushMsg;
    }

    public void setPushMsg(String pushMsg) {
        this.pushMsg = pushMsg;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public Long getPacketTime() {
        return packetTime;
    }

    public void setPacketTime(Long packetTime) {
        this.packetTime = packetTime;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
}
