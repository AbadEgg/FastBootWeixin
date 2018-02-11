package com.mlnx.mp_session.core;

import com.mlnx.mp_session.listenner.BroadCast;
import com.mlnx.mp_session.listenner.ecg.EcgListener;
import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.mptp.body.Topic;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.Date;
import java.util.List;

import io.netty.channel.Channel;

public abstract class Session implements Serializable {

    public static final int DeviceReaderIdleMaxTimeSeconds = 20;
    public static final int WebReaderIdleMaxTimeSeconds = 60;

    protected Channel channel;
    protected SocketAddress socketAddress;
    protected Date lastPacketTime;        // 最后一次收到数据包的时间
    protected List<Topic> topics; // 主题 用户消息分发
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

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;


        BroadCast.addEcgListenner(getEcgListener());
    }

    public abstract void removeLis();

    public abstract EcgListener getEcgListener();

    @Override
    public String toString() {
        return "Session{" +
                "channel=" + channel +
                ", socketAddress=" + socketAddress +
                ", lastPacketTime=" + lastPacketTime +
                ", topics=" + topics +
                ", deviceType=" + deviceType +
                ", patientId=" + patientId +
                ", key='" + key + '\'' +
                ", readerIdleTimeSeconds=" + readerIdleTimeSeconds +
                ", timeOut=" + timeOut +
                '}';
    }
}
