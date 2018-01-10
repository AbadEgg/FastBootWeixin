package com.mlnx.mp_server.core;

import com.mlnx.mp_server.config.ConfigService;
import com.mlnx.mp_server.listenner.BroadCast;
import com.mlnx.mp_server.listenner.EcgListenner;
import com.mlnx.mp_server.listenner.adapter.UsrEcgAdapter;
import com.mlnx.mptp.mptp.head.DeviceType;
import com.mlnx.mptp.utils.TopicUtils;

public class DeviceSession extends Session {

    private String deviceId;

    private EcgLis ecgLis = new EcgLis();

    public DeviceSession() {
        super(ConfigService.DeviceReaderIdleMaxTimeSeconds);
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * 设备注册上以后自动监听主题
     */
    @Override
    public void setDeviceType(DeviceType deviceType) {
        super.setDeviceType(deviceType);

        TopicUtils.DeviceTopic deviceTopic = null;
        switch (deviceType) {
            case BP_DEVICE:
                deviceTopic = new TopicUtils().new DeviceTopic(TopicUtils.TopicType.D_BP_TOPIC);
                deviceTopic.setDeviceId(deviceId);
                break;
            case SBP_DEVICE:
                deviceTopic = new TopicUtils().new DeviceTopic(
                        TopicUtils.TopicType.D_BPS_TOPIC);
                deviceTopic.setDeviceId(deviceId);
                break;
            case ECG_DEVICE:
                deviceTopic = new TopicUtils().new DeviceTopic(
                        TopicUtils.TopicType.D_ECG_TOPIC);
                deviceTopic.setDeviceId(deviceId);
                break;
        }

        if (deviceTopic != null) {
            setDeviceTopic(deviceTopic);
        }
    }

    @Override
    public void removeLis() {
        BroadCast.removeEcgListenner(ecgLis);
    }

    @Override
    public EcgListenner getEcgListener() {
        return ecgLis;
    }


    class EcgLis extends UsrEcgAdapter {

    }

    @Override
    public String toString() {
        return "DeviceSession [deviceId=" + deviceId + "]" + super.toString();
    }

}
