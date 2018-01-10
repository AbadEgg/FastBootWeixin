package com.mlnx.mptp.model;

import com.mlnx.mptp.mptp.body.bp.DeviceRunMode;

public class BpDeviceInfo extends DeviceInfo {
	protected DeviceRunMode deviceRunMode;
	protected Integer dynamicMeasureInterval;
	protected Integer currentPressure;
	protected Integer insertSpo;

	protected Integer bpControl;

	/**
	 * 判断是否有设备信息
	 * 
	 * @return
	 */
	public boolean deviceInfoOK() {
		return deviceRunMode != null || dynamicMeasureInterval != null
				|| currentPressure != null || insertSpo != null;
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

	public Integer getBpControl() {
		return bpControl;
	}

	public void setBpControl(Integer bpControl) {
		if (this.bpControl == null) {
			this.bpControl = bpControl;
		} else {
			this.bpControl |= bpControl;
		}

	}

	@Override
	public String toString() {
		return "BpDeviceInfo [deviceRunMode=" + deviceRunMode
				+ ", dynamicMeasureInterval=" + dynamicMeasureInterval
				+ ", currentPressure=" + currentPressure + ", insertSpo="
				+ insertSpo + ", bpControl=" + bpControl + "]";
	}

}
