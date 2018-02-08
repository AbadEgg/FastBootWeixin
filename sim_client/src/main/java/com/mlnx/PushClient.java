package com.mlnx;

import com.alibaba.fastjson.JSON;
import com.mlnx.config.Config;
import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.mptp.head.DeviceType;
import com.mlnx.mptp.utils.MptpLogUtils;
import com.mlnx.utils.ThreadUtil;
import com.mlnx.websocket.WebSocketListenner;
import com.mlnx.websocket.WebSocketUtils;

/**
 * Created by amanda.shan on 2017/9/19.
 * websocket  通讯接口
 */
public class PushClient implements WebSocketListenner {

    private WebSocketUtils webSocketUtils;
    private LifeUsrClientLis lifeUsrClientLis;

    public PushClient(LifeUsrClientLis lifeUsrClientLis) {
        this.lifeUsrClientLis = lifeUsrClientLis;
        webSocketUtils = new WebSocketUtils(Config.LIFE_PORT_USR_URI, this);
    }

    public void register(final String name, final String password) {
        ThreadUtil.execute(new Runnable() {
            public void run() {

                 MpPacket  MpPacket = new  MpPacket();
                 MpPacket.register(name, password, DeviceType.USR);
                try {
                    webSocketUtils.sendString(JSON.toJSONString( MpPacket));
                } catch (Exception e) {
                    e.printStackTrace();
                    lifeUsrClientLis.sendError();
                }
            }
        });
    }

    public void ping() {
        ThreadUtil.execute(new Runnable() {
            public void run() {

                 MpPacket  MpPacket = new  MpPacket();
                 MpPacket.ping(DeviceType.USR);
                try {
                    webSocketUtils.sendString(JSON.toJSONString( MpPacket));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void pong() {
        ThreadUtil.execute(new Runnable() {
            public void run() {

                 MpPacket  mpPacket = new  MpPacket();
                 mpPacket.pong(DeviceType.USR);
                try {
                    webSocketUtils.sendString(JSON.toJSONString( mpPacket));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void subscribe(final String topic) {
        ThreadUtil.execute(new Runnable() {
            public void run() {

                 MpPacket  mpPacket = new  MpPacket();
                 mpPacket.subscribe(DeviceType.USR, topic);
                try {
                    webSocketUtils.sendString(JSON.toJSONString( mpPacket));
                } catch (Exception e) {
                    e.printStackTrace();
                    lifeUsrClientLis.sendError();
                }
            }
        });
    }

    public void push(final String topic, final String msg, final Integer messageId) {
        ThreadUtil.execute(new Runnable() {
            public void run() {

//                 MpPacket  mpPacket = new  MpPacket();
//                Body body = new Body();
//                body.setTopic(topic);
//
//                 mpPacket.push(DeviceType.USR, topic, msg, messageId);
//                try {
//                    webSocketUtils.sendString(JSON.toJSONString( mpPacket));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    lifeUsrClientLis.sendError();
//                }
            }
        });
    }

    public void onMessage(String message) {
         MpPacket  MpPacket = JSON.parseObject(message,  MpPacket.class);
        lifeUsrClientLis.recive( MpPacket);
    }

    public void onMessage(byte[] bs) {

    }

    public void onClose(int code, String reason, boolean remote) {
        MptpLogUtils.e(String.format("wesocket onClose:code = %d  reason=%s", code, reason));
        lifeUsrClientLis.close();
    }

    public void disConnect() {
        webSocketUtils.disConnect();
    }

    public interface LifeUsrClientLis {
        void recive( MpPacket  MpPacket);

        void sendError();

        void close();
    }

}
