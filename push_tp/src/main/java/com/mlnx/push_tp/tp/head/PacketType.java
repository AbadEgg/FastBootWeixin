package com.mlnx.push_tp.tp.head;


public enum PacketType {

	REGISTER(0x10, "请求注册"), Reg_ACK(0x20, "注册应答"), PUBLISH(0x30, "发布消息"), PUBLISH_ACK(0x40, "发布应答"), SUBSCRIBE(0x80,
			"订阅请求"), SUB_ACK(0x90, "订阅应答"), UNSUBSCRIBE(0xa0,
					"取消订阅"), UNSUB_ACK(0xb0, "取订应答"), PINGREQ(0xc0, "Ping请求"), PINGRESP(0xd0, "Ping响应");

	private int code;
	private String description;

	private static final int BITMASK = 0xF0;

	private PacketType(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
