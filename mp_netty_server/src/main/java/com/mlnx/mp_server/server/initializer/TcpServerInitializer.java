package com.mlnx.mp_server.server.initializer;

import com.mlnx.mp_server.config.ConfigService;
import com.mlnx.mp_server.handle.EventHandle;
import com.mlnx.mp_server.handle.PushBpHandle;
import com.mlnx.mp_server.handle.PushEcgHandle;
import com.mlnx.mp_server.handle.PushSpoHandle;
import com.mlnx.mp_server.handle.RegisterHandle;
import com.mlnx.mp_server.handle.SubscribeHandle;
import com.mlnx.mp_server.handle.common.MpDecode;
import com.mlnx.mp_server.handle.common.MpEncode;
import com.mlnx.mp_server.handle.common.MpServerHandle;
import com.mlnx.mp_server.handle.common.MpVerify;
import com.mlnx.mp_server.handle.common.PushHandle;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class TcpServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {

		ch.pipeline().addLast("idleStateHandler",
				new IdleStateHandler(ConfigService.DeviceReaderIdleMaxTimeSeconds, 0, 0));

		ch.pipeline().addLast(new EventHandle());

		ch.pipeline().addLast(new MpDecode());
		ch.pipeline().addLast(new MpEncode());

		ch.pipeline().addLast(new MpVerify());
		ch.pipeline().addLast(new MpServerHandle());
		ch.pipeline().addLast(new RegisterHandle());
		ch.pipeline().addLast(new PushHandle());
		ch.pipeline().addLast(new SubscribeHandle());
		ch.pipeline().addLast(new PushEcgHandle());
		ch.pipeline().addLast(new PushBpHandle());
		ch.pipeline().addLast(new PushSpoHandle());
	}
}
