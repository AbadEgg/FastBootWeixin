package com.mlnx.mp_session.core;

import com.mlnx.mp_session.domain.EcgInfo;
import com.mlnx.mp_session.listenner.BroadCast;
import com.mlnx.mp_session.listenner.adapter.UsrEcgAdapter;
import com.mlnx.mp_session.listenner.ecg.EcgListener;
import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.model.ECGData;
import com.mlnx.mptp.model.analysis.RealEcgAnalysResult;
import com.mlnx.mptp.mptp.body.Topic;
import com.mlnx.mptp.push.PushPacket;
import com.mlnx.mptp.push.body.PushDataType;
import com.mlnx.mptp.push.body.SerialType;
import com.mlnx.mptp.utils.MptpLogUtils;
import com.mlnx.mptp.utils.TopicUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsrSession extends Session {

    public UsrSession() {
        super(WebReaderIdleMaxTimeSeconds);
    }

    private String userName;
    private String password;

    private SerialType serialType = SerialType.JSON;

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

    public SerialType getSerialType() {
        return serialType;
    }

    public void setSerialType(SerialType serialType) {
        this.serialType = serialType;
    }

    @Override
    public void removeLis() {
        BroadCast.removeEcgListenner(ecgLis);
    }

    @Override
    public EcgListener getEcgListener() {
        return ecgLis;
    }


    class EcgLis extends UsrEcgAdapter {

        @Override
        public void reciveEcgInfo(List<Topic> topics, EcgInfo ecgInfo) {
            super.reciveEcgInfo(topics, ecgInfo);

            boolean isExist = false;
            EcgInfo pushEcgInfo = new EcgInfo();

            StringBuilder builder = new StringBuilder();
            builder.append(userName + ":" + password + "  ");
            for (Topic topic : topics) {
                if (TopicUtils.contain(getTopics(), topic)) {
                    isExist = true;
                    switch (topic.getTopicType()) {
                        // 心电设备信息
                        case U_ECG_DEVICE_TOPIC:
                            pushEcgInfo.setEcgDeviceInfo(ecgInfo.getEcgDeviceInfo());
                            builder.append("推送心电设备信息 ");
                            break;
                        // 原始数据
                        case U_ECG_TOPIC:
                            if (pushEcgInfo.getEcgData() == null) {
                                pushEcgInfo.setEcgData(new ECGData());
                            }
                            pushEcgInfo.getEcgData().setSuccessionData(ecgInfo.getEcgData().getSuccessionData());
                            builder.append("原始数据 ");
                            break;
                        // 加密过的数据
                        case U_ECG_ENCRYPTION_TOPIC:
                            if (pushEcgInfo.getEcgData() == null) {
                                pushEcgInfo.setEcgData(new ECGData());
                            }
                            pushEcgInfo.getEcgData().setEncrySuccessionData(ecgInfo.getEcgData()
                                    .getEncrySuccessionData());
                            builder.append("加密过的数据 ");
                            break;
                        // 实时分析结果
                        case U_ECG_REAL_ANALY_TOPIC:

                            RealEcgAnalysResult result = ecgInfo.getRealEcgAnalysResult();
                            pushEcgInfo.setRealEcgAnalysResult(ecgInfo.getRealEcgAnalysResult());
                            builder.append("实时分析结果 ");
                            break;
                        // 心率
                        case U_ECG_HEART_TOPIC:
                            if (pushEcgInfo.getEcgData() == null) {
                                pushEcgInfo.setEcgData(new ECGData());
                            }
                            pushEcgInfo.getEcgData().setEcgHeart(ecgInfo.getEcgData()
                                    .getEcgHeart());
                            builder.append("心率 ");
                            break;
                    }
                }
            }

            if (isExist) {

                MptpLogUtils.d(builder.toString());

                Map<PushDataType, Object> pushDataMap = new HashMap<>();
                pushDataMap.put(PushDataType.ECG_INFO, pushEcgInfo);

                PushPacket packet = new PushPacket();
                packet.getBody().setPushSerialType(serialType);
                packet.push(DeviceType.SERVER, ecgInfo.getDeivceId(), pushDataMap);
                channel.writeAndFlush(packet);
            }
        }
    }

    @Override
    public String toString() {
        return "UsrSession [userName=" + userName + ", password=" + password
                + "]" + super.toString();
    }

}
