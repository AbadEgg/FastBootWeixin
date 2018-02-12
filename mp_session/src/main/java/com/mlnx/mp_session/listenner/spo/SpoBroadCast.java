package com.mlnx.mp_session.listenner.spo;

import com.mlnx.mp_session.domain.SpoInfo;
import com.mlnx.mptp.mptp.body.Topic;

import java.util.ArrayList;
import java.util.List;

public class SpoBroadCast implements SpoListener {

    private List<SpoListener> spoListeners = new ArrayList<>();

    public SpoBroadCast(List<SpoListener> spoListeners) {
        super();
        this.spoListeners = spoListeners;
    }


    @Override
    public void reciveSpoInfo(List<Topic> topics, SpoInfo spoInfo) {
        synchronized (spoListeners) {
            for (SpoListener spoListener : spoListeners) {
                spoListener.reciveSpoInfo(topics, spoInfo);
            }
        }
    }

    @Override
    public void deviceOnline(Topic topic, String deviceId) {

    }

    @Override
    public void deviceOfflien(Topic topic, String deviceId) {

    }
}
