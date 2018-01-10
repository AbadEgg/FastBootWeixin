package com.mlnx.mp_server.handle.common;

import com.mlnx.mp_server.core.DeviceSession;
import com.mlnx.mp_server.core.Session;
import com.mlnx.mp_server.core.SessionManager;
import com.mlnx.mp_server.protocol.AbstractMessage;
import com.mlnx.mp_server.protocol.PublishMessage;
import com.mlnx.mp_server.protocol.RegisterMessage;
import com.mlnx.mp_server.protocol.SubscribeMessage;
import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.mptp.body.Body;
import com.mlnx.mptp.mptp.body.ResponseCode;
import com.mlnx.mptp.mptp.head.DeviceType;
import com.mlnx.mptp.mptp.head.Header;
import com.mlnx.mptp.utils.TopicUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MpServerHandle extends SimpleChannelInboundHandler<MpPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MpPacket mpPacket)
            throws Exception {

        Header header = mpPacket.getHeader();
        Body body = mpPacket.getBody();

        AbstractMessage message = null;
        MpPacket packet = null;
        Session session = null;


//        MptpLogUtils.d("PacketType:" + mpPacket.getHeader().getPacketType());

        switch (mpPacket.getHeader().getPacketType()) {
            case REGISTER:
                RegisterMessage registerMessage = new RegisterMessage();
                message = registerMessage;
                registerMessage.setDeviceId(body.getDeviceId());
                registerMessage.setKeepAliveTimer(body.getKeepAliveTimer());
                break;
            case SUBSCRIBE:
                SubscribeMessage subscribeMessage = new SubscribeMessage();
                subscribeMessage.setTopic(body.getTopic());
                message = subscribeMessage;
                break;
            case UNSUBSCRIBE:
                session = SessionManager.get(ctx.channel());
                session.setDeviceTopic(null);

                packet = new MpPacket().unSubscribeAck(DeviceType.SERVER,
                        ResponseCode.SUCESS);
                ctx.channel().writeAndFlush(packet);
                break;

            case PUBLISH:
                PublishMessage publishMessage = new PublishMessage();
                if (body.getTopic() != null) {
                    TopicUtils.DeviceTopic deviceTopic = TopicUtils
                            .judgeTopic(body.getTopic());
                    publishMessage.setDeviceTopic(deviceTopic);
                }
                publishMessage.setBody(body);
                if (session instanceof DeviceSession && body.getDeviceId() == null) {
                    body.setDeviceId(((DeviceSession) session).getDeviceId());
                }

                message = publishMessage;
                break;
            case PUBLISH_ACK:
                break;
            case PINGREQ:
                packet = new MpPacket().pong(DeviceType.SERVER);
                ctx.channel().writeAndFlush(packet);
                break;
            case PINGRESP:
                break;
            default:
        }

        if (message != null) {
            message.setDeviceType(header.getDeviceType());
            message.setQoS(header.getQoS());
            ctx.fireChannelRead(message);
        }
    }

}
