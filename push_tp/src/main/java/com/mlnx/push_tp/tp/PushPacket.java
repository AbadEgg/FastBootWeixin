package com.mlnx.push_tp.tp;


import com.mlnx.push_tp.tp.body.Body;
import com.mlnx.push_tp.tp.body.ResponseCode;
import com.mlnx.push_tp.tp.head.DeviceType;
import com.mlnx.push_tp.tp.head.Header;
import com.mlnx.push_tp.tp.head.PacketType;
import com.mlnx.push_tp.tp.head.QoS;
import com.mlnx.push_tp.utils.PushLogUtils;

import java.util.Random;

public class PushPacket {

    private Header header;
    private Body body;

    public PushPacket() {
        super();

        header = new Header();
        body = new Body();
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
        return "PushPacket [header=" + header + ", body=" + body + "]";
    }

    /**
     * ping 服务器
     */
    public PushPacket ping(DeviceType deviceType) {

//		PushLogUtils.i("ping");
        header.setPacketType(PacketType.PINGREQ);
        header.setDeviceType(deviceType);

        return this;
    }

    /**
     * pong 服务器
     */
    public PushPacket pong(DeviceType deviceType) {

//		PushLogUtils.i("pong");
        header.setPacketType(PacketType.PINGRESP);
        header.setDeviceType(deviceType);

        return this;
    }

    /**
     * 注册 服务器
     */
    public PushPacket register(DeviceType deviceType) {

        PushLogUtils.i("注册服务器");

        header.setPacketType(PacketType.REGISTER);

        header.setDeviceType(deviceType);

        return this;
    }

    /**
     * 回复注册 服务器
     */
    public PushPacket registerAck(DeviceType deviceType, ResponseCode responseCode) {

//		PushLogUtils.i("回复注册服务器");
        header.setPacketType(PacketType.Reg_ACK);
        header.setDeviceType(deviceType);

        body.setResponseCode(responseCode);
        body.setPacketTime(System.currentTimeMillis());
        return this;
    }

    /**
     * 订阅消息
     */
    public PushPacket subscribe(DeviceType deviceType, String topic) {

//		PushLogUtils.i("订阅消息");
        header.setPacketType(PacketType.SUBSCRIBE);
        header.setDeviceType(deviceType);

        body.setTopic(topic);
        body.setPacketTime(System.currentTimeMillis());
        return this;
    }

    public PushPacket subscribeAck(DeviceType deviceType,
                                   ResponseCode responseCode) {
//		PushLogUtils.i("订阅消息回复");
        header.setPacketType(PacketType.SUB_ACK);
        header.setDeviceType(deviceType);

        body.setResponseCode(responseCode);
        return this;
    }

    /**
     * 订阅消息
     */
    public PushPacket unSubscribe(DeviceType deviceType, String topic) {

//		PushLogUtils.i("取消订阅消息");
        header.setPacketType(PacketType.UNSUBSCRIBE);
        header.setDeviceType(deviceType);

        body.setTopic(topic);
        body.setPacketTime(System.currentTimeMillis());
        return this;
    }

    public PushPacket unSubscribeAck(DeviceType deviceType,
                                     ResponseCode responseCode) {
//		PushLogUtils.i("取消订阅消息回复");
        header.setPacketType(PacketType.UNSUB_ACK);
        header.setDeviceType(deviceType);

        body.setResponseCode(responseCode);
        return this;
    }

    public PushPacket push(DeviceType deviceType, String topic, String msg) {

//		PushLogUtils.i("发布消息");
        header.setPacketType(PacketType.PUBLISH);
        header.setDeviceType(deviceType);
        header.setQoS(QoS.LEAST_ONE);

        this.body = new Body();
        body.setMessageId(new Random().nextInt());
        body.setTopic(topic);
        body.setPushMsg(msg);

        return this;
    }

    public PushPacket pushAck(DeviceType deviceType, Integer messageId,
                              ResponseCode responseCode) {

//		PushLogUtils.i("发布消息");
        header.setPacketType(PacketType.PUBLISH_ACK);
        header.setDeviceType(deviceType);

        body.setResponseCode(responseCode);
        body.setMessageId(messageId);
        return this;
    }

     public static void main(String[] args) {
     }

}
