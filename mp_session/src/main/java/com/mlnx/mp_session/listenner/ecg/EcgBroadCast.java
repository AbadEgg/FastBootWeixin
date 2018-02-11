package com.mlnx.mp_session.listenner.ecg;

import com.mlnx.mp_session.domain.EcgInfo;
import com.mlnx.mp_session.utils.ThreadUtil;
import com.mlnx.mptp.mptp.body.Topic;

import java.util.ArrayList;
import java.util.List;

public class EcgBroadCast implements EcgListener {

    private List<EcgListener> ecgListeners = new ArrayList<>();

    public EcgBroadCast(List<EcgListener> ecgListeners) {
        super();
        this.ecgListeners = ecgListeners;
    }

    @Override
    public void deviceOnline(Topic topic, String deviceId) {
        synchronized (ecgListeners) {
            for (EcgListener ecgListener : ecgListeners) {
                ecgListener.deviceOnline(topic, deviceId);
            }
        }

    }

    @Override
    public void deviceOfflien(Topic topic, String deviceId) {

        synchronized (ecgListeners) {
            for (EcgListener ecgListener : ecgListeners) {
                ecgListener.deviceOfflien(topic, deviceId);
            }
        }

    }

    @Override
    public void reciveEcgInfo(final List<Topic> topic, final EcgInfo ecgInfo) {
        ThreadUtil.execute(new Runnable() {

            @Override
            public void run() {
                synchronized (ecgListeners) {
                    for (EcgListener ecgListener : ecgListeners) {
                        ecgListener.reciveEcgInfo(topic, ecgInfo);
                    }
                }
            }
        });
    }

    @Override
    public void startEcgPacket(final Integer patientId) {
        ThreadUtil.execute(new Runnable() {

            @Override
            public void run() {
                synchronized (ecgListeners) {
                    for (EcgListener ecgListener : ecgListeners) {
                        ecgListener.startEcgPacket(patientId);
                    }
                }
            }
        });
    }

    @Override
    public void stopEcgPacket(final Integer patientId) {
        ThreadUtil.execute(new Runnable() {

            @Override
            public void run() {
                synchronized (ecgListeners) {
                    for (EcgListener ecgListener : ecgListeners) {
                        ecgListener.stopEcgPacket(patientId);
                    }
                }
            }
        });
    }


}
