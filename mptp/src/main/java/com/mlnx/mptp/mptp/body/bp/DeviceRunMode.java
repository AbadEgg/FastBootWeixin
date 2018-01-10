package com.mlnx.mptp.mptp.body.bp;

import com.mlnx.mptp.mptp.InvalidPacketException;

public enum DeviceRunMode {

	ONCE_MODE(0), CONTINUE_MODE(1), QUALITY_MODE(2);

	private int code;

	private DeviceRunMode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static DeviceRunMode decode(int code) throws InvalidPacketException {
		DeviceRunMode[] deviceRunModes = DeviceRunMode.values();
		for (int i = 0; i < deviceRunModes.length; i++) {
			if (code == deviceRunModes[i].code)
				return deviceRunModes[i];
		}
		throw new InvalidPacketException("设备模式不符合要求，收到的模式编码是：" + code);
	}
}
