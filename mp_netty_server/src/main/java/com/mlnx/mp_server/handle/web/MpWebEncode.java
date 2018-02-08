package com.mlnx.mp_server.handle.web;


import com.alibaba.fastjson.JSON;
import com.mlnx.mptp.mptp.MpPacket;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class MpWebEncode extends MessageToMessageEncoder<MpPacket> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MpPacket msg, List<Object> out) throws Exception {
		
		out.add(new TextWebSocketFrame(JSON.toJSONString(msg)));
	}

}
