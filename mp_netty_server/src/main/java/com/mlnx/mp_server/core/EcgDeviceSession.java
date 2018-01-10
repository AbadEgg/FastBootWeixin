package com.mlnx.mp_server.core;

import com.mlnx.mp_server.listenner.BroadCast;

public class EcgDeviceSession extends DeviceSession {
	private Integer numChannels;

	private Integer samplingRate;

	private Integer amplification;

	private Integer pose;

	private Long lastEcgDataTime;

	private boolean fristEcgPacket = true;

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

	public Integer getNumChannels() {
		return numChannels;
	}

	public void setNumChannels(Integer numChannels) {
		this.numChannels = numChannels;
	}

	public Integer getSamplingRate() {
		return samplingRate;
	}

	public void setSamplingRate(Integer samplingRate) {
		this.samplingRate = samplingRate;
	}

	public Integer getAmplification() {
		return amplification;
	}

	public void setAmplification(Integer amplification) {
		this.amplification = amplification;
	}

	public Integer getPose() {
		return pose;
	}

	public void setPose(Integer pose) {
		this.pose = pose;
	}

	@Override
	public void removeLis() {
		super.removeLis();

		if (!fristEcgPacket)
			BroadCast.ecgBroadCast.stopEcgPacket(patientId);
	}
}
