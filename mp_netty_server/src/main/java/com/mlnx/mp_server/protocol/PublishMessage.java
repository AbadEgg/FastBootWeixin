package com.mlnx.mp_server.protocol;


import com.mlnx.mptp.mptp.body.Body;
import com.mlnx.mptp.mptp.body.Topic;

public class PublishMessage extends AbstractMessage {

	private String deviceId;
	private Body body;
	private Topic topic ;

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
}
