package com.mlnx.mp_session.listenner.adapter;

import com.mlnx.mp_session.domain.SpoInfo;
import com.mlnx.mp_session.listenner.spo.SpoListener;
import com.mlnx.mptp.mptp.body.Topic;

import java.util.List;

/**
 * Created by amanda.shan on 2018/2/12.
 */
public class UserSpoAdapter implements SpoListener {
    @Override
    public void deviceOnline(Topic topic, String deviceId, Integer patientId) {

    }

    @Override
    public void deviceOfflien(Topic topic, String deviceId, Integer patientId) {

    }

    @Override
    public void reciveSpoInfo(List<Topic> topics, SpoInfo spoInfo) {

    }
}
