package com.mlnx.device.inter;

import com.mlnx.device.ecg.EcgDeviceInfo;

/**
 * Created by amanda.shan on 2017/12/19.
 */
public interface EcgDeviceService {

    EcgDeviceInfo getEcgDeviceInfo(String deviceId);
}
