package com.mlnx.mptp.model;

import java.util.Arrays;

public class ECGData {
	private Integer ecgHeart;

	private Integer accelerationSensorData;

	private Integer breath;

	private byte[] successionData;

	public Integer getEcgHeart() {
		return ecgHeart;
	}

	public void setEcgHeart(Integer ecgHeart) {
		this.ecgHeart = ecgHeart;
	}

	public Integer getAccelerationSensorData() {
		return accelerationSensorData;
	}

	public void setAccelerationSensorData(Integer accelerationSensorData) {
		this.accelerationSensorData = accelerationSensorData;
	}

	public Integer getBreath() {
		return breath;
	}

	public void setBreath(Integer breath) {
		this.breath = breath;
	}

	public byte[] getSuccessionData() {
		return successionData;
	}

	public void setSuccessionData(byte[] successionData) {
		this.successionData = successionData;
	}

	@Override
	public String toString() {
		return "ECGData [ecgHeart=" + ecgHeart + ", accelerationSensorData="
				+ accelerationSensorData + ", breath=" + breath
				+ ", successionData=" + Arrays.toString(successionData) + "]";
	}

}
