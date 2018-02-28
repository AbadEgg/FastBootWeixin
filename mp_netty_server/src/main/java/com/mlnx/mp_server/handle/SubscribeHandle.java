package com.mlnx.mp_server.handle;

import com.mlnx.mp_server.protocol.SubscribeMessage;
import com.mlnx.mp_session.core.Session;
import com.mlnx.mp_session.core.SessionManager;
import com.mlnx.mp_session.core.UsrSession;
import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.ResponseCode;
import com.mlnx.mptp.mptp.body.Topic;
import com.mlnx.mptp.push.PushPacket;
import com.mlnx.mptp.utils.MptpLogUtils;
import com.mlnx.mptp.utils.TopicUtils;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SubscribeHandle extends
        SimpleChannelInboundHandler<SubscribeMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SubscribeMessage msg)
            throws Exception {

        String topic = msg.getTopic();

        ResponseCode responseCode = ResponseCode.ILLEGAL_TOPICE;

        if (topic != null) {
            Session session = SessionManager.get(ctx.channel());
            List<Topic> topics = TopicUtils.getTopics(topic);

            MptpLogUtils.d(String.format("订阅主题:" + topic));
            if (topics != null) {
                session.setTopics(topics);
                if (session instanceof UsrSession && msg.getPushSerialType() != null){
                    UsrSession usrSession = (UsrSession) session;
                    usrSession.setSerialType(msg.getPushSerialType());
                }
                responseCode = ResponseCode.SUCESS;
            }
        }

        if (responseCode.equals(ResponseCode.ILLEGAL_TOPICE))
            MptpLogUtils.e("非法的订阅主题:" + topic);

        PushPacket packet = new PushPacket().subscribeAck(DeviceType.SERVER,
                responseCode);
        ctx.channel().writeAndFlush(packet);
    }

}