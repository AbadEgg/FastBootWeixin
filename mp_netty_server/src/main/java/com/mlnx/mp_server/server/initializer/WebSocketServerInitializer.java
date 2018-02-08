package com.mlnx.mp_server.server.initializer;

import com.mlnx.mp_server.config.ConfigService;
import com.mlnx.mp_server.handle.common.EventHandle;
import com.mlnx.mp_server.handle.common.MpDecode;
import com.mlnx.mp_server.handle.common.MpServerHandle;
import com.mlnx.mp_server.handle.common.MpVerify;
import com.mlnx.mp_server.handle.common.PushHandle;
import com.mlnx.mp_server.handle.common.RegisterHandle;
import com.mlnx.mp_server.handle.common.SubscribeHandle;
import com.mlnx.mp_server.handle.web.MpWebEncode;
import com.mlnx.mp_server.handle.web.MpWebServerHandle;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast("idleStateHandler",
				new IdleStateHandler(ConfigService.WebReaderIdleMaxTimeSeconds, 0, 0));

		ch.pipeline().addLast(new EventHandle());

		ch.pipeline().addLast("encoder", new HttpResponseEncoder());
		ch.pipeline().addLast("decoder", new HttpRequestDecoder());
		ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));

		ch.pipeline().addLast(new MpWebServerHandle());
		ch.pipeline().addLast(new MpWebEncode());

		ch.pipeline().addLast(new MpDecode());

		ch.pipeline().addLast(new MpVerify());
		ch.pipeline().addLast(new MpServerHandle());
		ch.pipeline().addLast(new RegisterHandle());
		ch.pipeline().addLast(new PushHandle());
		ch.pipeline().addLast(new SubscribeHandle());
	}

}
