package com.mlnx.mp_server.handle.common;

import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.utils.MptpLogUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public class MpEncode extends MessageToByteEncoder<MpPacket> {

	@Override
	protected void encode(ChannelHandlerContext arg0, MpPacket arg1,
			ByteBuf arg2) throws Exception {
		
		byte[] bs = arg1.encode();
		arg2.writeBytes(bs);
		MptpLogUtils.mpFrame("send  frame:" + ByteBufUtil.hexDump(bs));
	}

}
