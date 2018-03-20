package com.mlnx.device_server.service;

import com.mlnx.local.data.store.spo.SpoStore;
import com.mlnx.mp_session.domain.SpoInfo;
import com.mlnx.mp_session.listenner.BroadCast;
import com.mlnx.mp_session.listenner.spo.SpoListener;
import com.mlnx.mptp.mptp.body.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.PostConstruct;

/**
 * Created by amanda.shan on 2018/2/24.
 */
@Service
public class SpoService {

    @Autowired
    private SpoStore spoStore;

    @PostConstruct
    private void init() {
        BroadCast.addSpoListener(spoListener);
    }

    private SpoListener spoListener = new SpoListener() {
        @Override
        public void reciveSpoInfo(List<Topic> topics, SpoInfo spoInfo) {
            if (spoInfo.getResultSPO() != null && spoInfo.getResultSPO() != null && spoInfo.getPacketTime() != null) {
                spoStore.saveBp(spoInfo.getResultSPO(), spoInfo.getResultHeart(), spoInfo.getPacketTime());
            }
        }

        @Override
        public void deviceOnline(Topic topic, String deviceId, Integer patientId) {

        }

        @Override
        public void deviceOffline(Topic topic, String deviceId, Integer patientId) {

        }
    };
}
