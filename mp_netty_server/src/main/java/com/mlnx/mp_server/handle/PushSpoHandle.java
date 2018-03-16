package com.mlnx.mp_server.handle;

import com.mlnx.mp_server.protocol.SpoMessage;
import com.mlnx.mp_session.core.MpDeviceSession;
import com.mlnx.mp_session.core.Session;
import com.mlnx.mp_session.core.SessionManager;
import com.mlnx.mp_session.domain.SpoInfo;
import com.mlnx.mp_session.listenner.BroadCast;
import com.mlnx.mptp.mptp.body.Topic;
import com.mlnx.mptp.mptp.body.TopicType;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by amanda.shan on 2018/2/12.
 */
public class PushSpoHandle extends SimpleChannelInboundHandler<SpoMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SpoMessage msg) throws Exception {

       Session session =  SessionManager.get(ctx.channel());
       if (session instanceof MpDeviceSession){
           MpDeviceSession mpDeviceSession = (MpDeviceSession) session;
       }

        String deviceId = msg.getDeviceId();

        SpoInfo spoInfo = new SpoInfo();
        spoInfo.setDeivceId(deviceId);
        spoInfo.setPatientId(msg.getPatientId());
        spoInfo.setPacketTime(msg.getPacketTime());

        spoInfo.setResultSPO(msg.getSpo());
        spoInfo.setResultHeart(msg.getHeart());

        List<Topic> topics = new ArrayList<>();
        topics.add(new Topic(TopicType.U_BP_TOPIC, deviceId));

        if (topics.size() > 0){
            BroadCast.spoBroadCast.reciveSpoInfo(topics, spoInfo);
        }
    }
}
