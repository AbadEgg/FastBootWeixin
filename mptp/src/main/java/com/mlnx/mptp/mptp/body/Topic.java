package com.mlnx.mptp.mptp.body;

/**
 * Created by amanda.shan on 2018/2/6.
 */
public class Topic {

    private TopicType topicType;
    private String deviceId;

    public Topic(TopicType topicType, String deviceId) {
        this.topicType = topicType;
        this.deviceId = deviceId;
    }

    public Topic() {
    }

    public TopicType getTopicType() {
        return topicType;
    }

    public void setTopicType(TopicType topicType) {
        this.topicType = topicType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Topic) {
            Topic topic = (Topic) obj;
            return topic.deviceId.equals(deviceId) && topic.topicType.equals(topicType);
        }
        return false;
    }
}
