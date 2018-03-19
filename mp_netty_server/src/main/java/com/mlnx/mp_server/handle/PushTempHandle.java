package com.mlnx.mp_server.handle;

import com.mlnx.mp_server.protocol.TempMessage;
import com.mlnx.mp_session.domain.TempInfo;
import com.mlnx.mp_session.listenner.BroadCast;
import com.mlnx.mptp.mptp.body.Topic;
import com.mlnx.mptp.mptp.body.TopicType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fzh
 * @create 2018/3/19 10:43
 */
public class PushTempHandle extends SimpleChannelInboundHandler<TempMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TempMessage msg) throws Exception {
        String deviceId = msg.getDeviceId();

        TempInfo tempInfo = new TempInfo();
        tempInfo.setDeivceId(deviceId);
        tempInfo.setPatientId(msg.getPatientId());
        tempInfo.setPacketTime(msg.getPacketTime());

        tempInfo.setTemp(msg.getTemp());
        List<Topic> topics = new ArrayList<>();
        topics.add(new Topic(TopicType.U_TEMP_TOPIC, deviceId));

        if (topics.size() > 0){
            BroadCast.tempBroadCast.receiveTemp(topics, tempInfo);
        }
    }
}
