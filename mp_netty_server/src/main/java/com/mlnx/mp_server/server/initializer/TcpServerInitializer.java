package com.mlnx.mp_server.server.initializer;

import com.mlnx.mp_server.config.ConfigService;
import com.mlnx.mp_server.handle.*;
import com.mlnx.mp_server.handle.common.*;
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
