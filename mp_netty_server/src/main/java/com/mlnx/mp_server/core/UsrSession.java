package com.mlnx.mp_server.core;

import com.mlnx.analysis.domain.ReadEcgAnalysResult;
import com.mlnx.mp_server.config.ConfigService;
import com.mlnx.mp_server.listenner.BroadCast;
import com.mlnx.mp_server.listenner.EcgListenner;
import com.mlnx.mp_server.listenner.adapter.UsrEcgAdapter;
import com.mlnx.mptp.model.Ecg;
import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.mptp.body.Body;
import com.mlnx.mptp.mptp.body.Topic;
import com.mlnx.mptp.mptp.head.DeviceType;
import com.mlnx.mptp.utils.MptpLogUtils;
import com.mlnx.mptp.utils.TopicUtils;

public class UsrSession extends Session {

    public UsrSession() {
        super(ConfigService.WebReaderIdleMaxTimeSeconds);
    }

    private String userName;
    private String password;

    private EcgLis ecgLis = new EcgLis();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void removeLis() {
        BroadCast.removeEcgListenner(ecgLis);
    }

    @Override
    public EcgListenner getEcgListener() {
        return ecgLis;
    }


    class EcgLis extends UsrEcgAdapter {

        @Override
        public void reciveEcgBody(Topic topic, Ecg ecg) {
            super.reciveEcgBody(topic, ecg);
            if (getTopics() != null) {
                if (TopicUtils.contain(getTopics(), topic)) {

                    for (Topic topic1: getTopics()) {
                        switch (topic1.getTopicType()) {
                            case U_ECG_TOPIC:
                                MptpLogUtils.i("不支持心电数据推送");
                                break;
                            case U_ECG_HEART_TOPIC:
                                if (ecg.getHeartRate() != null) {
                                    Body body = new Body();
                                    body.getEcgBody().getEcgData().setEcgHeart(ecg.getHeartRate());
                                    body.setDeviceId(ecg.getDeivceId());
                                    body.setPatientID(ecg.getPatientId());
                                    MpPacket packet = new MpPacket().push(DeviceType.SERVER, body);
                                    channel.writeAndFlush(packet);
                                }
                                break;
                        }
                    }
                }
            }
        }

        @Override
        public void reciveReadEcgAnalysResult(Topic topic, ReadEcgAnalysResult result) {
            super.reciveReadEcgAnalysResult(topic, result);

            if (getTopics() != null) {
                if (TopicUtils.contain(getTopics(), topic)) {

                    for (Topic topic1: getTopics()) {
                        switch (topic1.getTopicType()) {
                            case U_ECG_ENCRYPTION_TOPIC:
                                MptpLogUtils.i("不支持心电数据推送");
                                break;
                            case U_ECG_REAL_ANALY_TOPIC:
                                MptpLogUtils.i("不支持心电数据推送");
                                break;
                            case U_ECG_HEART_TOPIC:
//                                if (result.getHeart() != null) {
//                                    Body body = new Body();
//                                    body.getEcgBody().getEcgData().setEcgHeart(result.getHeart());
//                                    body.setDeviceId(ecg.getDeivceId());
//                                    body.setPatientID(ecg.getPatientId());
//                                    MpPacket packet = new MpPacket().push(DeviceType.SERVER, body);
//                                    channel.writeAndFlush(packet);
//                                }
                                break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "UsrSession [userName=" + userName + ", password=" + password
                + "]" + super.toString();
    }

}
