package com.mlnx.device_server.service;

import com.mlnx.mp_server.support.MpSupportManager;
import com.mlnx.mp_session.core.Session;
import com.mlnx.mp_session.core.SessionManager;

import org.springframework.stereotype.Service;

/**
 * Created by amanda.shan on 2017/5/8.
 */
@Service
public class DeviceService {

    public String configDevice(String deviceId, String ssid, String password, Byte wifiChannel, byte[] serverIp,
                               Integer serverPort, Byte heartChannel) {
        return MpSupportManager.getInstance().configDevice(deviceId, ssid, password, wifiChannel, serverIp, serverPort,
                heartChannel);
    }

    public boolean testConfigDevice(String deviceId) {
        Session session = SessionManager.getConfig(deviceId);
        if (session == null)
            return false;
        else return true;
    }

}
