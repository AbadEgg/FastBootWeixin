package com.mlnx.listener;

import com.mlnx.mp_session.domain.*;

/**
 * Created by amanda.shan on 2018/3/2.
 */
public interface MsgListener {

    void receiveEcgInfo(EcgInfo ecgInfo);

    void receiveBpInfo(BpInfo bpInfo);

    void receiveSpoInfo(SpoInfo spoInfo);

    void receiveTempInfo(TempInfo tempInfo);
    
    void receiveCO2Info(CO2Info co2Info);
}
