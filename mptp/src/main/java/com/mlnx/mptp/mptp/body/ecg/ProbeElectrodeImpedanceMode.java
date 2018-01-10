package com.mlnx.mptp.mptp.body.ecg;

import com.mlnx.mptp.mptp.InvalidPacketException;

public enum ProbeElectrodeImpedanceMode {

	NORMAL(0, "电极阻抗正常"), INFINITY(1, "电极断开，阻抗无穷大");

	private int code;
	private String description;

	private ProbeElectrodeImpedanceMode(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public static ProbeElectrodeImpedanceMode decode(int code)
			throws InvalidPacketException {
		ProbeElectrodeImpedanceMode[] probeElectrodeImpedanceMode = ProbeElectrodeImpedanceMode
				.values();
		for (int i = 0; i < probeElectrodeImpedanceMode.length; i++) {
			if (code == probeElectrodeImpedanceMode[i].code)
				return probeElectrodeImpedanceMode[i];
		}
		throw new InvalidPacketException("设备模式不符合要求，收到的模式编码是：" + code);
	}

}
