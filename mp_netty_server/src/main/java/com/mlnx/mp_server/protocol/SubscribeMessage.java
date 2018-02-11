package com.mlnx.mp_server.protocol;

import com.mlnx.mptp.push.body.SerialType;

public class SubscribeMessage extends AbstractMessage {

	private String topic;

	private SerialType pushSerialType;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public SerialType getPushSerialType() {
		return pushSerialType;
	}

	public void setPushSerialType(SerialType pushSerialType) {
		this.pushSerialType = pushSerialType;
	}
}
