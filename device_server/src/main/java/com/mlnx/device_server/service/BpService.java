package com.mlnx.device_server.service;

import com.mlnx.local.data.store.bp.BpStore;
import com.mlnx.mp_session.domain.BpInfo;
import com.mlnx.mp_session.listenner.BroadCast;
import com.mlnx.mp_session.listenner.bp.BpListener;
import com.mlnx.mptp.mptp.body.Topic;
import com.mlnx.utils.RedisUtil;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by amanda.shan on 2018/2/24.
 */
@Service
public class BpService {

    private static final String BP_PATIENT = "bp_patient";

    @Autowired
    private BpStore bpStore;

    @Autowired
    private RedisUtil<String> redisUtil;

    @PostConstruct
    private void init() {
        BroadCast.addBpListener(bpListener);
    }

    private BpListener bpListener = new BpListener() {
        @Override
        public void reciveBpInfo(List<Topic> topics, BpInfo bpInfo) {
            if (bpInfo.getPacketTime() != null && bpInfo.getHeart() != null && bpInfo.getDbp() != null && bpInfo
                    .getSbp() != null) {
                bpStore.saveBp(bpInfo.getPatientId(),bpInfo.getSbp(), bpInfo.getDbp(), bpInfo.getHeart(), bpInfo.getPacketTime());
                redisUtil.sSet(BP_PATIENT,bpInfo.getPatientId().toString());
                if(redisUtil.ttl(BP_PATIENT)==-1){
                    DateTime dateTime = new DateTime().millisOfDay().withMaximumValue();
                    long seconds = new Duration(new DateTime(), dateTime).getStandardSeconds();
                    redisUtil.expire(BP_PATIENT,seconds);
                }
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
