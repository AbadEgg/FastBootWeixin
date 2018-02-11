package com.mlnx.mp_server.handle.common;

import com.mlnx.mp_server.protocol.RegisterMessage;
import com.mlnx.mp_session.core.Session;
import com.mlnx.mp_session.core.SessionManager;
import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.PacketType;
import com.mlnx.mptp.ResponseCode;
import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.mptp.body.Body;
import com.mlnx.mptp.mptp.head.Header;
import com.mlnx.mptp.utils.MptpLogUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 设备是否注册验证
 * 
 * @author Administrator
 *
 */
public class MpVerify extends SimpleChannelInboundHandler<MpPacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MpPacket mpPacket) throws Exception {
		Header header = mpPacket.getHeader();
		Body body = mpPacket.getBody();

		String deviceID = body.getDeviceId();

		// 不是注册命令
		if (mpPacket.getHeader().getPacketType().compareTo(PacketType.REGISTER) != 0) {

			Session session = SessionManager.get(ctx.channel());
			if (session != null) {
				SessionManager.refreshLastTime(ctx.channel());
				session.setTimeOut(false);
				ctx.fireChannelRead(mpPacket);
			} else {
				MptpLogUtils.e(header.getDeviceType().toString() + " 设备未注册 ：" + deviceID);
				MpPacket packet = new MpPacket().pushAck(DeviceType.SERVER,body.getMessageId(),
						ResponseCode.NOT_REGISTER);
				ctx.channel().writeAndFlush(packet);
			}
		} else {

			Session session = SessionManager.get(ctx.channel());
			// 已经注册
			if (session != null) {
				boolean closeChannel = !ctx.channel().equals(session.getChannel());
				SessionManager.remove(session.getChannel(), closeChannel);
			}
			RegisterMessage registerMessage = new RegisterMessage();
			registerMessage.setDeviceId(body.getDeviceId());
			registerMessage.setDeviceType(header.getDeviceType());
			registerMessage.setQoS(header.getQoS());
			
			registerMessage.setUsrName(body.getName());
			registerMessage.setPassword(body.getPassWord());
			
			ctx.fireChannelRead(registerMessage);
		}

	}

}
