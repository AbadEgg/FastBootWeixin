package com.mlnx;


import com.mlnx.listener.BroadCast;
import com.mlnx.mp_session.domain.BpInfo;
import com.mlnx.mp_session.domain.EcgInfo;
import com.mlnx.mp_session.domain.SpoInfo;
import com.mlnx.mptp.ResponseCode;
import com.mlnx.mptp.push.PushPacket;
import com.mlnx.mptp.push.body.PushDataType;
import com.mlnx.mptp.push.body.SerialType;
import com.mlnx.mptp.utils.MptpLogUtils;
import com.mlnx.utils.ThreadUtil;

import java.util.Map;
import java.util.Random;


/**
 * Created by amanda.shan on 2017/9/19.
 * lifedata 数据监听处理服务
 */
public class PushService implements PushClient.LifeUsrClientLis {

    private PushClient pushClient;
    private boolean stopSub;

    private boolean isListerDevice;
    private boolean isRegister;

    private String topic;
    private String msg;

    private boolean isPush = false;

    private String name;
    private String password;

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PushService() {
        pushClient = new PushClient(this);

        ThreadUtil.execute(new Runnable() {
            public void run() {

                while (true) {
                    if (isListerDevice) {
                        pushClient.ping();
//                         MptpLogUtils.d("发送ping包");
                    } else if (topic != null) {
                        sub();
                    }

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void sub(String topic) {

        this.topic = topic;
        stopSub = false;
        sub();
    }

    private void sub() {

        if (isRegister) {
            pushClient.subscribe(topic, SerialType.JSON);
            MptpLogUtils.i("发送监听包");
        } else {
            pushClient.register(name, password);
        }
    }

    public void push(String topic, String msg) {
        isPush = true;
        this.topic = topic;
        this.msg = msg;

        push();
    }

    private void push() {
        this.topic = topic;
        this.msg = msg;

        if (isListerDevice) {

            pushClient.push(topic, msg, new Random().nextInt());
            MptpLogUtils.i("发送push包");
        } else {
            sub();
        }
    }

    public void recive(PushPacket pushPacket) {

        switch (pushPacket.getHeader().getPacketType()) {
            case Reg_ACK:
                if (pushPacket.getBody().getResponseCode().equals(ResponseCode.SUCESS)) {
                    isRegister = true;
                    MptpLogUtils.i("注册成功");
                    if (isPush)
                        push();
                    else
                        sub();
                }
                break;
            case PINGREQ:
                pushClient.pong();
                MptpLogUtils.i("响应pong包");
                break;
            case PINGRESP:
//                 MptpLogUtils.d("收到pong包");
                break;
            case SUB_ACK:
                if (pushPacket.getBody().getResponseCode().equals(ResponseCode.SUCESS)) {
                    isListerDevice = true;
                    MptpLogUtils.i("监听成功");
                }
                break;
            case PUBLISH:

                Map<PushDataType, Object> map = pushPacket.getBody().getPushDataMap();
                for (PushDataType pushDataType : map.keySet()) {
                    switch (pushDataType) {
                        case ECG_INFO:

                            EcgInfo ecgInfo = (EcgInfo) map.get(pushDataType);
                            BroadCast.getInstance().reciveEcgInfo(ecgInfo);
                            MptpLogUtils.i(ecgInfo.getDeivceId() + " 收到推送心电:" + ecgInfo.toString());
                            break;
                        case BP_INFO:

                            BpInfo bpInfo = (BpInfo) map.get(pushDataType);
                            BroadCast.getInstance().reciveBpInfo(bpInfo);
                            MptpLogUtils.i(bpInfo.getDeivceId() + " 收到推送血压:" + bpInfo.toString());
                            break;
                        case SPO_INFO:

                            SpoInfo spoInfo = (SpoInfo) map.get(pushDataType);
                            BroadCast.getInstance().reciveSpoInfo(spoInfo);
                            MptpLogUtils.i(spoInfo.getDeivceId() + " 收到推送spo:" + spoInfo.toString());
                            break;
                    }
                }

                break;
            case PUBLISH_ACK:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        push();
                    }
                }).start();
                break;
        }
    }

    public void disConnect() {
        stopSub = true;
        pushClient.disConnect();
    }

    public void sendError() {

    }

    public void close() {
        isRegister = false;
        isListerDevice = false;

        if (!stopSub) {
            sub();
        }
    }
}
