package com.mlnx.mp_server.listenner;


import com.mlnx.mptp.utils.TopicUtils;
import com.mlnx.mptp.utils.TopicUtils.DeviceTopic;

public interface DeviceListenner {

	public void deviceOnline(TopicUtils.DeviceTopic topic, String deviceId);

	public void deviceOfflien(DeviceTopic topic, String deviceId);
}