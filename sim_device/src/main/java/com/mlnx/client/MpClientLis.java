package com.mlnx.client;

import com.mlnx.mptp.mptp.MpPacket;

public interface MpClientLis {

    void receive(MpPacket mpPacket);

    void sendError();

    void close();
}
