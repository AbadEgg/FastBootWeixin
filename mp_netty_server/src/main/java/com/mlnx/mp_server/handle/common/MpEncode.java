package com.mlnx.mp_server.handle.common;

import com.mlnx.mptp.mptp.MpPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public class MpEncode extends MessageToByteEncoder<MpPacket> {

	@Override
	protected void encode(ChannelHandlerContext arg0, MpPacket arg1,
			ByteBuf arg2) throws Exception {

		try {
			byte[] bs = arg1.encode();
			arg2.writeBytes(bs);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
