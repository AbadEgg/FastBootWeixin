package com.mlnx.mp_server.handle.web;


import com.alibaba.fastjson.JSON;
import com.mlnx.mptp.PacketType;
import com.mlnx.mptp.push.PushPacket;
import com.mlnx.mptp.push.body.PushDataType;
import com.mlnx.mptp.push.body.SerialType;
import com.mlnx.mptp.utils.ProtostuffCodecUtil;

import java.util.List;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class PushWebEncode extends MessageToMessageEncoder<PushPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, PushPacket msg, List<Object> out) throws Exception {

        if (msg.getBody().getPushSerialType() == null || msg.getBody().getPushSerialType().equals(SerialType.JSON)) {

            if (msg.getHeader().getPacketType().equals(PacketType.PUBLISH)){
                Map<PushDataType, Object> pushDataMap = msg.getBody().getPushDataMap();
                for (PushDataType pushDataType : pushDataMap.keySet()){
                    pushDataMap.put(pushDataType, JSON.toJSON(pushDataMap.get(pushDataType)));
                }
            }

            out.add(new TextWebSocketFrame(JSON.toJSONString(msg)));
        } else {

            if (msg.getHeader().getPacketType().equals(PacketType.PUBLISH)){
                Map<PushDataType, Object> pushDataMap = msg.getBody().getPushDataMap();
                for (PushDataType pushDataType : pushDataMap.keySet()){
                    pushDataMap.put(pushDataType, ProtostuffCodecUtil.serialize(pushDataMap.get(pushDataType)));
                }
            }

            ByteBuf byteBuf = Unpooled.copiedBuffer(ProtostuffCodecUtil.serialize(msg));
            out.add(new BinaryWebSocketFrame(byteBuf));
        }
    }

}
