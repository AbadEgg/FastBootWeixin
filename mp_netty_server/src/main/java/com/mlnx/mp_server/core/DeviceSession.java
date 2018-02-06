package com.mlnx.mp_server.core;

import com.mlnx.mp_server.config.ConfigService;
import com.mlnx.mp_server.listenner.BroadCast;
import com.mlnx.mp_server.listenner.EcgListenner;
import com.mlnx.mp_server.listenner.adapter.UsrEcgAdapter;

public class DeviceSession extends Session {

    private String deviceId;

    private EcgLis ecgLis = new EcgLis();

    public DeviceSession() {
        super(ConfigService.DeviceReaderIdleMaxTimeSeconds);
    }

    public DeviceSession(String deviceId) {
        super(ConfigService.DeviceReaderIdleMaxTimeSeconds);
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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
