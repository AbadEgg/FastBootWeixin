package com.mlnx.mptp.utils;

import com.alibaba.fastjson.JSON;
import com.mlnx.mptp.mptp.body.Topic;

import java.util.List;

/**
 * // bps/12345 bps/# bp/12345 bp/# ecg/12345 ecg/#
 */
public class TopicUtils {

    public static List<Topic> getTopics(String topic){
        return JSON.parseArray(topic, Topic.class);
    }

    public static Topic getTopic(String topic){
        return JSON.parseObject(topic, Topic.class);
    }

    public static boolean contain(List<Topic> topics, Topic topic){
        for (Topic topic1:topics){
            if (topic1.equals(topic)){
                return true;
            }
        }
        return false;
    }

}
