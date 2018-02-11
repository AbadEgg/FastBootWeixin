package com.mlnx.mptp.push.body;

import com.mlnx.mptp.ResponseCode;

import java.util.Map;

/**
 * Created by amanda.shan on 2017/10/23.
 */
public class Body {

    private String topic;
    private SerialType pushSerialType;
    private Integer keepAliveTimer;

    private String name;
    private String password;

    private Map<PushDataType, Object> pushDataMap;  // 推送的数据
    private Long packetTime;

    private Integer messageId;
    private ResponseCode responseCode;

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

    public Map<PushDataType, Object> getPushDataMap() {
        return pushDataMap;
    }

    public void setPushDataMap(Map<PushDataType, Object> pushDataMap) {
        this.pushDataMap = pushDataMap;
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

    public SerialType getPushSerialType() {
        return pushSerialType;
    }

    public void setPushSerialType(SerialType pushSerialType) {
        this.pushSerialType = pushSerialType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
