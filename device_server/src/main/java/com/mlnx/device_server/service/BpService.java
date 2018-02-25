package com.mlnx.device_server.service;

import com.mlnx.local.data.store.bp.BpStore;
import com.mlnx.mp_session.domain.BpInfo;
import com.mlnx.mp_session.listenner.BroadCast;
import com.mlnx.mp_session.listenner.bp.BpListener;
import com.mlnx.mptp.mptp.body.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.PostConstruct;

/**
 * Created by amanda.shan on 2018/2/24.
 */
@Service
public class BpService {

    @Autowired
    private BpStore bpStore;

    @PostConstruct
    private void init() {
        BroadCast.addBpListener(bpListener);
    }

    private BpListener bpListener = new BpListener() {
        @Override
        public void reciveBpInfo(List<Topic> topics, BpInfo bpInfo) {
            if (bpInfo.getPacketTime() != null && bpInfo.getHeart() != null && bpInfo.getDbp() != null && bpInfo
                    .getSbp() != null) {
                bpStore.saveBp(bpInfo.getSbp(), bpInfo.getDbp(), bpInfo.getHeart(), bpInfo.getPacketTime());
            }
        }

        @Override
        public void deviceOnline(Topic topic, String deviceId) {

        }

        @Override
        public void deviceOfflien(Topic topic, String deviceId) {

        }
    };
}
