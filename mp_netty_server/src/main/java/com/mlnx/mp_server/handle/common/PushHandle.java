package com.mlnx.mp_server.handle.common;

import com.mlnx.mp_server.protocol.PublishMessage;
import com.mlnx.mp_session.core.EcgDeviceSession;
import com.mlnx.mp_session.core.Session;
import com.mlnx.mp_session.core.SessionManager;
import com.mlnx.mp_session.domain.EcgInfo;
import com.mlnx.mp_session.listenner.BroadCast;
import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.ResponseCode;
import com.mlnx.mptp.model.ECGData;
import com.mlnx.mptp.model.ECGDeviceInfo;
import com.mlnx.mptp.model.analysis.RealEcgAnalysResult;
import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.mptp.body.Body;
import com.mlnx.mptp.mptp.body.DeviceState;
import com.mlnx.mptp.mptp.body.Topic;
import com.mlnx.mptp.mptp.body.TopicType;
import com.mlnx.mptp.mptp.head.QoS;
import com.mlnx.mptp.utils.MptpLogUtils;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PushHandle extends SimpleChannelInboundHandler<PublishMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PublishMessage msg)
            throws Exception {

        String deviceId = msg.getBody().getDeviceId();
        final Body body = msg.getBody();
        Topic topic = msg.getTopic();

        Session session = null;

        if (topic == null && msg.getDeviceType().equals(DeviceType.USR)) {
            MptpLogUtils.e("非法主题，类型：" + msg.getDeviceType());
            MpPacket packet = new MpPacket().pushAck(DeviceType.SERVER,
                    body.getMessageId(), ResponseCode.ILLEGAL_TOPICE);
            ctx.channel().writeAndFlush(packet);
            return;
        }

        session = SessionManager.get(ctx.channel());
        if (body.getDeviceState() != null && body.getDeviceState().equals(DeviceState.WAIT_CONFIG)) {
            MptpLogUtils.i("收到：" + deviceId + " 等待配置的包");
            SessionManager.addConfig(session);
        }

        switch (msg.getDeviceType()) {
            // 用户发送给设备的主题
            case USR:
                switch (topic.getTopicType()) {
                    case D_ECG_TOPIC:
                }
                break;

            // 心电设备发送给用户的主题
            case ECG_DEVICE:
                processEcg(session, body);
                break;
        }

        if (QoS.LEAST_ONE.equals(msg.getQoS())) {
            MpPacket packet = null;
            if (body.getMessageId() == null)
                packet = new MpPacket().pushAck(DeviceType.SERVER, null,
                        ResponseCode.LOST_MESSAGE_ID);
            else {
                packet = new MpPacket().pushAck(DeviceType.SERVER,
                        body.getMessageId(), ResponseCode.SUCESS);
            }
            ctx.channel().writeAndFlush(packet);
        }
    }

    private void processEcg( Session session, Body body){
        if (session instanceof EcgDeviceSession) {

            ECGData ecgData = body.getEcgBody().getEcgData();
            String deviceId = body.getDeviceId();

            // 更新设备信息session
            ECGDeviceInfo ecgDeviceInfo = body.getEcgBody()
                    .getEcgDeviceInfo();
            EcgDeviceSession ecgDeviceSession = (EcgDeviceSession) session;
            ecgDeviceSession.getEcgInfo().getEcgDeviceInfo().updateECGDeviceInfo(ecgDeviceInfo);


            // 新建广播EcgInfo
            EcgInfo ecgInfo = new EcgInfo();
            ecgInfo.setDeivceId(deviceId);
            ecgInfo.setPatientId(ecgDeviceSession.getPatientId());
            ecgInfo.setPacketTime(body.getPacketTime());
            List<Topic> topics = new ArrayList<>();

            // 推送设备信息
            if (ecgDeviceInfo != null && body.getEcgBody().isDeviceInfoUpdate()){
                ecgInfo.setEcgDeviceInfo(ecgDeviceInfo);
                topics.add(new Topic(TopicType.U_ECG_DEVICE_TOPIC, deviceId));
            }
            // 推送心电数据
            if (ecgData.getSuccessionData() != null || ecgData.getEncrySuccessionData() != null) {

                ecgInfo.setEcgData(ecgData);

                // 不需要分析的心电数据
                if (ecgData.getSuccessionData() != null) {
                    topics.add(new Topic(TopicType.U_ECG_TOPIC, deviceId));
                }
                // 需要分析的心电数据
                else if (ecgData.getEncrySuccessionData() != null) {

                    RealEcgAnalysResult result = ecgDeviceSession.getAnalysis().realAnalysis(ecgData
                            .getEncrySuccessionData(), body.getPacketTime());

                    ecgData.setSuccessionData(result.getEcgData());
                    topics.add(new Topic(TopicType.U_ECG_ENCRYPTION_TOPIC, deviceId));

                    result.setEcgData(null);

                    // 更新分析结果session
                    ecgDeviceSession.getEcgInfo().setRealEcgAnalysResult(result);

                    if (result.getHeart() != null || result.getHeartResult() != null || result.getPbNumb() !=
                            null) {
                        ecgInfo.setRealEcgAnalysResult(result);
                        topics.add(new Topic(TopicType.U_ECG_REAL_ANALY_TOPIC, deviceId));
                    }
                }


                ecgDeviceSession.setLastEcgDataTime(body.getPacketTime());
                if (ecgDeviceSession.isFristEcgPacket()) {
                    ecgDeviceSession.setFristEcgPacket(false);
                    BroadCast.ecgBroadCast.startEcgPacket(ecgDeviceSession.getPatientId());
                }

            }

            if (topics.size() > 0){
                BroadCast.ecgBroadCast.reciveEcgInfo(topics, ecgInfo);
            }
        }

    }
}
