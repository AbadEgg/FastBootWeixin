package com.mlnx.mp_session.listenner.adapter;

import com.mlnx.mp_session.domain.CO2Info;
import com.mlnx.mp_session.listenner.co2.CO2Listener;
import com.mlnx.mptp.mptp.body.Topic;

import java.util.List;

/**
 * @author fzh
 * @create 2018/3/20 14:12
 */
public class UserCO2Adapter implements CO2Listener {
    @Override
    public void receiveCO2(List<Topic> topics, CO2Info co2Info) {

    }

    @Override
    public void deviceOnline(Topic topic, String deviceId, Integer patientId) {

    }

    @Override
    public void deviceOffline(Topic topic, String deviceId, Integer patientId) {

    }
}
