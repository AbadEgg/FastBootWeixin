package com.mlnx.mp_session.listenner;

import com.mlnx.mp_session.listenner.ecg.EcgBroadCast;
import com.mlnx.mp_session.listenner.ecg.EcgListener;
import com.mlnx.mptp.utils.MptpLogUtils;

import java.util.ArrayList;
import java.util.List;

public class BroadCast {

    private static List<EcgListener> ecgListeners = new ArrayList<EcgListener>();

    public static void addEcgListenner(EcgListener ecgListener) {

        if (ecgListener == null)
            return;
        synchronized (ecgListeners) {
            if (!ecgListeners.contains(ecgListener)) {
                ecgListeners.add(ecgListener);
            } else {
                MptpLogUtils.w("不能重复监听心电设备");
            }
        }
    }

    public static void removeEcgListenner(EcgListener ecgListener) {

        synchronized (ecgListeners) {
            ecgListeners.remove(ecgListener);
        }
    }

    public static EcgBroadCast ecgBroadCast = new EcgBroadCast(ecgListeners);

}
