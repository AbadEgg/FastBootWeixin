package com.mlnx.mptp.mptp.body.ecg;

import com.mlnx.mptp.mptp.InvalidPacketException;

public enum ProbeChannelBiasMode {

	NORMAL(0, "正常"), HIGHER(1, "过高");

	private int code;
	private String description;

	private ProbeChannelBiasMode(int code, String description) {
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

	public static ProbeChannelBiasMode decode(int code)
			throws InvalidPacketException {
		ProbeChannelBiasMode[] probeChannelBiasMode = ProbeChannelBiasMode
				.values();
		for (int i = 0; i < probeChannelBiasMode.length; i++) {
			if (code == probeChannelBiasMode[i].code)
				return probeChannelBiasMode[i];
		}
		throw new InvalidPacketException("设备模式不符合要求，收到的模式编码是：" + code);
	}
}
