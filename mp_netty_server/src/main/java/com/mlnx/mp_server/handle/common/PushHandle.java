package com.mlnx.mp_server.handle.common;

import com.mlnx.mp_server.protocol.EcgMessage;
import com.mlnx.mp_server.protocol.PublishMessage;
import com.mlnx.mp_session.core.Session;
import com.mlnx.mp_session.core.SessionManager;
import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.ResponseCode;
import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.mptp.body.Body;
import com.mlnx.mptp.mptp.body.DeviceState;
import com.mlnx.mptp.mptp.body.Topic;
import com.mlnx.mptp.mptp.head.QoS;
import com.mlnx.mptp.utils.MptpLogUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PushHandle extends SimpleChannelInboundHandler<PublishMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PublishMessage msg)
            throws Exception {

        String deviceId = msg.getBody().getDeviceId();
        final Body body = msg.getBody();
        Topic topic = msg.getTopic();

        if (topic == null && msg.getDeviceType().equals(DeviceType.USR)) {
            MptpLogUtils.e("非法主题，类型：" + msg.getDeviceType());
            MpPacket packet = new MpPacket().pushAck(DeviceType.SERVER,
                    body.getMessageId(), ResponseCode.ILLEGAL_TOPICE);
            ctx.channel().writeAndFlush(packet);
            return;
        }

        Session session = SessionManager.get(ctx.channel());
        if (body.getDeviceState() != null && body.getDeviceState().equals(DeviceState.WAIT_CONFIG)) {
            MptpLogUtils.i("收到：" + deviceId + " 等待配置的包");
            SessionManager.addConfig(session);
        }

        switch (msg.getDeviceType()) {
            // 用户发送给设备的主题
            case USR:
                switch (topic.getTopicType()) {
                    case D_ECG_TOPIC:
                }
                break;

            // 心电设备发送给用户的主题
            case ECG_DEVICE:

                EcgMessage ecgMessage = new EcgMessage();
                ecgMessage.setDeviceType(DeviceType.ECG_DEVICE);
                ecgMessage.setDeviceId(deviceId);
                ecgMessage.setEcgData(body.getEcgBody().getEcgData());
                ecgMessage.setEcgDeviceInfo(body.getEcgBody().getEcgDeviceInfo());
                ecgMessage.setDeviceInfoUpdate(body.getEcgBody().isDeviceInfoUpdate());
                ecgMessage.setPacketTime(body.getPacketTime());

                ctx.fireChannelRead(ecgMessage);
                break;
        }

        if (QoS.LEAST_ONE.equals(msg.getQoS())) {
            MpPacket packet = null;
            if (body.getMessageId() == null)
                packet = new MpPacket().pushAck(DeviceType.SERVER, null,
                        ResponseCode.LOST_MESSAGE_ID);
            else {
                packet = new MpPacket().pushAck(DeviceType.SERVER,
                        body.getMessageId(), ResponseCode.SUCESS);
            }
            ctx.channel().writeAndFlush(packet);
        }
    }
}
