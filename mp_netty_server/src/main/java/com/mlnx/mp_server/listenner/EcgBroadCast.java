package com.mlnx.mp_server.listenner;

import com.mlnx.analysis.domain.ReadEcgAnalysResult;
import com.mlnx.mp_server.utils.ThreadUtil;
import com.mlnx.mptp.model.Ecg;
import com.mlnx.mptp.mptp.body.Topic;

import java.util.ArrayList;
import java.util.List;

public class EcgBroadCast implements EcgListenner {

    private List<EcgListenner> ecgListenners = new ArrayList<>();

    public EcgBroadCast(List<EcgListenner> ecgListenners) {
        super();
        this.ecgListenners = ecgListenners;
    }

    @Override
    public void deviceOnline(Topic topic, String deviceId) {
        synchronized (ecgListenners) {
            for (EcgListenner ecgListenner : ecgListenners) {
                ecgListenner.deviceOnline(topic, deviceId);
            }
        }

    }

    @Override
    public void deviceOfflien(Topic topic, String deviceId) {

        synchronized (ecgListenners) {
            for (EcgListenner ecgListenner : ecgListenners) {
                ecgListenner.deviceOfflien(topic, deviceId);
            }
        }

    }

    @Override
    public void reciveEcgBody(final Topic topic, final Ecg ecg) {
        ThreadUtil.execute(new Runnable() {

            @Override
            public void run() {
                synchronized (ecgListenners) {
                    for (EcgListenner ecgListenner : ecgListenners) {
                        ecgListenner.reciveEcgBody(topic, ecg);
                    }
                }
            }
        });
    }

    @Override
    public void reciveReadEcgAnalysResult(final Topic topic, final ReadEcgAnalysResult result) {
        ThreadUtil.execute(new Runnable() {

            @Override
            public void run() {
                synchronized (ecgListenners) {
                    for (EcgListenner ecgListenner : ecgListenners) {
                        ecgListenner.reciveReadEcgAnalysResult(topic, result);
                    }
                }
            }
        });
    }

    @Override
    public void startEcgPacket(final Integer patientId) {
        ThreadUtil.execute(new Runnable() {

            @Override
            public void run() {
                synchronized (ecgListenners) {
                    for (EcgListenner ecgListenner : ecgListenners) {
                        ecgListenner.startEcgPacket(patientId);
                    }
                }
            }
        });
    }

    @Override
    public void stopEcgPacket(final Integer patientId) {
        ThreadUtil.execute(new Runnable() {

            @Override
            public void run() {
                synchronized (ecgListenners) {
                    for (EcgListenner ecgListenner : ecgListenners) {
                        ecgListenner.stopEcgPacket(patientId);
                    }
                }
            }
        });
    }


}
