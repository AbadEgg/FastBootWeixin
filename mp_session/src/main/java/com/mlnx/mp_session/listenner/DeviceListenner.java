package com.mlnx.mp_session.listenner;


import com.mlnx.mptp.mptp.body.Topic;

public interface DeviceListenner {

	public void deviceOnline(Topic topic, String deviceId, Integer patientId);

	public void deviceOfflien(Topic topic, String deviceId, Integer patientId);
}