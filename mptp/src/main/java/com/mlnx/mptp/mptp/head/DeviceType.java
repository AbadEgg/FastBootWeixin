package com.mlnx.mptp.mptp.head;

import com.mlnx.mptp.utils.MptpLogUtils;

public enum DeviceType {

	SERVER(0X00, "服务器设备"), USR(0X01, "用户"), BP_DEVICE(0X02, "血压设备"), SBP_DEVICE(
			0X03, "带血氧的血压设备"), ECG_DEVICE(0X04, "心电设备"), MP_DEVICE(0X05, "多参设备");

	private int code;

	private String description;

	private static final int BITMASK = 0xFF;

	DeviceType(int code, String description) {

		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public int getCode() {
		return code;
	}

	public static DeviceType decode(int deviceCode) {

		int code = deviceCode & BITMASK;
		for (DeviceType deviceType : DeviceType.values()) {
			if (deviceType.code == code) {
				MptpLogUtils.mpDecode("recive deviceType:" + deviceType);
				return deviceType;
			}
		}
		return null;
	}

}
