package com.mlnx;


import com.alibaba.fastjson.JSON;
import com.mlnx.listener.BroadCast;
import com.mlnx.listener.MsgListener;
import com.mlnx.mp_session.domain.BpInfo;
import com.mlnx.mp_session.domain.EcgInfo;
import com.mlnx.mp_session.domain.SpoInfo;

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
            public void reciveEcgInfo(EcgInfo ecgInfo) {

            }

            @Override
            public void reciveBpInfo(BpInfo bpInfo) {

            }

            @Override
            public void reciveSpoInfo(SpoInfo spoInfo) {

            }
        });
    }

    public void sub(){

        TopicManager topicManager = new TopicManager();
        topicManager.lisEcgDevice("cms0001")
                .lisRealAnaly("cms0001")
                .lisHeart("cms0001")
                .lisSpo("cms0001")
                .lisBp("cms0001");

        testUsr.sub(JSON.toJSONString(topicManager.getTopics()));
    }

    public static void main(String[] args) throws InterruptedException {
        final TestClient testClient = new TestClient();
        testClient.lis();
        testClient.sub();



//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    List<String> deviceIds = new ArrayList<>();
//                    deviceIds.add("cms0001");
//
//                    Map<PushDataType, Object> pushDataMap = new HashMap<>();
//                    pushDataMap.put(PushDataType.ASK_DEVICE_INFO, JSON.toJSON(deviceIds));
//                    PushPacket pushPacket = new PushPacket().push(DeviceType.USR, null, pushDataMap);
//                    testClient.testUsr.push(null, JSON.toJSONString(pushPacket));
//
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();

    }
}
