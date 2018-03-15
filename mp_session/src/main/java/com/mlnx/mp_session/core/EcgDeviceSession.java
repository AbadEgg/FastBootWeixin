package com.mlnx.mp_session.core;

import com.mlnx.analysis.EcgAnalysis;
import com.mlnx.mp_session.domain.EcgInfo;
import com.mlnx.mp_session.listenner.BroadCast;
import com.mlnx.mptp.model.ECGData;
import com.mlnx.mptp.model.ECGDeviceInfo;
import com.mlnx.mptp.model.analysis.RealEcgAnalysResult;

import java.io.IOException;

public class EcgDeviceSession extends DeviceSession {

	private Long lastEcgDataTime;

	private boolean fristEcgPacket = true;

	private EcgAnalysis analysis;	// 分析功能

	private EcgInfo ecgInfo;

	public EcgDeviceSession(String deviceId, byte[] gpu8AcId) throws IOException {
		super(deviceId);

		analysis = new EcgAnalysis(deviceId, gpu8AcId);
		analysis.init();

		ecgInfo = new EcgInfo();
		ecgInfo.setRealEcgAnalysResult(new RealEcgAnalysResult());
		ecgInfo.setEcgDeviceInfo(new ECGDeviceInfo());
		ecgInfo.setEcgData(new ECGData());
	}

	public EcgAnalysis getAnalysis() {
		return analysis;
	}

	public Long getLastEcgDataTime() {
		return lastEcgDataTime;
	}

	public void setLastEcgDataTime(Long lastEcgDataTime) {
		this.lastEcgDataTime = lastEcgDataTime;
	}

	public boolean isFristEcgPacket() {
		return fristEcgPacket;
	}

	public void setFristEcgPacket(boolean fristEcgPacket) {
		this.fristEcgPacket = fristEcgPacket;
	}

	public EcgInfo getEcgInfo() {
		return ecgInfo;
	}

	public void setEcgInfo(EcgInfo ecgInfo) {
		this.ecgInfo = ecgInfo;
	}

	@Override
	public void removeLis() {
		super.removeLis();

		if (!fristEcgPacket) {
			BroadCast.ecgBroadCast.stopEcgPacket(patientId);
		}
	}
}
