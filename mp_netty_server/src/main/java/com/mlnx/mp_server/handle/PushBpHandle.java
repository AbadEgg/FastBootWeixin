package com.mlnx.mp_server.handle;

import com.mlnx.mp_server.protocol.BpMessage;
import com.mlnx.mp_session.domain.BpInfo;
import com.mlnx.mp_session.listenner.BroadCast;
import com.mlnx.mptp.mptp.body.Topic;
import com.mlnx.mptp.mptp.body.TopicType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amanda.shan on 2018/2/12.
 */
public class PushBpHandle extends SimpleChannelInboundHandler<BpMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BpMessage msg) throws Exception {

        String deviceId = msg.getDeviceId();

        BpInfo bpInfo = new BpInfo();
        bpInfo.setDeivceId(deviceId);
        bpInfo.setPatientId(msg.getPatientId());
        bpInfo.setPacketTime(msg.getPacketTime());

        bpInfo.setSbp(msg.getSbp());
        bpInfo.setDbp(msg.getDbp());

        List<Topic> topics = new ArrayList<>();
        topics.add(new Topic(TopicType.U_BP_TOPIC, deviceId));

        if (topics.size() > 0){
            BroadCast.bpBroadCast.reciveBpInfo(topics, bpInfo);
        }
    }
}
