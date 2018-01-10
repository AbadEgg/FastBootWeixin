package com.mlnx.mptp.model;

import com.mlnx.mptp.mptp.body.bp.DeviceRunMode;

public class WifiBpDeviceInfo extends BpDeviceInfo{

	private Integer resultSPO;
	private Integer resultHeart;

	public WifiBpDeviceInfo setData(BpDeviceInfo bpDeviceInfo, SPOResult spoResult) {
		deviceRunMode = bpDeviceInfo.getDeviceRunMode();
		dynamicMeasureInterval = bpDeviceInfo.getDynamicMeasureInterval();
		currentBat = bpDeviceInfo.getCurrentBat();
		currentPressure = bpDeviceInfo.getCurrentPressure();
		insertSpo = bpDeviceInfo.getInsertSpo();
		deviceId = bpDeviceInfo.getDeviceId();

		resultSPO = spoResult.getResultSPO();
		resultHeart = spoResult.getResultHeart();

		return this;
	}

	public DeviceRunMode getDeviceRunMode() {
		return deviceRunMode;
	}

	public void setDeviceRunMode(DeviceRunMode deviceRunMode) {
		this.deviceRunMode = deviceRunMode;
	}

	public Integer getDynamicMeasureInterval() {
		return dynamicMeasureInterval;
	}

	public void setDynamicMeasureInterval(Integer dynamicMeasureInterval) {
		this.dynamicMeasureInterval = dynamicMeasureInterval;
	}

	public Integer getCurrentBat() {
		return currentBat;
	}

	public void setCurrentBat(Integer currentBat) {
		this.currentBat = currentBat;
	}

	public Integer getCurrentPressure() {
		return currentPressure;
	}

	public void setCurrentPressure(Integer currentPressure) {
		this.currentPressure = currentPressure;
	}

	public Integer getInsertSpo() {
		return insertSpo;
	}

	public void setInsertSpo(Integer insertSpo) {
		this.insertSpo = insertSpo;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getResultSPO() {
		return resultSPO;
	}

	public void setResultSPO(Integer resultSPO) {
		this.resultSPO = resultSPO;
	}

	public Integer getResultHeart() {
		return resultHeart;
	}

	public void setResultHeart(Integer resultHeart) {
		this.resultHeart = resultHeart;
	}

	@Override
	public String toString() {
		return "WifiBpDeviceInfo [deviceRunMode=" + deviceRunMode + ", dynamicMeasureInterval=" + dynamicMeasureInterval
				+ ", currentBat=" + currentBat + ", currentPressure=" + currentPressure + ", insertSpo=" + insertSpo
				+ ", deviceId=" + deviceId + ", resultSPO=" + resultSPO + ", resultHeart=" + resultHeart + "]";
	}

}
