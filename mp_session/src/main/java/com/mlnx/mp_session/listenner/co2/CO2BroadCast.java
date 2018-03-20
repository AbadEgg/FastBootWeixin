package com.mlnx.mp_session.listenner.co2;

import com.mlnx.mp_session.domain.CO2Info;
import com.mlnx.mp_session.utils.ThreadUtil;
import com.mlnx.mptp.mptp.body.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fzh
 * @create 2018/3/20 13:58
 */
public class CO2BroadCast implements CO2Listener {

    private List<CO2Listener> co2Listeners = new ArrayList<>();

    public CO2BroadCast(List<CO2Listener> co2Listeners) {
        this.co2Listeners = co2Listeners;
    }

    @Override
    public void receiveCO2(final List<Topic> topics, final CO2Info co2Info) {
        ThreadUtil.execute(new Runnable() {

            @Override
            public void run() {
                synchronized (co2Listeners) {
                    for (CO2Listener co2Listener : co2Listeners) {
                        co2Listener.receiveCO2(topics, co2Info);
                    }
                }
            }
        });
    }

    @Override
    public void deviceOnline(Topic topic, String deviceId, Integer patientId) {

    }

    @Override
    public void deviceOffline(Topic topic, String deviceId, Integer patientId) {

    }
}
