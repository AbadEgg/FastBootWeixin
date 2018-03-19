package com.mlnx.mp_session.listenner.temp;

import com.mlnx.mp_session.domain.TempInfo;
import com.mlnx.mp_session.listenner.DeviceListenner;
import com.mlnx.mptp.mptp.body.Topic;

import java.util.List;

/**
 * @author fzh
 * @create 2018/3/19 11:31
 */
public interface TempListener extends DeviceListenner{

    /**
     * 接收体温数据
     * @param topics
     * @param tempInfo
     */
    public void receiveTemp(List<Topic> topics, TempInfo tempInfo);
}
