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
        PushService testUsr = new PushService();
        testUsr.setName("admin");
        testUsr.setName("123456");

        TopicManager topicManager = new TopicManager();
        topicManager.lisEcgDevice("HEK07EW17070015M")
                .lisRealAnaly("HEK07EW17070015M")
                .lisHeart("HEK07EW17070015M");

        testUsr.sub(JSON.toJSONString(topicManager.getTopics()));
    }

    public static void main(String[] args) throws InterruptedException {
        TestClient testClient = new TestClient();
        testClient.lis();
        testClient.sub();
    }
}
