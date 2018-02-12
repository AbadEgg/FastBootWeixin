package com.mlnx.mp_session.listenner.spo;

import com.mlnx.mp_session.domain.SpoInfo;
import com.mlnx.mp_session.listenner.DeviceListenner;
import com.mlnx.mptp.mptp.body.Topic;

import java.util.List;

public interface SpoListener extends DeviceListenner {
	/**
	 * 收到血氧body
	 * 
	 * @param topics
	 * @param spoInfo
	 */
	public void reciveSpoInfo(List<Topic> topics, SpoInfo spoInfo);
}
