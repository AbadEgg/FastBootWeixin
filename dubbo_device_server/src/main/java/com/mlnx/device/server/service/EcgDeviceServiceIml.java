package com.mlnx.device.server.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.mlnx.device.ecg.EcgDeviceInfo;
import com.mlnx.device.inter.EcgDeviceService;
import com.mlnx.device.server.mybatis.mapper.TEcgDeviceMapper;

import org.springframework.beans.factory.annotation.Autowired;

import static com.mlnx.device.ecg.dubbo.DerviceDubboServiceVersion.DEVICE_GROUP;
import static com.mlnx.device.ecg.dubbo.DerviceDubboServiceVersion.DEVICE_TIME_OUT;
import static com.mlnx.device.ecg.dubbo.DerviceDubboServiceVersion.DEVICE_V;
import static com.mlnx.device.ecg.dubbo.DerviceDubboServiceVersion.RETRIES;

/**
 * Created by amanda.shan on 2017/12/23.
 */

@Service(version = DEVICE_V, group = DEVICE_GROUP, timeout = DEVICE_TIME_OUT, retries = RETRIES)
public class EcgDeviceServiceIml implements EcgDeviceService {

    @Autowired
    private TEcgDeviceMapper tEcgDeviceMapper;

    @Override
    public EcgDeviceInfo getEcgDeviceInfo(String deviceId) {
        return tEcgDeviceMapper.selectEcgDeviceInfo(deviceId);
    }
}
