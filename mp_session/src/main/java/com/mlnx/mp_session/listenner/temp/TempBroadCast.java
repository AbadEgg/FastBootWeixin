package com.mlnx.mp_session.listenner.temp;

import com.mlnx.mp_session.domain.TempInfo;
import com.mlnx.mp_session.utils.ThreadUtil;
import com.mlnx.mptp.mptp.body.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fzh
 * @create 2018/3/19 14:05
 */
public class TempBroadCast implements TempListener {

    private List<TempListener> tempListeners = new ArrayList<>();

    public TempBroadCast(List<TempListener> tempListeners) {
        this.tempListeners = tempListeners;
    }

    @Override
    public void receiveTemp(final List<Topic> topics, final TempInfo tempInfo) {
        ThreadUtil.execute(new Runnable() {

            @Override
            public void run() {
                synchronized (tempListeners) {
                    for (TempListener tempListener : tempListeners) {
                        tempListener.receiveTemp(topics, tempInfo);
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
