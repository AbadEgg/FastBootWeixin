package com.mlnx.mp_server.handle.common;

import com.mlnx.analysis.domain.ReadEcgAnalysResult;
import com.mlnx.device.ecg.ECGChannelType;
import com.mlnx.mp_server.core.EcgDeviceSession;
import com.mlnx.mp_server.core.Session;
import com.mlnx.mp_server.core.SessionManager;
import com.mlnx.mp_server.listenner.BroadCast;
import com.mlnx.mp_server.protocol.PublishMessage;
import com.mlnx.mptp.model.ECGData;
import com.mlnx.mptp.model.ECGDeviceInfo;
import com.mlnx.mptp.model.Ecg;
import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.mptp.body.Body;
import com.mlnx.mptp.mptp.body.DeviceState;
import com.mlnx.mptp.mptp.body.ResponseCode;
import com.mlnx.mptp.mptp.body.Topic;
import com.mlnx.mptp.mptp.body.TopicType;
import com.mlnx.mptp.mptp.body.ecg.EcgBody;
import com.mlnx.mptp.mptp.head.DeviceType;
import com.mlnx.mptp.mptp.head.QoS;
import com.mlnx.mptp.utils.MptpLogUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PushHandle extends SimpleChannelInboundHandler<PublishMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PublishMessage msg)
            throws Exception {

        String deviceId = msg.getBody().getDeviceId();
        final Body body = msg.getBody();
        Topic topic = msg.getTopic();
        EcgBody ecgBody = msg.getBody().getEcgBody();
        ECGData ecgData = ecgBody.getEcgData();

        Session session = null;

        if (topic == null && !msg.getDeviceType().equals(DeviceType.USR)) {
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

            // 设备发送给用户的主题
            case ECG_DEVICE:

                if (session instanceof EcgDeviceSession) {

                    ECGDeviceInfo ecgDeviceInfo = body.getEcgBody()
                            .getEcgDeviceInfo();

                    if (ecgDeviceInfo.getEcgChannelType() != null) {
                        int num = ecgDeviceInfo.getEcgChannelType() == ECGChannelType.CHAN_1 ? 1
                                : (ecgDeviceInfo.getEcgChannelType() == ECGChannelType.CHAN_3 ? 8
                                : 12);
                        ((EcgDeviceSession) session).setNumChannels(num);
                    }

                    if (ecgDeviceInfo.getSampling() != null) {
                        ((EcgDeviceSession) session).setSamplingRate(ecgDeviceInfo
                                .getSampling());
                    }

                    if (ecgDeviceInfo.getMagnification() != null) {
                        ((EcgDeviceSession) session).setAmplification(ecgDeviceInfo
                                .getMagnification());
                    }

                    if (ecgDeviceInfo.getWearMode() != null) {
                        ((EcgDeviceSession) session).setPose(ecgDeviceInfo
                                .getWearMode().getCode());
                    }

                    if (body.getEcgBody().getEcgData().getSuccessionData() != null) {

                        Ecg ecg = new Ecg();
                        ecg.setPatientId(body.getPatientID());
                        ecg.setDeivceId(((EcgDeviceSession) session).getDeviceId());
                        ecg.setStartTime(body.getPacketTime());
                        ecg.setNumChannels(((EcgDeviceSession) session)
                                .getNumChannels());
                        ecg.setSamplingRate(((EcgDeviceSession) session)
                                .getSamplingRate());
                        ecg.setAmplification(((EcgDeviceSession) session)
                                .getAmplification());
                        ecg.setHeartRate(body.getEcgBody().getEcgData()
                                .getEcgHeart());
                        ecg.setPose(((EcgDeviceSession) session).getPose());

                        ecg.setBatteryLevel(body.getEcgBody().getEcgDeviceInfo().getBatteryLevel());
                        ecg.setPei(body.getEcgBody().getEcgDeviceInfo().getPei());

                        // 心电更新
                        final EcgDeviceSession session1 = (EcgDeviceSession) session;

                        Topic topic1 = new Topic();
                        topic1.setDeviceId(deviceId);
                        if (body.getEcgBody().getEcgData().getSuccessionData() != null) {
                            ecg.setEncryData(ecgData.getEncrySuccessionData());

                            topic1.setTopicType(TopicType.U_ECG_TOPIC);
                            BroadCast.ecgBroadCast.reciveEcgBody(topic1, ecg);
                        } else if (ecgData.getEncrySuccessionData() != null) {
                            ecg.setEncryData(ecgData.getEncrySuccessionData());

                            ReadEcgAnalysResult result = session1.getAnalysis().realAnalysis(ecgData
                                    .getSuccessionData(), body.getPacketTime());
                            ecg.setData(result.getEcgData());

                            topic1.setTopicType(TopicType.U_ECG_TOPIC);
                            BroadCast.ecgBroadCast.reciveEcgBody(topic1, ecg);

                            topic1.setTopicType(TopicType.U_ECG_REAL_ANALY_TOPIC);
                            BroadCast.ecgBroadCast.reciveReadEcgAnalysResult(topic1, result);
                        }


                        session1.setLastEcgDataTime(body.getPacketTime());
                        if (session1.isFristEcgPacket()) {
                            session1.setFristEcgPacket(false);
                            BroadCast.ecgBroadCast.startEcgPacket(session1.getPatientId());
                        }

                    }
                }

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
}
