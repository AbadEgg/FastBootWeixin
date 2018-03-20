package com.mlnx.mp_session.listenner.adapter;

import com.mlnx.mp_session.domain.BpInfo;
import com.mlnx.mp_session.listenner.bp.BpListener;
import com.mlnx.mptp.mptp.body.Topic;

import java.util.List;

/**
 * Created by amanda.shan on 2018/2/12.
 */
public class UserBpAdapter implements BpListener {
    @Override
    public void deviceOnline(Topic topic, String deviceId, Integer patientId) {

    }

    @Override
    public void deviceOffline(Topic topic, String deviceId, Integer patientId) {

    }

    @Override
    public void reciveBpInfo(List<Topic> topics, BpInfo bpInfo) {

    }
}
