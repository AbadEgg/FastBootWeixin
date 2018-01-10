package com.mlnx.mp_server.core;

import com.mlnx.mp_server.listenner.BroadCast;
import com.mlnx.mp_server.listenner.EcgListenner;
import com.mlnx.mptp.mptp.head.DeviceType;
import com.mlnx.mptp.utils.TopicUtils;
import com.mlnx.mptp.utils.TopicUtils.DeviceTopic;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.Date;

import io.netty.channel.Channel;

public abstract class Session implements Serializable {

    protected Channel channel;
    protected SocketAddress socketAddress;
    protected Date lastPacketTime;        // 最后一次收到数据包的时间
    protected TopicUtils.DeviceTopic deviceTopic; // 主题 用户消息分发
    protected DeviceType deviceType;
    protected Integer patientId;

    protected String key;

    private int readerIdleTimeSeconds; // 接收超时时间

    protected boolean timeOut;

    public Session(int readerIdleTimeSeconds) {
        super();
        this.readerIdleTimeSeconds = readerIdleTimeSeconds;
        lastPacketTime = new Date();
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getReaderIdleTimeSeconds() {
        return readerIdleTimeSeconds;
    }

    public void setReaderIdleTimeSeconds(int readerIdleTimeSeconds) {
        this.readerIdleTimeSeconds = readerIdleTimeSeconds;
    }

    public boolean isTimeOut() {
        return timeOut;
    }

    public void setTimeOut(boolean timeOut) {
        this.timeOut = timeOut;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public Date getLastPacketTime() {
        return lastPacketTime;
    }

    public void setLastPacketTime(Date lastPacketTime) {
        this.lastPacketTime = lastPacketTime;
    }

    public DeviceTopic getDeviceTopic() {
        return deviceTopic;
    }

    public abstract void removeLis();

    public void setDeviceTopic(DeviceTopic deviceTopic) {
        this.deviceTopic = deviceTopic;

        BroadCast.removeEcgListenner(getEcgListener());

        if (deviceTopic != null) {
            switch (deviceTopic.getTopicType()) {
                case D_ECG_TOPIC:
                case U_ECG_TOPIC:
                case U_ECG_HEART_TOPIC:
                    BroadCast.addEcgListenner(getEcgListener());
                    break;
            }
        }
    }

    public abstract EcgListenner getEcgListener();

    @Override
    public String toString() {
        return "Session [channel=" + channel + ", socketAddress="
                + socketAddress + ", lastPacketTime=" + lastPacketTime + ", deviceTopic="
                + (deviceTopic == null ? null : deviceTopic.getTopic()) + ", deviceType=" + deviceType
                + ", readerIdleTimeSeconds=" + readerIdleTimeSeconds
                + ", timeOut=" + timeOut + "]";
    }

}
