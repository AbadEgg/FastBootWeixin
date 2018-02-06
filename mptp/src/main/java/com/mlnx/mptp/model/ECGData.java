package com.mlnx.mptp.model;

import java.util.Arrays;

public class ECGData {
	private Integer ecgHeart;	// 心率

	private Integer asd;	// 加速度传感器数据

	private Integer breath;	// 呼吸

	private byte[] encrySuccessionData;	//加密心电数据

	private byte[] successionData;	//	未加密心电数据

	public Integer getEcgHeart() {
		return ecgHeart;
	}

	public void setEcgHeart(Integer ecgHeart) {
		this.ecgHeart = ecgHeart;
	}

	public Integer getAsd() {
		return asd;
	}

	public void setAsd(Integer asd) {
		this.asd = asd;
	}

	public Integer getBreath() {
		return breath;
	}

	public void setBreath(Integer breath) {
		this.breath = breath;
	}

	public byte[] getEncrySuccessionData() {
		return encrySuccessionData;
	}

	public void setEncrySuccessionData(byte[] encrySuccessionData) {
		this.encrySuccessionData = encrySuccessionData;
	}

	public byte[] getSuccessionData() {
		return successionData;
	}

	public void setSuccessionData(byte[] successionData) {
		this.successionData = successionData;
	}

	@Override
	public String toString() {
		return "ECGData [ecgHeart=" + ecgHeart + ", asd="
				+ asd + ", breath=" + breath
				+ ", successionData=" + Arrays.toString(successionData) + "]";
	}

}
