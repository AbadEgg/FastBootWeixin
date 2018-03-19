package com.mlnx.listener;

import com.mlnx.mp_session.domain.BpInfo;
import com.mlnx.mp_session.domain.EcgInfo;
import com.mlnx.mp_session.domain.SpoInfo;
import com.mlnx.mp_session.domain.TempInfo;

/**
 * Created by amanda.shan on 2018/3/2.
 */
public interface MsgListener {

    void reciveEcgInfo(EcgInfo ecgInfo);

    void reciveBpInfo(BpInfo bpInfo);

    void reciveSpoInfo(SpoInfo spoInfo);

    void receiveTempInfo(TempInfo tempInfo);
}
