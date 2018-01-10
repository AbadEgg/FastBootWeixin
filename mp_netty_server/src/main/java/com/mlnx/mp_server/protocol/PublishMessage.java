package com.mlnx.mp_server.protocol;


import com.mlnx.mptp.mptp.body.Body;
import com.mlnx.mptp.utils.TopicUtils;
import com.mlnx.mptp.utils.TopicUtils.DeviceTopic;

public class PublishMessage extends AbstractMessage {

	private String deviceId;
	private Body body;
	private TopicUtils.DeviceTopic deviceTopic;

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

	public DeviceTopic getDeviceTopic() {
		return deviceTopic;
	}

	public void setDeviceTopic(DeviceTopic deviceTopic) {
		this.deviceTopic = deviceTopic;
	}

}
