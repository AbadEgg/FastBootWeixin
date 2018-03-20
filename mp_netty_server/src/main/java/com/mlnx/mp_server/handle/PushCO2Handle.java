package com.mlnx.mp_server.handle;

import com.mlnx.mp_server.protocol.CO2Message;
import com.mlnx.mp_session.domain.CO2Info;
import com.mlnx.mp_session.listenner.BroadCast;
import com.mlnx.mptp.mptp.body.Topic;
import com.mlnx.mptp.mptp.body.TopicType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fzh
 * @create 2018/3/20 13:42
 */
public class PushCO2Handle extends SimpleChannelInboundHandler<CO2Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CO2Message msg) throws Exception {
        String deviceId = msg.getDeviceId();

        CO2Info co2Info = new CO2Info();
        co2Info.setDeivceId(deviceId);
        co2Info.setPatientId(msg.getPatientId());
        co2Info.setPacketTime(msg.getPacketTime());

        co2Info.setCo2Value(msg.getCo2Value());
        List<Topic> topics = new ArrayList<>();
        topics.add(new Topic(TopicType.U_CO2_TOPIC, deviceId));

        if (topics.size() > 0){
            BroadCast.co2BroadCast.receiveCO2(topics, co2Info);
        }
    }
}
