package com.mlnx.device.server.mybatis.mapper;

import com.mlnx.device.ecg.EcgDeviceInfo;
import com.mlnx.device.server.mybatis.entity.TEcgDevice;

import java.util.List;

public interface TEcgDeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TEcgDevice record);

    TEcgDevice selectByPrimaryKey(Integer id);

    List<TEcgDevice> selectAll();

    int updateByPrimaryKey(TEcgDevice record);

    EcgDeviceInfo selectEcgDeviceInfo(String deviceId);
}