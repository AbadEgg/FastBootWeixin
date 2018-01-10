package com.mlnx.mptp.mptp.body.ecg;

import com.mlnx.mptp.mptp.InvalidPacketException;

public enum WearMode {

	LEFT_ARM(0x01), RIGHT_ARM(0x02), WAIST_FRONT(0x03), WAIST_BACK(0x04), CHEST(
			0x05), CUSTOMIZED(0xFD), UNKNOWN(0xFE);

	private int code;

	private WearMode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static WearMode decode(int code) throws InvalidPacketException {
		WearMode[] wearModes = WearMode.values();
		for (int i = 0; i < wearModes.length; i++) {
			if (code == wearModes[i].code)
				return wearModes[i];
		}
		throw new InvalidPacketException("设备模式不符合要求，收到的模式编码是：" + code);
	}
}
