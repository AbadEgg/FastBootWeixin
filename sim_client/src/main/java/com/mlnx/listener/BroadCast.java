package com.mlnx.listener;

import com.mlnx.mp_session.domain.*;
import com.mlnx.mptp.utils.MptpLogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amanda.shan on 2018/3/2.
 */
public class BroadCast implements MsgListener{

    private static BroadCast instance;

    public static BroadCast getInstance() {
        if (instance == null){
            synchronized (BroadCast.class){
                if (instance == null){
                    instance = new BroadCast();
                }
            }
        }
        return instance;
    }

    private List<MsgListener> msgListeners = new ArrayList<MsgListener>();

    public void addMsgListener(MsgListener msgListener) {

        if (msgListener == null)
            return;
        synchronized (msgListeners) {
            if (!msgListeners.contains(msgListener)) {
                msgListeners.add(msgListener);
            } else {
                MptpLogUtils.w("不能重复监听心电设备");
            }
        }
    }

    public void removeMsgListener(MsgListener msgListener) {

        synchronized (msgListeners) {
            msgListeners.remove(msgListener);
        }
    }


    @Override
    public void receiveEcgInfo(EcgInfo ecgInfo) {
        synchronized (msgListeners) {
            for (MsgListener msgListener : msgListeners) {
                msgListener.receiveEcgInfo(ecgInfo);
            }
        }
    }

    @Override
    public void receiveBpInfo(BpInfo bpInfo) {
        synchronized (msgListeners) {
            for (MsgListener msgListener : msgListeners) {
                msgListener.receiveBpInfo(bpInfo);
            }
        }
    }

    @Override
    public void receiveSpoInfo(SpoInfo spoInfo) {
        synchronized (msgListeners) {
            for (MsgListener msgListener : msgListeners) {
                msgListener.receiveSpoInfo(spoInfo);
            }
        }
    }

    @Override
    public void receiveTempInfo(TempInfo tempInfo) {
        synchronized (msgListeners) {
            for (MsgListener msgListener : msgListeners) {
                msgListener.receiveTempInfo(tempInfo);
            }
        }
    }

    @Override
    public void receiveCO2Info(CO2Info co2Info) {
        synchronized (msgListeners) {
            for (MsgListener msgListener : msgListeners) {
                msgListener.receiveCO2Info(co2Info);
            }
        }
    }
}
