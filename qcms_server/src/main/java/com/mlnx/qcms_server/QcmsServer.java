package com.mlnx.qcms_server;

import com.mlnx.qcms_server.server.QcmsDeviceServer;
import com.mlnx.qcms_server.server.Server;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Created by amanda.shan on 2018/1/24.
 */
public class QcmsServer implements Server {

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workGroup = new NioEventLoopGroup();

    private QcmsDeviceServer qcmsDeviceServer;

    @Override
    public void start() throws Exception {

        try {
            qcmsDeviceServer = new QcmsDeviceServer(bossGroup, workGroup);
            qcmsDeviceServer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restart() throws Exception {
        qcmsDeviceServer.restart();
    }

    @Override
    public void shutdown() {

        qcmsDeviceServer.shutdown();
    }

}
