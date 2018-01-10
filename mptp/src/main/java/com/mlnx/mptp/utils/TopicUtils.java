package com.mlnx.mptp.utils;

/**
 * // bps/12345 bps/# bp/12345 bp/# ecg/12345 ecg/#
 */
public class TopicUtils {

    public enum TopicType {

        U_BP_TOPIC("u/bp/"), U_BPS_TOPIC("u/bps/"), U_ECG_TOPIC("u/ecg/"), U_ECG_HEART_TOPIC("u/ecg/heart/"),
        D_BP_TOPIC("d/bp/"), D_BPS_TOPIC("d/bps/"), D_ECG_TOPIC("d/ecg/");

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
        private String deviceId;

        public DeviceTopic(TopicType topicType) {
            this.topicType = topicType;
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

        public String getTopic() {
            return topicType.getPrefix() + deviceId;
        }

        @Override
        public String toString() {
            return "DeviceTopic{" +
                    "topicType=" + topicType +
                    ", deviceId='" + deviceId + '\'' +
                    '}';
        }
    }

    public static DeviceTopic judgeTopic(String topic) {

        DeviceTopic deviceTopic = null;

        if (topic.startsWith("u/")) {
            int beginIndex = topic.indexOf("/");
            topic = topic.substring(beginIndex + 1);
            if (topic.startsWith("bp/")) {
                deviceTopic = new TopicUtils().new DeviceTopic(
                        TopicType.U_BP_TOPIC);
            } else if (topic.startsWith("bps/")) {
                deviceTopic = new TopicUtils().new DeviceTopic(
                        TopicType.U_BPS_TOPIC);
            } else if (topic.startsWith("ecg/heart/")) {
                deviceTopic = new TopicUtils().new DeviceTopic(
                        TopicType.U_ECG_HEART_TOPIC);
                beginIndex = topic.indexOf("/");
                topic = topic.substring(beginIndex + 1);
            } else if (topic.startsWith("ecg/")) {
                deviceTopic = new TopicUtils().new DeviceTopic(
                        TopicType.U_ECG_TOPIC);
            } else {
                return null;
            }
        } else if (topic.startsWith("d/")) {

            int beginIndex = topic.indexOf("/");
            topic = topic.substring(beginIndex + 1);
            if (topic.startsWith("bp/")) {
                deviceTopic = new TopicUtils().new DeviceTopic(
                        TopicType.D_BP_TOPIC);
            } else if (topic.startsWith("bps/")) {
                deviceTopic = new TopicUtils().new DeviceTopic(
                        TopicType.D_BPS_TOPIC);
            } else if (topic.startsWith("ecg/")) {
                deviceTopic = new TopicUtils().new DeviceTopic(
                        TopicType.D_ECG_TOPIC);
            } else {
                return null;
            }
        } else
            return null;

        int beginIndex = topic.indexOf("/");
        deviceTopic.setDeviceId(topic.substring(beginIndex + 1));

        return deviceTopic;
    }

    public static void main(String[] args) {

        DeviceTopic contain = new TopicUtils().new DeviceTopic(TopicType.U_ECG_HEART_TOPIC);
        DeviceTopic topic = new TopicUtils().new DeviceTopic(TopicType.U_ECG_TOPIC);

        System.out.println(containTopic(topic, contain));
    }

    /**
     * contain 是否包含在 topic 内
     *
     * @param topic
     * @param contain
     * @return
     */
    public static boolean containTopic(DeviceTopic topic, DeviceTopic contain) {
        if (contain.getTopicType().prefix.equals(topic.getTopicType().prefix) || contain.getTopicType().prefix
                .startsWith(topic.getTopicType().prefix)) {
            if (contain.deviceId.equals("#")
                    || contain.deviceId.contains(topic.deviceId))
                return true;
        }
        return false;
    }


}
