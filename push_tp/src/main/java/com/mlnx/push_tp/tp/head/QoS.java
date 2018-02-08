package com.mlnx.push_tp.tp.head;


public enum QoS {

	MOST_ONE(0x02, "QOS 等级1"), LEAST_ONE(0x04, "QOS 等级2");

	private int code;

	private String description;

	private static final int BITMASK = 0x06;

	private QoS(int code, String description) {
		this.code = code;
		this.description = description;
	}

}
