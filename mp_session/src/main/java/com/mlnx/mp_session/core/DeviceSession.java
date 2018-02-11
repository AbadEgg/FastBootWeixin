package com.mlnx.mp_session.core;


import com.mlnx.mp_session.listenner.BroadCast;
import com.mlnx.mp_session.listenner.adapter.UsrEcgAdapter;
import com.mlnx.mp_session.listenner.ecg.EcgListener;

public class DeviceSession extends Session {

    private String deviceId;

    private EcgLis ecgLis = new EcgLis();

    public DeviceSession() {
        super(DeviceReaderIdleMaxTimeSeconds);
    }

    public DeviceSession(String deviceId) {
        super(DeviceReaderIdleMaxTimeSeconds);
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
    public EcgListener getEcgListener() {
        return ecgLis;
    }


    class EcgLis extends UsrEcgAdapter {

    }

    @Override
    public String toString() {
        return "DeviceSession [deviceId=" + deviceId + "]" + super.toString();
    }

}
