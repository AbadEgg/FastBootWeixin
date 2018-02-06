package com.mlnx.mp_server.listenner.adapter;

import com.mlnx.analysis.domain.ReadEcgAnalysResult;
import com.mlnx.mp_server.listenner.EcgListenner;
import com.mlnx.mptp.model.Ecg;
import com.mlnx.mptp.mptp.body.Topic;

public class UsrEcgAdapter implements EcgListenner {

	@Override
	public void reciveEcgBody(Topic topic, Ecg ecg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reciveReadEcgAnalysResult(Topic topic, ReadEcgAnalysResult result) {

	}

	@Override
	public void startEcgPacket(Integer patientId) {

	}

	@Override
	public void stopEcgPacket(Integer patientId) {

	}

	@Override
	public void deviceOnline(Topic topic, String deviceId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deviceOfflien(Topic topic, String deviceId) {
		// TODO Auto-generated method stub

	}

}
