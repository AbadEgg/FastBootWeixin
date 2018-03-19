package com.mlnx.mp_session.listenner.adapter;

import com.mlnx.mp_session.domain.TempInfo;
import com.mlnx.mp_session.listenner.temp.TempListener;
import com.mlnx.mptp.mptp.body.Topic;

import java.util.List;

/**
 * @author fzh
 * @create 2018/3/19 17:05
 */
public class UserTempAdapter implements TempListener {
    @Override
    public void receiveTemp(List<Topic> topics, TempInfo tempInfo) {

    }

    @Override
    public void deviceOnline(Topic topic, String deviceId, Integer patientId) {

    }

    @Override
    public void deviceOfflien(Topic topic, String deviceId, Integer patientId) {

    }
}
