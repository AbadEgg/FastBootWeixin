package com.mlnx;


import com.alibaba.fastjson.JSON;
import com.mlnx.mptp.mptp.body.Topic;
import com.mlnx.mptp.mptp.body.TopicType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amanda.shan on 2017/10/23.
 */
public class TestClient {

    public static void main(String[] args) throws InterruptedException {
        PushService testUsr = new PushService();
        testUsr.setName("adminadmin");
        testUsr.setName("123456");

        List<Topic> topics = new ArrayList<Topic>();
        topics.add(new Topic(TopicType.U_ECG_HEART_TOPIC, "cms0001"));
        topics.add(new Topic(TopicType.U_BP_TOPIC, "cms0001"));
        topics.add(new Topic(TopicType.U_SPO_TOPIC, "cms0001"));
//        topics.add(new Topic(TopicType.U_ECG_DEVICE_TOPIC, "HEK07EW17070015M"));
        testUsr.sub(JSON.toJSONString(topics));
    }
}
