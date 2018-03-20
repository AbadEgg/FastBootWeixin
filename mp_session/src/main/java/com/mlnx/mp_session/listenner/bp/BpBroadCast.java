package com.mlnx.mp_session.listenner.bp;

import com.mlnx.mp_session.domain.BpInfo;
import com.mlnx.mptp.mptp.body.Topic;

import java.util.ArrayList;
import java.util.List;

public class BpBroadCast implements BpListener {

    private List<BpListener> bpListeners = new ArrayList<>();

    public BpBroadCast(List<BpListener> bpListeners) {
        super();
        this.bpListeners = bpListeners;
    }


    @Override
    public void deviceOnline(Topic topic, String deviceId, Integer patientId) {

    }

    @Override
    public void deviceOffline(Topic topic, String deviceId, Integer patientId) {

    }

    @Override
    public void reciveBpInfo(List<Topic> topics, BpInfo bpInfo) {
        synchronized (bpListeners) {
            for (BpListener bpListener : bpListeners) {
                bpListener.reciveBpInfo(topics, bpInfo);
            }
        }
    }
}
