package com.mlnx.mp_session.listenner.co2;

import com.mlnx.mp_session.domain.CO2Info;
import com.mlnx.mp_session.listenner.DeviceListenner;
import com.mlnx.mptp.mptp.body.Topic;

import java.util.List;

/**
 * @author fzh
 * @create 2018/3/20 13:56
 */
public interface CO2Listener extends DeviceListenner {

    public void receiveCO2(List<Topic> topics, CO2Info co2Info);
}
