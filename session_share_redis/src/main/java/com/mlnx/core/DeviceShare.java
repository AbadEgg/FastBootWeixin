package com.mlnx.core;

import com.mlnx.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fzh
 * @create 2018/2/26 12:25
 */
@Service
public class DeviceShare {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final static String DEVICE_SHARE = "device_share";

    private RedisUtil<String> redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        redisUtil = new RedisUtil<String>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisUtil.setRedisTemplate(redisTemplate);
    }

    /**
     * 保存设备id
     * @param deviceId
     * @return
     */
    public long saveDevice(String deviceId){
        return redisUtil.sSet(DeviceShare.DEVICE_SHARE,deviceId);
    }

    /**
     * 删除设备id
     * @param deviceId
     * @return
     */
    public long deleteDevice(String deviceId){
        if(isOnline(deviceId)){
            return redisUtil.sRemove(DeviceShare.DEVICE_SHARE,deviceId);
        }
        return 0;
    }

    /**
     * 判断设备是否在线
     * @param deviceId
     * @return
     */
    public boolean isOnline(String deviceId){
        return redisUtil.sHasKey(DeviceShare.DEVICE_SHARE,deviceId);
    }

    public List<Boolean> isOnline(List<String> deviceIds){
        List<Boolean> result = new ArrayList<>();
        for (String deviceId:deviceIds) {
            boolean res = isOnline(deviceId);
            result.add(res);
        }
        return result;
    }
}
