package com.mlnx.device_server.service;

import com.mlnx.device.ecg.EcgDeviceInfo;
import com.mlnx.device_server.comm.utils.DateUtils;
import com.mlnx.device_server.comm.utils.ThreadUtil;
import com.mlnx.device_server.mybatis.mapper.TDeviceMapper;
import com.mlnx.ecg.store.EcgStore;
import com.mlnx.ecg.store.domain.Ecg;
import com.mlnx.local.data.domain.DeviceOnlineRecord;
import com.mlnx.local.data.store.device.DeviceStore;
import com.mlnx.local.data.store.ecg.EcgAnalysisStore;
import com.mlnx.mp_server.protocol.RegisterMessage;
import com.mlnx.mp_server.support.Action;
import com.mlnx.mp_server.support.EcgSupport;
import com.mlnx.mp_server.support.MpSupportManager;
import com.mlnx.mp_session.domain.EcgInfo;
import com.mlnx.mp_session.listenner.BroadCast;
import com.mlnx.mp_session.listenner.ecg.EcgListener;
import com.mlnx.mptp.model.ECGData;
import com.mlnx.mptp.model.ECGDeviceInfo;
import com.mlnx.mptp.mptp.body.DeviceState;
import com.mlnx.mptp.mptp.body.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by amanda.shan on 2017/12/19.
 */
