package com.mlnx.mptp.mptp.body;

public enum DataType {

	PACKET_DATA, BP_DATA, SPO_DATA, ECG_DATA, CONFIG_DATA;

	public static DataType decode(int code) {
		byte b = (byte) (code >> 8 & 0xff);
		if (b == 0x01 || b == 0x02)
			return BP_DATA;
		else if (b == 0x04)
			return SPO_DATA;
		else if (b == 0x05 || b == 0x06)
			return ECG_DATA;
		else if (b == 0x07)
			return CONFIG_DATA;
		else
			return PACKET_DATA;
	}
}
