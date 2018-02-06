package com.mlnx.mp_server.listenner;


import com.mlnx.mptp.mptp.body.Topic;

public interface DeviceListenner {

	public void deviceOnline(Topic topic, String deviceId);

	public void deviceOfflien(Topic topic, String deviceId);
}