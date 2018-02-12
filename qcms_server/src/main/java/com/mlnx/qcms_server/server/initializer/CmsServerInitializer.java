package com.mlnx.qcms_server.server.initializer;


import com.mlnx.qcms_server.config.ConfigService;
import com.mlnx.qcms_server.handle.EventHandle;
import com.mlnx.qcms_server.handle.QcmsDecode;

import com.mlnx.qcms_server.handle.QcmsEncode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class CmsServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ch.pipeline().addLast("idleStateHandler",
                new IdleStateHandler(ConfigService.DeviceReaderIdleMaxTimeSeconds, 0, 0));

        ch.pipeline().addLast(new EventHandle());

        ch.pipeline().addLast(new QcmsDecode());

        ch.pipeline().addLast(new QcmsEncode());
    }
}
