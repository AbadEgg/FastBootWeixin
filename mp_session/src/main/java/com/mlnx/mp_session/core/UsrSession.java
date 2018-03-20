package com.mlnx.mp_session.core;

import com.mlnx.mp_session.domain.BpInfo;
import com.mlnx.mp_session.domain.EcgInfo;
import com.mlnx.mp_session.domain.SpoInfo;
import com.mlnx.mp_session.domain.TempInfo;
import com.mlnx.mp_session.listenner.BroadCast;
import com.mlnx.mp_session.listenner.adapter.UserBpAdapter;
import com.mlnx.mp_session.listenner.adapter.UserSpoAdapter;
import com.mlnx.mp_session.listenner.adapter.UserTempAdapter;
import com.mlnx.mp_session.listenner.adapter.UsrEcgAdapter;
import com.mlnx.mp_session.listenner.bp.BpListener;
import com.mlnx.mp_session.listenner.ecg.EcgListener;
import com.mlnx.mp_session.listenner.spo.SpoListener;
import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.model.ECGData;
import com.mlnx.mptp.mptp.body.Topic;
import com.mlnx.mptp.push.PushPacket;
import com.mlnx.mptp.push.body.PushDataType;
import com.mlnx.mptp.push.body.SerialType;
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
    private SpoLis spoLis = new SpoLis();
    private BpLis bpLis = new BpLis();
    private TempLis tempLis = new TempLis();

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
        BroadCast.removeBpListener(bpLis);
        BroadCast.removeSpoListener(spoLis);
        BroadCast.removeTempListener(tempLis);
    }

    @Override
    public EcgListener getEcgListener() {
        return ecgLis;
    }

    @Override
    public SpoListener getSpoListener() {
        return spoLis;
    }

    @Override
    public BpListener getBpListener() {
        return bpLis;
    }

    @Override
    public TempLis getTempListener() {
        return tempLis;
    }

    class EcgLis extends UsrEcgAdapter {

        @Override
        public void reciveEcgInfo(List<Topic> topics, EcgInfo ecgInfo) {
            super.reciveEcgInfo(topics, ecgInfo);

            boolean isExist = false;
            EcgInfo pushEcgInfo = new EcgInfo();
            pushEcgInfo.setDeivceId(ecgInfo.getDeivceId());
            pushEcgInfo.setPatientId(ecgInfo.getPatientId());

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
                        default:
                            break;
                    }
                }

//                MptpLogUtils.i(builder.toString());
            }

            if (isExist) {

//                MptpLogUtils.d(builder.toString());

                Map<PushDataType, Object> pushDataMap = new HashMap<>();
                pushDataMap.put(PushDataType.ECG_INFO, pushEcgInfo);

                PushPacket packet = new PushPacket();
                packet.getBody().setPushSerialType(serialType);
                packet.push(DeviceType.SERVER, ecgInfo.getDeivceId(), pushDataMap);
                channel.writeAndFlush(packet);
            }
        }
    }

    class SpoLis extends UserSpoAdapter{
        @Override
        public void reciveSpoInfo(List<Topic> topics, SpoInfo spoInfo) {
            super.reciveSpoInfo(topics, spoInfo);

            boolean isExist = false;
            for (Topic topic : topics) {
                if (TopicUtils.contain(getTopics(), topic)) {
                    isExist = true;
                    switch (topic.getTopicType()) {
                        case U_SPO_TOPIC:

                            break;
                    }
                }
            }

            if (isExist) {

                Map<PushDataType, Object> pushDataMap = new HashMap<>();
                pushDataMap.put(PushDataType.SPO_INFO, spoInfo);

                PushPacket packet = new PushPacket();
                packet.getBody().setPushSerialType(serialType);
                packet.push(DeviceType.SERVER, spoInfo.getDeivceId(), pushDataMap);
                channel.writeAndFlush(packet);
            }
        }
    }

    class BpLis extends UserBpAdapter{

        @Override
        public void reciveBpInfo(List<Topic> topics, BpInfo bpInfo) {
            super.reciveBpInfo(topics, bpInfo);

            boolean isExist = false;
            for (Topic topic : topics) {
                if (TopicUtils.contain(getTopics(), topic)) {
                    isExist = true;
                    switch (topic.getTopicType()) {
                        case U_BP_TOPIC:

                            break;
                        default:
                            break;
                    }
                }
            }

            if (isExist) {

                Map<PushDataType, Object> pushDataMap = new HashMap<>();
                pushDataMap.put(PushDataType.BP_INFO, bpInfo);

                PushPacket packet = new PushPacket();
                packet.getBody().setPushSerialType(serialType);
                packet.push(DeviceType.SERVER, bpInfo.getDeivceId(), pushDataMap);
                channel.writeAndFlush(packet);
            }
        }
    }

    class TempLis extends UserTempAdapter{

        @Override
        public void receiveTemp(List<Topic> topics, TempInfo tempInfo) {
            super.receiveTemp(topics, tempInfo);

            boolean isExist = false;
            for (Topic topic : topics) {
                if (TopicUtils.contain(getTopics(), topic)) {
                    isExist = true;
                    switch (topic.getTopicType()) {
                        case U_TEMP_TOPIC:

                            break;
                        default:
                            break;
                    }
                }
            }

            if (isExist) {

                Map<PushDataType, Object> pushDataMap = new HashMap<>();
                pushDataMap.put(PushDataType.TEMP_INFO, tempInfo);

                PushPacket packet = new PushPacket();
                packet.getBody().setPushSerialType(serialType);
                packet.push(DeviceType.SERVER, tempInfo.getDeivceId(), pushDataMap);
                channel.writeAndFlush(packet);
            }
        }
    }

    public void pushInfo(List<String> deviceIds){
        for (String deviceId : deviceIds){
            if(SessionManager.get(deviceId)!=null){
                Session session = SessionManager.get(SessionManager.get(deviceId));
                PushPacket packet = null;
                if (session instanceof EcgDeviceSession){

                    EcgDeviceSession ecgDeviceSession = (EcgDeviceSession) session;

                    EcgInfo pushEcgInfo = new EcgInfo();
                    pushEcgInfo.setDeivceId(ecgDeviceSession.getDeviceId());
                    pushEcgInfo.setPatientId(ecgDeviceSession.getPatientId());

                    pushEcgInfo.setEcgDeviceInfo(ecgDeviceSession.getEcgInfo().getEcgDeviceInfo());

                    Map<PushDataType, Object> pushDataMap = new HashMap<>();
                    pushDataMap.put(PushDataType.ECG_INFO, pushEcgInfo);

                    packet = new PushPacket();
                    packet.getBody().setPushSerialType(serialType);
                    packet.push(DeviceType.SERVER, pushEcgInfo.getDeivceId(), pushDataMap);
                }else if (session instanceof MpDeviceSession){
                    MpDeviceSession mpDeviceSession = (MpDeviceSession) session;

                    Map<PushDataType, Object> pushDataMap = new HashMap<>();
                    pushDataMap.put(PushDataType.ECG_INFO, mpDeviceSession.getEcgInfo());
                    pushDataMap.put(PushDataType.BP_INFO,mpDeviceSession.getBpInfo());
                    pushDataMap.put(PushDataType.SPO_INFO,mpDeviceSession.getSpoInfo());
                    pushDataMap.put(PushDataType.TEMP_INFO,mpDeviceSession.getTempInfo());

                    packet = new PushPacket();
                    packet.getBody().setPushSerialType(serialType);
                    packet.push(DeviceType.SERVER, mpDeviceSession.getDeviceId(), pushDataMap);
                }
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
