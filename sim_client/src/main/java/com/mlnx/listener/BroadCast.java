package com.mlnx.listener;

import com.mlnx.mp_session.domain.BpInfo;
import com.mlnx.mp_session.domain.EcgInfo;
import com.mlnx.mp_session.domain.SpoInfo;
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
    public void reciveEcgInfo(EcgInfo ecgInfo) {
        synchronized (msgListeners) {
            for (MsgListener msgListener : msgListeners) {
                msgListener.reciveEcgInfo(ecgInfo);
            }
        }
    }

    @Override
    public void reciveBpInfo(BpInfo bpInfo) {
        synchronized (msgListeners) {
            for (MsgListener msgListener : msgListeners) {
                msgListener.reciveBpInfo(bpInfo);
            }
        }
    }

    @Override
    public void reciveSpoInfo(SpoInfo spoInfo) {
        synchronized (msgListeners) {
            for (MsgListener msgListener : msgListeners) {
                msgListener.reciveSpoInfo(spoInfo);
            }
        }
    }
}
