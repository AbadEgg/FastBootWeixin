package com.mlnx.mp_server.handle.web;

import com.mlnx.mp_server.protocol.WebPublishMessage;
import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.ResponseCode;
import com.mlnx.mptp.mptp.head.QoS;
import com.mlnx.mptp.push.PushPacket;
import com.mlnx.mptp.push.body.Body;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PushHandle extends SimpleChannelInboundHandler<WebPublishMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebPublishMessage msg)
            throws Exception {

        PushPacket pushPacket = msg.getPushPacket();
        Body body = pushPacket.getBody();

        PushPacket sendPacket = null;
        if (body.getMessageId() == null)
            sendPacket = new PushPacket().pushAck(DeviceType.SERVER, null,
                    ResponseCode.LOST_MESSAGE_ID);
        else {
            sendPacket = new PushPacket().pushAck(DeviceType.SERVER,
                    body.getMessageId(), ResponseCode.SUCESS);
        }

//        if (pushPacket.getBody().getTopic() == null) {
//            MptpLogUtils.e(String.format("push 非法主题，设备类型：%s", msg.getDeviceType()));
//            sendPacket.getBody().setResponseCode(ResponseCode.ILLEGAL_TOPICE);
//            return;
//        }else {
//            BroadCast.getInstance().addPushData(pushPacket);
//        }

        if (sendPacket != null && QoS.LEAST_ONE.equals(msg.getQoS())) {
            ctx.channel().writeAndFlush(sendPacket);
        }
    }
}
