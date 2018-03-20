package com.mlnx.mp_session.listenner.adapter;

import com.mlnx.mp_session.domain.EcgInfo;
import com.mlnx.mp_session.listenner.ecg.EcgListener;
import com.mlnx.mptp.mptp.body.Topic;

import java.util.List;

public class UserEcgAdapter implements EcgListener {

	@Override
	public void deviceOnline(Topic topic, String deviceId, Integer patientId) {

	}

	@Override
	public void deviceOffline(Topic topic, String deviceId, Integer patientId) {

	}

	@Override
	public void reciveEcgInfo(List<Topic> topics, EcgInfo ecgInfo) {

	}

	@Override
	public void startEcgPacket(Integer patientId) {

	}

	@Override
	public void stopEcgPacket(Integer patientId) {

	}
}
