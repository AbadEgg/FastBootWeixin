package com.mlnx.mp_server.listenner.adapter;

import com.mlnx.mp_server.listenner.EcgListenner;
import com.mlnx.mptp.model.Ecg;
import com.mlnx.mptp.utils.TopicUtils;

public class UsrEcgAdapter implements EcgListenner {

	@Override
	public void reciveEcgBody(TopicUtils.DeviceTopic topic, Ecg ecg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startEcgPacket(Integer patientId) {

	}

	@Override
	public void stopEcgPacket(Integer patientId) {

	}

	@Override
	public void deviceOnline(TopicUtils.DeviceTopic topic, String deviceId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deviceOfflien(TopicUtils.DeviceTopic topic, String deviceId) {
		// TODO Auto-generated method stub

	}

}
