package com.mlnx.push_tp.tp.head;


import com.mlnx.push_tp.utils.PushLogUtils;

public enum DeviceType {

	SERVER(0X00, "服务器设备"), USR(0X01, "用户"), DEVICE(0X02, "用户");

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
				PushLogUtils.mpDecode("recive deviceType:" + deviceType);
				return deviceType;
			}
		}
		return null;
	}

}
