package com.mlnx.mp_server;

import com.mlnx.mp_server.server.MpDeviceServer;
import com.mlnx.mp_server.server.MpWebServer;
import com.mlnx.mp_server.server.Server;
import com.mlnx.mp_server.server.initializer.MpCmsServerInitializer;
import com.mlnx.mp_session.core.SessionManager;
import com.mlnx.qcms_server.server.QcmsDeviceServer;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class MpServer implements Server {

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workGroup = new NioEventLoopGroup();

    private MpDeviceServer mpDeviceServer;
    private MpWebServer mpWebServer;
    private QcmsDeviceServer qcmsDeviceServer;

    private Boolean isRefresh = false;
    private Thread sessionManagerThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                isRefresh = true;
                while (isRefresh) {
                    int sleepTime = SessionManager.refreshSessions();

                    if (sleepTime <= 5) {
                        sleepTime = 5;
                    }
                    synchronized (this) {
                        wait(sleepTime * 1000);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    public void start() throws Exception {

        try {
            mpDeviceServer = new MpDeviceServer(bossGroup, workGroup);
            mpDeviceServer.start();
            mpWebServer = new MpWebServer(bossGroup, workGroup);
            mpWebServer.start();

            qcmsDeviceServer = new QcmsDeviceServer(bossGroup, workGroup);
            qcmsDeviceServer.start(new MpCmsServerInitializer());

            sessionManagerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restart() throws Exception {
        mpDeviceServer.restart();
        mpWebServer.restart();
        qcmsDeviceServer.restart();
    }

    @Override
    public void shutdown() {

        mpDeviceServer.shutdown();
        mpWebServer.shutdown();
        qcmsDeviceServer.shutdown();

        isRefresh = false;
        synchronized (sessionManagerThread) {
            sessionManagerThread.notifyAll();
        }
        try {
            sessionManagerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