@Service
public class EcgService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Queue<Ecg> ecgs = new ConcurrentLinkedDeque<>();
    private long saveTime;
    private AtomicInteger ecgSize = new AtomicInteger(0);

    @Autowired
    private EcgStore ecgStore;

    @Autowired
    private DeviceStore deviceStore;

    @Autowired
    private EcgAnalysisStore ecgAnalysisStore;

    @Autowired
    private TDeviceMapper tDeviceMapper;

    @Value("${ecg.device.mac}")
    private String deviceMac;

    @PostConstruct
    private void init() {
        MpSupportManager.getInstance().setEcgSupport(ecgSupport);
        BroadCast.addEcgListenner(ecgListenner);
    }

    private EcgSupport ecgSupport = new EcgSupport() {
        @Override
        public void verifyEcg(final Action action) {

            ThreadUtil.execute(new Runnable() {
                @Override
                public void run() {
                    RegisterMessage message = action.getRegisterMessage();
                    EcgDeviceInfo ecgDeviceInfo = tDeviceMapper.getEcgDeviceInfo(message.getDeviceId());
                    try {
                        MpSupportManager.getInstance().verifyEcg(action, ecgDeviceInfo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void verifyCms(final Action action) {
            ThreadUtil.execute(new Runnable() {
                @Override
                public void run() {
                    RegisterMessage message = action.getRegisterMessage();
                    Integer patientId = tDeviceMapper.getPatientId(message.getDeviceId());
                    try {
                        MpSupportManager.getInstance().verifyCmsMp(action, patientId);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private EcgListener ecgListenner = new EcgListener() {
        @Override
        public void deviceOnline(Topic topic, String deviceId, Integer patientId) {
            DeviceOnlineRecord record = new DeviceOnlineRecord();
            record.setDate(new Date());
            record.setDeviceId(deviceId);
            record.setDeviceState(DeviceState.DEVICE_ONLINE.toString());
            deviceStore.save(record);
        }

        @Override
        public void deviceOfflien(Topic topic, String deviceId, Integer patientId) {
            DeviceOnlineRecord record = new DeviceOnlineRecord();
            record.setDate(new Date());
            record.setDeviceId(deviceId);
            record.setDeviceState(DeviceState.DEVICE_OFFLINE.toString());
            deviceStore.save(record);
        }

        @Override
        public void reciveEcgInfo(List<Topic> topics, EcgInfo ecgInfo) {

            if (ecgInfo.getEcgData() != null) {

                Ecg ecg = new Ecg();

                ecg.setPatientId(ecgInfo.getPatientId());
                ecg.setDeivceId(ecgInfo.getDeivceId());
                ecg.setDeviceType(ecgInfo.getDeviceType() + "");
                ecg.setStartTime(ecgInfo.getPacketTime());

                ECGDeviceInfo info = ecgInfo.getEcgDeviceInfo();
                if (info != null) {
                    ecg.setEcgChannelType(info.getEcgChannelType());
                    ecg.setSamplingRate(info.getSampling());
                    ecg.setAmplification(info.getAmplification());
                    ecg.setBatteryLevel(info.getBatteryLevel());
                    ecg.setPei(info.getPei());
                }

                ECGData ecgData = ecgInfo.getEcgData();
                if (ecgData != null) {
                    ecg.setHeartRate(ecgData.getEcgHeart());
                    ecg.setData(ecgData.getSuccessionData());
                    ecg.setEncryData(ecgData.getEncrySuccessionData());
                }

                saveEcg(ecg);
            }

            if (ecgInfo.getRealEcgAnalysResult() != null) {
                ecgAnalysisStore.save(ecgInfo.getPacketTime(), ecgInfo.getRealEcgAnalysResult().getHeart(), ecgInfo
                        .getRealEcgAnalysResult().getPbNumb(), ecgInfo.getRealEcgAnalysResult().getHeartResult());
            }
        }

        @Override
        public void startEcgPacket(Integer patientId) {

        }

        @Override
        public void stopEcgPacket(Integer patientId) {

        }
    };

    private void saveEcg(Ecg ecg) {

//        logger.debug("收到心电:" + ecg.toString());

        try {
            ecgs.add(ecg);
            ecgSize.incrementAndGet();
//            logger.debug("当前缓存心电数据大小：" + ecgSize.intValue());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        if (ecgSize.intValue() > 1 || System.currentTimeMillis() - saveTime > 3 * 1000) {

//            logger.debug("缓存心电数据大小：" + ecgSize.intValue());

            List<Ecg> ecgList = new ArrayList<>();

            Ecg ecg1 = ecgs.poll();
            while (ecg1 != null) {
                ecgList.add(ecg1);
                ecgSize.decrementAndGet();
                ecg1 = ecgs.poll();
            }

//            logger.debug("发送心电数据大小：" + ecgList.size());
            if (ecgList.size() > 0) {
                ecgStore.save(ecgList);
            }

            saveTime = System.currentTimeMillis();
        }
    }

    public List<Map<String, Object>> getEcg(Integer patientId, Long startTime, Long endTime) {

        List<Map<String, Object>> ecgs = ecgStore.getEcg(startTime, endTime, patientId);

        StringBuilder builder = new StringBuilder();
        builder.append(DateUtils.format(System.currentTimeMillis(), "HH:mm:ss:SSS"));
        builder.append(":  mStartTime " + startTime + "   ");
        builder.append(DateUtils.format(startTime, "HH:mm:ss:SSS"));
        builder.append("-> mEndTime " + endTime + "   ");
        builder.append(DateUtils.format(endTime, "HH:mm:ss:SSS"));
        builder.append("\n");
        builder.append(ecgs.size() + "");
        logger.debug(builder.toString());

        return ecgs;
    }

    public List<Map<String, Object>> getEncryEcg(Integer patientId, Long startTime, Long endTime) {

        List<Map<String, Object>> ecgs = ecgStore.getEncryEcg(startTime, endTime, patientId);

        StringBuilder builder = new StringBuilder();
        builder.append(DateUtils.format(System.currentTimeMillis(), "HH:mm:ss:SSS"));
        builder.append(":  mStartTime " + startTime + "   ");
        builder.append(DateUtils.format(startTime, "HH:mm:ss:SSS"));
        builder.append("-> mEndTime " + endTime + "   ");
        builder.append(DateUtils.format(endTime, "HH:mm:ss:SSS"));
        builder.append("\n");
        builder.append(ecgs.size() + "");
        logger.debug(builder.toString());

        return ecgs;
    }

    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger(0);
        integer.incrementAndGet();
        integer.incrementAndGet();


        byte[] guAcid = new byte[]{(byte) 0x86, (byte) 0xda, 0x26, (byte) 0xc6, 0x25, (byte) 0x99, (byte) 0xe2,
                (byte) 0xc0, (byte) 0xe5, (byte) 0xb8, 0x6e, 0x50};
        System.out.println(new String(guAcid));

    }
}
