package com.mlnx;

import com.alibaba.fastjson.JSON;
import com.mlnx.config.Config;
import com.mlnx.mp_session.domain.*;
import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.push.PushPacket;
import com.mlnx.mptp.push.body.Body;
import com.mlnx.mptp.push.body.PushDataType;
import com.mlnx.mptp.push.body.SerialType;
import com.mlnx.mptp.utils.MptpLogUtils;
import com.mlnx.mptp.utils.ProtostuffCodecUtil;
import com.mlnx.utils.ThreadUtil;
import com.mlnx.websocket.WebSocketListenner;
import com.mlnx.websocket.WebSocketUtils;

import java.util.Map;

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
            @Override
            public void run() {

                PushPacket pushPacket = new PushPacket();
                pushPacket.register(name, password, DeviceType.USR);
                try {
                    webSocketUtils.sendString(JSON.toJSONString(pushPacket));
                } catch (Exception e) {
                    e.printStackTrace();
                    lifeUsrClientLis.sendError();
                }
            }
        });
    }

    public void ping() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {

                PushPacket pushPacket = new PushPacket();
                pushPacket.ping(DeviceType.USR);
                try {
                    webSocketUtils.sendString(JSON.toJSONString(pushPacket));
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

                PushPacket pushPacket = new PushPacket();
                pushPacket.pong(DeviceType.USR);
                try {
                    webSocketUtils.sendString(JSON.toJSONString(pushPacket));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void subscribe(final String topic, final SerialType serialType) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {

                PushPacket pushPacket = new PushPacket();
                pushPacket.subscribe(DeviceType.USR, topic, serialType);
                try {
                    webSocketUtils.sendString(JSON.toJSONString(pushPacket));
                } catch (Exception e) {
                    e.printStackTrace();
                    lifeUsrClientLis.sendError();
                }
            }
        });
    }

    public void push(final String topic, final Map<PushDataType, Object> pushDataMap) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {

                PushPacket PushPacket = new PushPacket();
                Body body = new Body();
                body.setTopic(topic);

                PushPacket.push(DeviceType.USR, topic, pushDataMap);
                try {
                    webSocketUtils.sendString(JSON.toJSONString(PushPacket));
                } catch (Exception e) {
                    e.printStackTrace();
                    lifeUsrClientLis.sendError();
                }
            }
        });
    }

    @Override
    public void onMessage(String message) {
        PushPacket PushPacket = JSON.parseObject(message, PushPacket.class);
        Map<PushDataType, Object> map = PushPacket.getBody().getPushDataMap();
        if (map != null) {
            for (PushDataType pushDataType : map.keySet()) {
                switch (pushDataType) {
                    case ECG_INFO:
                        map.put(pushDataType, JSON.parseObject(map.get(pushDataType).toString(), EcgInfo.class));
                        break;
                    case SPO_INFO:
                        map.put(pushDataType, JSON.parseObject(map.get(pushDataType).toString(), SpoInfo.class));
                        break;
                    case BP_INFO:
                        map.put(pushDataType, JSON.parseObject(map.get(pushDataType).toString(), BpInfo.class));
                        break;
                    case TEMP_INFO:
                        map.put(pushDataType,JSON.parseObject(map.get(pushDataType).toString(), TempInfo.class));
                        break;
                    case CO2_INFO:
                        map.put(pushDataType,JSON.parseObject(map.get(pushDataType).toString(), CO2Info.class));
                        break;
                    default:
                        break;
                }
            }
        }
        lifeUsrClientLis.recive(PushPacket);
    }

    @Override
    public void onMessage(byte[] bs) {
        PushPacket PushPacket = null;
        try {
            PushPacket = ProtostuffCodecUtil.deserializer(bs, PushPacket.class);

            Map<PushDataType, Object> map = PushPacket.getBody().getPushDataMap();
            if (map != null) {
                for (PushDataType pushDataType : map.keySet()) {
                    switch (pushDataType) {
                        case ECG_INFO:
                            map.put(pushDataType, ProtostuffCodecUtil.deserializer((byte[]) map.get(pushDataType),
                                    EcgInfo.class));

                            break;
                        default:
                            break;
                    }
                }
            }
            lifeUsrClientLis.recive(PushPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        MptpLogUtils.e(String.format("wesocket onClose:code = %d  reason=%s", code, reason));
        lifeUsrClientLis.close();
    }

    public void disConnect() {
        webSocketUtils.disConnect();
    }

    public interface LifeUsrClientLis {
        void recive(PushPacket PushPacket);

        void sendError();

        void close();
    }

}
