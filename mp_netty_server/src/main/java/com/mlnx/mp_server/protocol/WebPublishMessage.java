package com.mlnx.mp_server.protocol;

import com.mlnx.mptp.push.PushPacket;

/**
 * Created by amanda.shan on 2018/2/8.
 */
public class WebPublishMessage  extends AbstractMessage {

    private PushPacket pushPacket;

    public PushPacket getPushPacket() {
        return pushPacket;
    }

    public void setPushPacket(PushPacket pushPacket) {
        this.pushPacket = pushPacket;
    }
}