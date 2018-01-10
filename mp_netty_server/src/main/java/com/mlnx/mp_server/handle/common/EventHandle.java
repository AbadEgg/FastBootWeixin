package com.mlnx.mp_server.handle.common;

import com.mlnx.mp_server.core.Session;
import com.mlnx.mp_server.core.SessionManager;
import com.mlnx.mptp.utils.MptpLogUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class EventHandle extends ChannelInboundHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		MptpLogUtils.e("exceptionCaught", cause);

		Session session = SessionManager.get(ctx.channel());
		if (session == null)
			return;
		MptpLogUtils.d("设备掉线移除：" + session.toString());
		SessionManager.remove(ctx.channel());

	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {

		if (evt instanceof IdleStateEvent) {
			IdleStateEvent e = (IdleStateEvent) evt;
			if (e.state() == IdleState.READER_IDLE) {
				Session clinet = SessionManager.get(ctx.channel());
				if (clinet == null) {
					ctx.channel().close();
				}
				return;
			}
		}
		super.userEventTriggered(ctx, evt);
	}

}
