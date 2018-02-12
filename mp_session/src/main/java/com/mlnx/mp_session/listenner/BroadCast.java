package com.mlnx.mp_session.listenner;

import com.mlnx.mp_session.listenner.bp.BpBroadCast;
import com.mlnx.mp_session.listenner.bp.BpListener;
import com.mlnx.mp_session.listenner.ecg.EcgBroadCast;
import com.mlnx.mp_session.listenner.ecg.EcgListener;
import com.mlnx.mp_session.listenner.spo.SpoBroadCast;
import com.mlnx.mp_session.listenner.spo.SpoListener;
import com.mlnx.mptp.utils.MptpLogUtils;

import java.util.ArrayList;
import java.util.List;

public class BroadCast {

    private static List<EcgListener> ecgListeners = new ArrayList<EcgListener>();
    private static List<SpoListener> spoListeners = new ArrayList<>();
    private static List<BpListener> bpListeners = new ArrayList<>();


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

    public static void addSpoListener(SpoListener spoListener) {

        if (spoListener == null)
            return;
        synchronized (spoListeners) {
            if (!spoListeners.contains(spoListener)) {
                spoListeners.add(spoListener);
            } else {
                MptpLogUtils.w("不能重复监听血氧设备");
            }
        }
    }

    public static void removeSpoListener(SpoListener spoListener) {

        synchronized (spoListeners) {
            spoListeners.remove(spoListener);
        }
    }

    public static void addBpListener(BpListener bpListener) {

        if (bpListener == null)
            return;
        synchronized (bpListeners) {
            if (!bpListeners.contains(bpListener)) {
                bpListeners.add(bpListener);
            } else {
                MptpLogUtils.w("不能重复监听血压设备");
            }
        }
    }

    public static void removeBpListener(BpListener bpListener) {

        synchronized (bpListeners) {
            bpListeners.remove(bpListener);
        }
    }

    public static EcgBroadCast ecgBroadCast = new EcgBroadCast(ecgListeners);
    public static SpoBroadCast spoBroadCast = new SpoBroadCast(spoListeners);
    public static BpBroadCast bpBroadCast = new BpBroadCast(bpListeners);

}
