package com.mlnx.device_server.mybatis.mapper;

import com.mlnx.device.ecg.EcgDeviceInfo;

public interface TDeviceMapper {

    Integer getPatientId(String deviceId);

    EcgDeviceInfo getEcgDeviceInfo(String deviceId);
}