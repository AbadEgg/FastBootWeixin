package com.mlnx.mp_session.listenner.spo;

import com.mlnx.mp_session.listenner.DeviceListenner;
import com.mlnx.mptp.mptp.body.Body;
import com.mlnx.mptp.mptp.body.Topic;

public interface SpoListener extends DeviceListenner {
	/**
	 * 收到血氧body
	 * 
	 * @param topic
	 * @param body
	 */
	public void reciveSpoBody(Topic topic, Body body);
}
