package com.mlnx.mp_server.listenner;

import com.mlnx.mptp.utils.MptpLogUtils;

import java.util.ArrayList;
import java.util.List;

public class BroadCast {

    private static List<EcgListenner> ecgListenners = new ArrayList<EcgListenner>();

    public static void addEcgListenner(EcgListenner ecgListenner) {

        if (ecgListenner == null)
            return;
        synchronized (ecgListenners) {
            if (!ecgListenners.contains(ecgListenner)) {
                ecgListenners.add(ecgListenner);
            } else {
                MptpLogUtils.w("不能重复监听心电设备");
            }
        }
    }

    public static void removeEcgListenner(EcgListenner ecgListenner) {

        synchronized (ecgListenners) {
            ecgListenners.remove(ecgListenner);
        }
    }

    public static EcgBroadCast ecgBroadCast = new EcgBroadCast(ecgListenners);

}
