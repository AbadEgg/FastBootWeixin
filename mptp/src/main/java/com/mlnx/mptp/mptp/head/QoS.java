package com.mlnx.mptp.mptp.head;

import com.mlnx.mptp.utils.MptpLogUtils;
import com.mlnx.mptp.mptp.InvalidPacketException;

public enum QoS {

	MOST_ONE(0x02, "QOS 等级1"), LEAST_ONE(0x04, "QOS 等级2");

	private int code;

	private String description;

	private static final int BITMASK = 0x06;

	private QoS(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public static int encode(int code, QoS qoS) {
		code &= ~BITMASK;
		code |= qoS.code;
		return code;
	}

	public static QoS decode(int qoSCode) throws InvalidPacketException {

		int code = qoSCode & BITMASK;
		for (QoS qoS : QoS.values()) {
			if (qoS.code == code) {
				MptpLogUtils.mpDecode("recive QoS:" + qoS);
				return qoS;
			}
		}
		throw new InvalidPacketException(String.format("无效的QoS：0x%x", code));
	}

}
