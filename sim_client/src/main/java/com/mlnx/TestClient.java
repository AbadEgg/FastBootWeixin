package com.mlnx;


import com.alibaba.fastjson.JSON;
import com.mlnx.listener.BroadCast;
import com.mlnx.listener.MsgListener;
import com.mlnx.mp_session.domain.*;
import com.mlnx.mptp.push.body.PushDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by amanda.shan on 2017/10/23.
 */
public class TestClient {

    private PushService testUsr;

    public TestClient() {

        testUsr = new PushService();
        testUsr.setName("admin");
        testUsr.setName("123456");

    }

    public void lis(){
        BroadCast.getInstance().addMsgListener(new MsgListener() {
            @Override
            public void receiveEcgInfo(EcgInfo ecgInfo) {

            }

            @Override
            public void receiveBpInfo(BpInfo bpInfo) {

            }

            @Override
            public void receiveSpoInfo(SpoInfo spoInfo) {

            }

            @Override
            public void receiveTempInfo(TempInfo tempInfo) {

            }

            @Override
            public void receiveCO2Info(CO2Info co2Info) {

            }
        });
    }

    public void sub(){

        TopicManager topicManager = new TopicManager();
        topicManager.lisEcgDevice("cms0001")
                .lisRealAnaly("cms0001")
                .lisHeart("cms0001")
                .lisSpo("cms0001")
                .lisBp("cms0001")
                .lisTemp("cms0001")
                .lisCO2("cms0001");

        testUsr.sub(JSON.toJSONString(topicManager.getTopics()));
    }

    public static void main(String[] args) throws InterruptedException {
        final TestClient testClient = new TestClient();

        testClient.testUsr.register();
        testClient.lis();
        testClient.sub();


        Thread.sleep(2000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    List<String> deviceIds = new ArrayList<>();
                    deviceIds.add("cms0001");

                    Map<PushDataType, Object> pushDataMap = new HashMap<>();
                    pushDataMap.put(PushDataType.ASK_DEVICE_INFO, JSON.toJSON(deviceIds));
                    testClient.testUsr.push(null, pushDataMap);

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
