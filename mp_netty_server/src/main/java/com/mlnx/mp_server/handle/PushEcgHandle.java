package com.mlnx.mp_server.handle;

import com.mlnx.mp_server.protocol.EcgMessage;
import com.mlnx.mp_session.core.EcgDeviceSession;
import com.mlnx.mp_session.core.MpDeviceSession;
import com.mlnx.mp_session.core.Session;
import com.mlnx.mp_session.core.SessionManager;
import com.mlnx.mp_session.domain.EcgInfo;
import com.mlnx.mp_session.listenner.BroadCast;
import com.mlnx.mptp.model.ECGData;
import com.mlnx.mptp.model.ECGDeviceInfo;
import com.mlnx.mptp.model.analysis.RealEcgAnalysResult;
import com.mlnx.mptp.mptp.body.Topic;
import com.mlnx.mptp.mptp.body.TopicType;
import com.mlnx.mptp.utils.MptpLogUtils;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by amanda.shan on 2018/2/12.
 */
public class PushEcgHandle extends SimpleChannelInboundHandler<EcgMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EcgMessage msg) throws Exception {
        Session session = (EcgDeviceSession) SessionManager.get(ctx.channel());

        String deviceId = msg.getDeviceId();

        if (session instanceof EcgDeviceSession) {

            ECGData ecgData = msg.getEcgData();

            // 更新设备信息session
            ECGDeviceInfo ecgDeviceInfo = msg.getEcgDeviceInfo();
            EcgDeviceSession ecgDeviceSession = (EcgDeviceSession) session;
            ecgDeviceSession.setECGDeviceInfo(ecgDeviceInfo);
            ecgDeviceSession.getEcgInfo().getEcgDeviceInfo().updateECGDeviceInfo(ecgDeviceInfo);

            // 新建广播EcgInfo
            EcgInfo ecgInfo = new EcgInfo();
            ecgInfo.setDeivceId(deviceId);
            ecgInfo.setDeviceType(ecgDeviceSession.getDeviceType());
            ecgInfo.setPatientId(ecgDeviceSession.getPatientId());
            ecgInfo.setPacketTime(msg.getPacketTime());
            List<Topic> topics = new ArrayList<>();

            // 推送设备信息
            if (ecgDeviceInfo != null && msg.isDeviceInfoUpdate()){
                ecgInfo.setEcgDeviceInfo(ecgDeviceInfo);
                topics.add(new Topic(TopicType.U_ECG_DEVICE_TOPIC, deviceId));
            }
            // 推送心电数据
            if (ecgData.getSuccessionData() != null || ecgData.getEncrySuccessionData() != null) {

                ecgInfo.setEcgData(ecgData);

                // 不需要分析的心电数据
                if (ecgData.getSuccessionData() != null) {
                    topics.add(new Topic(TopicType.U_ECG_TOPIC, deviceId));

                    if (ecgData.getEcgHeart() != null && ecgData.getEcgHeart() != 0){
                        topics.add(new Topic(TopicType.U_ECG_HEART_TOPIC, deviceId));
                    }

                    ecgDeviceSession.setECGData(ecgData);

                }
                // 需要分析的心电数据
                else if (ecgData.getEncrySuccessionData() != null) {

                    RealEcgAnalysResult result = ecgDeviceSession.getAnalysis().realAnalysis(ecgData
                            .getEncrySuccessionData(), msg.getPacketTime());

                    ecgData.setEcgHeart(result.getHeart());
                    ecgData.setSuccessionData(result.getEcgData());
                    topics.add(new Topic(TopicType.U_ECG_ENCRYPTION_TOPIC, deviceId));

                    result.setEcgData(null);

                    // 更新分析结果session
                    ecgDeviceSession.getEcgInfo().setRealEcgAnalysResult(result);

                    if (result.getHeart() != null || result.getHeartResult() != null || result.getPbNumb() !=
                            null) {

                        ecgInfo.getEcgData().setEcgHeart(result.getHeart());
                        topics.add(new Topic(TopicType.U_ECG_HEART_TOPIC, deviceId));

                        ecgInfo.setRealEcgAnalysResult(result);
                        topics.add(new Topic(TopicType.U_ECG_REAL_ANALY_TOPIC, deviceId));
                    }

                    ecgDeviceSession.setECGData(ecgData);
                    ecgDeviceSession.setRealEcgAnalysResult(result);

                }
                ecgDeviceSession.setLastEcgDataTime(msg.getPacketTime());
                if (ecgDeviceSession.isFristEcgPacket()) {
                    ecgDeviceSession.setFristEcgPacket(false);
                    BroadCast.ecgBroadCast.startEcgPacket(ecgDeviceSession.getPatientId());
                }

            }

            if (topics.size() > 0){
                BroadCast.ecgBroadCast.reciveEcgInfo(topics, ecgInfo);
            }
        } else if (session instanceof MpDeviceSession){
            ECGData ecgData = msg.getEcgData();

            MpDeviceSession mpDeviceSession = (MpDeviceSession) session;

            // 新建广播EcgInfo
            EcgInfo ecgInfo = new EcgInfo();
            ecgInfo.setDeivceId(deviceId);
            ecgInfo.setDeviceType(mpDeviceSession.getDeviceType());
            ecgInfo.setPatientId(mpDeviceSession.getPatientId());
            ecgInfo.setPacketTime(msg.getPacketTime());
            List<Topic> topics = new ArrayList<>();

            ecgInfo.setEcgData(ecgData);
            if (ecgData.getEcgHeart() != null){
                topics.add(new Topic(TopicType.U_ECG_HEART_TOPIC, deviceId));
            }

            mpDeviceSession.setECGData(ecgData);

            if (topics.size() > 0){
                BroadCast.ecgBroadCast.reciveEcgInfo(topics, ecgInfo);
            }
        }else{
            MptpLogUtils.e("非法sesson类型");
        }
    }
}
