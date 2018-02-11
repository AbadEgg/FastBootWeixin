package com.mlnx.mp_server.handle.web;

import com.mlnx.mp_server.protocol.AbstractMessage;
import com.mlnx.mp_server.protocol.RegisterMessage;
import com.mlnx.mp_server.protocol.SubscribeMessage;
import com.mlnx.mp_server.protocol.WebPublishMessage;
import com.mlnx.mp_session.core.Session;
import com.mlnx.mp_session.core.SessionManager;
import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.ResponseCode;
import com.mlnx.mptp.push.PushPacket;
import com.mlnx.mptp.push.body.Body;
import com.mlnx.mptp.push.head.Header;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by amanda.shan on 2017/9/11.
 */
public class PushServerHandle extends SimpleChannelInboundHandler<PushPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PushPacket pushPacket) throws Exception {
        Header header = pushPacket.getHeader();
        Body body = pushPacket.getBody();
        body.setPacketTime(System.currentTimeMillis());

        AbstractMessage message = null;
        PushPacket sendLifePacket = null;

        Session session = SessionManager.get(ctx.channel());
        if (session != null) {
            SessionManager.refreshLastTime(ctx.channel());
            session.setTimeOut(false);
        }

        switch (pushPacket.getHeader().getPacketType()) {
            case REGISTER:
                RegisterMessage registerMessage = new RegisterMessage();
                message = registerMessage;
                registerMessage.setKeepAliveTimer(body.getKeepAliveTimer());
                registerMessage.setUsrName(body.getName());
                registerMessage.setPassword(body.getPassword());
                break;
            case SUBSCRIBE:

                SubscribeMessage subscribeMessage = new SubscribeMessage();
                subscribeMessage.setTopic(body.getTopic());
                subscribeMessage.setPushSerialType(body.getPushSerialType());
                message = subscribeMessage;

                break;
            case UNSUBSCRIBE:
                session.setTopics(null);

                sendLifePacket = new PushPacket().unSubscribeAck(DeviceType.SERVER,
                        ResponseCode.SUCESS);
                ctx.channel().writeAndFlush(sendLifePacket);
                break;
            case PUBLISH:
                WebPublishMessage publishMessage = new WebPublishMessage();
                publishMessage.setPushPacket(pushPacket);
                message = publishMessage;
                break;
            case PUBLISH_ACK:
                break;
            case PINGREQ:
                sendLifePacket = new PushPacket().pong(DeviceType.SERVER);
                ctx.channel().writeAndFlush(sendLifePacket);
                break;
            case PINGRESP:
                break;
        }

        if (message != null) {
            message.setDeviceType(header.getDeviceType());
            message.setQoS(header.getQoS());
            ctx.fireChannelRead(message);
        }

    }
}
