package com.mlnx.mp_server.handle.web;

import com.alibaba.fastjson.JSON;
import com.mlnx.mp_server.protocol.WebPublishMessage;
import com.mlnx.mp_session.core.Session;
import com.mlnx.mp_session.core.SessionManager;
import com.mlnx.mp_session.core.UsrSession;
import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.ResponseCode;
import com.mlnx.mptp.mptp.head.QoS;
import com.mlnx.mptp.push.PushPacket;
import com.mlnx.mptp.push.body.Body;
import com.mlnx.mptp.push.body.PushDataType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

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


        for (PushDataType type : body.getPushDataMap().keySet()){
            switch (type){
                case ASK_DEVICE_INFO:
                    List<String> deviceIds = JSON.parseArray(body.getPushDataMap().get(type).toString(), String.class);
                    Session session = SessionManager.get(ctx.channel());
                    if (session instanceof UsrSession){
                        UsrSession usrSession = (UsrSession) session;
                        usrSession.pushInfo(deviceIds);
                    }
                    break;
                default:
                    break;
            }
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
