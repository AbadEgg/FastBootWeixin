package com.mlnx.device_server.comm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by amanda.shan on 2017/3/23.
 */
@Component
public class MpServerConfig {

    @Value("${mp.server.device.port}")
    private int mpDevicePort;
    @Value("${mp.server.web.port}")
    private int mpWebPort;

    public int getMpDevicePort() {
        return mpDevicePort;
    }

    public void setMpDevicePort(int mpDevicePort) {
        this.mpDevicePort = mpDevicePort;
    }

    public int getMpWebPort() {
        return mpWebPort;
    }

    public void setMpWebPort(int mpWebPort) {
        this.mpWebPort = mpWebPort;
    }
}
