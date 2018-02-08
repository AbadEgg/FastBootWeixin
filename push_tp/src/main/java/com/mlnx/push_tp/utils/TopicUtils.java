package com.mlnx.push_tp.utils;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * // bps/12345 bps/# bp/12345 bp/# ecg/12345 ecg/#
 */
public class TopicUtils {

    public enum TopicType {

        U_LIFE_TOPIC("u/life/"),  D_LIFE_TOPIC("d/life/");

        private String prefix;

        private TopicType(String type) {
            this.prefix = type;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

    }

    public class DeviceTopic {

        private TopicType topicType;
        private List<String> deviceIds;

        public DeviceTopic(TopicType topicType) {
            this.topicType = topicType;
        }

        public TopicType getTopicType() {
            return topicType;
        }

        public void setTopicType(TopicType topicType) {
            this.topicType = topicType;
        }

        public List<String> getDeviceIds() {
            return deviceIds;
        }

        public void setDeviceId(String deviceId) {
            deviceIds = new ArrayList<String>();
            deviceIds.add(deviceId);
        }

        public void setDeviceIds(List<String> deviceIds) {
            this.deviceIds = deviceIds;
        }

        public String getTopic() {
            return topicType.getPrefix() + JSON.toJSONString(deviceIds);
        }

        @Override
        public String toString() {
            return "DeviceTopic{" +
                    "topicType=" + topicType +
                    ", deviceId='" + deviceIds + '\'' +
                    '}';
        }
    }

    public static DeviceTopic judgeTopic(String topic) {

        DeviceTopic deviceTopic = null;

        if (topic.startsWith("u/")) {
            int beginIndex = topic.indexOf("/");
            topic = topic.substring(beginIndex + 1);
            if (topic.startsWith("life/")) {
                deviceTopic = new TopicUtils().new DeviceTopic(
                        TopicType.U_LIFE_TOPIC);
            }else {
                return null;
            }
        } else if (topic.startsWith("d/")) {

            int beginIndex = topic.indexOf("/");
            topic = topic.substring(beginIndex + 1);
            if (topic.startsWith("life/")) {
                deviceTopic = new TopicUtils().new DeviceTopic(
                        TopicType.D_LIFE_TOPIC);
            } else {
                return null;
            }
        } else
            return null;

        int beginIndex = topic.indexOf("/");

        String s = topic.substring(beginIndex + 1);
        deviceTopic.setDeviceIds(JSON.parseArray(s, String.class));

        return deviceTopic;
    }

    public static void main(String[] args) {

//        DeviceTopic contain = new TopicUtils().new DeviceTopic(TopicType.U_ECG_HEART_TOPIC);
//        DeviceTopic topic = new TopicUtils().new DeviceTopic(TopicType.U_ECG_TOPIC);

//        System.out.println(containTopic(topic, contain));

        List<String> mDeviceIds = new ArrayList<String>();
        mDeviceIds.add(String.format("SIM_LIFE%05d", 100));
        mDeviceIds.add(String.format("SIM_LIFE%05d", 101));
        mDeviceIds.add(String.format("SIM_LIFE%05d", 102));

        String topic = "u/life/";
        topic += JSON.toJSONString(mDeviceIds);
        DeviceTopic deviceTopic = judgeTopic(topic);
        System.out.println(deviceTopic);

        mDeviceIds.clear();
        mDeviceIds.add(String.format("SIM_LIFE%05d", 103));
        topic = "u/life/";
        topic += JSON.toJSONString(mDeviceIds);
        DeviceTopic deviceTopic1 = judgeTopic(topic);
        System.out.println(deviceTopic1);

        System.out.println(containTopic(deviceTopic, deviceTopic1));
    }

    /**
     * contain 是否包含在 topic 内
     *
     * @param topic
     * @param contain
     * @return
     */
    public static boolean containTopic(DeviceTopic topic, DeviceTopic contain) {
        if (contain.getTopicType().prefix.equals(topic.getTopicType().prefix)) {
            if (topic.deviceIds.containsAll(contain.deviceIds))
                return true;
        }
        return false;
    }


}
