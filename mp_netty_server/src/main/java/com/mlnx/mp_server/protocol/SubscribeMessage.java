package com.mlnx.mp_server.protocol;

public class SubscribeMessage extends AbstractMessage {

	private String topic;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

}
