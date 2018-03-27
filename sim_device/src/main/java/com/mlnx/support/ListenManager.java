package com.mlnx.support;

import com.mlnx.client.MpClientLis;
import com.mlnx.mp_server.support.MpSupportManager;

/**
 * @author fzh
 * @create 2018/3/27 14:54
 */
public class ListenManager {

    private static ListenManager instance;

    public static ListenManager getInstance() {
        if (instance == null) {
            synchronized (MpSupportManager.class) {
                if (instance == null) {
                    instance = new ListenManager();
                }
            }
        }
        return instance;
    }

    private MpClientLis mpClientLis;

    public void setMpClientLis(MpClientLis mpClientLis) {
        this.mpClientLis = mpClientLis;
    }

    public MpClientLis getMpClientLis() {
        return mpClientLis;
    }
}
