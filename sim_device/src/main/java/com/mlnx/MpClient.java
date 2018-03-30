package com.mlnx;

import com.mlnx.client.MpClientBootstrap;
import com.mlnx.client.MpClientLis;
import com.mlnx.config.Config;
import com.mlnx.mp_server.utils.ThreadUtil;
import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.mptp.MpPacket;

/**
 * @author fzh
 * @create 2018/3/26 16:57
 */
public class MpClient {

    private MpClientBootstrap mpClientBootstrap;
    private MpClientLis mpClientLis;

    public MpClient(MpClientLis mpClientLis) {
        this.mpClientLis = mpClientLis;
        this.mpClientBootstrap = new MpClientBootstrap(Config.MP_DEVICE_PORT,Config.HOST, mpClientLis);
    }

    public void register(final String deviceId) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {

                MpPacket mpPacket = new MpPacket();
                mpPacket.register(deviceId, DeviceType.ECG_DEVICE);
                try {
                    mpClientBootstrap.send(mpPacket);
                } catch (Exception e) {
                    e.printStackTrace();
                    mpClientLis.sendError();
                }
            }
        });
    }

    public void ping() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {

                MpPacket mpPacket = new MpPacket();
                mpPacket.ping(DeviceType.ECG_DEVICE);
                try {
                    mpClientBootstrap.send(mpPacket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void pong() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {

                MpPacket mpPacket = new MpPacket();
                mpPacket.pong(DeviceType.ECG_DEVICE);
                try {
                    mpClientBootstrap.send(mpPacket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void subscribe(final String topic) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {

                MpPacket mpPacket = new MpPacket();
                mpPacket.subscribe(DeviceType.ECG_DEVICE, topic);
                try {
                    mpClientBootstrap.send(mpPacket);
                } catch (Exception e) {
                    e.printStackTrace();
                    mpClientLis.sendError();
                }
            }
        });
    }

    public void disConnect() {
        mpClientBootstrap.socketChannel.close();
    }

    public void push(MpPacket packet) {
        try {
            mpClientBootstrap.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
            mpClientLis.sendError();
        }
    }
}
