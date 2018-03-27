package com.mlnx.mptp.mptp;

import com.mlnx.device.ecg.ECGChannelType;
import com.mlnx.device.ecg.ECGDeviceRunMode;
import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.PacketType;
import com.mlnx.mptp.ResponseCode;
import com.mlnx.mptp.mptp.body.Body;
import com.mlnx.mptp.mptp.config.MptpConfig;
import com.mlnx.mptp.mptp.head.Header;
import com.mlnx.mptp.utils.MptpLogUtils;

import java.nio.ByteBuffer;

public class MpPacket implements Codec {

    private static final ByteBuffer buffer = ByteBuffer
            .allocate(MptpConfig.MP_PACKET_CAPABILITY);

    private Header header;
    private Body body;

    public MpPacket() {
        super();
        init();
    }

    public MpPacket(boolean notInit) {
        super();
    }

    @Override
    public void init() {
        header = new Header();
        header.init();
        body = new Body();
        body.init();
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "MpPacket [header=" + header + ", body=" + body + "]";
    }

    @Override
    public void decode(ByteBuffer buf) throws InvalidPacketException {

        header.decode(buf);
        body.decode(buf);
    }

    /**
     * ping 服务器
     */
    public MpPacket ping(DeviceType deviceType) {

//		MptpLogUtils.i("ping");
        header.setPacketType(PacketType.PINGREQ);
        header.setDeviceType(deviceType);

        return this;
    }

    /**
     * pong 服务器
     */
    public MpPacket pong(DeviceType deviceType) {

//		MptpLogUtils.i("pong");
        header.setPacketType(PacketType.PINGRESP);
        header.setDeviceType(deviceType);

        return this;
    }

    /**
     * 注册 服务器
     */
    public MpPacket register(String deviceId, DeviceType deviceType) {

        MptpLogUtils.i("注册服务器");

        header.setPacketType(PacketType.REGISTER);

        header.setDeviceType(deviceType);
        body.setDeviceId(deviceId);
        body.init();

        return this;
    }

    /**
     * 注册 服务器
     */
    public MpPacket register(String name, String password, DeviceType deviceType) {

//		MptpLogUtils.i("注册服务器");

        header.setPacketType(PacketType.REGISTER);

        header.setDeviceType(deviceType);
        body.setName(name);
        body.setPassWord(password);

        return this;
    }

    /**
     * 回复注册 服务器
     */
    public MpPacket registerAck(DeviceType deviceType, ResponseCode responseCode) {

//		MptpLogUtils.i("回复注册服务器");
        header.setPacketType(PacketType.Reg_ACK);
        header.setDeviceType(deviceType);

        body.setResponseCode(responseCode);
        body.setPacketTime(System.currentTimeMillis());
        return this;
    }

    /**
     * 回复注册 服务器
     */
    public MpPacket registerAck(DeviceType deviceType, Integer patientId, ResponseCode responseCode) {

        body.setPatientId(patientId);
        registerAck(deviceType, responseCode);
        return this;
    }

    /**
     * 回复注册 服务器
     */
    public MpPacket registerAck(ECGChannelType ecgChannelType, ECGDeviceRunMode ecgDeviceRunMode, DeviceType
            deviceType, Integer patientId, ResponseCode responseCode) {
        body.getEcgBody().getEcgDeviceInfo().setEcgDeviceRunMode(ecgDeviceRunMode);
        body.getEcgBody().getEcgDeviceInfo().setEcgChannelType(ecgChannelType);
        registerAck(deviceType, patientId, responseCode);
        return this;
    }

    /**
     * 订阅消息
     */
    public MpPacket subscribe(DeviceType deviceType, String topic) {

//		MptpLogUtils.i("订阅消息");
        header.setPacketType(PacketType.SUBSCRIBE);
        header.setDeviceType(deviceType);

        body.setTopic(topic);
        body.setPacketTime(System.currentTimeMillis());
        return this;
    }

    public MpPacket subscribeAck(DeviceType deviceType,
                                 ResponseCode responseCode) {
//		MptpLogUtils.i("订阅消息回复");
        header.setPacketType(PacketType.SUB_ACK);
        header.setDeviceType(deviceType);

        body.setResponseCode(responseCode);
        return this;
    }

    /**
     * 订阅消息
     */
    public MpPacket unSubscribe(DeviceType deviceType, String topic) {

//		MptpLogUtils.i("取消订阅消息");
        header.setPacketType(PacketType.UNSUBSCRIBE);
        header.setDeviceType(deviceType);

        body.setTopic(topic);
        body.setPacketTime(System.currentTimeMillis());
        return this;
    }

    public MpPacket unSubscribeAck(DeviceType deviceType,
                                   ResponseCode responseCode) {
//		MptpLogUtils.i("取消订阅消息回复");
        header.setPacketType(PacketType.UNSUB_ACK);
        header.setDeviceType(deviceType);

        body.setResponseCode(responseCode);
        return this;
    }

    public MpPacket push(DeviceType deviceType, Body body) {

//		MptpLogUtils.i("发布消息");
        header.setPacketType(PacketType.PUBLISH);
        header.setDeviceType(deviceType);

        this.body = body;
        return this;
    }

    public MpPacket pushAck(DeviceType deviceType, Integer messageId,
                            ResponseCode responseCode) {

//		MptpLogUtils.i("发布消息");
        header.setPacketType(PacketType.PUBLISH_ACK);
        header.setDeviceType(deviceType);

        body.setResponseCode(responseCode);
        body.setMessageId(messageId);
        return this;
    }

    @Override
    public byte[] encode() {

        synchronized (MpPacket.class) {
            buffer.clear();

            byte[] bs = body.encode();
            header.setBodyLength(bs.length);
            buffer.put(header.encode());
            buffer.put(bs);
            buffer.flip();
            bs = new byte[buffer.remaining()];
            buffer.get(bs);

            return bs;
        }
    }

    public static MpPacket build(){
        MpPacket mpPacket = new MpPacket(false);
        return mpPacket;
    }

    public Header buildHeader(){
        if (header == null){
            header = new Header();
        }
        return header;
    }

    public Body buildBody(){
        if (body == null){
            body = new Body();
        }
        return body;
    }


}
