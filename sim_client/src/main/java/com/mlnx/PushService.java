package com.mlnx;


import com.alibaba.fastjson.JSON;
import com.mlnx.listener.BroadCast;
import com.mlnx.mp_session.domain.*;
import com.mlnx.mptp.ResponseCode;
import com.mlnx.mptp.push.PushPacket;
import com.mlnx.mptp.push.body.PushDataType;
import com.mlnx.mptp.push.body.SerialType;
import com.mlnx.mptp.utils.MptpLogUtils;
import com.mlnx.utils.ThreadUtil;

import java.util.Map;


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
    private Map<PushDataType, Object> pushDataMap;

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
            @Override
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

    public void register(){
        pushClient.register(name, password);
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

    public void push(String topic, final Map<PushDataType, Object> pushDataMap) {
        this.topic = topic;
        this.pushDataMap = pushDataMap;

        push();
    }

    private void push() {

//        if (isListerDevice) {

            pushClient.push(topic, pushDataMap);
            MptpLogUtils.i("发送push包");
//        } else {
//            sub();
//        }
    }

    @Override
    public void recive(PushPacket pushPacket) {

        switch (pushPacket.getHeader().getPacketType()) {
            case Reg_ACK:
                if (pushPacket.getBody().getResponseCode().equals(ResponseCode.SUCESS)) {
                    isRegister = true;
                    MptpLogUtils.i("注册成功");
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
                            BroadCast.getInstance().receiveEcgInfo(ecgInfo);
                            MptpLogUtils.i(ecgInfo.getDeivceId() + " 收到推送心电:" + JSON.toJSONString(ecgInfo));
                            break;
                        case BP_INFO:

                            BpInfo bpInfo = (BpInfo) map.get(pushDataType);
                            BroadCast.getInstance().receiveBpInfo(bpInfo);
                            MptpLogUtils.i(bpInfo.getDeivceId() + " 收到推送血压:" + JSON.toJSONString(bpInfo));
                            break;
                        case SPO_INFO:

                            SpoInfo spoInfo = (SpoInfo) map.get(pushDataType);
                            BroadCast.getInstance().receiveSpoInfo(spoInfo);
                            MptpLogUtils.i(spoInfo.getDeivceId() + " 收到推送spo:" + JSON.toJSONString(spoInfo));
                            break;
                        case TEMP_INFO:

                            TempInfo tempInfo = (TempInfo) map.get(pushDataType);
                            BroadCast.getInstance().receiveTempInfo(tempInfo);
                            MptpLogUtils.i(tempInfo.getDeivceId() + " 收到推送temp:" + JSON.toJSONString(tempInfo));
                            break;
                        case CO2_INFO:

                            CO2Info co2Info = (CO2Info) map.get(pushDataType);
                            BroadCast.getInstance().receiveCO2Info(co2Info);
                            MptpLogUtils.i(co2Info.getDeivceId() + " 收到推送co2:" + JSON.toJSONString(co2Info));
                            break;

                        default:
                            break;
                    }
                }

                break;
            case PUBLISH_ACK:
                break;
            default:
                break;
        }
    }

    public void disConnect() {
        stopSub = true;
        pushClient.disConnect();
    }

    @Override
    public void sendError() {

    }

    @Override
    public void close() {
        isRegister = false;
        isListerDevice = false;

        if (!stopSub) {
            sub();
        }
    }
}
