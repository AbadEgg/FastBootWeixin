package com.mlnx;

import com.mlnx.mptp.mptp.body.Topic;
import com.mlnx.mptp.mptp.body.TopicType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by amanda.shan on 2018/3/2.
 */
public class TopicManager {

    private Map<String, List<TopicType>> topicMap = new HashMap<String, List<TopicType>>();

    public void removeDevice(String deviceId) {
        topicMap.remove(deviceId);
    }

    public TopicManager lisHeart(String deviceId) {
        addTopic(deviceId, TopicType.U_ECG_HEART_TOPIC);
        return this;
    }

    public TopicManager lisRealAnaly(String deviceId) {
        addTopic(deviceId, TopicType.U_ECG_REAL_ANALY_TOPIC);
        return this;
    }

    public TopicManager lisEcgDevice(String deviceId) {
        addTopic(deviceId, TopicType.U_ECG_DEVICE_TOPIC);
        return this;
    }

    public TopicManager lisBp(String deviceId) {
        addTopic(deviceId, TopicType.U_BP_TOPIC);
        return this;
    }

    public TopicManager lisSpo(String deviceId) {
        addTopic(deviceId, TopicType.U_SPO_TOPIC);
        return this;
    }

    public TopicManager lisBt(String deviceId) {
        addTopic(deviceId, TopicType.U_BT_TOPIC);
        return this;
    }

    public TopicManager lisTemp(String deviceId) {
        addTopic(deviceId, TopicType.U_TEMP_TOPIC);
        return this;
    }

    public TopicManager lisCO2(String deviceId) {
        addTopic(deviceId, TopicType.U_CO2_TOPIC);
        return this;
    }

    public TopicManager addTopic(String deviceId, TopicType topicType) {
        List<TopicType> topicTypes = topicMap.get(deviceId);
        if (topicTypes == null) {
            topicTypes = new ArrayList<TopicType>();
            topicMap.put(deviceId, topicTypes);
        }
        if (!topicTypes.contains(topicType)) {
            topicTypes.add(topicType);
        }

        return this;
    }

    public List<Topic> getTopics() {

        List<Topic> topics = new ArrayList<Topic>();

        for (String deviceId : topicMap.keySet()) {
            List<TopicType> topicTypes = topicMap.get(deviceId);
            for (TopicType topicType : topicTypes) {
                topics.add(new Topic(topicType, deviceId));
            }
        }

        return topics;
    }
}
