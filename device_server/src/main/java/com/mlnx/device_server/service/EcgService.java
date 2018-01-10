package com.mlnx.device_server.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mlnx.device.ecg.EcgDeviceInfo;
import com.mlnx.device.inter.EcgDeviceService;
import com.mlnx.device_server.comm.utils.DateUtils;
import com.mlnx.device_server.comm.utils.ThreadUtil;
import com.mlnx.ecg.store.EcgStore;
import com.mlnx.mp_server.listenner.BroadCast;
import com.mlnx.mp_server.listenner.EcgListenner;
import com.mlnx.mp_server.protocol.RegisterMessage;
import com.mlnx.mp_server.support.Action;
import com.mlnx.mp_server.support.EcgSupport;
import com.mlnx.mp_server.support.MpSupportManager;
import com.mlnx.mptp.model.Ecg;
import com.mlnx.mptp.utils.TopicUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import static com.mlnx.device.ecg.dubbo.DerviceDubboServiceVersion.DEVICE_GROUP;
import static com.mlnx.device.ecg.dubbo.DerviceDubboServiceVersion.DEVICE_V;

/**
 * Created by amanda.shan on 2017/12/19.
 */
@Service
public class EcgService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference(version = DEVICE_V, group = DEVICE_GROUP, check = true)
    private EcgDeviceService ecgDeviceService;

    private Queue<Ecg> ecgs = new ConcurrentLinkedDeque<>();
    private long saveTime;
    private AtomicInteger ecgSize = new AtomicInteger(0);

    @Autowired
    private EcgStore ecgStore;

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
                    EcgDeviceInfo ecgDeviceInfo = ecgDeviceService.getEcgDeviceInfo(message.getDeviceId());
                    MpSupportManager.getInstance().verifyEcg(action, ecgDeviceInfo);
                }
            });
        }
    };

    private EcgListenner ecgListenner = new EcgListenner() {
        @Override
        public void reciveEcgBody(TopicUtils.DeviceTopic topic, final Ecg ecg) {
            ThreadUtil.execute(new Runnable() {
                @Override
                public void run() {
                    saveEcg(ecg);
                }
            });
        }

        @Override
        public void startEcgPacket(Integer patientId) {

        }

        @Override
        public void stopEcgPacket(Integer patientId) {

        }

        @Override
        public void deviceOnline(TopicUtils.DeviceTopic topic, String deviceId) {

        }

        @Override
        public void deviceOfflien(TopicUtils.DeviceTopic topic, String deviceId) {

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

        if (ecgSize.intValue() > 10 || System.currentTimeMillis() - saveTime > 3 * 1000) {

            logger.debug("缓存心电数据大小：" + ecgSize.intValue());

            List<Ecg> ecgList = new ArrayList<>();

            Ecg ecg1 = ecgs.poll();
            while (ecg1 != null) {
                ecgList.add(ecg1);
                ecgSize.decrementAndGet();
                ecg1 = ecgs.poll();
            }

            logger.debug("发送心电数据大小：" + ecgList.size());
            if (ecgList.size() > 0) {
                ecgStore.save(ecgList);
            }

            saveTime = System.currentTimeMillis();
        }
    }

    public List<Ecg> getEcg(Integer patientId, Long startTime, Long endTime) {

        List<Ecg> ecgs = ecgStore.getEcg(startTime, endTime, patientId);

        StringBuilder builder = new StringBuilder();
        builder.append(DateUtils.format(System.currentTimeMillis(), "HH:mm:ss:SSS"));
        builder.append(":  mStartTime " + startTime + "   ");
        builder.append(DateUtils.format(startTime, "HH:mm:ss:SSS"));
        builder.append("-> mEndTime " + endTime + "   ");
        builder.append(DateUtils.format(startTime, "HH:mm:ss:SSS"));
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