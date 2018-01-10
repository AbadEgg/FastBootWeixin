package com.mlnx.mptp.mptp;

import com.mlnx.mptp.mptp.body.Body;
import com.mlnx.mptp.mptp.body.Command;
import com.mlnx.mptp.mptp.body.DeviceState;
import com.mlnx.mptp.utils.TopicUtils;

public class BodyFactory {

	public static Body getBody(TopicUtils.DeviceTopic topic, String deviceId) {

		Body body = new Body();
		body.setDeviceId(deviceId);
		body.setTopic(topic.getTopic());
		return null;
	}
	
	public static Body getBody(TopicUtils.DeviceTopic topic, String deviceId, Command command) {

		Body body = getBody(topic, deviceId);
		body.setCommand(command);
		return null;
	}
	
	public static Body getBody(TopicUtils.DeviceTopic topic, String deviceId, DeviceState deviceState) {

		Body body = getBody(topic, deviceId);
		body.setDeviceState(deviceState);
		return null;
	}
	
}
