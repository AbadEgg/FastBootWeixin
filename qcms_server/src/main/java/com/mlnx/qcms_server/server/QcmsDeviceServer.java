package com.mlnx.qcms_server.server;


import com.mlnx.qcms.utils.QcmsLogUtils;
import com.mlnx.qcms_server.config.ConfigService;
import com.mlnx.qcms_server.server.initializer.CmsServerInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class QcmsDeviceServer implements Server {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;

    private ChannelFuture channelFuture;

    public QcmsDeviceServer(EventLoopGroup bossGroup, EventLoopGroup workGroup) {
        super();
        this.bossGroup = bossGroup;
        this.workGroup = workGroup;
    }

    @Override
    public void start() throws Exception {

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    // .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new CmsServerInitializer()).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            channelFuture = b.bind(ConfigService.MP_DEVICE_PORT).sync(); // (7)
            QcmsLogUtils.i("cms web utils start ok port:" + ConfigService.MP_DEVICE_PORT);
        } finally {
            // 在jvm关闭的时候执行
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    shutdown();
                }
            });
        }
    }

    public void start(CmsServerInitializer cmsServerInitializer) throws Exception {

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    // .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(cmsServerInitializer).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            channelFuture = b.bind(ConfigService.MP_DEVICE_PORT).sync(); // (7)
            QcmsLogUtils.i("cms web utils start ok port:" + ConfigService.MP_DEVICE_PORT);
        } finally {
            // 在jvm关闭的时候执行
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    shutdown();
                }
            });
        }
    }


    @Override
    public void restart() throws Exception {

        shutdown();
        start();
    }

    @Override
    public void shutdown() {

        QcmsLogUtils.i("QcmsDeviceServer shutdown");

        if (channelFuture != null) {
            channelFuture.channel().close().syncUninterruptibly();
            channelFuture = null;
        }
    }
}
