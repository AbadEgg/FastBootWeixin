package com.mlnx.mptp.mptp.body;

public enum GroupType {
	RESPONSE_CODE(0X0000), DEVICE_TIME(0x0001), DEVICE_ID(0x0002), COMMAND(
			0x0003), TOPIC(0x0004), DEVICE_STATE(0x0005), KEEP_ALIVE_TIMER(
			0x0006), MESSAGE_ID(0x0007), USR_NAME(0x0008), PASSOWRD(0x0009),

	BP_DEVICE_MODE(0x0101), DYNAMIC_MEASURE_INTERVAL(0x0102), CURRENT_PRESSURE(
			0x0103), BP_BAT(0x0104), INSERT_SPO(0x0105), BP_AO_CONTROL(0x0106),

	SBP(0x0201), DBP(0x0202), HEART(0x0203), CREDIBILITY(0x0204), WEAR(0x0205),

	PATIENT_ID(0x0301), TEMP(0x0302),

	SPO(0X0401), SPO_HEART(0X0402), SPO_WAVE(0X0403),

	ECG_DEVICE_MODE(0X0501), ECG_CHANNEL_TYPE(0X0502), PACKET_INTERVAL(0X0503), BATTERY_LEVEL(
			0X0504), SIGNAL_STRENGTH(0X0505), SD_REMAIN(0X0506), MAGNIFICATION(
			0X0507), SAMPLING(0X0508), PROBE_CHANNEL_BIAS(0X0509), PROBE_ELECTRODE_IMPEDANCE(
			0X050a), WEAR_MODE(0X050b), SD_CAPACITY(0X050c),

	ECG_HEART(0X0601), ACCELERATION_SENSOR_DATA(0X0602), BREATH(0X0603), ENCRY_SUCCESSION_DATA(
			0X0604),SUCCESSION_DATA(0X0605),

	WIFI_SSID(0x0701), WIFI_PASSWORD(0x0702), WIFI_CHANNEL(
			0x0703), SERVER_IP(0x0704), SERVER_PORT(0x0705), HEART_CHANNEL(0x0706);

	private int code;
	private byte[] encodes;

	private GroupType(int code) {
		this.code = code;
		encodes = new byte[2];
		encodes[1] = (byte) (code & 0xff);
		encodes[0] = (byte) (code >> 8 & 0xff);
	}

	public byte[] getEncodes() {
		return encodes;
	}

	public int getCode() {
		return code;
	}

	public static GroupType decode(int code) {
		GroupType[] groupTypes = GroupType.values();
		for (int i = 0; i < groupTypes.length; i++) {
			if (code == groupTypes[i].code)
				return groupTypes[i];
		}
		return null;
	}

}
