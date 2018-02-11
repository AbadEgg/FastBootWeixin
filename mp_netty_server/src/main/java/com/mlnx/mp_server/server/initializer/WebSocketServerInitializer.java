package com.mlnx.mp_server.server.initializer;

import com.mlnx.mp_server.config.ConfigService;
import com.mlnx.mp_server.handle.EventHandle;
import com.mlnx.mp_server.handle.RegisterHandle;
import com.mlnx.mp_server.handle.SubscribeHandle;
import com.mlnx.mp_server.handle.web.PushHandle;
import com.mlnx.mp_server.handle.web.PushServerHandle;
import com.mlnx.mp_server.handle.web.PushWebEncode;
import com.mlnx.mp_server.handle.web.PushWebServerHandle;

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

		ch.pipeline().addLast(new PushWebServerHandle());
		ch.pipeline().addLast(new PushWebEncode());

		ch.pipeline().addLast(new PushServerHandle());
		ch.pipeline().addLast(new RegisterHandle());
		ch.pipeline().addLast(new PushHandle());
		ch.pipeline().addLast(new SubscribeHandle());
	}

}
