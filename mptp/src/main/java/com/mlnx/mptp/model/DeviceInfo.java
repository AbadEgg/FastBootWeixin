package com.mlnx.mptp.model;

public class DeviceInfo {

	protected Integer currentBat; // 电池电量值 (百分数 0 10 20 ... 100)
	protected String deviceId;

	public Integer getCurrentBat() {
		return currentBat;
	}

	public void setCurrentBat(Integer currentBat) {
		this.currentBat = currentBat;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
