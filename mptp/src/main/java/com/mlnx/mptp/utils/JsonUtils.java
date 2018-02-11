package com.mlnx.mptp.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * Created by amanda.shan on 2016/10/20.
 */
public class JsonUtils {

    /**
     * 泛型序列化
     *
     * @param text
     * @param type
     * @param <T>
     * @return
     */
    public static final <T> T parseObject(String text, TypeReference<T> type) throws Exception {

        T t = null;
        try {
            t = JSON.parseObject(text, type);
        } catch (Exception e) {
            throw e;
        }
        return t;
    }

    /**
     * 序列化
     *
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> T parseObject(String text, Class<T> clazz) throws Exception {
        T t = null;
        try {
            t = JSON.parseObject(text, clazz);
        } catch (Exception e) {
            throw e;
        }
        return t;
    }

    public static final String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }
}
